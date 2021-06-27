<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>
                <el-select v-model="query.state" placeholder="支付状态" class="handle-select mr10">
                    <el-option key="0" label="支付状态" value></el-option>
                    <el-option key="1" label="未支付" value="0"></el-option>
                    <el-option key="2" label="已支付" value="1"></el-option>
                    <el-option key="3" label="已失效" value="2"></el-option>
                    <el-option key="4" label="已退款" value="3"></el-option>
                </el-select>
                <el-input v-model="query.userName" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button icon="el-icon-refresh" @click="getData">刷新</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="orderId" sortable label="订单ID" width="70" align="center"></el-table-column>
                <el-table-column prop="userName" label="用户名" align="center"></el-table-column>
                <el-table-column prop="scheduleId" label="排片ID" align="center"></el-table-column>
                <el-table-column prop="number" label="票数" align="center"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
                <el-table-column prop="seat" label="座位"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">
                        <el-tag
                            v-if="scope.row.state==0 || scope.row.state==1"
                            :type="scope.row.state==1?'success':(scope.row.state==0?'warning':'')"
                        >{{scope.row.state==1?'已支付':'未支付'}}</el-tag>

                        <el-tag
                            v-else
                            :type="scope.row.state==2?'danger':(scope.row.state==3?'info':'')"
                        >{{scope.row.state==2?'已失效':'已退款'}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="date" :formatter="dateFormat" label="下单时间" width="180"></el-table-column>
                <el-table-column label="操作" width="80" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'basetable',
    data() {
        return {
            query: {
                state: '',
                userName: '',
                pageIndex: 1,
                pageSize: 5
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            pageTotal: 0
        };
    },
    created() {
        this.getData();
    },
    methods: {
        dateFormat: function (row, column) {
            var date = row[column.property];
            if (date == undefined) {
                return '';
            }
            return this.moment(date).format('YYYY-MM-DD HH:mm:ss');
        },
        // 获取 easy-mock 的模拟数据
        getData() {
            console.log(this.query);
            this.$http
                .post('/order/orders', this.query)
                .then((res) => {
                    console.log(res);
                    this.tableData = res.data.data.list;
                    this.pageTotal = res.data.data.totalCount;
                })
                .catch(() => {
                    // this.$message.error('用户过期,请重新登录！');
                    // setTimeout(() => {
                    //     this.$router.push('/login');
                    // }, 1000);
                });
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
        },

        // 删除操作
        handleDelete(index, row) {
            if (row.state == 1) {
                this.$message.error('不能删除已支付的订单！');
                return;
            }
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$http.delete('/order/del/' + row.orderId).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success('删除成功');
                            //this.tableData.splice(index, 1);
                            this.getData();
                            this.delList = [];
                        } else {
                            this.$message.error('删除失败');
                        }
                    });
                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            if (this.multipleSelection == '') {
                this.$message.error('请选择');
            } else {
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        const length = this.multipleSelection.length;
                        let str = '';
                        for (let i = 0; i < length; i++) {
                            this.delList.push(this.multipleSelection[i].orderId);
                        }
                        this.$http.post('/order/delAll', this.delList).then((res) => {
                            if (res.data.code == 200) {
                                this.$message.success('删除成功');
                                this.getData();
                                this.delList = [];
                            } else {
                                this.$message.error('删除失败');
                            }
                        });
                    })
                    .catch(() => {});
            }
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
