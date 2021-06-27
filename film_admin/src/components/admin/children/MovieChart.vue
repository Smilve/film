<template>
    <div>
        <div class="container">
            <div class="schart-box">
                <div class="content-title">电影</div>
                <schart class="schart" canvasId="line" :options="options1"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title"></div>
                <div class="select">
                    <el-select v-model="movieId" placeholder="请选择" @change="changeSelect">
                        <el-option
                            v-for="item in movieList"
                            :key="item.movieId"
                            :label="item.movieName"
                            :value="item.movieId"
                        ></el-option>
                    </el-select>
                </div>
                <schart class="schart" canvasId="line1" :options="options2"></schart>
            </div>
        </div>
    </div>
</template>

<script>
import Schart from 'vue-schart';
import moment from 'moment';
export default {
    name: 'moviecharts',
    components: {
        Schart
    },
    data() {
        return {
            options1: {
                type: 'line',
                title: {
                    text: '电影Top3售票最近七天票房(元)'
                },
                bgColor: '#fbfbfb',
                labels: ['1', '2', '3', '4', '5', '6', '7'],
                datasets: [
                    {
                        label: '你好，李焕英',
                        data: [234, 278, 270, 190, 230, 230, 230]
                    },
                    {
                        label: '唐人街探案3',
                        data: [164, 178, 150, 135, 160, 160, 160]
                    },
                    {
                        label: '人潮汹涌',
                        data: [114, 138, 200, 235, 190, 190, 190]
                    }
                ]
            },
            options2: {
                type: 'line',
                title: {
                    text: '指定电影最近七天票房(元)'
                },
                bgColor: '#fbfbfb',
                labels: ['1', '2', '3', '4', '5', '6', '7'],
                datasets: [
                    {
                        label: '你好，李焕英',
                        data: [234, 278, 270, 190, 230, 230, 230]
                    }
                ]
            },
            movieList: [
                {
                    movieId: '1',
                    movieName: '你好，李焕英'
                },
                {
                    movieId: '2',
                    movieName: '唐人街探案3'
                }
            ],
            movieId: '1'
        };
    },
    created() {
        this.setLabels();
        this.getTopMovie();
        this.getMovieList();
    },
    methods: {
        setLabels() {
            this.options1.labels = [
                this.getDate(6),
                this.getDate(5),
                this.getDate(4),
                this.getDate(3),
                this.getDate(2),
                this.getDate(1),
                this.getDate(0)
            ];
            this.options2.labels = [
                this.getDate(6),
                this.getDate(5),
                this.getDate(4),
                this.getDate(3),
                this.getDate(2),
                this.getDate(1),
                this.getDate(0)
            ];
        },
        getTopMovie() {
            this.$http.get('/chart/topMovie').then((res) => {
                let data = res.data.data;
                for (let i = 0; i < 3; i++) {
                    this.options1.datasets[i].label = data[i].movieName;
                    this.options1.datasets[i].data = data[i].data;
                }
                console.log(data);
                console.log(1);
            });
        },
        getMovieList() {
            this.$http.get('/chart/movieInfo').then((res) => {
                this.movieList = res.data.data;
            });
            this.movieId = this.movieList[0].movieId;
            this.$http.get('/chart/movie/' + this.movieId).then((res) => {
                this.options2.datasets[0].label = res.data.data[0].movieName;
                this.options2.datasets[0].data = res.data.data[0].data;
            });
        },
        changeSelect(val) {
            this.$http.get('/chart/movie/' + val).then((res) => {
                this.options2.datasets[0].label = res.data.data[0].movieName;
                this.options2.datasets[0].data = res.data.data[0].data;
            });
        },
        getDate(day) {
            return this.moment().subtract(day, 'days').format('MM-DD');
        }
    }
};
</script>

<style scoped>
.schart-box {
    display: inline-block;
    margin: 20px;
}
.schart {
    width: 600px;
    height: 400px;
}
.content-title {
    clear: both;
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}
.select {
    margin-left: 350px;
    margin-bottom: 10px;
}
</style>