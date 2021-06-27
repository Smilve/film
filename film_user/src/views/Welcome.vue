<template>
  <el-container>
    <el-header>
      <div class="header-inner">
        <a href="/welcome" class="link">
          <img class="logo" style src="../assets/logo.png" />
          <span class="logoName">橘子电影</span>
        </a>
        <el-menu
          :default-active="activeUrl"
          class="nav-menu"
          mode="horizontal"
          :router="true"
          active-text-color="#ffb400"
          text-color="#000000"
          @select="handleSelect"
        >
          <el-menu-item :index="item.path" v-for="item in menuList" :key="item.id">{{item.name}}</el-menu-item>
        </el-menu>
        <div class="searchContainer">
          <el-input v-model="key" class="searchBar" placeholder="找电影、影院"></el-input>
          <el-button id="searchBtn" icon="el-icon-search" type="primary" circle @click="search"></el-button>
        </div>
        <login class="login"></login>
      </div>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer>
      <div class="footer-mini"></div>
      <div class="footer">
        <!-- <img style="width: 357px; height: 50px" src="../assets/register-footer.png" /> -->
      </div>
      <el-backtop class="backtop"></el-backtop>
    </el-footer>
  </el-container>
</template>

<script>
import login from "../components/login/login";
export default {
  components: {
    login,
  },
  name: "Welcome",
  data() {
    return {
      url: "",
      activeUrl: this.$route.path.substring(
        0,
        this.$route.path.indexOf("/", 1) === -1
          ? this.$route.path.length
          : this.$route.path.indexOf("/", 1)
      ),
      menuList: [
        {
          id: 1,
          name: "首页",
          path: "/home",
        },
        {
          id: 2,
          name: "电影",
          path: "/movie",
        },
        {
          id: 3,
          name: "影院",
          path: "/cinema",
        },
        {
          id: 4,
          name: "榜单",
          path: "/rank",
        },
      ],
      //搜索关键字
      key: "",
    };
  },
  watch: {
    $route() {
      this.activeUrl = this.$route.path.substring(
        0,
        this.$route.path.indexOf("/", 1) === -1
          ? this.$route.path.length
          : this.$route.path.indexOf("/", 1)
      );
    },
  },
  methods: {
    search() {
      let tmp = this.key;
      this.$router.push("/search/movieSearch?key=" + tmp);
      this.key = "";
    },
    handleCommand(command) {
      if (command === "logout") {
        window.sessionStorage.clear();
        return this.$router.push("/login");
      }
      this.$router.push("/" + command);
    },
    handleSelect(index, indexPath) {
      //设置点击导航菜单影院时，电影id为0，即查找所有影院
      if (index === "/cinema") {
        this.$store.commit("setMovieId", "0");
      }
    },
  },
};
</script>

<style scoped>
.logo {
  width: 70px;
  height: 65px;
  margin-top: 5px;
}
.link {
  position: relative;
}
.logoName {
  display: block;
  width: 100px;
  color: rgba(255, 180, 0, 0.8);
  font-size: 25px;
  font-weight: normal;
  transition: all 1s ease;
  position: absolute;
  top: 50%;
  margin-left: 80px;
  transform: translateY(-50%);
  font-weight: 700;
}
.logoName:hover {
  text-shadow: 2px 0px 1px yellow;
}
a {
  text-decoration: none;
}
.el-header {
  height: 80px !important;
  border-bottom: 1px solid #e6e6e6;
}

.header-inner {
  width: 75%;
  margin: 0 12.5%;
  display: flex;
}

.login {
  margin: 20px 0px 0px 15px;
}
.nav-menu {
  border-bottom: 0px solid #eee !important;
  height: 60px;
  margin: 9px auto;
}

.el-menu-item {
  font-size: 20px;
}

.el-menu-item:hover {
  color: #ffb400 !important;
}

.searchContainer {
  padding: 20px 0;
  display: flex;
}

.searchBar >>> input {
  border-radius: 50px;
  border-color: #dcdfe6;
}

#searchBtn {
  transform: translate(-100%, 0);
  background: #ffb400;
  border-color: #ffb400;
}

/* .el-dropdown-link:hover .icon-arrow { */
/* 定义动画的状态 */
/* -webkit-transform: rotate(180deg) scale(1);
  -moz-transform: rotate(180deg) scale(1);
  -o-transform: rotate(180deg) scale(1);
  -transform: rotate(180deg) scale(1);
} */

.footer-mini {
  border-top: 1px solid #eee;
  padding-top: 20px;
  text-align: center;
}

.footer {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-main {
  padding: 0px;
}

.backtop {
  color: #ffb400;
}
</style>