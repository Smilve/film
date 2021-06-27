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
                <el-table-column prop="cinemaId" sortable label="电影院ID" width="80" align="center"></el-table-column>
                <el-table-column prop="cinemaName" label="电影院名" align="center"></el-table-column>
                <el-table-column prop="phone" label="电话" align="center"></el-table-column>
                <el-table-column prop="provice" label="省份" align="center" width="70px"></el-table-column>
                <el-table-column prop="city" label="城市" align="center" width="70px"></el-table-column>
                <el-table-column prop="country" label="地区" align="center" width="70px"></el-table-column>
                <el-table-column prop="address" label="详细地址" align="center"></el-table-column>
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
                <el-form-item label="电影院名" prop="cinemaName">
                    <el-input v-model="form.cinemaName"></el-input>
                </el-form-item>

                <el-form-item label="电话" prop="phone">
                    <el-input v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="省份" prop="provice">
                    <el-input v-model="form.provice"></el-input>
                </el-form-item>
                <el-form-item label="城市" prop="city">
                    <el-input v-model="form.city"></el-input>
                </el-form-item>

                <el-form-item label="地区" prop="country">
                    <el-input v-model="form.country"></el-input>
                </el-form-item>
                <el-form-item label="详细地址" prop="address">
                    <el-input type="textarea" rows="5" v-model="form.address"></el-input>
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
    name: 'basetable',

    data() {
        return {
            query: {
                cinemaName: '',
                pageIndex: 1,
                pageSize: 5
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            // fileList: [],
            editVisible: false,
            pageTotal: 0,
            form: {
                cinemaName: '',
                phone: '',
                provice: '',
                city: '',
                country: '',
                address: ''
            },
            idx: -1, //修改的行的序号
            // id: -1,
            title: '添加', //添加还是编辑
            rules: {
                cinemaName: [{ required: true, message: '请输入电影院名称', trigger: 'blur' }],
                phone: [
                    { required: true, message: '请输入联系电话', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                provice: [
                    { required: true, message: '请输入省份', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                city: [
                    { required: true, message: '请输入城市', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                country: [
                    { required: true, message: '请输入地区', trigger: 'blur' },
                    {
                        min: 2,
                        max: 10,
                        message: '长度在 2 到 10 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
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
            this.$http
                .post('/cinema/cinemas', this.query)
                .then((res) => {
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
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$http.delete('/cinema/del/' + row.cinemaId).then((res) => {
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
                            this.delList.push(this.multipleSelection[i].cinemaId);
                        }
                        this.$http.post('/cinema/delAll', this.delList).then((res) => {
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
            if (this.title == '添加') {
                this.$refs.form.validate((valid) => {
                    if (!valid) return false;
                    this.$http.post('/cinema/insert', this.form).then((res) => {
                        this.$message.success(`添加电影院 ${this.form.cinemaName} 成功`);
                        this.$refs.form.resetFields();
                        this.editVisible = false;
                        this.getData();
                    });
                });
            } else {
                this.saveEdit();
            }
        },

        // 编辑操作
        handleEdit(index) {
            this.idx = index;
            this.$http.get('/cinema/cinema/' + this.tableData[index].cinemaId).then((res) => {
                this.title = '编辑';
                this.form = res.data.data;
                this.editVisible = true;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                this.$http.post('/cinema/update', this.form).then((res) => {
                    this.editVisible = false;
                    this.$message.success(`修改电影院 ${this.form.cinemaName} 成功`);
                    this.getData();
                    this.$refs.form.resetFields();
                });
            });
        },

        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        //关闭时清空表单
        dialogClosed() {
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
</style>
