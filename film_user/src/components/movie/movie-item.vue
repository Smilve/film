<template>
  <div class="movie-item">
    <div v-on:mouseover="isHover = true" v-on:mouseleave="isHover = false">
      <a :href="movieInfoUrl">
        <img class="poster" :src="this.global.base + movieItem.photo" />
      </a>
      <div class="movie-item-hover" v-if="isHover">
        <a :href="movieInfoUrl">
          <img class="poster-hover" :src="this.global.base + movieItem.photo" />
          <div class="movie-hover-info">
            <div class="title-hover">
              <span class="name-tags">{{movieItem.movieName}}</span>
            </div>
            <div class="title-hover">
              <span class="name-tags">主演:</span>
              {{movieItem.actor}}
            </div>
            <div class="title-hover">
              <span class="name-tags">类型:</span>
              {{movieItem.type}}
            </div>
            <div class="title-hover">
              <span class="name-tags">地区:</span>
              {{movieItem.nation}}
            </div>
            <div class="title-hover">
              <span class="name-tags">语言:</span>
              {{movieItem.language}}
            </div>
            <div class="title-hover">
              <span class="name-tags">片长:</span>
              {{movieItem.time}}分钟
            </div>
          </div>
        </a>
      </div>
    </div>
    <div class="title-style">
      <a :href="movieInfoUrl">{{movieItem.movieName}}</a>
    </div>
    <div class="title-style score">{{movieItem.score}}</div>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "movie-item",
  props: {
    movieItem: Object,
  },
  data() {
    return {
      isHover: false,
      movieInfoUrl: "",
      movieDate: "",
    };
  },
  created() {
    this.movieDate = moment(this.movieItem.date).format("YYYY-MM-DD");
    //0为已上映电影，1为即将上映电影
    if (this.movieItem.score == null) {
      this.movieInfoUrl = "/movieInfo/" + this.movieItem.movieId + "/1";
    } else {
      this.movieInfoUrl = "/movieInfo/" + this.movieItem.movieId + "/0";
    }
  },
  watch: {
    movieItem() {},
  },
};
</script>

<style scoped>
.movie-item {
  margin-top: 30px;
  margin-left: 30px;
  display: inline-block;
  vertical-align: top;
  position: relative;
}

.poster {
  width: 160px;
  height: 220px;
}

.title-style {
  width: 160px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 16px;
  color: #333;
  margin-top: 10px;
}

.score {
  color: #ffb400;
  font-size: 24px;
  font-style: italic;
}

a {
  text-decoration: none;
  color: #333;
}

.movie-item-hover {
  position: absolute;
  width: 160px;
  height: 220px;
  z-index: 10;
  top: 0px;
  left: 0px;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 0 16px #fff, 0 0 6px rgba(0, 0, 0, 0.2);
}

.poster-hover {
  width: 160px;
  height: 220px;
}

.movie-hover-info {
  padding: 16px;
  height: 220px;
  box-sizing: border-box;
  position: absolute;
  z-index: 11;
  top: 0px;
  background: rgba(0, 0, 0, 0.6);
  width: 218px;
  color: #d1cece;
}

.title-hover:first-child {
  font-size: 14px;
  line-height: 16px;
  margin-top: 20px;
}

.title-hover {
  font-size: 12px;
  line-height: 14px;
  margin-top: 11px;
  margin-left: 10px;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.name-tags {
  color: #fff;
}
</style>