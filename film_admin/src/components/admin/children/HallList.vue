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
                <el-input v-model="query.cinemaName" placeholder="电影院名" class="handle-input mr10"></el-input>
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
                <el-table-column prop="hallId" sortable label="影厅ID" width="70" align="center"></el-table-column>
                <el-table-column prop="hallName" sortable label="影厅名字" align="center"></el-table-column>
                <el-table-column prop="cinemaName" label="电影院名" align="center"></el-table-column>
                <el-table-column prop="row" label="座位行数" align="center"></el-table-column>
                <el-table-column prop="col" label="座位列数" align="center"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index)"
                        >编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
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
                <el-form-item label="影院">
                    <el-select
                        v-model="form.cinemaId"
                        :disabled="this.query.cinemaId=='null'?false:true"
                        placeholder="请选择"
                    >
                        <el-option
                            v-for="cinema in cinemaData"
                            :key="cinema.cinemaId"
                            :label="cinema.cinemaName"
                            :value="cinema.cinemaId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="影厅名" prop="hallName">
                    <el-input v-model="form.hallName"></el-input>
                </el-form-item>
                <el-form-item label="行数">
                    <el-input v-model="form.row"></el-input>
                </el-form-item>
                <el-form-item label="列数">
                    <el-input v-model="form.col"></el-input>
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
    name: 'hallList',

    data() {
        return {
            query: {
                cinemaName: '',
                pageIndex: 1,
                pageSize: 5,
                cinemaId: localStorage.cinemaId
            },
            tableData: [],
            cinemaData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            seatDialogVisible: false,
            seatDialogWidth: '',
            pageTotal: 0,
            isSelected: {
                0: 'seat-default',
                1: 'seat-choose'
            },
            form: {
                cinemaId: '',
                hallName: '',
                hallId: '',
                row: null,
                col: null,
                seat: []
            },
            seats: [],
            hallId: '',
            idx: -1, //修改的行的序号
            // id: -1,
            title: '添加', //添加还是编辑
            rules: {
                // cinema: [{ required: true, message: '请选择', trigger: 'change' }],
                hallName: [{ required: true, message: '请输入影厅名', trigger: ['blur', 'change'] }]
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
            //为data中的对象添加值
            //this.$set(this.query, 'cinemaId', cinemaId);
            if (this.query.cinemaId != 'null') {
                this.form.cinemaId = parseInt(this.query.cinemaId);
            } else {
                this.form.cinemaId = '';
            }
            this.$http
                .post('/hall/halls', this.query)
                .then((res) => {
                    this.tableData = res.data.data.list;
                    this.pageTotal = res.data.data.totalCount;
                })
                .catch(() => {});
            this.$http.get('/hall/getCinemaInfo').then((res) => {
                this.cinemaData = res.data.data;
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
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$http.delete('/hall/del/' + row.hallId).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success('删除成功');
                            this.getData();
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
                        for (let i = 0; i < length; i++) {
                            this.delList.push(this.multipleSelection[i].hallId);
                        }
                        this.$http.post('/hall/delAll', this.delList).then((res) => {
                            if (res.data.code == 200) {
                                this.$message.success('删除成功');
                                this.getData();
                                this.delList = [];
                                // this.$router.go(0)
                            } else {
                                this.$message.error('删除失败');
                            }
                        });
                    })
                    .catch(() => {});
            }
        },
        //添加操作
        handleAdd() {
            this.title = '添加';
            this.editVisible = true;
        },
        saveAdd() {
            //this.submitUpload();
            if (this.form.cinemaId != '' && this.form.hallName != '') {
                if (this.title == '添加') {
                    let row = this.form.row;
                    let col = this.form.col;
                    let seat = {};
                    for (let i = 0; i < row; i++) {
                        let arr = [];
                        for (let j = 0; j < col; j++) {
                            arr.push(0);
                        }
                        seat[i + 1] = arr;
                    }
                    this.form.seat = JSON.stringify(seat);
                    this.$refs.form.validate((valid) => {
                        if (!valid) return false;
                        this.$http.post('/hall/insert', this.form).then((res) => {
                            this.$message.success(`添加影厅 ${this.form.hallName} 成功`);
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
        },

        // 编辑操作
        handleEdit(index) {
            this.idx = index;
            this.$http.get('/hall/hall/' + this.tableData[index].hallId).then((res) => {
                this.title = '编辑';
                this.form = res.data.data;
                this.editVisible = true;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                let row = this.form.row;
                let col = this.form.col;
                let seat = {};
                for (let i = 0; i < row; i++) {
                    let arr = [];
                    for (let j = 0; j < col; j++) {
                        arr.push(0);
                    }
                    seat[i + 1] = arr;
                }
                this.form.seat = JSON.stringify(seat);
                this.$http.post('/hall/update', this.form).then((res) => {
                    this.editVisible = false;
                    this.$message.success(`修改影厅 ${this.form.hallName} 成功`);
                    this.getData();
                    this.$refs.form.resetFields();
                });
            });
        },
        //排座
        handleSeat(index, row) {
            this.seatDialogVisible = true;
            this.seatDialogWidth = 200 + row.row * 40 + 'px';
            this.seats = JSON.parse(row.seat);
            this.hallId = row.hallId;
        },
        selectSeat(key, idx) {
            this.$set(this.seats[key], idx, this.seats[key][idx] == 0 ? 1 : 0);
        },
        saveSeat() {
            this.$http
                .post('/hall/updateSeat', {
                    hallId: this.hallId,
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
            if (this.query.cinemaId != 'null') {
                this.form.cinemaId = this.form.cinemaId = parseInt(this.query.cinemaId);
            } else {
                this.form.cinemaId = '';
            }
            this.form.hallName = '';
            this.form.hallId = '';
            this.form.col = '';
            this.form.row = '';
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
.yellow {
    color: #d6ec10;
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
