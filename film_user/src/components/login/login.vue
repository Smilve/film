<template>
  <div>
    <div class="login" v-if="$store.state.user.isLogin">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <el-avatar :src="this.global.base+this.$store.state.user.url" :size="50"></el-avatar>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item class="drop" command="userMenu">个人中心</el-dropdown-item>
          <el-dropdown-item class="drop" command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="logout" v-else>
      <el-button class="yellow" type="primary" @click="handleLogin">登录</el-button>
    </div>
    <!-- 登录 -->
    <el-dialog
      v-loading="loading"
      :visible.sync="loginDialogVisible"
      width="25%"
      @close="loginDialogClosed"
    >
      <el-form :model="formData" :rules="loginRules" ref="loginForm" label-width>
        <div class="input">
          <el-form-item label prop="username">
            <el-input v-model="formData.username" placeholder="用户名" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item label prop="password">
            <el-input
              v-model="formData.password"
              placeholder="密码"
              show-password
              prefix-icon="el-icon-lock"
            ></el-input>
          </el-form-item>
        </div>

        <el-form-item class="btn">
          <el-button class="yellow" type="primary" @click="onSubmit">登录</el-button>
          <el-button @click="loginDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
      <div class="toRegister">
        <el-link type="info" :underline="false" @click="handleToRegister">没有账号？去注册吧</el-link>
      </div>
    </el-dialog>
    <!-- 注册 -->
    <el-dialog
      v-loading="loading"
      :visible.sync="registerDialogVisible"
      width="27%"
      @close="registerDialogClosed"
    >
      <el-form :model="registerData" :rules="registerRules" ref="registerForm" label-width>
        <div class="input2">
          <el-row :gutter="20">
            <el-form-item label prop="username">
              <el-col :span="22">
                <el-input
                  v-model="registerData.username"
                  placeholder="用户名"
                  prefix-icon="el-icon-user"
                ></el-input>
              </el-col>
              <el-col :span="2">
                <i class="el-icon-success color-green" v-if="corretData.username"></i>
              </el-col>
            </el-form-item>
          </el-row>
          <el-row :gutter="20">
            <el-form-item label prop="password">
              <el-col :span="22">
                <el-input
                  v-model="registerData.password"
                  placeholder="密码"
                  show-password
                  prefix-icon="el-icon-lock"
                ></el-input>
              </el-col>
              <el-col :span="2">
                <i class="el-icon-success color-green" v-if="corretData.password"></i>
              </el-col>
            </el-form-item>
          </el-row>
          <el-row :gutter="20">
            <el-form-item label prop="cpassword">
              <el-col :span="22">
                <el-input
                  v-model="registerData.cpassword"
                  placeholder="确认密码"
                  show-password
                  prefix-icon="el-icon-lock"
                ></el-input>
              </el-col>
              <el-col :span="2">
                <i class="el-icon-success color-green" v-if="corretData.cpassword"></i>
              </el-col>
            </el-form-item>
          </el-row>
          <el-row :gutter="20">
            <el-form-item label prop="phone">
              <el-col :span="22">
                <el-input
                  v-model="registerData.phone"
                  placeholder="手机号"
                  prefix-icon="el-icon-mobile-phone"
                ></el-input>
              </el-col>
              <el-col :span="2">
                <i class="el-icon-success color-green" v-if="corretData.phone"></i>
              </el-col>
            </el-form-item>
          </el-row>
          <el-row :gutter="20">
            <el-form-item label prop="email">
              <el-col :span="22">
                <el-input
                  v-model="registerData.email"
                  placeholder="邮箱"
                  prefix-icon="el-icon-message"
                ></el-input>
              </el-col>
              <el-col :span="2">
                <i class="el-icon-success color-green" v-if="corretData.email"></i>
              </el-col>
            </el-form-item>
          </el-row>
        </div>

        <el-form-item class="btn">
          <el-button type="primary" class="yellow" @click="onRegister">注册</el-button>
          <el-button @click="registerDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
      <div class="toRegister">
        <el-link type="info" :underline="false" @click="handleToLogin">已有账号？立即登录</el-link>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "login",

  data() {
    //这里存放数据
    var checkusername1 = (rule, value, callback) => {
      if (value == null || value == "") {
        callback(new Error("用户名不能为空"));
      }
      var len = value.length;
      const regChinese = /.*[\u4e00-\u9fa5].*/;
      if (len == 0) {
        callback(new Error("用户名不能为空"));
      } else if (len < 3 || len > 10) {
        callback(new Error("长度在 3 到 10 个字符"));
      } else if (value.indexOf(" ") != -1) {
        callback(new Error("用户名不能含有空格"));
      } else if (regChinese.test(value)) {
        callback(new Error("用户名不能含有中文字符"));
      } else {
        return callback();
      }
    };
    var checkusername = async (rule, value, callback) => {
      if (value == null || value == "") {
        callback(new Error("用户名不能为空"));
      }
      this.corretData.username = false;
      const regChinese = /.*[\u4e00-\u9fa5].*/;
      var len = value.length;
      if (len == 0) {
        callback(new Error("用户名不能为空"));
      } else if (len < 3 || len > 10) {
        callback(new Error("长度在 3 到 10 个字符"));
      } else if (value.indexOf(" ") != -1) {
        callback(new Error("用户名不能含有空格"));
      } else if (regChinese.test(value)) {
        callback(new Error("用户名不能含有中文字符"));
      } else {
        const { data: res } = await axios.post("/user/check", {
          username: value,
        });
        if (res.code !== 200) {
          callback(new Error("此用户名已被注册"));
        } else {
          this.corretData.username = true;
          return callback();
        }
      }
    };
    var checkPassword = (rule, value, callback) => {
      this.corretData.password = false;
      var len = value.length;
      if (len == 0) {
        callback(new Error("密码不能为空"));
      } else if (len < 6 || len > 15) {
        callback(new Error("长度在 6 到 15 个字符"));
      } else if (value.indexOf(" ") != -1) {
        callback(new Error("密码不能含有空格"));
      } else {
        this.corretData.password = true;
        return callback();
      }
    };
    var checkcPassword = (rule, value, callback) => {
      this.corretData.cpassword = false;
      var len = value.length;
      if (len == 0) {
        callback(new Error("密码不能为空"));
      } else if (len < 6 || len > 15) {
        callback(new Error("长度在 6 到 15 个字符"));
      } else if (value.indexOf(" ") != -1) {
        callback(new Error("密码不能含有空格"));
      } else if (value !== this.registerData.password) {
        callback(new Error("两次输入密码不一致"));
      } else {
        this.corretData.cpassword = true;
        return callback();
      }
    };
    var checkPhone = (rule, value, callback) => {
      this.corretData.phone = false;
      const regPhone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57]|19[0-9])[0-9]{8}$/;
      if (value.length == 0) {
        callback(new Error("手机号不能为空"));
      } else if (!regPhone.test(value)) {
        callback(new Error("请输入合法的手机号"));
      } else {
        this.corretData.phone = true;
        return callback();
      }
    };
    var checkEmail = (rule, value, callback) => {
      this.corretData.email = false;
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (value.length == 0) {
        callback(new Error("邮箱不能为空"));
      } else if (!regEmail.test(value)) {
        callback(new Error("请输入合法的邮箱"));
      } else {
        this.corretData.email = true;
        return callback();
      }
    };
    return {
      loading: false,
      loginDialogVisible: false,
      registerDialogVisible: false,
      formData: { username: "user", password: "123456" },
      registerData: {
        username: "",
        password: "123456",
        cpassword: "123456",
        email: "test@qq.com",
        phone: "19822964632",
      },
      corretData: {
        username: false,
        password: false,
        cpassword: false,
        email: false,
        phone: false,
      },
      loginusername: localStorage.username,
      loginRules: {
        username: [
          {
            validator: checkusername1,
            trigger: "blur",
          },
        ],
        password: [
          {
            validator: checkPassword,
            trigger: "blur",
          },
        ],
      },
      registerRules: {
        username: [
          {
            validator: checkusername,
            trigger: "blur",
          },
        ],
        password: [
          {
            validator: checkPassword,
            trigger: "blur",
          },
        ],
        cpassword: [
          {
            validator: checkcPassword,
            trigger: "blur",
          },
        ],
        email: [
          {
            validator: checkEmail,
            trigger: "blur",
          },
        ],
        phone: [
          {
            validator: checkPhone,
            trigger: "blur",
          },
        ],
      },
    };
  },

  methods: {
    //登录
    onSubmit() {
      this.$refs.loginForm.validate(async (valid) => {
        if (!valid) return;
        this.loading = true;
        const { data: res } = await axios.post("/user/login", this.formData);
        if (res.code === 200) {
          const data = res.data;
          localStorage.setItem("token", data.token);
          localStorage.setItem("user", JSON.stringify(data));
          localStorage.setItem("userName", data.userName);
          localStorage.setItem("photo", data.photo);
          this.$store.commit("setUrl", data.photo);
          this.$store.commit("setLogin", true);
          this.loading = false;
          this.loginDialogVisible = false;
          this.$message.success("登录成功");
        } else if (res.code === 2002) {
          setTimeout(() => {
            this.loading = false;
            this.$message.error("用户名或密码错误");
            return;
          }, 1000);
        } else if (res.code === 2003) {
          setTimeout(() => {
            this.loading = false;
            this.$message.error("用户已被冻结");
            return;
          }, 1000);
        }
      });
    },
    //注册
    onRegister() {
      this.$refs.registerForm.validate(async (valid) => {
        if (!valid) return;
        this.loading = true;
        console.log(this.registerData);
        const { data: res } = await axios.post(
          "/user/register",
          this.registerData
        );
        if (res.code === 200) {
          setTimeout(() => {
            this.loading = false;
            this.registerDialogVisible = false;
            this.loginDialogVisible = false;
            this.$message.success("注册成功，请登录！");
          }, 1000);
        } else {
          setTimeout(() => {
            this.loading = false;
            this.$message.error("注册失败");
            return;
          }, 1000);
        }
      });
      setTimeout(() => {
        this.loginDialogVisible = true;
      }, 3000);
      this.formData.username = this.registerData.username;
      this.formData.password = this.registerData.password;
    },

    handleLogin() {
      this.loginDialogVisible = true;
    },
    handleLogout() {
      localStorage.clear();
      // this.getNewsList();
      this.checkIsLogin();
    },
    loginDialogClosed() {
      this.$refs.loginForm.resetFields();
    },
    registerDialogClosed() {
      this.$refs.registerForm.resetFields();
      for (let key in this.corretData) {
        this.corretData[key] = false;
      }
    },
    async checkIsLogin() {
      if (localStorage.getItem("token") != null) {
        this.$store.commit("setLogin", true);
        const user = JSON.parse(localStorage.user);
      } else {
        this.$store.commit("setLogin", false);
      }
      //发起请求测试用户token是否失效
      if (this.$store.state.user.isLogin) {
        const data = await axios.get("/order/test");
      }
    },
    getLoginusername() {
      this.loginusername = localStorage.username;
    },
    handleToRegister() {
      this.loginDialogVisible = false;
      this.registerDialogVisible = true;
    },
    handleToLogin() {
      this.registerDialogVisible = false;
      this.loginDialogVisible = true;
    },
    getLoginDialogVisible() {
      if (!localStorage.token && this.$route.query.login != undefined) {
        this.loginDialogVisible = true;
        this.$message.warning("请登录后发表评论");
      }
    },

    //去个人中心或退出
    handleCommand(command) {
      if (command === "logout") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.$store.commit("setLogin", false);
          localStorage.clear();
          this.$message.success("退出成功");
          this.checkIsLogin();
        }, 1000);
        return this.$router.push("/welcome");
      } else {
        this.$router.push("/" + command);
      }
    },
  },
  created() {
    this.checkIsLogin();
    //this.getLoginDialogVisible();
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style scoped>
/* @import url(); 引入公共css类 */
html,
body {
  overflow: hidden;
  margin: 0px;
  padding: 0;
  width: 100%;
  height: 100%;
}
.el-container {
  margin: 0;
  padding: 0;
  background-color: rgba(253, 247, 247, 0.966);
}
.el-header {
  background-color: #7a7d83;
  color: #333;
  text-align: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 999;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 5px;
}
.header {
  margin-left: 2em;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header span {
  font-weight: 700;
  letter-spacing: 3px;
  font-size: 18px;
  text-shadow: 2px 2px rgb(169, 175, 141);
}
.header img {
  width: 50px;
  height: 50px;
  border: 2px solid #eee;
  border-radius: 50%;
  background-color: rgba(223, 201, 201, 0.934);
  margin-right: 10px;
}
.el-main {
  background-color: #b7babe;
  color: #333;
  text-align: center;
  line-height: 160px;
  width: 60%;
  margin: 0 auto;
  margin-top: 52px;
}
.box-card {
  background-color: #eee;
  width: 550px;
  margin: 0 auto;
  margin-bottom: 20px;
  text-align: center;
  font-size: 20px;
}
.box-card:hover {
  transform: translate3d(-2px, -2px, 0);
}
a {
  text-decoration: none;
  color: #9a9da2;
}
.el-timeline-item {
  text-align: left;
  color: rgb(143, 37, 37);
}
.el-dialog {
  border-radius: 10%;
}
.input {
  width: 80%;
  margin: 0 auto;
  margin-top: 20px;
  margin-bottom: 40px;
}
.input2 {
  width: 90%;
  margin: 0 auto;
  margin-top: 20px;
  margin-bottom: 20px;
}
.btn {
  display: flex;
  justify-content: flex-end;
}
.login span {
  font-size: 15px;
  font-weight: 700;
  color: rgb(210, 211, 183);
  margin-right: 10px;
}
.logout {
  margin-right: 20px;
}
.toRegister {
  margin-top: 20px;
}
.toRegister a {
  font-size: 12px;
}
.drop:hover {
  color: #ffb400 !important;
}
.yellow {
  background-color: #ffb400;
  border-color: #ffb400;
}
</style>
