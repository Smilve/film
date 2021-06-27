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
                <el-input v-model="query.movieName" placeholder="电影名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button icon="el-icon-add" @click="handleAdd">添加</el-button>
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
                <el-table-column prop="scheduleId" sortable label="排片ID" width="70" align="center"></el-table-column>
                <el-table-column prop="movieName" sortable label="电影名" align="center"></el-table-column>
                <el-table-column prop="cinemaName" label="电影院名" align="center"></el-table-column>
                <el-table-column prop="hallName" label="影厅名" align="center"></el-table-column>
                <el-table-column prop="movieIntervalName" label="放映场次名" align="center"></el-table-column>
                <el-table-column prop="date" label="放映时间" align="center"></el-table-column>
                <el-table-column prop="language" label="语言版本" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index)"
                        >修改</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >撤销</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-setting"
                            class="yellow"
                            @click="handleSeat(scope.$index, scope.row)"
                        >排座</el-button>
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

        <!-- 添加弹出框 -->
        <el-dialog :title="title" :visible.sync="editVisible" width="30%" @close="dialogClosed">
            <el-form
                status-icon
                hide-requied-aterisk
                ref="form"
                :rules="rules"
                :model="form"
                label-width="80px"
            >
                <el-form-item label="电影">
                    <el-select v-model="form.movieId" placeholder="请选择" width="80%">
                        <el-option
                            v-for="movie in movieData"
                            :key="movie.movieId"
                            :label="movie.movieName"
                            :value="movie.movieId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="影院">
                    <el-select
                        v-model="form.cinemaId"
                        :disabled="this.query.cinemaId=='null'?false:true"
                        placeholder="请选择"
                        @change="changeSelect"
                    >
                        <el-option
                            v-for="cinema in cinemaData"
                            :key="cinema.cinemaId"
                            :label="cinema.cinemaName"
                            :value="cinema.cinemaId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="影厅">
                    <el-select v-model="form.hallId" placeholder="请选择">
                        <el-option
                            v-for="hall in hallData"
                            :key="hall.hallId"
                            :label="hall.hallName"
                            :value="hall.hallId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="场次">
                    <el-select v-model="form.movieIntervalId" placeholder="请选择">
                        <el-option
                            v-for="movieInterval in movieIntervalData"
                            :key="movieInterval.movieIntervalId"
                            :label="movieInterval.movieIntervalName"
                            :value="movieInterval.movieIntervalId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="放映时间">
                    <el-date-picker
                        v-model="form.date"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="选择放映时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="语言版本" width="80px">
                    <el-col :span="17">
                        <el-input v-model="form.language"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="售价" width="80px" prop="price">
                    <el-col :span="17">
                        <el-input v-model="form.price"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 安排座位弹出框 -->
        <el-dialog title="安排座位" :visible.sync="seatDialogVisible" :width="seatDialogWidth">
            <div style="display:flex; align-content: center">
                <span style="margin-right: 50px">启用</span>
                <span class="seat" :class="isSelected[0]"></span>
                <span style="margin: 0 50px">禁用</span>
                <span class="seat" :class="isSelected[1]"></span>
            </div>
            <br />
            <div
                class="row"
                v-for="(value, key) in seats"
                :key="key"
                style="display: flex; align-items: center"
            >
                <span
                    style="color:#999; margin:0  30px;align:center; display: inline-block; width: 20px;"
                >{{key}}</span>
                <div style="margin-left:30px;display: flex; justify-content: flex-end">
                    <span
                        class="seat"
                        :class="isSelected[seats[key][index]]"
                        @click="selectSeat(key, index)"
                        v-for="(item, index) in value"
                        :key="index"
                    ></span>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="seatDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveSeat">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'basetable',
    data() {
        let checkPrice = (rule, value, callback) => {
            if (!value) {
                callback(new Error('售价不能为空'));
            } else {
                if (/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入正确格式的售价'));
                }
            }
        };
        return {
            query: {
                movieName: '',
                pageIndex: 1,
                pageSize: 5,
                cinemaId: localStorage.cinemaId
            },
            tableData: [],
            movieData: [],
            cinemaData: [],
            hallData: [],
            movieIntervalData: [],
            multipleSelection: [],
            delList: [],
            // fileList: [],
            editVisible: false,
            pageTotal: 0,
            idx: -1, //修改的行的序号
            title: '',
            seatDialogVisible: false,
            seatDialogWidth: '',
            seats: [],
            scheduleId: '',
            isSelected: {
                0: 'seat-default',
                1: 'seat-choose'
            },
            form: {
                movieId: '',
                cinemaId: '',
                hallName: '',
                hallId: '',
                seat: '',
                movieIntervalId: '',
                date: '',
                price: '',
                language: ''
            },
            rules: {
                // cinema: [{ required: true, message: '请选择', trigger: 'change' }],
                price: [
                    { required: true, message: '售价不能为空', trigger: ['blur', 'change'] },

                    { validator: checkPrice, trigger: 'blur' }
                ]
            }
        };
    },
    // 页面创建是执行
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            if (this.query.cinemaId != 'null') {
                this.form.cinemaId = parseInt(this.query.cinemaId);
                this.changeSelect();
            }
            this.$http
                .post('/schedule/schedules', this.query)
                .then((res) => {
                    this.tableData = res.data.data.list;
                    this.pageTotal = res.data.data.totalCount;
                })
                .catch(() => {});
            this.$http.get('/schedule/getMovieInfo').then((res) => {
                this.movieData = res.data.data;
            });
            this.$http.get('/hall/getCinemaInfo').then((res) => {
                this.cinemaData = res.data.data;
            });
            this.$http.get('/movieInterval').then((res) => {
                this.movieIntervalData = res.data.data;
            });
        },
        // 触发搜索按钮
        handleSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要撤销吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$http.delete('/schedule/del/' + row.scheduleId).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success('撤销成功');
                            this.getData();
                        } else {
                            this.$message.error('撤销失败');
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
                this.$confirm('确定要撤销吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        const length = this.multipleSelection.length;
                        for (let i = 0; i < length; i++) {
                            this.delList.push(this.multipleSelection[i].scheduleId);
                        }
                        this.$http.post('/schedule/delAll', this.delList).then((res) => {
                            if (res.data.code == 200) {
                                this.$message.success('撤销成功');
                                this.getData();
                                this.delList = [];
                            } else {
                                this.$message.error('撤销失败');
                            }
                        });
                    })
                    .catch(() => {});
            }
        },
        //添加操作
        handleAdd() {
            this.title = '排片';
            this.editVisible = true;
        },
        //电影院改变时
        changeSelect(val) {
            this.hallData = [];
            this.$http.get('/schedule/getHallInfo/' + this.form.cinemaId).then((res) => {
                this.hallData = res.data.data;
            });
        },
        saveAdd() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                if (
                    this.form.movieId != '' &&
                    this.form.cinemaId != '' &&
                    this.form.hallId != '' &&
                    this.form.movieIntervalId != '' &&
                    this.form.date != '' &&
                    this.form.price != ''
                ) {
                    if (this.title == '排片') {
                        this.hallData.some((item, i) => {
                            if (item.hallId == this.form.hallId) {
                                this.form.seat = item.seat;
                                return;
                            }
                        });
                        this.$refs.form.validate((valid) => {
                            if (!valid) return false;
                            this.$http.post('/schedule/insert', this.form).then((res) => {
                                this.$message.success(`排片成功`);
                                this.$refs.form.resetFields();
                                this.editVisible = false;
                                this.getData();
                            });
                        });
                    } else {
                        this.saveEdit();
                    }
                } else {
                    this.$message.error(`请输入必填信息`);
                }
            });
        },

        // 编辑操作
        handleEdit(index) {
            this.title = '编辑';
            this.idx = index;
            // 深拷贝
            //this.form = JSON.parse(JSON.stringify(this.tableData[index]));
            this.$http.get('/schedule/' + this.tableData[index].scheduleId).then((res) => {
                this.form = res.data.data;
                this.editVisible = true;
            });
            this.hallData = [];
            this.$http.get('/schedule/getHallInfo/' + this.tableData[index].cinemaId).then((res) => {
                this.hallData = res.data.data;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                //浅拷贝问题？ 需要把form表单的数据写进常量中
                const up = {
                    movieId: this.form.movieId,
                    cinemaId: this.form.cinemaId,
                    hallId: this.form.hallId,
                    seat: '',
                    movieIntervalId: this.form.movieIntervalId,
                    date: this.form.date,
                    price: this.form.price,
                    scheduleId: this.form.scheduleId,
                    language: this.form.language
                };
                this.hallData.some((item, i) => {
                    if (item.hallId == up.hallId) {
                        up.seat = item.seat;
                        return;
                    }
                });
                this.$http.post('/schedule/update', up).then((res) => {
                    this.$message.success(`修改排片成功`);
                    this.getData();
                });
                this.editVisible = false;
            });
        },
        //排座
        handleSeat(index, row) {
            this.seatDialogVisible = true;
            this.seatDialogWidth = 200 + row.row * 40 + 'px';
            this.seats = JSON.parse(row.seat);
            this.scheduleId = row.scheduleId;
        },
        selectSeat(key, idx) {
            this.$set(this.seats[key], idx, this.seats[key][idx] == 0 ? 1 : 0);
        },
        saveSeat() {
            this.$http
                .post('/schedule/seats', {
                    scheduleId: this.scheduleId,
                    seat: JSON.stringify(this.seats)
                })
                .then((res) => {
                    if (res.data.code !== 200) this.$message.error('更新座位失败！');
                    this.$message.success('更新座位成功！');
                    this.seatDialogVisible = false;
                    this.getData();
                });
        },

        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        //关闭时清空表单
        dialogClosed() {
            this.form.hallId = '';
            this.form.movieId = '';
            if (this.query.cinemaId != 'null') {
                this.form.cinemaId = parseInt(this.query.cinemaId);
            } else {
                this.form.cinemaId = '';
            }
            this.form.movieIntervalId = '';
            this.form.date = '';
            this.form.price = '';
            this.$refs.form.resetFields();
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
.upload-demo {
    width: 80%;
}
.inline {
    display: inline-block;
    width: 50%;
}
.red {
    color: #ff0000;
}
.yellow {
    color: #d6ec10;
}
.seat {
    display: inline-block;
    width: 30px;
    height: 26px;
    margin: 0 5px;
    background-position: 0 1px;
}
.seat-default {
    background: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAHKSURBVEhLxZfbysIwDMezKeJUVAR9A/H9X0FQZMOLeem14sWc4AnP9muybM4557eT/mCYdLT/tknaqQgJ/ACVf7/Oz4TfbvVisYDlcgm1Wg2SRON0OoGqqtDr9bglAAoHGY1GQgqzl5zz+SyGwyF7z7wITyYTtrJD13W2HrzEuFqtspUdnU4Hbrcbew5fSa5SqfQbYbmzoCgKew5fK6disciWgyd8vV5hPB5DoVAA27bpSct6vQbLsmjFhmFwK0MpJjFNky2H3W4nZEf24rPZbMTlcmFPiPv9LgaDAXu+rNY0jS0HzG5Zh+zFRwo9bS/GuNvtgpwM+SQsVwetVosa/GDnpMhFsfUAJ3I4HMgmYRQIZl1awsbzTyYyqzHRkhK2Yj+RwklwBYMHRpDMhd0tTrXiT52jwCsxCrqPZc3RARLM7O1266V/XMrlMlQqFfYcVqsVZXa9Xo8Wzhq/cOYx/i+e8KeYZIFfgyyMxWw2o4Y8mc/nXty9jz08NqfTKTSbTapBLAt8kh6buDocGh+3xNrtNjQaDbLxRSj7/f7pNolLv98Xx+ORvVfeBla+S3VJ4K7hGO/I7S8MDht2UbjklspRokj+NRQKwB/pWISi3oSUQQAAAABJRU5ErkJggg==')
        no-repeat;
}
.seat-choose {
    background: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAKVSURBVEhLxZXPThpRFMY/hhFBG4okbdiYxgXpxmJi0phi4sKlL+HWlSsfwMSFr+DKjSufwMSFiyakaaagQaOxW1txUUjDHwVh5vacmdtxgGEsA1N/yYHLvdzzzT33nDMhQeAFUOT3f+fFhAeGutRs4tfpKV5dXECoqpz9R8hlKxqFsrqK96mUnOzGVfhrsYh3+/tIXV8Dk5Nydkh0HW3a+21zE59WVuTkE33C5/k8PmxvW4ITE/SPkFzxQacDUOS0nR18XFiQkxZ9dzx9dARwaCOR0UQZ9kP29vAQupz6S39y8UmVMeZcOIwInfp54QAQdJDe2LkLt9tAq2Vmp294L/vhe6Zxb13YwrSMvKZRZMIoLy2hvLYG3N/7E+c9FN7f2SzK8/MQiQS0szO5aGFndfHkBJndXSAeNzc2ZmehUw3GCwUru4eBTlpbXETs6grqwwNgGBBkX7a2kF1eNv9inziWy1minFxU/NM3N2jHYr5PbJAPtdGwHpp8hiiS6YMDUPBNTOE6WfL21sxAGx7Xav5KivaIx0fy7kghGqv0QHR+6yd/GGQUcx52M0Idu+0UDn+ORxovLp24i8CEO9SrvQhM+LlL8hQWnJHcBHxYiKvDA7OOqzTobGwgWalYjZ2hjlObm0N7Zmb4kqIkipZKmLq7e6oU8ldJJqHu7YGK1kOYke3OF1xKzvLsEfa+Y34IDrcfc4q6YAsrfDcBozgy3RSeIvuRyVidinsrNfixW7WKn+m0qcXYLwlum9+Pj5G4vITO709KEDaDmrsfFPLBrtnMrkhvqDfr63gt13nBlYZhiFyhIH8Nz2dNE005dmNgcgl6Fxv8dvGJXq9DcIgHYId63LBbvqpBeJfTCHiJMoEJewP8Afy6sw903o8jAAAAAElFTkSuQmCC')
        no-repeat;
}
</style>
