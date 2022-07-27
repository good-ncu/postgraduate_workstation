<template>
  <el-card>
    <!--内容主体区域-->
    <el-form :model="passwdForm" :rules="passwdFormRules" ref="passwdFormRef" label-width="70px" id="update_from">
      <el-form-item label="该用户新密码" prop="user_passwd" label-width="230px">
        <el-input v-model="passwdForm.user_passwd" :type="reflag?'text':'password'" class="user_add_input"><i
            slot="suffix"
            :class="[reflag?'iconfont icon-yincang':'iconfont icon-xianshi']"
            style="margin-top:8px;font-size:18px;"
            autocomplete="auto"
            @click="reflag=!reflag"/>
        </el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="user_re_passwd" label-width="230px">
        <el-input v-model="passwdForm.user_re_passwd" :type="reflag2?'text':'password'" class="user_add_input"><i
            slot="suffix"
            :class="[reflag2?'iconfont icon-yincang':'iconfont icon-xianshi']"
            style="margin-top:8px;font-size:18px;"
            autocomplete="auto"
            @click="reflag2=!reflag2"/>
        </el-input>
      </el-form-item>
    </el-form>

    <!--内容底部区域-->
    <div style="display: flex; justify-content: center">
      <el-button type="primary" @click="UpdatePasswd" style="font-size: 20px">确定</el-button>
    </div>

  </el-card>


</template>

<script>
export default {
  created() {
    console.log(1)
    // 页面创建时获取缓存中的账户信息数据
    this.self_info.univ_name = localStorage.getItem("univ_name");
    this.self_info.univ_code = localStorage.getItem("univ_code");
    this.user_id = localStorage.getItem("user_id");
    this.user_sbdw = localStorage.getItem("user_sbdw");
    console.log(1)
  },

  methods: {
    // 获取当前时间
    getTime() {
      let date = new Date();//当前时间
      let month = zeroFill(date.getMonth() + 1);//月
      let day = zeroFill(date.getDate());//日
      let hour = zeroFill(date.getHours());//时
      let minute = zeroFill(date.getMinutes());//分
      let second = zeroFill(date.getSeconds());//秒
      let curTime = date.getFullYear() + "-" + month + "-" + day
          + " " + hour + ":" + minute + ":" + second;
      this.nowTime = curTime;
    },

    // 获取用户信息
    async getUserInfo(){
      const {data: res} = await this.$http.get("getUpdate?user_id=" + this.user_id);
      this.info_Form = res;
    },

    //确认修改用户密码（增强版）
    UpdatePasswd() {
      this.$confirm('确认修改密码?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.passwdFormRef.validate(async valid => {
          console.log(this.passwdForm.user_passwd);
          if (!valid) return;
          //验证成功 发起修改请求
          const {data: res} = await this.$http.put(`updatePasswd?user_passwd=${this.passwdForm.user_passwd}&user_id=${this.user_id}`);
          console.log(res);
          console.log(this.passwdForm);
          if (res != "success") return this.$message.error("操作失败");
          this.$message.success("操作成功");
          this.passwdForm = "";
        })
      }).catch(() => {
      })
    },

  },


  data() {
    // 密码验证模块
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
        // password 是表单上绑定的字段
      } else if (value !== this.passwdForm.user_passwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    };

    return {

      //修改用户密码
      passwdForm: {
        user_passwd: '',
        user_re_passwd: ''
      },

      user_id: '',
      nowTime: "",

      reflag: false,
      reflag2: false,


      // 本账号信息实体
      self_info: {
        univ_name: "",
        univ_code: "",
      },

      // 表单验证
      //修改密码表单验证
      passwdFormRules: {
        user_passwd: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 6, message: '长度至少6个字符', trigger: 'blur'},
          {
            pattern: /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,30}$/,
            message: '密码为数字，小写字母，大写字母，特殊符号中的至少三种'
          }
        ],
        user_re_passwd: [
          {required: true, message: '请再次输入新密码', trigger: 'blur'},
          {min: 6, message: '长度至少6个字符', trigger: 'blur'},
          {required: true, validator: validatePass2, trigger: 'blur'}
        ],
      }


    };
  },

}


</script>

<style scoped>
#update_from >>> .el-form-item__label {
  font-size: 20px;
}

.user_add_input {
  font-size: 20px;
}
</style>
