import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user:{
      url: localStorage.photo,
      isLogin: false
    },
    movie:{
      movieId:0
    }
  },
  mutations: {
    setUrl(state,url){
      state.user.url=url;
    },
    setMovieId(state,movieId){
      state.movie.movieId=movieId;
    },
    setLogin(state,login){
      state.user.isLogin=login;
    }
  },
  actions: {
  },
  modules: {
  }
})
