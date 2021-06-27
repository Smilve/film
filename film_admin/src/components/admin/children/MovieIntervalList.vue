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
                <el-table-column
                    prop="movieIntervalId"
                    sortable
                    label="场次ID"
                    width="70"
                    align="center"
                ></el-table-column>
                <el-table-column prop="movieIntervalName" label="场次名称" align="center"></el-table-column>
                <el-table-column prop="beginTime" label="场次开始时间" align="center"></el-table-column>
                <el-table-column prop="endTime" label="场次结束时间" align="center"></el-table-column>

                <el-table-column label="操作" width="150" align="center">
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
                <el-form-item label="场次名称">
                    <el-input v-model="form.movieIntervalName"></el-input>
                </el-form-item>
                <el-form-item label="开始时间" prop="beginTime">
                    <el-input v-model="form.beginTime"></el-input>
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-input v-model="form.endTime"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'movieIntervalList',
    data() {
        return {
            query: {
                pageIndex: 1,
                pageSize: 5
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {
                movieIntervalName: '',
                beginTime: '',
                endTime: ''
            },
            idx: -1, //修改的行的序号
            title: '添加', //添加还是编辑
            rules: {
                // cinema: [{ required: true, message: '请选择', trigger: 'change' }],
                movieIntervalName: [{ required: true, message: '请输入场次名', trigger: ['blur', 'change'] }],
                beginTime: [{ required: true, message: '请输入开始时间', trigger: ['blur', 'change'] }],
                endTime: [{ required: true, message: '请输入结束时间', trigger: ['blur', 'change'] }]
            }
        };
    },
    created() {
        this.getData();
    },
    methods: {
        //获取数据
        getData() {
            this.$http.post('/movieInterval/movieIntervals', this.query).then((res) => {
                this.tableData = res.data.data.list;
                this.pageTotal = res.data.data.totalCount;
            });
        },

        //添加操作
        handleAdd() {
            this.title = '添加';
            this.editVisible = true;
        },
        saveAdd() {
            if (this.form.movieIntervalName != '' && this.form.beginTime != '' && this.form.endTime != '') {
                if (this.title == '添加') {
                    this.$refs.form.validate((valid) => {
                        if (!valid) return false;
                        this.$http.post('/movieInterval', this.form).then((res) => {
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
            this.$http.get('/movieInterval/' + this.tableData[index].movieIntervalId).then((res) => {
                this.title = '编辑';
                this.form = res.data.data;
                this.editVisible = true;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                this.$http.put('/movieInterval', this.form).then((res) => {
                    this.editVisible = false;
                    this.$message.success(`修改场次成功`);
                    this.$refs.form.resetFields();
                    this.getData();
                    this.form.beginTime = '';
                    this.form.endTime = '';
                });
            });
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            }).then(() => {
                this.$http.delete('/movieInterval/' + row.movieIntervalId).then((res) => {
                    if (res.data.code == 200) {
                        this.$message.success('删除成功');
                        this.getData();
                        this.delList = [];
                    } else {
                        this.$message.error('删除失败');
                    }
                });
            });
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
                }).then(() => {
                    const length = this.multipleSelection.length;
                    let str = '';
                    for (let i = 0; i < length; i++) {
                        this.delList.push(this.multipleSelection[i].movieIntervalId);
                    }
                    this.$http.post('/movieInterval/delAll', this.delList).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success('删除成功');
                            this.getData();
                            this.delList = [];
                        } else {
                            this.$message.error('删除失败');
                        }
                    });
                });
            }
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        //关闭时清空表单
        dialogClosed() {
            this.form.movieIntervalName = '';
            this.form.beginTime = '';
            this.form.endTime = '';
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
</style>
