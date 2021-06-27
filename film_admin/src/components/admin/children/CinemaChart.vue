<template>
    <div>
        <div class="container">
            <div class="schart-box">
                <div class="content-title">电影院</div>
                <schart class="schart" canvasId="line" :options="options1"></schart>
            </div>
            <div class="schart-box">
                <div class="content-title"></div>
                <div class="select">
                    <el-select v-model="cinemaId" placeholder="请选择" @change="changeSelect">
                        <el-option
                            v-for="item in cinemaList"
                            :key="item.cinemaId"
                            :label="item.cinemaName"
                            :value="item.cinemaId"
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
    name: 'cinemacharts',
    components: {
        Schart
    },
    data() {
        return {
            options1: {
                type: 'line',
                title: {
                    text: '电影院Top3售票最近七天电影票房(元)'
                },
                bgColor: '#fbfbfb',
                labels: ['1', '2', '3', '4', '5', '6', '7'],
                datasets: [
                    {
                        label: '太平洋影城',
                        data: [234, 278, 270, 190, 230, 230, 230]
                    },
                    {
                        label: '万达影城',
                        data: [164, 178, 150, 135, 160, 160, 160]
                    },
                    {
                        label: '大地影城',
                        data: [114, 138, 200, 235, 190, 190, 190]
                    }
                ]
            },
            options2: {
                type: 'line',
                title: {
                    text: '指定电影院最近七天电影票房(元)'
                },
                bgColor: '#fbfbfb',
                labels: ['1', '2', '3', '4', '5', '6', '7'],
                datasets: [
                    {
                        label: '太平洋影城',
                        data: [234, 278, 270, 190, 230, 230, 230]
                    }
                ]
            },
            cinemaList: [
                {
                    cinemaId: '1',
                    cinemaName: '太平洋影城'
                },
                {
                    cinemaId: '2',
                    cinemaName: '万达影城'
                }
            ],
            cinemaId: '1'
        };
    },
    created() {
        this.setLabels();
        this.getTopCinema();
        this.getCinemaList();
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
        getTopCinema() {
            this.$http.get('/chart/topCinema').then((res) => {
                let data = res.data.data;
                for (let i = 0; i < 3; i++) {
                    this.options1.datasets[i].label = data[i].cinemaName;
                    this.options1.datasets[i].data = data[i].data;
                }
            });
        },
        getCinemaList() {
            this.$http.get('/chart/cinemaInfo').then((res) => {
                this.cinemaList = res.data.data;
            });
            this.cinemaId = this.cinemaList[0].cinemaId;
            this.$http.get('/chart/cinema/' + this.cinemaId).then((res) => {
                this.options2.datasets[0].label = res.data.data[0].cinemaName;
                this.options2.datasets[0].data = res.data.data[0].data;
            });
        },
        changeSelect(val) {
            this.$http.get('/chart/cinema/' + val).then((res) => {
                console.log(res.data.data);
                this.options2.datasets[0].label = res.data.data[0].cinemaName;
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