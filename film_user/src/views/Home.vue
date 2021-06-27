<template>
  <div>
    <el-carousel :height="carouselHeight">
      <el-carousel-item v-for="item in photoList" :key="item.url">
        <img :src="item.url" alt />
      </el-carousel-item>
    </el-carousel>
    <div class="whole">
      <div class="left">
        <div class="panel">
          <div class="panel-header">
            <h2 style="color: #ef4238">正在热映 ({{queryIngMovie.total}}部)</h2>
            <a href="/movie/ingMovie">全部</a>
          </div>
          <div class="panel-content">
            <movie-item :movieItem="item" v-for="(item, index) in ingMovieList" :key="index"></movie-item>
          </div>
        </div>
        <div class="panel">
          <div class="panel-header">
            <h2 style="color: #2d98f3">即将上映 ({{queryWillMovie.total}}部)</h2>
            <a href="/movie/willMovie">全部</a>
          </div>
          <div class="panel-content">
            <div class="panel-content">
              <movie-item :movieItem="item" v-for="(item,index) in willMovieList" :key="index"></movie-item>
            </div>
          </div>
        </div>
        <div class="panel">
          <div class="panel-header">
            <h2 style="color: #ef4238">热播电影 ({{queryClassicMovie.total}}部)</h2>
            <a href="/movie/classicMovie">全部</a>
          </div>
          <div class="panel-content">
            <div class="panel-content">
              <movie-item :movieItem="item" v-for="(item,index) in classicMovieList" :key="index"></movie-item>
            </div>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="panel">
          <div class="panel-header">
            <h2 style="color: #ffb400">TOP 10</h2>
            <a href="/rank/topMovieList">查看完整榜单</a>
          </div>
          <div class="panel-content">
            <div class="board" v-for="(item, index) in topMovieList" :key="index">
              <div class="board-left">
                <i class="board-index">{{index+1}}</i>
              </div>
              <div class="board-middle">
                <a :href="'/movieInfo/' + item.movieId+'/0'">
                  <p class="name">{{ item.movieName }}</p>
                </a>
              </div>
              <div class="board-right">
                <!-- item.score.toFixed(1) -->
                <p>{{ item.score }} 分</p>
              </div>
            </div>
          </div>
        </div>
        <div class="panel" v-if="$store.state.user.isLogin">
          <div class="panel-header">
            <h2 style="color: #ffb400">猜你喜欢</h2>
          </div>
          <div class="panel-content">
            <div class="panel-content">
              <movie-item :movieItem="item" v-for="(item,index) in guessLikeMovieList" :key="index"></movie-item>
              <div v-if="this.guessLikeMovieList == null">请评论一些电影，系统会为您推荐一些电影。</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import movieItem from "../components/movie/movie-item";
import moment from "moment";
export default {
  name: "Home",
  components: {
    movieItem,
  },
  data() {
    return {
      queryIngMovie: {
        total: 0,
        pageSize: 4,
        pageIndex: 1,
      },
      queryWillMovie: {
        total: 0,
        pageSize: 4,
        pageIndex: 1,
        date: moment().format("YYYY-MM-DD"),
      },
      queryClassicMovie: {
        total: 0,
        pageSize: 4,
        pageIndex: 1,
        date: moment().subtract(150, "days").format("YYYY-MM-DD"),
      },
      queryTopMovie: {
        pageIndex: 1,
        pageSize: 10,
        date: moment().format("YYYY-MM-DD"),
      },

      photoList: [
        { url: require("../assets/zzx1.jpg") },
        { url: require("../assets/qe1.jpg") },
        { url: require("../assets/sdyjq.jpg") },
        { url: require("../assets/vcs1.jpg") },
      ],
      ingMovieList: [],
      willMovieList: [],
      classicMovieList: [],
      carouselHeight: "",
      topMovieList: [],
      guessLikeMovieList: [],
    };
  },
  created() {
    this.getIngMovieList();
    this.getWillMovieList();
    this.getClassicMovieList();
    this.getHeight();
    this.getTopMovieList();

    if (this.$store.state.user.isLogin) {
      this.getGuessLikeMovieList();
    }
  },
  methods: {
    async getIngMovieList() {
      const { data: res } = await axios.post(
        "/movie/ingMovie",
        this.queryIngMovie
      );
      this.ingMovieList = res.data.list;
      this.queryIngMovie.total = res.data.totalCount;
    },
    async getWillMovieList() {
      const { data: res } = await axios.post(
        "/movie/willMovie",
        this.queryWillMovie
      );
      this.willMovieList = res.data.list;
      this.queryWillMovie.total = res.data.totalCount;
    },
    async getClassicMovieList() {
      const { data: res } = await axios.post(
        "/movie/classicMovie",
        this.queryClassicMovie
      );
      this.classicMovieList = res.data.list;
      this.queryClassicMovie.total = res.data.totalCount;
    },
    getHeight() {
      let clientWidth = `${document.documentElement.clientWidth}`;
      clientWidth *= 0.8;
      this.carouselHeight = (clientWidth / 1700) * 520 + "px";
    },
    async getTopMovieList() {
      const { data: res } = await axios.post(
        "/movie/topMovie",
        this.queryTopMovie
      );
      this.topMovieList = res.data.list;
    },
    //推荐电影
    async getGuessLikeMovieList() {
      let id = JSON.parse(localStorage.user).userId;
      const { data: res } = await axios.get("/movie/recommend/" + id);
      if (res.code != 200) return this.$message.error("推荐电影获取失败");
      this.guessLikeMovieList = res.data;
    },
  },
  computed: {
    isFollow() {
      return this.$store.state.user.isLogin; //需要监听的数据
    },
  },
  watch: {
    isFollow(newVal, oldVal) {
      if (newVal) {
        this.getGuessLikeMovieList();
      }
    },
  },
};
</script>

<style scoped>
.el-carousel {
  width: 80%;
  margin: 15px auto;
}

.el-carousel__item > img {
  width: 100%;
  height: auto;
}

.whole {
  width: 1200px;
  margin: 30px auto;
  display: flex;
}

.left {
  width: 72%;
}

.right {
  width: 38%;
  margin-left: 50px;
  /*background-color: lightpink;*/
}

h2 {
  font-size: 26px;
}

.panel-header > a {
  text-align: center;
  text-decoration: none;
  color: #999;
  padding-right: 14px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAOCAYAAAASVl2WAAAABmJLR0QA/wD/AP+gvaeTAAAAv0lEQVQY013RTUpDQRAE4G8eghcR8ScgKCIugpJFjuIjqAvBc7jxj0muEnCjiIQQJOImB3GnbnpkfL1qpqqrunpSzvkDPxjhGdq2VarBF3q4wRHknP8RzvCEQzzguCalaHZwiwHecY6XogCf8TjFHh7Rh9Tx3AylIZa4TgWpSBuY4BSrYlFXKsr4bjrTW5HkJJa9SBW4jbtukmKxG5MDLOKqfzEPcB9LzQN8LSdfwxj7eMMlZvV/NFiPzFddEH4Bt5Y1mf3fnDwAAAAASUVORK5CYII=)
    no-repeat 100%;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /*margin-right: 20px;*/
  /*margin-left: 20px;*/
}

.movie-item {
  margin-left: 0;
  margin-right: 30px;
}

.movie-item:nth-child(4n) {
  margin-right: 0;
}

.board {
  display: flex;
  margin: 10px 10px;
}

.board-left {
  display: flex;
  align-items: center;
}

.board-middle {
  display: flex;
  /*align-items: center;*/
  /*justify-content: center;*/
  margin-left: 10px;
  width: 150px;
  font-size: 18px;
}

.board-middle > a {
  text-decoration: none;
  color: #333;
}

.board-right {
  display: flex;
  font-size: 14px;
  font-weight: 700;
  color: #ffb400;
  margin-left: 40px;
  align-items: center;
}

.board-index {
  color: #ffb400;
  display: inline-block;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 18px;
  /*font-weight: 700;*/
  align-items: center;
}

.panel-content {
  margin: 0px 0px 50px 0px;
}
</style>