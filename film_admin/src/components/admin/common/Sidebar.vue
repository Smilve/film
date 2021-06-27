<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            text-color="#60627c"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-lx-people',
                    index: 'user',
                    title: '用户管理'
                },
                {
                    icon: 'el-icon-lx-crown',
                    index: 'movie',
                    title: '电影管理'
                },
                {
                    icon: 'el-icon-lx-record',
                    index: '3',
                    title: '影院管理',
                    subs: [
                        {
                            index: 'cinema',
                            title: '影院管理'
                        },
                        {
                            index: 'hall',
                            title: '影厅管理'
                        },
                        {
                            index: 'movieInterval',
                            title: '场次管理'
                        },
                        {
                            index: 'schedule',
                            title: '排片管理'
                        }
                    ]
                },
                {
                    icon: 'el-icon-lx-text',
                    index: 'order',
                    title: '订单管理'
                },
                {
                    icon: 'el-icon-lx-comment',
                    index: 'commet',
                    title: '评论管理'
                },
                {
                    icon: 'el-icon-pie-chart',
                    index: '6',
                    title: '统计分析',
                    subs: [
                        {
                            index: 'cinemaChart',
                            title: '影院票房'
                        },
                        {
                            index: 'movieChart',
                            title: '电影票房'
                        }
                    ]
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', (msg) => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
        this.checkRole();
    },
    methods: {
        checkRole() {
            const cinemaId = localStorage.cinemaId;
            console.log(cinemaId);
            if (cinemaId != 'null') {
                this.items = [
                    {
                        icon: 'el-icon-lx-home',
                        index: 'dashboard',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-lx-record',
                        index: '3',
                        title: '影院管理',
                        subs: [
                            {
                                index: 'hall',
                                title: '影厅管理'
                            },
                            
                            {
                                index: 'schedule',
                                title: '排片管理'
                            }
                        ]
                    },
                    
                    
                    {
                        icon: 'el-icon-pie-chart',
                        index: '6',
                        title: '统计分析',
                        subs: [
                            {
                                index: 'cinemaChart',
                                title: '影院票房'
                            },
                            {
                                index: 'movieChart',
                                title: '电影票房'
                            }
                        ]
                    }
                ];
            }
        }
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
