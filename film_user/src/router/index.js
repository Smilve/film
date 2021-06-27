import Vue from 'vue'
import VueRouter from 'vue-router'
import { Message }from 'element-ui'


Vue.use(VueRouter)

const routes = [
  {
    path:'/', redirect: 'welcome'
  },
  {
    path: '/welcome',
    component:() => import( '../views/Welcome.vue'),
    redirect: { name: 'home'},
    children: [
      { path: '/home/', name: 'home', component: () => import( '../views/Home.vue') },
      {
        path: '/userMenu',
        name: 'userMenu',
        component: ()=>import('../views/user/UserMenu.vue'),
        redirect: {name: 'userInfo'},
        children:[
          {path: '/userInfo', component: ()=>import('../views/user/UserInfo.vue'),name:'userInfo'},
          {path: '/orderInfo', component: ()=>import('../views/user/OrderInfo.vue'), name:'orderInfo'}
        ]
      },
      {
        path: '/movie/',
        component:()=>import('../views/movie/Movie.vue'),
        name: 'movie',
        redirect: {name: '正在热映'},
        children: [
          { path: 'ingMovie', name:'正在热映', component:()=>import('../views/movie/IngMovie.vue') },
          { path: 'willMovie', name: '即将上映', component:()=>import('../views/movie/WillMovie.vue') },
          { path: 'classicMovie', name: '经典影片', component:()=>import('../views/movie/ClassicMovie.vue') }
          ]
      },
      {
        path: '/movieInfo/:movieId/:will',
        name: '电影详细信息',
        component:()=>import('../views/movie/MovieInfo.vue') 
      },
      {
        path: '/cinema',
        component:()=>import('../views/cinema/Cinema.vue'),
        name: 'cinema'
      },
      {
        path: '/cinemaInfo/:cinemaId/',
        name: '影院详细信息',
        component:()=>import('../views/cinema/CinemaInfo.vue'),
      },
      {
        path: '/rank/',
        component: ()=>import('../views/rank/RankList.vue'),
        name: 'rank',
        redirect: '/rank/topMovieList',
        children: [
          { path: 'topMovieList', name: 'Top10榜', component: ()=>import('../views/rank/TopMovieList.vue')}
        ]
      },
      {
        path: '/seatChoose/:scheduleId',
        component: ()=>import('../views/order/SeatChoose.vue'),
        name: 'seatChoose',
      },
      {
        path: '/orderDetail/:orderId',
        component: ()=>import('../views/order/OrderDetail.vue'),
        name: 'orderDetail'
      },
      
      {
        path: '/search/',
        component: ()=>import('../views/search/Search.vue'),
        name: 'search',
        redirect: {name:'movieSearch'},
        children: [
          {
            path:'movieSearch',
            component:  ()=>import('../views/search/MovieSearch.vue'),
            name: 'movieSearch',
          },
          {
            path: 'cinemaSearch',
            component:()=>import('../views/search/CinemaSearch.vue'),
            name: 'cinemaSearch'
          }
        ]
      }
    ]
  },
  {
    path: '/pay',
    component: ()=>import('../views/order/Pay.vue'),
    name: 'pay'
  },
  {
    path: '/*',
    component: ()=>import("../views/Error404")
  }
]

//去除url路径的#
const router = new VueRouter({
  mode: 'history',
  // base:'/film_user/',
  routes
})


//挂载路由导航守卫，类似权限验证
router.beforeEach((to, from, next) =>{
  //to 将要访问的路径
  //from 从哪个页面来
  //next 一个放行函数

  if(to.path == '/login' || to.path == '/404' || to.path == '/register') return next();

  //获取token
  // const token = window.sessionStorage.getItem("token")
  // if(!token){
  //   Message.error('抱歉，请先登录')
  //   return next('/login');
  // }
  next();
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location){
  return originalPush.call(this, location).catch(err => err)
}
export default router
