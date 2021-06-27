<template>
  <el-container>
    <el-header>
      <el-menu
        :default-active="$route.path"
        class="nav-menu"
        mode="horizontal"
        :router="true"
        background-color="#47464a"
        text-color="#999999"
        active-text-color="#ffb400"
      >
        <el-menu-item
          :index="item.path"
          v-for="item in menuList"
          :key="item.id"
          @click="handleMenu(item.id)"
        >{{item.name}}</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <!-- 分类查询 -->
      <div class="tags-container">
        <div class="tags-line">
          <div class="tags-title">类型:</div>
          <el-radio-group fill="#ffb400" v-model="categoryRadio">
            <el-radio-button
              :label="item.movieCategoryId"
              v-for="item in categoryList"
              :key="item.movieCategoryId"
            >{{item.movieCategoryName}}</el-radio-button>
          </el-radio-group>
        </div>
        <div class="tags-line">
          <div class="tags-title">区域:</div>
          <el-radio-group fill="#ffb400" v-model="areaRadio">
            <el-radio-button
              :label="item.movieAreaId"
              v-for="item in areaList"
              :key="item.movieAreaId"
            >{{item.movieAreaName}}</el-radio-button>
          </el-radio-group>
        </div>
        <div class="tags-line" v-if="state">
          <div class="tags-title">年代:</div>
          <el-radio-group fill="#ffb400" v-model="ageRadio">
            <el-radio-button
              :label="item.movieAgeId"
              v-for="item in ageList"
              :key="item.movieAgeId"
            >{{item.movieAgeName}}</el-radio-button>
          </el-radio-group>
        </div>
      </div>
      <router-view
        :movieCategoryName="categoryList[categoryRadio].movieCategoryName"
        :movieAreaName="areaList[areaRadio].movieAreaName"
        :movieAgeName="ageList[ageRadio].movieAgeName"
      ></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "Movie",
  data() {
    return {
      state: false,
      menuList: [
        {
          id: "1",
          name: "正在热映",
          path: "/movie/ingMovie",
        },
        {
          id: "2",
          name: "即将上映",
          path: "/movie/willMovie",
        },
        {
          id: "3",
          name: "热播电影",
          path: "/movie/classicMovie",
        },
      ],
      categoryRadio: 0,
      categoryList: [
        { movieCategoryId: 0, movieCategoryName: "全部" },
        { movieCategoryId: 1, movieCategoryName: "爱情" },
        { movieCategoryId: 2, movieCategoryName: "喜剧" },
        { movieCategoryId: 3, movieCategoryName: "动画" },
        { movieCategoryId: 4, movieCategoryName: "剧情" },
        { movieCategoryId: 5, movieCategoryName: "恐怖" },
        { movieCategoryId: 6, movieCategoryName: "惊悚" },
        { movieCategoryId: 7, movieCategoryName: "动作" },
        { movieCategoryId: 8, movieCategoryName: "科幻" },
        { movieCategoryId: 9, movieCategoryName: "悬疑" },
        { movieCategoryId: 10, movieCategoryName: "犯罪" },
        { movieCategoryId: 11, movieCategoryName: "冒险" },
        { movieCategoryId: 12, movieCategoryName: "战争" },
        { movieCategoryId: 13, movieCategoryName: "奇幻" },
        { movieCategoryId: 14, movieCategoryName: "运动" },
        { movieCategoryId: 15, movieCategoryName: "家庭" },
        { movieCategoryId: 16, movieCategoryName: "古装" },
        { movieCategoryId: 17, movieCategoryName: "武侠" },
        { movieCategoryId: 18, movieCategoryName: "西部" },
        { movieCategoryId: 19, movieCategoryName: "历史" },
        { movieCategoryId: 20, movieCategoryName: "传记" },
        { movieCategoryId: 21, movieCategoryName: "歌舞" },
        { movieCategoryId: 22, movieCategoryName: "短片" },
        { movieCategoryId: 23, movieCategoryName: "纪录片" },
        { movieCategoryId: 24, movieCategoryName: "其他" },
      ],
      areaRadio: 0,
      areaList: [
        { movieAreaId: 0, movieAreaName: "全部" },
        { movieAreaId: 1, movieAreaName: "大陆" },
        { movieAreaId: 2, movieAreaName: "美国" },
        { movieAreaId: 3, movieAreaName: "韩国" },
        { movieAreaId: 4, movieAreaName: "日本" },
        { movieAreaId: 5, movieAreaName: "中国香港" },
        { movieAreaId: 6, movieAreaName: "中国台湾" },
        { movieAreaId: 7, movieAreaName: "泰国" },
        { movieAreaId: 8, movieAreaName: "印度" },
        { movieAreaId: 9, movieAreaName: "法国" },
        { movieAreaId: 10, movieAreaName: "英国" },
        { movieAreaId: 11, movieAreaName: "俄罗斯" },
        { movieAreaId: 12, movieAreaName: "意大利" },
        { movieAreaId: 13, movieAreaName: "西班牙" },
        { movieAreaId: 14, movieAreaName: "德国" },
        { movieAreaId: 15, movieAreaName: "波兰" },
        { movieAreaId: 16, movieAreaName: "澳大利亚" },
        { movieAreaId: 17, movieAreaName: "伊朗" },
        { movieAreaId: 18, movieAreaName: "其他" },
      ],
      ageRadio: 0,
      ageList: [
        { movieAgeId: 0, movieAgeName: "全部" },
        { movieAgeId: 1, movieAgeName: "2021" },
        { movieAgeId: 2, movieAgeName: "2020" },
        { movieAgeId: 3, movieAgeName: "2019" },
        { movieAgeId: 4, movieAgeName: "2018" },
        { movieAgeId: 5, movieAgeName: "2017" },
        { movieAgeId: 6, movieAgeName: "2016" },
        { movieAgeId: 7, movieAgeName: "2015" },
        { movieAgeId: 8, movieAgeName: "2014" },
        { movieAgeId: 9, movieAgeName: "2013" },
        { movieAgeId: 10, movieAgeName: "2012" },
        { movieAgeId: 11, movieAgeName: "2011" },
        { movieAgeId: 12, movieAgeName: "2000-2010" },
        { movieAgeId: 13, movieAgeName: "90年代" },
        { movieAgeId: 14, movieAgeName: "80年代" },
        { movieAgeId: 15, movieAgeName: "70年代" },
        { movieAgeId: 16, movieAgeName: "更早" },
      ],

      //orderByColumn: "releaseDate",
    };
  },

  created() {},
  methods: {
    handleMenu(id) {
      if (id == 3) {
        this.state = true;
      } else {
        this.state = false;
      }
    },
  },
  watch: {
    $route() {
      //页面发生变化时初始化类别选项
      this.categoryRadio = 0;
      this.areaRadio = 0;
      this.ageRadio = 0;
      //this.orderByColumn = "releaseDate";
    },
  },
};
</script>

<style scoped>
.el-header {
  padding: 0px;
}

.nav-menu {
  display: flex;
  justify-content: space-around;
}

.el-menu-item {
  font-size: 16px;
}

.el-menu-item:hover {
  color: #ffffff !important;
}

.el-main {
  width: 1200px;
  margin: 0 auto;
}

.tags-container {
  border: solid #eeeeee 2px;
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.1);
}

.tags-line {
  width: 100%;
  display: flex;
  padding: 10px;
  box-sizing: border-box;
  border-bottom: solid #eeeeee 1px;
}

.tags-line:last-child {
  border-bottom: 0;
}

.tags-title {
  width: 5%;
  height: 24px;
  line-height: 24px;
  float: left;
  color: #999;
  font-size: 14px;
}

.el-radio-group {
  width: 92%;
  margin-left: 20px;
}

.el-radio-button {
  margin-right: 10px;
}

.el-radio-button:last-child {
  margin-right: 0;
}

.el-radio-button >>> .el-radio-button__inner:hover {
  color: #ffb400 !important;
}

.el-radio-button >>> .el-radio-button__inner {
  border: solid #eee 0px !important;
  border-radius: 50px !important;
}

.order-by-container {
  margin: 30px 10px 0 10px;
}
</style>