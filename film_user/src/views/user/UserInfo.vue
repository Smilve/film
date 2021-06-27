<template>
  <div class="user-info-container">
    <div style="width: 30%">
      <h1>用户头像</h1>
      <img class="photo" :src="this.global.base +$store.state.user.url" alt />
      <!--  -->
      <el-upload
        ref="upload"
        class="upload-demo"
        :action="global.base+'/user/photo'"
        name="file"
        list-type="picture"
        :on-change="handleChange"
        :on-success="handleSuccess"
        :file-list="fileList"
        :show-file-list="false"
      >
        <el-button class="yellow" size="small" type="primary">更换头像</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
    <div style="width: 70%">
      <el-form :model="userInfo" ref="userInfoForm" label-width="80px" :role="updateUserRules">
        <el-form-item label="昵称">
          <el-input v-model="userInfo.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="userInfo.sex" :label="0">男</el-radio>
          <el-radio v-model="userInfo.sex" :label="1">女</el-radio>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker v-model="userInfo.birthday" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userInfo.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="yellow" type="primary" @click="updateUser">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "UserInfo",
  data() {
    var checkPhone = (rule, value, callback) => {
      const regPhone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57]|19[0-9])[0-9]{8}$/;
      if (value.length == 0) {
        callback(new Error("手机号不能为空"));
      } else if (!regPhone.test(value)) {
        callback(new Error("请输入合法的手机号"));
      } else {
        return callback();
      }
    };
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (value.length == 0) {
        callback(new Error("邮箱不能为空"));
      } else if (!regEmail.test(value)) {
        callback(new Error("请输入合法的邮箱"));
      } else {
        return callback();
      }
    };
    return {
      picDialog: false,
      pics: [],
      pictureList: [],
      picSrc: "",
      fileList: [],
      userInfo: {},
      updateUserRules: {
        email: [
          {
            validator: checkEmail,
            trigger: "blur",
          },
        ],
        phone: [
          {
            validator: checkPhone,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    async getUser() {
      const userName = localStorage.userName;
      const { data: res } = await axios.get("/user/username/" + userName);
      if (res.code === 200) {
        this.userInfo = res.data;
        this.$store.state.user.url = this.userInfo.photo;
      }
    },

    //上传图片
    async handleSuccess(response, file, fileList) {
      const photo = {
        photo: "/images/user/" + file.name,
        userId: this.userInfo.userId,
      };
      const { data: res } = await axios.post("/user/update/", photo);
      if (res.code === 200) {
        this.$message.success("上传图片成功");
        setTimeout(() => {
          this.$store.state.user.url = "/images/user/" + file.name;
        }, 1000);
      }
    },

    handleChange(file, fileList) {
      if (fileList.length > 1) {
        fileList.splice(0, 1);
      }
    },
    updateUser() {
      this.$refs.userInfoForm.validate(async (valid) => {
        if (!valid) return;
        if (this.userInfo.birthday != null) {
          this.userInfo.birthday = moment(this.userInfo.birthday).format(
            "YYYY-MM-DD"
          );
        }

        const { data: res } = await axios.post("/user/update/", this.userInfo);
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.getUser();
          return;
        }
        this.$message.error("修改失败");
      });
    },
  },
  created() {
    this.getUser();
  },
};
</script>

<style scoped>
.user-info-container {
  display: flex;
}
.upload-demo {
  width: 80%;
}
.photo {
  width: 258px;
  height: 258px;
}
.profile-title {
  padding: 26px 0;
  color: #ec443f;
  font-size: 18px;
  border-bottom: 1px solid #e1e1e1;
  margin-bottom: 30px;
}
.user-box {
  border: 1px solid #e5e5e5;
  margin: 0 40px 30px 0;
}
.yellow {
  background: #ffb400;
  border-color: #ffb400;
}
.el-radio >>> .el-radio__input.is-checked + .el-radio__label {
  color: #ffb400;
}
.el-radio >>> .el-radio__input.is-checked .el-radio__inner {
  border-color: #ffb400;
  background: #ffb400;
}
</style>