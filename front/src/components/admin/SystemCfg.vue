<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>修改密码</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card align="center">



      <div style="text-align: left;width: 40%">
        <el-form label-position="top" label-width="180px" :model="addRole">
          <el-form-item label="原密码">
            <el-input v-model="rePassword.old_password"></el-input>
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="rePassword.new_password"></el-input>
          </el-form-item>
          <el-form-item label="重复输入">
            <el-input v-model="rePassword.re_new_password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="rePassword">确定修改</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-card>



  </div>
</template>

<script>
export default {
  created() {
    this.get();
  },
  methods: {
    //获取权限表
    async getRoleList() {
      const {data: res} = await this.$http.get("getRole");
      this.roleList = res;
    },


    //修改密码
    async rePassword() {
      if (this.addRole.role_name == '') {
        return this.$message.error("请输入权限名称");
      }
      if (this.addRole.role_value == '') {
        return this.$message.error("请输入权限价值");
      }
      if (this.addRole.role_days == '') {
        return this.$message.error("请输入持续时间");
      }
      //正则表达式
      var re = /^[0-9]+.?[0-9]*/;//判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
      if (!re.test(this.addRole.role_value)||!re.test(this.addRole.role_days)) {
        return this.$message.error("请输入正整数价格或时间");
      }

      const {data: res} = await this.$http.get("addRole?role_name=" + this.addRole.role_name+"&role_cost="+this.addRole.role_cost+"&role_value="+this.addRole.role_value+"&role_days="+this.addRole.role_days);
      if (res != "success") {
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
      this.getRoleList();
    },



  },
  data() {
    return {

      //添加权限表单信息
      rePassword: {
        old_password: '',
        new_password: '',
        re_new_password:''
      },

      //修改分类弹窗
      roleDialogVisible: false,
      //分类验证
      roleFormRules: {
        role_name: [
          {required: true, message: '请填写新分类名称', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        role_cost: [
          {required: true, message: '请填写用户折扣值', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        role_value: [
          {required: true, message: '请填写该会员等级价格', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        role_days: [
          {required: true, message: '请填写有效期限', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
      }
    }
  }
}
</script>

<style scoped>

</style>