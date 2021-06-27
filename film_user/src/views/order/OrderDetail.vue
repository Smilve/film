<template>
  <div>
    <el-steps :active="2" align-center>
      <el-step title="选择影片场次"></el-step>
      <el-step title="选择座位"></el-step>
      <el-step title="15分钟内付款"></el-step>
      <el-step title="影院取票观影" status="wait"></el-step>
    </el-steps>
    <div class="order-info-container">
      <!-- 订单状态 -->
      <div class="order-info-status" v-if="state === 0 && (minutes > 0 || seconds > 0)">
        <div class="pay-icon">
          <img src="../../assets/pay.jpg" />
        </div>
        <div class="pay-info">
          <!-- <div class="pay-info-status">待支付</div> -->
          <div class="pay-info-desc">
            请在
            <span>{{minutes}}</span>分钟
            <span>{{seconds}}</span>秒内完成支付
          </div>
          <span class="order-info-helper">超时订单会自动取消，如遇支付问题，请致电橘子客服：1234-1234</span>
        </div>
      </div>
      <div class="order-info-status invalid" v-if="state === 0 && minutes <= 0 && seconds <= 0">
        <div class="pay-icon">
          <img src="../../assets/order-invalid.png" />
        </div>
        <div class="pay-info">
          <div class="pay-info-status invalid">订单由于未支付，已失效</div>
        </div>
      </div>
      <div class="order-info-status finish" v-if="state === 1">
        <div class="pay-icon">
          <img src="../../assets/finish.png" />
        </div>
        <div class="pay-info">
          <div class="pay-info-status finish">已完成</div>
        </div>
      </div>

      <!-- 订单信息 -->
      <div class="order-info-desc">
        <div class="order-info-header">
          <div style="font-size:16px;color:#333;margin-bottom:10px;">橘子订单号: {{order.orderId}}</div>
          <span class="warning">
            请仔细核对场次信息，出票后将
            <span style="color:#faaf00;">无法退票和改签</span>
          </span>
        </div>

        <table class="order-table">
          <thead>
            <tr>
              <th>影片</th>
              <th>时间</th>
              <th>影院</th>
              <th>座位</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="movie-name">《{{orderVo.movieName}}》</td>
              <td class="showtime">{{orderVo.date}} {{orderVo.beginTime}}</td>
              <td class="cinema-name">{{orderVo.cinemaName}}</td>
              <td>
                <span class="hall">{{orderVo.hallName}}</span>
                <div class="seats">
                  <span class="order-seat" v-for="item in seat" :key="item">{{item}}</span>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 影院信息、提交订单 -->
      <div class="order-info-area">
        <div></div>
        <div class="submit-order">
          <div>
            实际支付：
            <span>{{(order.price)}}</span>
          </div>
          <div>
            <el-button
              @click="toPay"
              style="width: 200px; margin-top: 20px;background-color:#f03d37;color:white;"
              round
            >立即支付</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "OrderDetail",
  data() {
    return {
      orderId: this.$route.params.orderId,
      order: {},
      orderVo: {},
      seat: [],
      state: 0,
      minutes: 15,
      seconds: 0,
    };
  },
  created() {
    this.getorderInfo();
  },
  mounted() {
    this.add();
  },
  methods: {
    async getorderInfo() {
      const { data: res } = await axios.get("/order/info/" + this.orderId);
      if (res.code !== 200) return this.$message.error("获取订单信息失败");
      this.order = res.data.order;
      this.orderVo = res.data.orderVo;
      this.seat = JSON.parse(this.order.seat);
      this.state = this.order.state;
      this.computeLeftTime();
    },
    async toPay() {
      sessionStorage.clear();
      //进这个页面直接获取支付宝表单，保持页面计时和支付宝超时时间 一致
      const { data: resp } = await axios.get("/order/pay/" + this.orderId);
      if (resp.code !== 200) return this.$message.error("获取支付宝表单失败！");
      //将支付宝返回的表单信息写入sessionStorage，再打开新标签页，读取支付宝二维码
      sessionStorage.setItem("payaction", resp.data);
      const { data: res } = await axios.get("/order/order/" + this.orderId);
      if (res.code == 200 && res.data.state == 1) {
        this.$message.success("付款成功！");
        this.state = 1;
        setTimeout(() => {
          this.$router.push("/orderInfo");
        }, 1000);
      } else if (res.data.state == 2) {
        this.$message.warning("订单已失效，请重新选择!");
        this.$router.push("/movie");
        return;
      }
      if (this.minutes == 0 && this.seconds == 0) {
        console.log("失败");
      } else {
        window.open("/pay", "_blank");
        this.$confirm(
          "付款完成前请不要关闭此窗口，完成付款后请根据您的情况点击下面的按钮：",
          "请您在新打开的页面上完成付款。",
          {
            confirmButtonText: "已完成付款",
            cancelButtonText: "付款遇到问题",
            type: "warning",
          }
        )
          .then(async () => {
            const { data: res } = await axios.get(
              "/order/order/" + this.orderId
            );
            if (res.code == 200 && res.data.state == 1) {
              setTimeout(() => {}, 1000);
              this.$alert(
                "支付成功，您的取票码为：" +
                  res.data.code +
                  "，您可以前往个人中心查看订单信息！",
                "支付通知",
                {
                  confirmButtonText: "我知道了",
                  callback: (action) => {
                    this.state = 1;
                    this.$router.push("/movie");
                  },
                }
              );
            } else {
              this.$message.error("支付失败，请重新支付！");
            }
          })
          .catch(() => {
            this.$message.warning("已取消支付!");
          });
      }
    },
    computeLeftTime() {
      let cur = new Date();
      let orderDate = new Date(this.order.date);
      let diff = this.minutes * 60 - (cur - orderDate) / 1000;
      if (diff < 0) {
        this.minutes = 0;
        this.seconds = 0;
      } else {
        this.minutes = Math.floor(diff / 60);
        this.seconds = Math.floor(diff % 60);
      }
    },
    //计时转换数字格式
    num: function (n) {
      return n < 10 ? "0" + n : "" + n;
    },
    //计时走动
    add: function () {
      var _this = this;
      var time = window.setInterval(async function () {
        if (_this.seconds === 0 && _this.minutes !== 0) {
          _this.seconds = 59;
          _this.minutes -= 1;
        } else if (_this.minutes === 0 && _this.seconds === 0) {
          _this.seconds = 0;
          window.clearInterval(time);
          //回退座位信息
          const { data: res1 } = await axios.get(
            "/schedule/" + _this.order.scheduleId
          );
          //获取场次座位信息
          let seats = JSON.parse(res1.data.seat);
          //解析出订单选择的座位，更新座位信息
          for (let seat of _this.seat) {
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
            scheduleId: _this.order.scheduleId,
            seat: JSON.stringify(seats),
          });
          if (res2.code != 200)
            return _this.$message.error("座位信息更新错误！");
          //订单失效
          const { data: resp } = await axios.get(
            "/order/order/" + _this.order.orderId
          );
          if (resp.code == 200 && resp.data.state == 0) {
            const { data: res } = await axios.post("/order/update", {
              orderId: _this.order.orderId,
              state: 2,
            });
            if (res.code !== 200)
              return _this.$message.error("订单状态更新失败");
            _this.$alert("订单超时，请重新选择！", "订单通知", {
              confirmButtonText: "确定",
              type: "warning",
              callback: () => {
                setTimeout(() => {
                  _this.$router.push("/movie");
                }, 500);
              },
            });
          }
        } else {
          _this.seconds -= 1;
        }
      }, 1000);
    },
  },
  watch: {
    second: {
      handler(newVal) {
        this.num(newVal);
      },
    },
    minute: {
      handler(newVal) {
        this.num(newVal);
      },
    },
  },
  computed: {
    second: function () {
      return this.num(this.seconds);
    },
    minute: function () {
      return this.num(this.minutes);
    },
  },
};
</script>

<style scoped>
.warning {
  font-size: 12px;
  color: #666;
  padding-left: 18px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAC+1JREFUaAWtWk2oXVcV3vu8n+YlfXnJs1LUotVAg7OCATUobQhWCJLEgYopUkpAcKJTQ6BIQdRZKTgRQsnADMRBkoEDbcmTQohalToQUq0/tKNG04RHvU3fu2f7fd9aa5993303PFM3eWevvdb61vrW2ufv3puc/k+jvPKJlTR+51jq0+GUy0HMB1JOK6n0y0pRynoq+XZK5XWsr6cuX02Le3+RD/0Nuvc/8vsJUa595KHUjY+nvpwEwcdTSQuYUymIqsMw55yhgiH0MEGzgeMa/i6mhXI5f+bmm1Df07inQsqrKGA0fjal/inw6izIUECQjuAqIOg1hVhhKEOOpS99Ot/t6p65l4IiV6S561z++PC+tPnud9O4fButXZIzu+8COTKgEacBqyAeM31nyMLnNEJJz3cp/zAfuXWL7jsZOy6k/O7Dx9O4fwEsVvEnLgb2nUC2oYhJuRKJAjDHLm2Pydzmm9iqp/Njty5X/F2E7i62airXHjyT+vFFnEpbirDmqiAn18q187T1vTmHH84nKwJF89zCBmoPJRdcdmW1R86ytnKmErmLoLyz7OXvD+9Kb43OIcMp82EqDE2Tu2JEnI152RHE63CZu1GLgBGRJnZT/tVXhV7o8urpfOQf79ZYW4SZhaiIG6OXdDslqAY2YrpAydv1irudTHc2HDYj7/gIWTEMpih2aPVAwnR1rvvA0VnFzD61bmgnDrNbyuAls4CWUKR28xYSRJKgFRFz4FuMTjQpuNNDRRKJL+Vwv/mvc5Fv67xtIeW3H8I1gdPJazAunkC8eEAoZvE/JVdWpgCjsHlG2sMcRFWkJ2nxdskogaMZjt7lVHlxedtrpm2KQLo78cJmEyMzLAzUnk4EBiHJRDf+XNa1SNhOkjcptfhJX61cNeknstiarp87mZ9Yn7ibTeyInhO8xZYyXYR4gYKTZb9iVJ0rtS7pjdzlr+WF+z6Om+lHuzR3FOBrbqtxFEMxmdICcDY5sjT6vuQ+9S+UK/v2RX7O8+0ivYeHnZ4T0HqbIzj9eB6zmxpIVneCCl9TxDOgT2X+S/lzb/2Jax9vlGurX8QbwWuI8KDCRACFtHj0VecdpPyw62yAjrWWjFvzxh1wTfzTqBi9N6Xxa7jhL5GscjCAO7YFKRr17Bym8OFC2FL+0j126xGHTkz92srP8OT+SiQgsYonmgF1IMyiGxcG59oGCht1C+mRfGSk97Pm1Np8lkXQLQLHzCJMjtmjNb5WqNmxc7Pv9yW/J3TlZALx5Bk5OetOJn11tsRYohlL/UZ51hQ4CyhoN3J5yvprhTOoBY/docJ0AisBwfzb4pPKHvlsc8Aze6/UwntjHN92nFysOR4EPoGLassYnK8sPUS978j4BCrEqY0+eFB1xE9MD2GBcIwEMnsLqaNIAtDfX52nhLy34oWwABVfixp2py2QWnvdYeDc9Xf6E5S8kHLC+VAnogyshJgZSPZtZNqCBLEqpswuBA1bduo1vvIQjGF4y6kGMmeMyIVOsWHi2GcrRJ/s8KHIOOJmA8E3wshbVsAwwqDAvF1a0XY38wb0SrC7lO95k4KFzQhhp5aW3oTI4UQnEVhZIldbYdGMlPvHy6/2r3T6eFrKgseKjgqsToGYBpPwDRbDOjgUDI0VTSMDMfHaj3dzOTVKsVMLLjqV6SC58SSegzlD1tLyYFcdo2YujDffPdbxpZDO/DMOFkSv1opmASnaNRRcJzGBVxGG2/46yXZqRU66tnKDVyHRYPq1csWQ7njzMLa/HIzOsHj7bOCdUDcMLqBOsCjAUNRHgcIznXpxZ6qQcuXxeXycXRIGbopMPE9Z5SLbQbaWQgebEaeMPxyGsxyKLh3soDtgZAYAXWtgZCaYSZVYRjkoOItAGrd5A+TTTRWS7vtDvT7Im3kViXMU0MjMFz6W2/zJR1DHpz4fwCtKWanBFHa7AwlaIRE4qiosFEMpFNjLLXn6WTLqlksZy18kBXRyjAG8o2s+ObvN3C2fNVAGHnCxp6LvnepWkYwIEQbRJmsYO49MItEkdRdMRs+KHU/vSL+hHQn8gDMprsu2IHKxNVAiQzQpBjMtl+dFWtwnDA4WAgc7feRL2YMIUWEMIleVg8/304XMdXvz2G7xSt8eSNjgaJblMMpD42qCJiep0I/3+vUAKC6dYJVOTpQgUGYCpTOwFSbUcFCRcp4upM84tWDzGFvxykS757ed0FIYv71UvNkZL63jYs+3a4eVxNKYKCfEbeaQgwzDhK7O2A88wWlqxzj1+AqVRKF1X9rb+FFM6Aw/nZ94K0yfGW7zGuF3sQqsXYDIOa6ZuJi5F2GHs/4RFgITB0Z+OT9q9uGID3eH6ioKIY7hYi0Ho0hReqvDoC7HmWEFpb92eFhfNw/3w12I4PijVneINlkkdT/zwdF9lHzcfwOfrz9LG0e5sudRvBp8yxZwZNVb8GyAEWd+LeSuBlEhpfkYJ+OJRlzP5eX9X0dPLuixD5iCRfeJ3ab7EdBM3ClenHFbtHyMg7/3EOLn+LZtE1fjV5Fjl5g5IcokXguQkcpGkOyK0G/BdzmfyuWV/StplG7AtjAVNICMW8FUIjXW1d80w7r6Oo4t5fuRMYbSCuAcKptRFATbFXnxoGG5/G7W+EDcmN+98sEuH3obv0/ktUpK+RgWWk0kHn9SI5F3nw72z5LLk2Qcb+4qWjG8wMhlXlEMc3gRgVOZVIbCdw9N4XVFPHKt5S+8zYsdoy+XNONAo3XEOj7UAEvdhUGmXf6OjXOXvrC9Cs/v6C93rzKHFcFbh8lct3j6yGgebmMBzOl4E2XDFY3vpI03PuquPlTu9P9EQL57RTW02/BOaiGZkSYJYGUEaLcd+c3c6ic/nw/9Hj/mwPTKpxbG//7zy/go8GmgNVSAu4dsQSy+dZWpbCcIkswA5IlTY35x18fysdGb2hH+sIKtOk9HDZKRI1b18whULispTHEK0VUdI5hFCN89F0VIbQU9Jw6wT5KjRwzHe2PCj42qMnhI7vJ5FkGknVqUCn4pynkkElwzo6oZpkFlgRhMw4mFzHku9e9o3RzmUh507JXgLtRQLuhUCrlplMfDM2k0N98/E+FrIfnIzTfR8edtOxncEzl7kq5hIVBtJt92sjJmmnHu+k8RkQqff/pNfJ9MP2AFhg1yzYmFbSiVzGexlZs3GHJQDgbons/HknaDGSIcZTy09u2D4+twXw1SBIaTBbZboAGY0IdEHJTIdCB1riv5J1zhh5tv4kfC04xlLvQ1vyo02LAZ8ai1Nu0mPn8cyF9OtyJCcIx1Kr9ePo7iL2J3ZGNsSz50h868PpSkST5RNPRBeugygO4ftlgz5lY8dRyR32R8MZ3TyfkTafaX2HJ8bP0ycp01ODXcB5BCNDWQUaltCjBNg/AiTA90rOsOzMB7jvBXKmBjDYFJzm4tgnmcVlAZ5vFL9/8UzE9FEBbDfwRQV4dkaS2hVzyBozP9CLMADXzYOTrE3S8eusIEPqcLiyfTkxXcCPVib3QSu+6B08h71ShChYVOp+rYXCuui4fhsINGMsiwxpAJiWIp02Q1xg5gxcI50ARgry6spNOmmD7OLIS/1c3NPXAU4S+om4qHyHU3prtXicFHu9YQYWrqqp6NcT7Sye668HM7HC/MryT8fphmfzkevnebxy/uOQMG30d8boO5alYPVZy0JMdGytFrDv9I4Gv5VLjHhI9Ujmc2RDm7cCL9IOCzZuJ2NMovdx/HA/UF9HQ1iCoNDjUI00rpxIKfsfMC7ZSMgukuM1iYG4Mo5s3cpae3u7C3Izzz1NrqnJ/4z+VucfEACPwIX9uPttpZnIpoDVGhv9rEUrMXUN2JtwJGzMHnxE6LYIyIXePtROBvEuM7+JGl528qfM2xXTEi7U2A3bUsfE+zXWg6H8msAL5BnV9YXHom3p/CvJP5ngqJwCyIv0/gAYqv9vGteMn6b06yswAOkLRpOAVZMAc6sIHq1vBFxaWFxf5S+8ohh//h8L4KafPwq31+K57K5mGQP4hvZw5gXgFp+z0kp3W8f93OJb+OnbmOnbo6t4T/eIYPRW2ce5X/C3wonn0uNoJvAAAAAElFTkSuQmCC)
    no-repeat;
  background-size: 12px;
  background-position-y: 2px;
  margin-bottom: 8px;
}
.el-step {
  margin: 40px auto;
}

.order-info-container {
  width: 80%;
  margin: 0 auto 40px auto;
}

.order-info-status {
  height: 120px;
  background: #fffbf2;
  display: flex;
  align-items: center;
  margin-bottom: 40px;
}

.pay-icon {
  width: 40px;
  height: 40px;
  margin-left: 50px;
}

.pay-icon > img {
  width: 100%;
  height: 100%;
}

.pay-info {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
}

.pay-info-status {
  color: #faaf00;
  font-size: 24px;
  font-weight: 700;
}

.pay-info-desc {
  color: black;
  font-size: 16px;
}

.pay-info-desc > span {
  font-size: 16px;
  color: #faaf00;
  margin: 5px 5px;
}

.order-info-status.finish {
  background: #f4fff8;
}

.order-info-status.invalid {
  background: #ffe0e0;
}

.pay-info-status.finish {
  color: #26ce61;
}

.pay-info-status.invalid {
  color: #faaaaa;
}

.order-info-header {
  display: flex;
  justify-content: space-between;
}

.order-info-header > span > div {
  color: #f03d37;
}

.order-info-helper {
  color: #f03d37;
  font-size: 13px;
  display: flex;
  margin-bottom: 8px;
}

.order-table {
  width: 100%;
  border: 1px solid #e5e5e5;
  border-spacing: 0;
  margin-bottom: 60px;
}

.order-table thead {
  background-color: #f7f7f7;
}

.order-table thead th {
  font-size: 16px;
  color: #333;
  width: 25%;
  padding: 14px 0;
  font-weight: 400;
}

.order-table td {
  text-align: center;
  padding: 20px 0;
  color: #333;
}

.order-table .movie-name {
  font-size: 16px;
}

.order-table .showtime {
  font-size: 14px;
  color: #ffb400;
}

.order-table .cinema-name {
  font-size: 14px;
}

.order-table .hall {
  display: inline-block;
  font-size: 14px;
  color: #424242;
  font-weight: 700;
  margin-right: 10px;
  vertical-align: top;
}

.order-table .seats {
  display: inline-flex;
  text-align: left;
  flex-wrap: wrap;
}

.order-table .seats span {
  margin-right: 5px;
}

.order-info-area {
  display: flex;
  justify-content: space-between;
}

.order-cinema-name {
  font-weight: 700;
}

.order-cinema-info {
  display: flex;
  flex-direction: column;
  margin-top: 15px;
}

.submit-order {
  margin-top: 40px;
}

.submit-order span {
  color: #f03d37;
  font-size: 32px;
  font-weight: 700;
}

.submit-order span:before {
  content: "￥";
  font-size: 20px;
}
.el-steps >>> .el-step__head.is-finish {
  color: #ffb400;
  border-color: #ffb400;
}
.el-steps >>> .el-step__title.is-finish {
  color: #ffb400;
}
.el-steps >>> .el-step__head.is-process {
  color: #c0c4cc;
  border-color: #c0c4cc;
}
.el-steps >>> .el-step__title.is-process {
  color: #c0c4cc;
}
</style>
    DivForm