<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card>

      <div style="width: 65%;float: left">
        <el-table
            height="600px"
            :data="roleList"
            style="width: 100%">
          <el-table-column
              label="编号"
              width="80">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.role_id }}</span>
            </template>
          </el-table-column>
          <el-table-column
              label="权限名称"
              width="150">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.role_name }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
              label="享受折扣"
              width="110">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.role_cost }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
              label="收费标准"
              width="140">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.role_value }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
              label="持续天数"
              width="140">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.role_days }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="showRoleDialog(scope.row.role_name,scope.row.role_cost,scope.row.role_value,scope.row.role_days)">编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deleteRole(scope.row.role_id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>


      <div style="float: left; margin-top: 20px;margin-left: 25px">
        <el-form label-position="top" label-width="180px" :model="addRole">
          <el-form-item label="权限名称">
            <el-input v-model="addRole.role_name"></el-input>
          </el-form-item>
          <el-form-item label="收费标准">
            <el-input v-model="addRole.role_value"></el-input>
          </el-form-item>
          <el-form-item label="持续天数">
            <el-input v-model="addRole.role_days"></el-input>
          </el-form-item>
          <el-form-item label="折扣数额">
            <el-slider v-model="addRole.role_cost" :format-tooltip="formatTooltip" show-input max="1" step="0.1" :show-input-controls="false"  input-size="5px"></el-slider>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addRoleInfo">确定添加</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-card>


    <!--修改分类名对话框-->
    <el-dialog title="修改分类名称" :visible.sync="roleDialogVisible" width="50%" @close="roleDialogClosed">
      <!--内容主体区域-->
      <el-form :model="roleForm" :rules="roleFormRules" ref="roleFormRef" label-width="80px">
        <el-form-item label="权限名称" prop="role_name" label-width="80px">
          <el-input v-model="roleForm.role_name"></el-input>
        </el-form-item>
        <el-form-item label="权限价格" prop="role_value" label-width="80px">
          <el-input v-model="roleForm.role_value"></el-input>
        </el-form-item>
        <el-form-item label="有效时间" prop="role_days" label-width="80px">
          <el-input v-model="roleForm.role_days"></el-input>
        </el-form-item>
        <el-form-item label="折扣率">
          <el-slider v-model="roleForm.role_cost" :format-tooltip="formatTooltip" show-input max="1" step="0.1" :show-input-controls="false"></el-slider>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="roleUserInfo">确定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  created() {
    this.getRoleList();
  },
  methods: {
    //获取权限表
    async getRoleList() {
      const {data: res} = await this.$http.get("getRole");
      this.roleList = res;
    },


    async addRoleInfo() {
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

    //显示分类修改对话框
    async showRoleDialog(role_name,role_cost,role_value,role_days) {
      this.old_role = role_name;
      this.roleForm.role_name = role_name;
      this.roleForm.role_cost = role_cost;
      this.roleForm.role_value = role_value;
      this.roleForm.role_days = role_days;
      this.roleDialogVisible = true;
    },
    //关闭窗口
    roleDialogClosed() {
      // this.$refs.classFormRef.resetField();//重置信息
    },

    //确认修改分类
    roleUserInfo() {
      this.$refs.roleFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.get(`updateUserRoleAndOld?new_role=${this.roleForm.role_name}&old_role=${this.old_role}&role_cost=${this.roleForm.role_cost}&role_value=${this.roleForm.role_value}&role_days=${this.roleForm.role_days}`);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        this.old_role = '';
            //隐藏
        this.roleDialogVisible = false;
        this.getRoleList();
      })
    },

    //删除
    async deleteRole(role_id) {
      const confirmResult = await this.$confirm('此操作将永久删除该权限，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteRole?role_id=" + role_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getRoleList();
    },


    //折扣选择显示数值
    formatTooltip(val) {
      return val;
    }
  },
  data() {
    return {
      //折扣数值
      costValue:1,
      //暂存权限
      old_role: '',
      //权限列表
      roleList: [],

      roleForm: {
        role_name: [],
        role_cost:[],
        role_value: [],
        role_days: []
      },

      //添加权限表单信息
      addRole: {
        role_name: '',
        role_cost: 1,
        role_value:'',
        role_days:''
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
