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
                <el-table-column prop="movieId" sortable label="电影ID" width="70" align="center"></el-table-column>
                <el-table-column prop="movieName" sortable label="电影名" align="center"></el-table-column>
                <el-table-column label="海报(查看大图)" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="baseUrl+scope.row.photo"
                            :preview-src-list="[baseUrl+scope.row.photo]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="director" label="导演" align="center"></el-table-column>
                <!-- <el-table-column label="性别" align="center">
                    <template slot-scope="scope">{{scope.row.sex==0?'男':'女'}}</template>
                </el-table-column>-->

                <el-table-column prop="actor" label="主演" align="center"></el-table-column>
                <el-table-column prop="type" label="类型" align="center"></el-table-column>
                <el-table-column prop="nation" label="地区" align="center"></el-table-column>
                <el-table-column prop="language" label="语言" align="center"></el-table-column>
                <el-table-column prop="score" label="评分" align="center"></el-table-column>

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
        <el-dialog :title="title" :visible.sync="editVisible" width="50%" @close="dialogClosed">
            <el-form
                status-icon
                :hide-requied-aterisk="false"
                ref="form"
                :rules="rules"
                :model="form"
                label-width="80px"
            >
                <el-form-item label="电影名" prop="movieName" class="inline">
                    <el-input v-model="form.movieName"></el-input>
                </el-form-item>

                <el-form-item label="导演" prop="director" class="inline">
                    <el-input v-model="form.director"></el-input>
                </el-form-item>
                <el-form-item label="演员" prop="actor" class="inline">
                    <el-input v-model="form.actor"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type" class="inline">
                    <el-input v-model="form.type"></el-input>
                </el-form-item>

                <el-form-item label="国家" prop="nation" class="inline">
                    <el-input v-model="form.nation"></el-input>
                </el-form-item>
                <el-form-item label="语言" prop="language" class="inline">
                    <el-input v-model="form.language"></el-input>
                </el-form-item>
                <el-form-item label="时长" prop="time" class="inline">
                    <el-input v-model="form.time"></el-input>
                </el-form-item>
                <el-form-item label="上映时间" prop="date" class="inline">
                    <el-date-picker
                        value-format="yyyy-MM-dd HH:mm"
                        v-model="form.date"
                        type="datetime"
                        placeholder="选择上映时间"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="简介" prop="intro">
                    <el-input type="textarea" rows="5" v-model="form.intro"></el-input>
                </el-form-item>
                <el-form-item label="海报" prop="photo">
                    <el-upload
                        ref="upload"
                        class="upload-demo"
                        :action="baseUrl+
                        '/movie/photo'"
                        name="file"
                        list-type="picture"
                        :on-change="handleChangePic"
                        :on-success="handleSuccess"
                        :file-list="fileList"
                    >
                        <el-button type="primary">上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
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
        var validatePhoto = (rule, value, callback) => {
            if (this.form.photo == null) {
                callback(new Error('请添加海报'));
            }
        };
        return {
            query: {
                movieName: '',
                pageIndex: 1,
                pageSize: 5
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            // fileList: [],
            editVisible: false,
            fileList: [],
            pageTotal: 0,
            form: { date: '', actor: '', director: '', intro: '', language: '', movieName: '', nation: '', photo: '', time: '', type: '' },
            idx: -1, //修改的行的序号
            // id: -1,
            title: '添加', //添加还是编辑
            baseUrl: 'http://127.0.0.1:8080',
            rules: {
                movieName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                director: [
                    { required: true, message: '请输入导演', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                actor: [
                    { required: true, message: '请输入演员', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                type: [
                    { required: true, message: '请输入类型', trigger: 'blur' },
                    {
                        min: 2,
                        max: 15,
                        message: '长度在 2 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                nation: [
                    { required: true, message: '请输入区域', trigger: 'blur' },
                    {
                        min: 2,
                        max: 10,
                        message: '长度在 2 到 10 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                language: [
                    { required: true, message: '请输入语言', trigger: 'blur' },
                    {
                        min: 2,
                        max: 10,
                        message: '长度在 2 到 10 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                time: [{ required: true, message: '请输入时长', trigger: 'blur' }],

                intro: [{ required: true, message: '请输入介绍', trigger: ['blur', 'change'] }]
                // photo: [{ validator: validatePhoto, trigger: ['blur', 'change'] }]
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
                .post('/movie/movies', this.query)
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
                    this.$http.delete('/movie/del/' + row.movieId).then((res) => {
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

                        for (let i = 0; i < length; i++) {
                            this.delList.push(this.multipleSelection[i].movieId);
                        }
                        this.$http.post('/movie/delAll', this.delList).then((res) => {
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
                    this.$http.post('/movie/insert', this.form).then((res) => {
                        this.$message.success(`添加影片 ${this.form.movieName} 成功`);
                        this.editVisible = false;
                        this.fileList = [];
                        this.getData();
                        this.$refs.form.resetFields();
                    });
                });
            } else {
                this.saveEdit();
            }
        },
        //上传图片
        handleSuccess(response, file, fileList) {
            this.form.photo = '/images/movie/' + fileList[0].name;
        },
        submitUpload() {
            //event.preventDefault();
            this.$refs.upload.submit();
        },
        handleChangePic(file, fileList) {
            if (fileList.length > 1) {
                fileList.splice(0, 1);
            }
        },
        // 编辑操作
        handleEdit(index) {
            this.idx = index;
            this.$http.get('/movie/movie/' + this.tableData[index].movieId).then((res) => {
                this.title = '编辑';
                this.form = res.data.data;
                this.fileList = [{ name: '', url: this.baseUrl + this.form.photo }];
                this.editVisible = true;
            });
        },
        // 保存编辑
        saveEdit() {
            this.$refs.form.validate((valid) => {
                if (!valid) return;
                this.$http.post('/movie/update', this.form).then((res) => {
                    this.editVisible = false;
                    this.$message.success(`修改电影 ${this.form.movieName} 成功`);
                    this.getData();
                    this.fileList = [];
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
            this.fileList = [];

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
