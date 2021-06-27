<template>
  <div class="search-container">
    <div class="empty-list" v-if="this.movieList.length==0">
      <h3>很抱歉，没找到相关的影视剧</h3>
      <dl>
        <dt>小橘建议您：</dt>
        <dd>1. 请检查输入的关键词是否有误</dd>
        <dd>2. 请缩短关键词</dd>
      </dl>
    </div>
    <div>
      <movie-item :movieItem="item" v-for="(item,index) in movieList" :key="index"></movie-item>
    </div>
  </div>
</template>

<script>
import movieItem from "../../components/movie/movie-item";
import moment from "moment";
export default {
  name: "MovieSearch",
  components: {
    movieItem,
  },
  data() {
    return {
      key: this.$route.query.key,
      movieList: [],
    };
  },
  created() {
    this.getSearchMovieList();
  },
  methods: {
    async getSearchMovieList() {
      this.key = this.$route.query.key;
      const { data: res } = await axios.post("/movie/like", {
        movieName: this.key,
      });
      if (res.code !== 200) return this.$message.error("获取信息失败");
      this.movieList = res.data;
    },
    getDate(date) {
      return moment(date).format("YYYY年MM月DD日");
    },
  },
  watch: {
    $route() {
      this.getSearchMovieList();
    },
  },
};
</script>

<style scoped>
.empty-list h3 {
  margin-top: 98px;
  font-size: 26px;
  color: #999;
  font-weight: 400;
}
.empty-list dl dt {
  color: #ef4238;
}
.empty-list dl dd {
  color: #333;
  margin-top: 10px;
  margin-left: 0;
}
.search-container {
  width: 980px;
  margin: 0px auto;
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap;
}

a {
  text-decoration: none;
  cursor: pointer;
}
</style>