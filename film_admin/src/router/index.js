import Vue from 'vue';
import Router from 'vue-router';



Vue.use(Router);
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题(push到自己的错误)
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        
        {
            path: '/login',
            component: () => import( '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '/',
            // component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            component:  () => import('../components/admin/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import( '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/user',
                    component: () => import( '../components/admin/children/UserList.vue'),
                    meta: { title: '用户列表' }
                },
                {
                    path: '/movie',
                    component: () => import( '../components/admin/children/MovieList.vue'),
                    meta: { title: '电影列表' }
                },{
                    path: '/order',
                    component: () => import( '../components/admin/children/OrderList.vue'),
                    meta: { title: '订单列表' }
                },{
                    path: '/commet',
                    component: () => import( '../components/admin/children/CommetList.vue'),
                    meta: { title: '评论列表' }
                },
                {
                    path: '/cinema',
                    component: () => import( '../components/admin/children/CinemaList.vue'),
                    meta: { title: '影院列表' }
                },
                {
                    path: '/hall',
                    component: () => import( '../components/admin/children/HallList.vue'),
                    meta: { title: '影厅列表' }
                },
                {
                    path: '/movieInterval',
                    component: () => import( '../components/admin/children/MovieIntervalList.vue'),
                    meta: { title: '影厅列表' }
                },
                {
                    path: '/schedule',
                    component: () => import( '../components/admin/children/ScheduleList.vue'),
                    meta: { title: '排片列表' }
                },
                {
                    path: '/cinemaChart',
                    component: () => import( '../components/admin/children/CinemaChart.vue'),
                    meta: { title: '影院图表' }
                },
                {
                    path: '/movieChart',
                    component: () => import( '../components/admin/children/MovieChart.vue'),
                    meta: { title: '电影图表' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: () => import('../components/page/Permission.vue'),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: () => import('../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import('../components/page/403.vue'),
                }
            ]
        },
        
        {
            path: '*',
            redirect: '/404'
        }
    ],
    // 去除路由跳转的 /#
    mode:'history',
    // base:'/admin/'
});
