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
                <el-select v-model="query.sex" placeholder="性别" class="handle-select mr10">
                    <el-option key="0" label="性别" value></el-option>
                    <el-option key="1" label="男" value="0"></el-option>
                    <el-option key="2" label="女" value="1"></el-option>
                </el-select>
                <el-input v-model="query.username" placeholder="用户名" class="handle-input mr10"></el-input>
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
                <el-table-column prop="userId" sortable label="用户ID" width="70" align="center"></el-table-column>
                <el-table-column prop="userName" sortable label="用户名" align="center"></el-table-column>
                <el-table-column prop="password" label="密码" v-show="false"></el-table-column>
                <el-table-column label="性别" align="center">
                    <template slot-scope="scope">{{scope.row.sex==0?'男':'女'}}</template>
                </el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="'http://127.0.0.1:8080'+scope.row.photo"
                            :preview-src-list="['http://127.0.0.1:8080'+scope.row.photo]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="电话" align="center"></el-table-column>
                <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">
                        <el-tag
                            :type="scope.row.state==0?'success':(scope.row.state==1?'danger':'')"
                            @click="status(scope.$index, scope.row)"
                        >{{scope.row.state==0?'正常':'冻结'}}</el-tag>
                    </template>
                </el-table-column>

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

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="dialogClosed">
            <el-form ref="form" :rules="rules" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.userName" disabled></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="form.sex" placeholder="性别" class="handle-select mr10">
                        <el-option key="0" label="男" :value="0"></el-option>
                        <el-option key="1" label="女" :value="1"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
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
                sex: '',
                username: '',
                pageIndex: 1,
                pageSize: 5
            },
            state: {
                userId: 0,
                state: 0
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1, //修改的行的序号
            // id: -1,
            rules: {
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        min: 6,
                        max: 15,
                        message: '长度在 6 到 15 个字符',
                        trigger: 'blur'
                    }
                ]
            }
        };
    },
    created() {
        this.getData();
    },
    methods: {
        // 获取 easy-mock 的模拟数据
        getData() {
            this.$http
                .post('/user/users', this.query)
                .then((res) => {
                    console.log(res.data);
                    this.tableData = res.data.data.list;
                    this.pageTotal = res.data.data.totalCount;
                })
                .catch(() => {
                    this.$message.error('用户过期,请重新登录！');
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
        //冻结解冻操作
        status(index, row) {
            if (row.state == 0) {
                this.$confirm('确定要冻结此用户吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.state.userId = row.userId;
                        this.state.state = 1;
                        console.log(this.state);
                        this.$http.post('/user/state', this.state).then((res) => {
                            this.$message.success('冻结成功');
                            row.state = 1;
                        });
                    })
                    .catch(() => {});
            } else {
                this.$confirm('确定要解冻此用户吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.state.userId = row.userId;
                        this.state.state = 0;
                        this.$http.post('/user/state', this.state).then((res) => {
                            this.$message.success('解冻成功');
                            row.state = 0;
                        });
                    })
                    .catch(() => {});
            }
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$http.delete('/user/del/' + row.userId).then((res) => {
                        if (res.data.code == 200) {
                            this.$message.success('删除成功');
                            //this.tableData.splice(index, 1);
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
                        let str = '';
                        //this.delList = this.delList.concat(this.multipleSelection);
                        for (let i = 0; i < length; i++) {
                            //str += this.multipleSelection[i].userId + ',';
                            this.delList.push(this.multipleSelection[i].userId);
                        }
                        this.$http.post('/user/delAll', this.delList).then((res) => {
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
        // 编辑操作
        handleEdit(index) {
            this.idx = index;
            // 深拷贝
            //this.form = JSON.parse(JSON.stringify(this.tableData[index]));
            this.$http.get('/user/user/' + this.tableData[index].userId).then((res) => {
                this.form = res.data.data;
                this.form.password = '';
                this.editVisible = true;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                this.editVisible = false;
                // this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                console.log(this.form);
                const up = { userId: this.form.userId, password: this.form.password, sex: this.form.sex };
                this.$http.post('/user/update', up).then((res) => {
                    this.$message.success(`修改用户 ${this.form.userName} 成功`);
                    this.getData();
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
            //清空提示语
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
