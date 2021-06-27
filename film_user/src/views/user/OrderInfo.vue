<template>
  <div class="bill-list-container">
    <div class="order-box" v-for="(item,index) in orderList" :key="index">
      <div class="order-header">
        <span class="order-date">{{item.date}}</span>
        <span class="order-id">橘子订单号: {{item.orderId}}</span>
        <span class="del-order" v-if="item.state != 1" @click="delOrder(item.orderId)">
          <i class="el-icon-delete"></i>
        </span>
      </div>
      <div class="order-body">
        <div class="poster">
          <img :src="global.base+item.photo" />
        </div>
        <div class="order-content">
          <div class="movie-name">《{{item.movieName}}》</div>
          <div class="cinema-name">{{item.cinemaName}}</div>
          <div class="hall-ticket">
            <span>{{item.hallName+' '}}</span>
            <div class="seats">
              <span class="order-seat" v-for="item in item.seat" :key="item">{{item}}</span>
            </div>
          </div>
          <div class="show-time">{{getDate(item.date) + ' ' + item.beginTime}}</div>
        </div>
        <div class="order-price">￥{{item.price}}</div>
        <div class="order-status" v-if="item.state==1">
          取票码：
          <span style="color:red;font-weight:700;">{{item.code}}</span>
        </div>
        <div class="order-status" v-if="item.state==0||item.state==2">{{item.state==0?'待支付':'已失效'}}</div>
        <div class="order-status" v-if="item.state==3">{{item.state==3?'已退款':''}}</div>
        <div class="actions">
          <div v-if="item.state==0">
            <a :href="'/orderDetail/'+item.orderId" class="order-detail">查看详情</a>
          </div>
          <div v-if="item.state==1 && getNowDate(item.date,item.beginTime)">
            <el-button type="text" @click="refund(item.orderId)">退款</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "OrderInfo",
  data() {
    return {
      userId: 0,
      orderList: {},
      queryInfo: {
        userId: "",
      },
      date: moment().format("YYYY-MM-DD HH:MM:SS"),
      billList: [
        {
          sysSession: {
            sysMovie: {
              moviePoster: "",
            },
            sysCinema: {},
            sysHall: {
              sysHallCategory: {},
            },
            sysMovieRuntime: {},
          },
        },
      ],
    };
  },
  created() {
    this.getOrderInfo();
  },
  methods: {
    async getOrderInfo() {
      this.userId = JSON.parse(localStorage.user).userId;
      const { data: res } = await axios.get("/order/user/" + this.userId);
      if (res.code !== 200) return this.$message.error("订单信息获取失败！");
      this.orderList = res.data;
      this.orderList.map((order) => {
        order.seat = JSON.parse(order.seat);
      });
    },
    async delOrder(id) {
      const _this = this;
      const resp = await this.$confirm(
        "此操作将永久删除订单信息,是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      if (resp == "cancel") {
        return _this.$message.info("已取消删除");
      }
      const { data: res } = await axios.delete("/order/del/" + id);
      if (res.code !== 200) {
        return this.$message.error("删除失败！");
      }
      this.getOrderInfo();
      this.$message.success("删除成功！");
    },
    //退款
    refund(orderId) {
      this.$confirm("您确定退款吗？", "退款操作", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        const { data: res } = await axios.get("/order/refund/" + orderId);
        if (res.code == 200) {
          const { data: re } = await axios.get("/order/order/" + orderId);
          console.log(re);
          let seat = JSON.parse(re.data.seat);
          const { data: res1 } = await axios.get(
            "/schedule/" + re.data.scheduleId
          );
          //获取场次座位信息
          let seats = JSON.parse(res1.data.seat);
          //解析出订单选择的座位，更新座位信息
          for (let seat of seat) {
            let row = seat.substring(0, seat.indexOf("排"));
            let col = Number.parseInt(
              seat.substring(seat.indexOf("排") + 1, seat.length - 1)
            );
            if (seats[row][col - 1] == 3) {
              seats[row][col - 1] = 0;
            }
          }
          //更新订单信息和场次座位信息
          const { data: res2 } = await axios.post("/schedule/seats", {
            scheduleId: re.data.scheduleId,
            seat: JSON.stringify(seats),
          });
          if (res2.code != 200) return console.log("座位信息更新错误！");
          this.$message.success("退款成功");
          this.getOrderInfo();
          return;
        }
        this.$message.success("退款失败");
      });
    },

    getDate(date) {
      return moment(date).format("MM月DD日");
    },
    getNowDate(date, time) {
      return (
        this.date < moment(date + " " + time).format("YYYY-MM-DD HH:MM:SS")
      );
    },
  },
};
</script>

<style scoped>
.bill-list-container {
  min-height: 900px;
}

.profile-title {
  padding: 26px 0;
  color: #ec443f;
  font-size: 18px;
  border-bottom: 1px solid #e1e1e1;
  margin-bottom: 30px;
}

.order-box {
  border: 1px solid #e5e5e5;
  margin: 0 40px 30px 0;
}

.order-box .order-header {
  background-color: #f7f7f7;
  font-size: 14px;
  padding: 16px 20px;
}

.order-box .order-header .order-date {
  color: #333;
  display: inline-block;
  margin-right: 30px;
}

.order-box .order-header .order-id {
  color: #999;
}

.del-order {
  float: right;
}

.order-box .order-body {
  padding: 20px;
  padding-right: 0;
  display: flex;
}

.order-box .order-body .poster {
  width: 66px;
  height: 91px;
  margin-right: 11px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.poster img {
  width: 100%;
  height: 100%;
}

.order-content {
  width: 49%;
}

.movie-name {
  font-size: 16px;
  font-weight: 700;
  color: #333;
  margin: 4px 0 7px -6px;
}

.cinema-name,
.hall-ticket {
  font-size: 12px;
  color: #999;
  margin-bottom: 4px;
}

.hall-ticket .seats {
  display: inline-flex;
  text-align: left;
  flex-wrap: wrap;
}

.hall-ticket .seats span {
  margin-right: 5px;
}
.show-time {
  font-size: 12px;
  color: #f03d37;
}

.order-price,
.order-status,
.actions {
  font-size: 14px;
  color: #333;
  width: 14%;
  line-height: 95px;
}

a {
  text-decoration: none;
  cursor: pointer;
  color: #333;
}
</style>