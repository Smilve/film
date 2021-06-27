<template>
  <div>
    <div class="movie-list">
      <movie-item :movieItem="item" v-for="item in movieList" :key="item.movieId"></movie-item>
    </div>

    <div class="no-cinema" v-if="sorry">
      <p>抱歉，没有找到相关结果，请尝试用其他条件筛选。</p>
    </div>

    <div class="pageHelper">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageIndex"
        :page-size="pageSize"
        background
        layout="prev, pager, next"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import movieItem from "../../components/movie/movie-item";
import moment from "moment";
export default {
  name: "WillMovie",
  components: {
    movieItem,
  },
  props: {
    movieCategoryName: String,
    movieAreaName: String,
    movieAgeName: String,
  },
  data() {
    return {
      total: 0,
      pageSize: 30,
      pageIndex: 1,
      date: moment().format("YYYY-MM-DD"),
      movieList: [],
      sorry: false,
    };
  },
  computed: {
    listenChange() {
      const { movieCategoryName, movieAreaName, movieAgeName } = this;
      return { movieCategoryName, movieAreaName, movieAgeName };
    },
  },
  created() {
    this.getMovieList();
  },
  watch: {
    listenChange() {
      this.getMovieList();
    },
  },
  methods: {
    async getMovieList() {
      let queryInfo = {
        type: this.movieCategoryName,
        nation: this.movieAreaName,
        date: this.date,
        pageSize: this.pageSize,
        pageIndex: this.pageIndex,
      };
      const { data: res } = await axios.post("/movie/willMovie", queryInfo);
      if (res.code === 200) {
        this.movieList = res.data.list;
        this.total = res.data.totalCount;
        if (this.movieList.length === 0) this.sorry = true;
        else this.sorry = false;
      }
    },
    handleCurrentChange(newPage) {
      this.pageIndex = newPage;
      this.getMovieList();
    },
  },
};
</script>

<style scoped>
.movie-list {
  display: flex;
  flex-wrap: wrap;
}

.pageHelper {
  display: flex;
  justify-content: center;
  margin: 40px 0px;
}

.no-cinema {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  margin-bottom: 40px;
  font-size: 16px;
  color: #333;
}
</style>