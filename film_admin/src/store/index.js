import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user:{
      cinemaId:localStorage.cinemaId
    },
  },
  mutations: {
    setCinemaId(state,cinemaId){
      state.user.cinemaId=cinemaId;
    },
    
  },
  actions: {
  },
  modules: {
  }
})
