<template>
  <el-card>
    <!--内容主体区域-->
    <el-form :model="info_Form" :rules="info_FormRules" ref="editFormRef" label-width="70px" id="update_from">
      <el-form-item label="工作站名称" prop="user_sbdw" label-width="230px">
        <el-input v-model="info_Form.user_sbdw" class="user_add_input" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="高校联系人及电话" prop="user_gxphone" label-width="230px">
        <el-input v-model="info_Form.user_gxphone" clearable class="user_add_input"></el-input>
      </el-form-item>
      <el-form-item label="申报单位联系人及电话" prop="user_sbdwphone" label-width="230px">
        <el-input v-model="info_Form.user_sbdwphone" clearable class="user_add_input"></el-input>
      </el-form-item>
    </el-form>
    <!--内容底部区域-->
    <div style="display: flex; justify-content: center">
      <el-button type="primary" @click="editUserInfo" style="font-size: 20px">确定</el-button>
    </div>

  </el-card>


</template>

<script>
export default {
  created() {
    // 页面创建时获取缓存中的账户信息数据
    this.self_info.univ_name = localStorage.getItem("univ_name");
    this.self_info.univ_code = localStorage.getItem("univ_code");
    this.user_id = localStorage.getItem("user_id");
    this.user_sbdw = localStorage.getItem("user_sbdw");

    this.getUserInfo();

    console.log(this.user_id);

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

    // 提交用户信息修改
    editUserInfo() {
      this.$confirm('是否确认修改用户信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return;
          //验证成功 发起修改请求
          const {data: res} = await this.$http.put("editUser", this.info_Form);
          console.log(res);
          console.log(this.info_Form);
          if (res != "success") return this.$message.error("操作失败");
          this.$message.success("操作成功");
          await this.getUserInfo();
        })
      }).catch(() => {

      })
    },

  },


  data() {
    return {

      //获取后端接口数据到表格中
      info_Form: {
        user_sbdw: '',
        user_gxphone: '',
        user_sbdwphone: ''
      },
      user_id: '',
      nowTime: "",


      user_sbdw: '',


      // 本账号信息实体
      self_info: {
        univ_name: "",
        univ_code: "",
      },

      // 表单验证
      info_FormRules: {
        // user_sbdw: [
        //   {required: true, message: '请输入申报单位名称', trigger: 'blur'},
        //   {min: 1, message: '请输入正确的申报单位名称', trigger: 'blur'}
        // ],
        user_gxphone: [
          {required: true, message: '请输入高校联系人及电话', trigger: 'blur'},
          {min: 1, max:100, message: '输入字符限制在100字符以内', trigger: 'blur'}
        ],
        user_sbdwphone: [
          {required: true, message: '请输入申请设站单位联系人及电话', trigger: 'blur'},
          {min: 1, max:100, message: '输入字符限制在100以内', trigger: 'blur'}
        ],
      },


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
