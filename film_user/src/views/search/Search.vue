<template>
  <div>
    <div class="searchContainer">
      <el-input v-model="key" class="searchBar" placeholder="找电影、影人、影院"></el-input>
      <el-button id="searchBtn" icon="el-icon-search" type="primary" circle @click="searchHandle"></el-button>
    </div>
    <div>
      <el-menu
        :default-active="$route.path"
        class="nav-menu"
        mode="horizontal"
        :router="true"
        background-color="#47464a"
        text-color="#999999"
        active-text-color="#ffb400"
      >
        <el-menu-item :index="item.path" v-for="item in menuList" :key="item.id">{{item.name}}</el-menu-item>
      </el-menu>
    </div>
    <div>
      <router-view :key="key"></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      menuList: [
        {
          id: "1",
          name: "电影",
          path: "/search/movieSearch",
        },
        {
          id: "2",
          name: "影院",
          path: "/search/cinemaSearch",
        },
      ],
      key: this.$route.query.key,
    };
  },
  created() {},
  methods: {
    searchHandle() {
      this.$router.push(this.$route.path + "?key=" + this.key);
    },
  },
  watch: {
    $route() {
      this.key = this.$route.query.key;
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
  font-size: 16px;
}

.el-menu-item {
  font-size: 16px;
}

.el-menu-item:hover {
  color: #ffffff !important;
}

.searchContainer {
  padding: 30px 0;
  display: flex;
  background: #47464a;
  justify-content: center;
}

.searchBar {
  width: 630px;
}

.searchBar >>> input {
  padding-right: 0;
  border-radius: 50px;
  width: 630px;
  height: 50px;
  border-color: #dcdfe6;
}

#searchBtn {
  transform: translate(-100%, 0);
  width: 50px;
  height: 50px;
  background: #ffb400;
  border-color: #ffb400;
}
</style>