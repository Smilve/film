<template>
    <div class="container">
        <div class="box">
            <div class="logo">
                <img src="../../assets/logo.jpg" alt />
            </div>
            <el-form
                hide-required-asterisk
                status-icon
                class="login_form"
                ref="form"
                :rules="rules"
                :model="form"
                label-width="70px"
            >
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="form.userName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="password1">
                    <el-input type="password" v-model="form.password1" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" placeholder="性别" class="handle-select mr10">
                        <el-option key="0" label="男" :value="0"></el-option>
                        <el-option key="1" label="女" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="生日" prop="birthday">
                    <el-col :span="11">
                        <el-date-picker
                            type="date"
                            placeholder="选择日期"
                            v-model="form.birthday"
                            style="width: 110%;"
                        ></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item prop="email" label="邮箱">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="phone" label="电话">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm()">提交</el-button>
                    <el-button @click="resetForm()">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        //验证用户是否已注册
        var validateUser = (rule, value, callback) => {
            console.log(value.length);
            if (value == '' || value.length < 3 || value.length > 10) {
                callback();
            } else {
                console.log(this.flag);
                this.$http.post('/user/check', { userName: this.form.userName }).then((res) => {
                    if (res.data.code == 200) {
                        callback();
                    } else {
                        callback('该用户已被注册');
                    }
                });
            }
        };
        // 确认密码验证
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        // 手机号验证
        var contactPhone = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入手机号'));
            } else {
                if (!/^1[3456789]\d{9}$/.test(value)) {
                    callback(new Error('请输入正确的手机号'));
                } else {
                    callback();
                }
            }
        };
        return {
            flag: false,
            form: { userName: '', password: '', password1: '', sex: '', birthday: '', email: '', phone: '' },
            rules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    {
                        min: 3,
                        max: 10,
                        message: '长度在 3 到 10 个字符',
                        trigger: ['blur', 'change']
                    },
                    { validator: validateUser, trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        min: 6,
                        max: 15,
                        message: '长度在 6 到 15 个字符',
                        trigger: ['blur', 'change']
                    }
                ],
                password1: [
                    {
                        min: 6,
                        max: 15,
                        message: '长度在 6 到 15 个字符',
                        trigger: ['blur', 'change']
                    },
                    { validator: validatePass2, trigger: ['blur', 'change'] }
                ],
                sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
                birthday: [{ required: true, message: '请选择生日', trigger: 'change' }],
                email: [
                    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                phone: [{ validator: contactPhone, required: true, trigger: 'blur' }]
            }
        };
    },
    methods: {
        submitForm() {
            this.$refs.form.validate((valid) => {
                if (!valid) return false;
                console.log(this.form);
                this.$http.post('/user/insert', this.form).then((res) => {
                    if (res.data.code == 200) {
                        this.$message.success('注册成功，请登录。');
                        setTimeout(() => {
                            this.$router.push('/login');
                        }, 600);
                    } else {
                        this.$message.error('注册失败。');
                    }
                });
            });
        },
        resetForm() {
            this.$refs.form.resetFields();
        },
        hasUser() {
            console.log(this.form.userName);
            this.$http.post('/user/check', { userName: this.form.userName }).then((res) => {
                console.log(res.data);
            });
        }
    }
};
</script>

<style>
.container {
    height: 100%;
}
.box {
    height: 500px;
    width: 380px;
    border-radius: 5px;
    position: absolute;
    top: 50%;
    left: 30%;
    transform: translate(-50%, -50%);
}
.logo {
    height: 70px;
    width: 70px;
    border: 1px solid rgb(233, 224, 224);
    background-color: #eee;
    border-radius: 50%;
    padding: 3px;
    box-shadow: 0 0 5px #ddd;
    position: absolute;
    left: 20%;
    transform: translate(-50%, -50%);
}
.logo img {
    height: 100%;
    width: 100%;
    border-radius: 50%;
}
.login_form {
    position: absolute;
    bottom: 10%;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
}
</style>