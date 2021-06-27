<template>
  <div class="search-container">
    <div class="empty-list" v-if="this.cinemaList.length==0">
      <h3>很抱歉，没找到相关的影院</h3>
      <dl>
        <dt>小橘建议您：</dt>
        <dd>1. 请检查输入的关键词是否有误</dd>
        <dd>2. 请缩短关键词</dd>
      </dl>
    </div>
    <div class="search-result" v-for="(item,index) in cinemaList" :key="index">
      <div class="result-left">
        <div class="cinema-name">
          <a :href="'/cinemaInfo/' + item.cinemaId">{{item.cinemaName}}</a>
        </div>
        <div class="cinema-address">电话：{{item.phone}}</div>
        <div class="cinema-address">地址：{{item.country}} {{item.address}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CinemaSearch",
  data() {
    return {
      key: this.$route.query.key,
      cinemaList: [],
    };
  },
  created() {
    this.getSearchCinemaList();
  },
  methods: {
    async getSearchCinemaList() {
      this.key = this.$route.query.key;
      const { data: res } = await axios.post("/cinema/like", {
        cinemaName: this.key,
      });
      if (res.code !== 200) return this.$message.error("获取信息失败");
      this.cinemaList = res.data;
    },
  },
  watch: {
    $route() {
      this.getSearchCinemaList();
    },
  },
};
</script>

<style scoped>
.search-container {
  width: 980px;
  margin: 0px auto;
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap;
}
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
.search-result:first-child {
  margin-top: 40px;
}

.search-result {
  width: 1200px;
  margin: 0 auto;
  padding: 30px 0;
  border-bottom: solid #eee 1px;
  display: flex;
  justify-content: space-between;
}

.cinema-name {
  color: #333333;
  font-size: 24px;
  font-weight: 700;
}

.cinema-address {
  margin: 10px 0;
  color: #999999;
}

a {
  text-decoration: none;
  cursor: pointer;
  color: #333333;
}

.result-right {
  display: flex;
  align-items: center;
}

.result-right span {
  color: #409eff;
  font-size: 16px;
  font-weight: 700;
}
</style>