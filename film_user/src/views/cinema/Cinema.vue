<template>
  <!-- 分类查询 -->
  <div>
    <div class="tags-container">
      <div class="tags-line">
        <div class="tags-title">类型:</div>
        <el-radio-group fill="#ffb440" v-model="brandRadio" size="medium">
          <el-radio-button
            :label="item.cinemaBrandId"
            v-for="item in cinemaBrandList"
            :key="item.cinemaBrandId"
          >{{ item.cinemaBrandName }}</el-radio-button>
        </el-radio-group>
      </div>
      <div class="tags-line">
        <div class="tags-title">行政区:</div>
        <el-radio-group fill="#ffb440" v-model="areaRadio" size="medium">
          <el-radio-button
            :label="item.cinemaAreaId"
            v-for="item in cinemaAreaList"
            :key="item.cinemaAreaId"
          >{{ item.cinemaAreaName }}</el-radio-button>
        </el-radio-group>
      </div>
      <div class="tags-line">
        <div class="tags-title">特殊厅:</div>
        <el-radio-group fill="#ffb440" v-model="hallCategoryRadio" size="medium">
          <el-radio-button
            :label="item.hallCategoryId"
            v-for="item in hallCategoryList"
            :key="item.hallCategoryId"
          >{{ item.hallCategoryName }}</el-radio-button>
        </el-radio-group>
      </div>
    </div>
    <div class="list-header">
      <h2 class>
        <span>影院列表</span>
      </h2>
    </div>
    <div class="list-content" v-for="(item,index) in cinemaList" :key="index">
      <div class="list-left">
        <a href="#" class="cinema-name">{{ item.cinemaName }}</a>
        <p class="cinema-address">地址：{{ item.address }}</p>
        <!-- <el-tag
          size="small"
          effect="plain"
          type="danger"
          v-for="hall in item.sysHallCategoryList"
          :key="hall.hallId"
        >{{hall.hallCategoryName}}</el-tag>-->
      </div>
      <div class="list-right">
        <el-button
          class="choose-seat"
          @click="toCinemaInfo(item.cinemaId)"
          type="primary"
          round
          size="medium"
        >选座购票</el-button>
      </div>
    </div>
    <div class="no-cinema" v-if="sorry">
      <p>抱歉，没有找到相关结果，请尝试用其他条件筛选。</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cinema",
  data() {
    return {
      brandRadio: 0,
      areaRadio: 0,
      hallCategoryRadio: 0,
      cinemaBrandList: [
        { cinemaBrandId: 0, cinemaBrandName: "全部" },
        { cinemaBrandId: 1, cinemaBrandName: "太平洋电影城" },
        { cinemaBrandId: 2, cinemaBrandName: "万达影城" },
        { cinemaBrandId: 3, cinemaBrandName: "星美国际影城" },
        { cinemaBrandId: 4, cinemaBrandName: "横店影视" },
        { cinemaBrandId: 5, cinemaBrandName: "星光影城" },
        { cinemaBrandId: 6, cinemaBrandName: "金逸影城" },
        { cinemaBrandId: 7, cinemaBrandName: "大地影院" },
        { cinemaBrandId: 8, cinemaBrandName: "苏宁影城" },
        { cinemaBrandId: 9, cinemaBrandName: "中影国际影城" },
        { cinemaBrandId: 10, cinemaBrandName: "保利国际影城" },
        { cinemaBrandId: 11, cinemaBrandName: "橙天嘉禾影城" },
        { cinemaBrandId: 12, cinemaBrandName: "百老汇影城" },
      ],
      cinemaAreaList: [
        {
          cinemaAreaId: 0,
          cinemaAreaName: "全部",
        },
        {
          cinemaAreaId: 1,
          cinemaAreaName: "武侯区",
        },

        {
          cinemaAreaId: 2,
          cinemaAreaName: "双流区",
        },
        {
          cinemaAreaId: 3,
          cinemaAreaName: "郫都区",
        },
        {
          cinemaAreaId: 4,
          cinemaAreaName: "新都区",
        },
        {
          cinemaAreaId: 5,
          cinemaAreaName: "锦江区",
        },
        {
          cinemaAreaId: 6,
          cinemaAreaName: "金牛区",
        },
        {
          cinemaAreaId: 7,
          cinemaAreaName: "成华区",
        },
        {
          cinemaAreaId: 8,
          cinemaAreaName: "青羊区",
        },
        {
          cinemaAreaId: 9,
          cinemaAreaName: "龙泉驿区",
        },
        {
          cinemaAreaId: 10,
          cinemaAreaName: "温江区",
        },
        {
          cinemaAreaId: 11,
          cinemaAreaName: "金堂县",
        },
        {
          cinemaAreaId: 12,
          cinemaAreaName: "都江堰市",
        },
        {
          cinemaAreaId: 13,
          cinemaAreaName: "崇州市",
        },
        {
          cinemaAreaId: 14,
          cinemaAreaName: "青白江区",
        },
        {
          cinemaAreaId: 15,
          cinemaAreaName: "彭州市",
        },
        {
          cinemaAreaId: 16,
          cinemaAreaName: "新津区",
        },
        {
          cinemaAreaId: 17,
          cinemaAreaName: "邛崃市",
        },
        {
          cinemaAreaId: 18,
          cinemaAreaName: "大邑县",
        },
        {
          cinemaAreaId: 19,
          cinemaAreaName: "简阳市",
        },
      ],
      hallCategoryList: [
        { hallCategoryId: 0, hallCategoryName: "全部" },
        { hallCategoryId: 1, hallCategoryName: "激光厅" },
        { hallCategoryId: 2, hallCategoryName: "IMAX厅" },
        { hallCategoryId: 3, hallCategoryName: "动感厅" },
        { hallCategoryId: 4, hallCategoryName: "杜比全景声厅" },
        { hallCategoryId: 5, hallCategoryName: "贵宾厅" },
        { hallCategoryId: 6, hallCategoryName: "RealD厅" },
        { hallCategoryId: 7, hallCategoryName: "RealD 6FL厅" },
        { hallCategoryId: 8, hallCategoryName: "LUXE巨幕厅" },
        { hallCategoryId: 9, hallCategoryName: "4DX厅" },
        { hallCategoryId: 10, hallCategoryName: "DTS:X 临镜音厅" },
        { hallCategoryId: 11, hallCategoryName: "儿童厅" },
        { hallCategoryId: 12, hallCategoryName: "4K厅" },
        { hallCategoryId: 13, hallCategoryName: "60帧厅" },
        { hallCategoryId: 14, hallCategoryName: "巨幕厅" },
        { hallCategoryId: 15, hallCategoryName: "MX4D厅" },
      ],
      cinemaList: [],
      sorry: false,
    };
  },
  created() {
    this.getCinemaList();
  },
  computed: {
    listenChange() {
      const { brandRadio, areaRadio, hallCategoryRadio } = this;
      return { brandRadio, areaRadio, hallCategoryRadio };
    },
  },
  watch: {
    listenChange() {
      this.getCinemaList();
    },
  },
  mounted() {
    this.getCinemaList();
  },
  methods: {
    async getCinemaList() {
      const query = {
        cinemaBrandName: this.cinemaBrandList[this.brandRadio].cinemaBrandName,
        cinemaAreaName: this.cinemaAreaList[this.areaRadio].cinemaAreaName,
        hallCategoryName: this.hallCategoryList[this.hallCategoryRadio]
          .hallCategoryName,
        movieId: this.$store.state.movie.movieId,
      };
      console.log(query.movieId);
      const { data: res } = await axios.post("/cinema/getCinemaByUser", query);
      if (res.code !== 200) return this.$message.error("获取服务器信息失败");
      this.cinemaList = res.data;
      if (this.cinemaList.length == 0) this.sorry = true;
      else this.sorry = false;
    },
    toCinemaInfo(id) {
      this.$router.push("/cinemaInfo/" + id);
    },
  },
};
</script>

<style scoped>
.tags-container {
  width: 1100px;
  margin: 40px auto;
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

.el-radio-button >>> .el-radio-button__inner {
  border: solid #eee 0px !important;
  border-radius: 50px !important;
}

.el-radio-button >>> .el-radio-button__inner:hover {
  color: #ffb400 !important;
}

.order-by-container {
  margin: 30px 10px 0 10px;
}

.list-header {
  width: 1100px;
  display: flex;
  margin: 0 auto;
}

.list-header > h2 {
  padding-left: 6px;
  border-left: 4px solid #ffb400;
  font-size: 18px;
  color: #333;
}

.list-content {
  width: 1100px;
  display: flex;
  margin: 0 auto;
  justify-content: space-between;
  border-bottom: solid #eeeeee 1px;
  padding: 10px 0;
  margin-bottom: 30px;
}

.list-right {
  margin-top: 30px;
}

a {
  text-decoration: none;
}

.cinema-name {
  color: #333;
}

.buy-ticket {
  display: inline-block;
  color: #ffffff;
  border-radius: 100px;
  background-color: #409eff;
  width: 100%;
  font-size: 14px;
  text-align: center;
}

.cinema-address {
  color: #999999;
}

.el-tag {
  margin: 0 10px 10px 0;
}

.el-button {
  margin-right: 20px;
}

.no-cinema {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  margin-bottom: 40px;
  font-size: 16px;
  color: #333;
}

.choose-seat {
  background: #ffb400;
  border-color: #ffb400;
}
</style>