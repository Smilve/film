<template>
  <div class="top">
    <h1 class="top">收银台</h1>
    <div v-html="payaction" id="commit" ref="commit"></div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      payaction: "",
    };
  },
  created() {
    this.showPayPage();
  },
  methods: {
    showPayPage() {
      this.$nextTick(function () {
        //把刚才写在sessionStorage的页面显示在当前页面
        this.payaction = sessionStorage.getItem("payaction");
        sessionStorage.removeItem("payaction");
        setTimeout(() => {
          console.log(this.$refs.commit.children[0]);
          this.$refs.commit.children[0].submit();
        }, 0);
      });
    },
  },
};
</script>
<style scoped>
.top {
  opacity: 0;
  margin-top: 50px;
  text-align: center;
  vertical-align: middle;
  margin-bottom: 100px;
}
</style>