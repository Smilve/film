<template>
  <div class="movie-container">
    <div class="header">
      <div class="header-inner clearfix">
        <div class="movie-info-left">
          <div class="avatar-shadow">
            <img class="avatar" :src="this.global.base+this.movieInfo.photo" />
          </div>
        </div>
        <div class="movie-info-right">
          <div class="movie-info-msg">
            <h1 class="movie-name">{{movieInfo.movieName}}</h1>
            <!-- <div class="movie-ename">{{movieInfo.movieNameEn}}</div> -->
            <ul>
              <li>{{movieInfo.type}}</li>
              <li>{{movieInfo.nation}} / {{movieInfo.time}}分钟</li>
              <li>{{movieInfo.date}} {{movieInfo.nation}}上映</li>
            </ul>
            <div v-show="this.will==1?false:true" class="like-to-look">
              <el-button class="like-to-look-btn">想看</el-button>
              <el-button class="like-to-look-btn" @click="commetDialogVisible = true">评分</el-button>
            </div>
          </div>

          <div class="movie-info-btn" v-show="this.will==1?false:true">
            <el-button class="buy-btn" type="primary" @click="toCinema">特惠购票</el-button>
          </div>
          <div class="movie-info-score">
            <div class="movie-index" v-show="this.will==1?false:true">
              <span class="movie-index-title">点映评分</span>
              <div style="display: flex">
                <span class="movie-score-num">{{this.scoreInfo.score}}</span>
                <div class="score-container">
                  <el-rate :value="this.scoreInfo.score/2" disabled text-color="#ff9900"></el-rate>
                  <span>{{this.scoreInfo.num}} 人评分</span>
                </div>
              </div>
            </div>
            <div class="movie-index box-office-container">
              <span class="movie-index-title">累计票房</span>
              <div style="display: flex;align-items: flex-end;">
                <span class="box-office">{{this.scoreInfo.price==null?0:this.scoreInfo.price}}</span>
                <span class="unit">元</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="movie-info-detail-container">
      <div class="movie-info-detail clearfix">
        <div class="main-content">
          <div class="crumbs-nav-container">
            <a href="/welcome">橘子电影</a> &gt;
            <a href="/movie">电影</a>
            &gt; {{movieInfo.movieName}}
          </div>

          <div class="tab-body">
            <div class="module">
              <div class="mod-title">
                <h2>剧情简介</h2>
              </div>
              <div class="mod-content">
                <span class="dra">{{movieInfo.intro}}</span>
              </div>
            </div>
            <div class="module">
              <div class="mod-title">
                <h2>演职人员</h2>
              </div>
              <div class="mod-content">
                <div>导演：{{this.movieInfo.director}}</div>
                <div>演员：{{this.movieInfo.actor}}</div>
              </div>
            </div>

            <div class="module" v-show="this.will==1?false:true">
              <div class="mod-title">
                <h2>热门短评</h2>
                <el-button
                  @click="commetDialogVisible = true"
                  class="btn-commit"
                  size="medium"
                  plain
                  round
                >写短评</el-button>
              </div>
              <div class="mod-content">
                <div class="commet-container" v-for="(item,index) in commetList" :key="index">
                  <div>
                    <el-avatar :src="global.base+item.photo" class="user-avatar"></el-avatar>
                  </div>
                  <div class="commet-main">
                    <div class="main-header">
                      <div class="user-name">{{item.userName}}</div>
                      <div class="commet-date">
                        {{item.date}}
                        <el-rate
                          style="margin-left: 10px;"
                          :value="item.score/2"
                          allow-half
                          disabled
                        ></el-rate>
                      </div>
                    </div>
                    <div class="commet-content">{{item.content}}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog :visible.sync="commetDialogVisible" width="40%" @close="commetDialogClosed">
      <el-form
        class="write-commet-dialog"
        :model="commetInfo"
        :rules="commetFormRules"
        ref="commetForm"
        label-width="0"
      >
        <el-form-item class="commet-score-container">
          <span class="movie-score-num">{{ (commetInfo.score * 2)}} 分</span>
        </el-form-item>
        <el-form-item>
          <el-rate
            :colors="colors"
            v-model="commetInfo.score"
            allow-half
            text-color="#F7BA2A"
            show-text
          ></el-rate>
        </el-form-item>
        <el-form-item style="width: 80%" prop="content">
          <el-input
            type="textarea"
            :rows="5"
            style="font-size: 16px;"
            placeholder="快来说说你的想法吧"
            v-model="commetInfo.content"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button class="yellow" type="primary" @click="submitCommet">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import movieItem from "./../../components/movie/movie-item";
import moment from "moment";
export default {
  name: "MovieInfo",
  components: {
    movieItem,
  },
  data() {
    return {
      baseUrl: "http://127.0.0.1:8080",
      movieInfo: {},
      scoreInfo: {},
      movieId: this.$route.params.movieId,
      will: this.$route.params.will,
      commetDialogVisible: false,
      commetInfo: {
        score: 0,
        content: "",
        userId: 0,
        movieId: 0,
        date: null,
      },
      commetList: [],
      commetFormRules: {
        content: [
          {
            required: true,
            message: "请输入评论内容",
            trigger: ["blur"],
          },
        ],
      },
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
    };
  },
  created() {
    this.getMovieInfo();
    this.getCommetList();
    this.getAvgScore();
  },
  methods: {
    async getMovieInfo() {
      const { data: res } = await axios.get("/movie/movie/" + this.movieId);
      if (res.code !== 200) return this.$message.error("电影信息查询失败");
      this.movieInfo = res.data;
    },
    async getCommetList() {
      const { data: res } = await axios.get(
        "/commet/commetByMovieId/" + this.movieId
      );
      if (res.code !== 200) return this.$message.error("评论信息查询失败");
      this.commetList = res.data;
    },
    commetDialogClosed() {
      this.$refs.commetForm.resetFields();
    },
    //提交影评
    async submitCommet() {
      this.$refs.commetForm.validate(async (valid) => {
        if (!valid) return;
        if (this.commetInfo.score == 0) {
          this.$message.error("请评分！");
        } else {
          const commet = {
            score: this.commetInfo.score * 2,
            content: this.commetInfo.content,
            userId: JSON.parse(window.localStorage.user).userId,
            movieId: this.movieInfo.movieId,
          };
          this.commetDialogVisible = false;
          const { data: res } = await axios.post("/commet/add", commet);
          if (res.code !== 200) return this.$message.error("评论失败");
          this.$message.success("评论发布成功");
          this.commetInfo.score = 0;
          this.commetInfo.content = "";
          this.getCommetList();
          this.getAvgScore();
        }
      });
    },
    //获取电影评分
    async getAvgScore() {
      const { data: res } = await axios.get("/commet/score/" + this.movieId);
      if (res.code !== 200) return this.$message.error("获取评分失败");
      this.scoreInfo = res.data;
    },
    toCinema() {
      this.$store.commit("setMovieId", this.movieId);
      this.$router.push("/cinema");
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
  cursor: pointer;
}

.header {
  padding: 0;
  width: 100%;
  min-width: 1200px;
  background: url("../../assets/movie-background.jpg");
}

.header-inner {
  width: 1200px;
  margin: 0 auto;
  position: relative;
}

.clearfix::before,
.clearfix::after {
  content: " ";
  display: table;
}

.clearfix::after {
  clear: both;
}

.movie-info-left {
  width: 300px;
  height: 370px;
  float: left;
  position: relative;
  top: 70px;
  overflow: hidden;
  z-index: 9;
}

.avatar-shadow {
  position: relative;
  margin: 0 30px;
  width: 240px;
  height: 330px;
  padding-bottom: 40px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPAAAAAyAgMAAAAfG76+AAAADFBMVEUAAAAAAAAAAAAAAAA16TeWAAAABHRSTlMOAgoGQ0SIeAAAADpJREFUSMdjGAWjYBRgAasoAAwdFACKbB7VPEI076YAUGbzfwrAqOYRormcAjCANodSAEY1j2oexJoBlx1+yE7RXIIAAAAASUVORK5CYII=)
    no-repeat bottom;
}

.avatar {
  border: 4px solid #fff;
  height: 322px;
  width: 232px;
}

.movie-info-msg {
  position: absolute;
  color: #fff;
  font-size: 14px;
  z-index: 1;
}

.movie-name {
  width: 900px;
  margin-top: 0;
  font-size: 26px;
  line-height: 32px;
  font-weight: 700;
  margin-bottom: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  max-height: 64px;
}

.movie-ename {
  width: 340px;
  font-size: 18px;
  line-height: 1.3;
  margin-bottom: 14px;
}

ul {
  width: 250px;
  list-style: none;
  padding-left: 0;
  margin-bottom: 20px;
}

ul li {
  margin: 12px 0;
  line-height: 100%;
}

.like-to-look {
  margin-top: 80px;
}
.like-to-look-btn {
  /* background-color: rgba(0, 0, 0, 0.4); */
  opacity: 0.5;
  height: 36px;
  width: 120px;
  color: #ffc600;
}
.movie-info-btn {
  position: absolute;
  bottom: 20px;
}

.buy-btn {
  margin-top: 10px;
  width: 250px;
  height: 40px;
  font-size: 16px;
  text-align: center;
  background: #ffc600;
  border-color: #ffc600;
}

.movie-info-score {
  position: absolute;
  top: 145px;
  left: 342px;
}

.movie-index {
  margin-bottom: 16px;
  color: #fff;
}

.box-office-container {
  display: flex;
  flex-direction: column;
}

.movie-index-title {
  font-size: 12px;
  margin-bottom: 8px;
}

.movie-score-num {
  font-size: 30px;
  color: #ffc600;
  height: 30px;
  line-height: 30px;
  margin-right: 10px;
}

.score-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 12px;
}

.box-office {
  font-size: 30px;
}

.unit {
  font-size: 12px;
  padding-left: 1px;
  line-height: 24px;
}

.movie-info-right {
  height: 300px;
  position: relative;
  margin-right: 30px;
  margin-left: 300px;
  margin-top: 70px;
}

.movie-info-detail-container {
  width: 1200px;
  margin: 0 auto;
}

.movie-info-detail {
  margin-top: 80px;
}

.main-content {
  width: 730px;
  float: left;
  margin-bottom: 20px;
}

.crumbs-nav-container {
  margin-bottom: 25px;
  color: #333;
}

.crumbs-nav-container a {
  color: inherit;
}

.el-tabs >>> .el-tabs__item {
  font-size: 20px;
}

.tab-body {
  margin-top: 40px;
}

.module {
  position: relative;
  margin-bottom: 60px;
}

.mod-title h2 {
  display: inline-block;
  margin: 0;
  padding: 0;
  font-weight: 400;
  font-size: 18px;
  color: #333;
  line-height: 18px;
}

.mod-title h2:before {
  float: left;
  content: "";
  display: inline-block;
  width: 4px;
  height: 18px;
  margin-right: 6px;
  background-color: #ffc600;
}

.mod-content {
  margin-top: 20px;
  color: #333;
}

.mod-content .dra {
  font-size: 14px;
  line-height: 26px;
}

.more {
  float: right;
  cursor: pointer;
  font-size: 14px;
  color: #999;
  padding-right: 14px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAOCAYAAAASVl2WAAAABmJLR0QA/wD/AP+gvaeTAAAAv0lEQVQY013RTUpDQRAE4G8eghcR8ScgKCIugpJFjuIjqAvBc7jxj0muEnCjiIQQJOImB3GnbnpkfL1qpqqrunpSzvkDPxjhGdq2VarBF3q4wRHknP8RzvCEQzzguCalaHZwiwHecY6XogCf8TjFHh7Rh9Tx3AylIZa4TgWpSBuY4BSrYlFXKsr4bjrTW5HkJJa9SBW4jbtukmKxG5MDLOKqfzEPcB9LzQN8LSdfwxj7eMMlZvV/NFiPzFddEH4Bt5Y1mf3fnDwAAAAASUVORK5CYII=)
    no-repeat 100%;
}

.actors-container {
  font-size: 16px;
  color: #333;
  display: flex;
  justify-content: flex-start;
}

#pane-actors .actors-container {
  flex-direction: column;
}

#pane-actors .actors-container .actors-group {
  margin-left: 0;
  margin-bottom: 40px;
}

#pane-actors .actors-container .actors-group .actors-list {
  flex-wrap: wrap;
}

#pane-actors .actors-container .actors-group .actors-list .actor {
  margin-bottom: 20px;
}

.actors-group:first-child {
  margin-left: 0;
}

.actors-group {
  float: left;
  margin-left: 20px;
}

.actors-type {
  margin-bottom: 16px;
}

.actors-list {
  display: flex;
  margin-left: -20px;
  padding: 0;
}

.actor {
  float: left;
  width: 128px;
  margin-left: 20px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.portrait {
  margin-bottom: 6px;
  width: 128px;
  height: 170px;
  overflow: hidden;
}

.portrait .default-img {
  width: 128px;
  height: 170px;
}

.actor-name {
  margin-top: 8px;
  line-height: 1.2;
  color: #333;
  display: inline-block;
  width: 128px;
  text-align: center;
  padding-bottom: 1px;
  margin-bottom: -1px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.pictures-list {
  display: flex;
}

.pictures-list > .el-image:first-child {
  width: 465px;
  height: 258px;
}

.pictures-list .default-img {
  border-style: none;
  cursor: pointer;
  width: 126px;
  height: 126px;
}

.little-pictures {
  width: 262px;
  height: 262px;
  display: flex;
  flex-wrap: wrap;
  margin-left: 10px;
  justify-content: space-between;
}

#pane-pictures .little-pictures {
  width: 100%;
  justify-content: flex-start;
  margin-left: 0;
}

#pane-pictures .default-img {
  margin-top: 10px;
  margin-left: 10px;
}

.commet-container {
  display: flex;
  margin-top: 20px;
}

.user-avatar {
  width: 50px;
  height: 50px;
  margin-right: 20px;
}

.commet-main {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.main-header {
  font-size: 16px;
}

.user-name {
  margin-top: 2px;
}

.commet-date {
  margin-top: 4px;
  color: #999;
  display: flex;
}

.commet-content {
  margin-top: 20px;
  padding-bottom: 30px;
  border-bottom: 1px solid #e5e5e5;
  color: #666;
  line-height: 26px;
  font-size: 14px;
}

.write-commet-dialog {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.write-commet-dialog >>> i {
  font-size: 30px;
}

.commet-score-container {
  margin-bottom: 10px;
}

.btn-commit {
  float: right;
  border-color: #ffc600;
  color: #ffc600;
}
.btn-commit:hover {
  color: #fff;
  background: #ffc600;
  border-color: #ffc600;
}
.yellow {
  background: #ffc600;
  border-color: #ffc600;
}
</style>