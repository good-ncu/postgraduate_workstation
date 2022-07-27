<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>


    <!--用户列表主体-->
    <el-card>


      <el-row :gutter="120">
        <!--搜索区域-->
        <el-col :span="10">
          <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <!--搜索按钮-->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>
        </el-col>
      </el-row>


      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="userList" border stripe>
        <el-table-column type="index"></el-table-column><!--索引列-->
        <el-table-column label="用户名" prop="user_name"></el-table-column>
        <el-table-column label="密码" prop="user_password"></el-table-column>
        <el-table-column label="手机号" prop="user_phone"></el-table-column>
        <el-table-column label="账户余额" prop="user_money"></el-table-column>
        <el-table-column label="签名" prop="user_introduce"></el-table-column>
        <el-table-column label="角色" prop="user_role"></el-table-column>
        <el-table-column label="状态" prop="user_state">
          <!--作用域插槽-->
          <template slot-scope="scope">
            <el-switch v-model="scope.row.user_state" @change="userStateChanged(scope.row)"
                       :active-value="1" :inactive-value="0"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!--修改-->
            <el-tooltip effect="dark" content="修改信息" placement="top-start" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                         @click="showEditDialog(scope.row.user_id)"></el-button>
            </el-tooltip>
            <!--删除-->
            <el-tooltip effect="dark" content="删除用户" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                         @click="deleteUser(scope.row.user_id)"></el-button>
            </el-tooltip>
            <!--权限-->
            <el-tooltip effect="dark" content="分配权限" placement="top-start" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini"
                         @click="showRoleDialog(scope.row.user_role,scope.row.user_id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>


      <!--分页组件 size-change 每页最大变化数 current-change 当前最大变化 layout功能组件-->
      <div>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum"
            :page-sizes="[1,2,5,100]"
            :page-size="queryInfo.pageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-card>


    <!--新增用户区域-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">

        <el-form-item label="用户名" prop="user_name">
          <el-input v-model="addForm.user_name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="user_password">
          <el-input v-model="addForm.user_password"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="user_phone">
          <el-input v-model="addForm.user_phone"></el-input>
        </el-form-item>
        <el-form-item label="余额" prop="user_money">
          <el-input v-model="addForm.user_money"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="user_introduce">
          <el-input v-model="addForm.user_introduce"></el-input>
        </el-form-item>
        <el-form-item label="等级" prop="user_role">
          <el-select v-model="addForm.user_role" placeholder="请选择">
            <el-option
                v-for="item in roleList"
                :key="item.role_id"
                :label="item.role_name"
                :value="item.role_name">
              <span style="float: left">{{ item.role_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="addUser">确定</el-button>
      </span>
    </el-dialog>


    <!--修改对话框-->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <!--内容主体区域-->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">

        <el-form-item label="用户名" prop="user_name">
          <el-input v-model="editForm.user_name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="user_password">
          <el-input v-model="editForm.user_password"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="user_phone" disabled>
          <el-input v-model="editForm.user_phone"></el-input>
        </el-form-item>
        <el-form-item label="余额" prop="user_money">
          <el-input v-model="editForm.user_money"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="user_introduce">
          <el-input v-model="editForm.user_introduce"></el-input>
        </el-form-item>
        <el-form-item label="等级" prop="user_role">
          <el-select v-model="editForm.user_role" placeholder="请选择">
            <el-option
                v-for="item in roleList"
                :key="item.role_id"
                :label="item.role_name"
                :value="item.role_name">
              <span style="float: left">{{ item.role_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="editUserInfo">确定</el-button>
      </span>
    </el-dialog>


    <!--角色等级对话框-->
    <el-dialog title="修改用户等级" :visible.sync="roleDialogVisible" width="50%" @close="roleDialogClosed">
      <!--内容主体区域-->
      <el-form :model="roleForm" :rules="roleFormRules" ref="roleFormRef" label-width="70px">
        <el-form-item label="等级" prop="user_role">
          <el-select v-model="roleForm.user_role" placeholder="请选择">
            <el-option
                v-for="item in roleList"
                :key="item.role_id"
                :label="item.role_name"
                :value="item.role_name">
              <span style="float: left">{{ item.role_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
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

// 手机号码验证
var contactPhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('手机号不能为空'))
  } else {
    const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
    if (reg.test(value)) {
      callback()
    } else {
      return callback(new Error('请输入正确的手机号'))
    }
  }
}


export default {
  created() {
    this.getUserList();
    this.getRoleList();
  },

  methods: {
    // 获取所有用户
    async getUserList() {
      const {data: res} = await this.$http.get("allUser", {params: this.queryInfo});
      this.userList = res.data;// 用户列表数据封装
      this.total = res.number;// 总用户数封装
      this.user_state = res.data.user_state;
    },
    async getRoleList() {
      const {data: res} = await this.$http.get("getRole");
      this.roleList = res;
    },
    // 最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getUserList();
    },
    // pageNum的触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getUserList();
    },

    async userStateChanged(userInfo) {
      //写入新状态的值
      const {data: res} = await this.$http.put(`userState?user_id=${userInfo.user_id}&user_state=${userInfo.user_state}`);
      if (res != "success") {
        userInfo.user_id = !userInfo.user_id;
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
    },
    //监听添加用户操作
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    addUser() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res0} = await this.$http.post("checkUser", this.addForm)
        console.log(res0);
        if (res0 != "success") {
          return this.$message.error("已存在该用户");
        }
        const {data: res1} = await this.$http.post("checkPhone", this.addForm)
        console.log(res1);
        if (res1 != "success") {
          return this.$message.error("该手机号已被使用");
        }
        const {data: res} = await this.$http.post("addUser", this.addForm);
        if (res != "success") {
          return this.$message.error("操作失败");
        }
        this.$message.success("操作成功");
        this.addDialogVisible = false;
        this.getUserList();
      });
    },
    // 根据主键删除用户信息
    async deleteUser(user_id) {
      const confirmResult = await this.$confirm('此操作将永久删除用户，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteUser?user_id=" + user_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getUserList();
    },
    //显示对话框
    async showEditDialog(user_id) {
      const {data: res} = await this.$http.get("getUpdate?user_id=" + user_id);
      this.editForm = res;//查询出的用户信息反填回编辑表单中
      this.editDialogVisible = true;//开启编辑对话框
    },
    //关闭窗口
    editDialogClosed() {
      this.$refs.editFormRef.resetField();//重置信息
    },


    //显示权限对话框
    async showRoleDialog(user_role, user_id) {
      this.roleForm.user_role = user_role;
      this.roleForm.user_id = user_id;
      this.roleDialogVisible = true;
    },


    //关闭窗口
    roleDialogClosed() {
      //this.$refs.roleFormRef.resetField();//重置信息
    },

    //确认修改权限
    roleUserInfo() {
      this.$refs.roleFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.get(`updateRole?user_role=${this.roleForm.user_role}&user_id=${this.roleForm.user_id}`);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        //隐藏
        this.roleDialogVisible = false;
        this.getUserList();
      })
    },

    //确认修改用户信息
    editUserInfo() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.put("editUser", this.editForm);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        //隐藏
        this.editDialogVisible = false;
        this.getUserList();
      })
    },
  },


  data() {
    return {
      roleList: [],
      //查询信息实体
      queryInfo: {
        query: "",//查询信息
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },
      userList: [],//用户列表
      total: 0,//总记录数
      addDialogVisible: false,//对话框状态
      // 添加表单信息
      addForm: {
        user_name: '',
        user_password: '',
        user_phone: '',
        user_money: '',
        user_introduce: '',
        user_role: ''
      },
      //修改用户信息
      editForm: {},
      //显示/隐藏修改用户栏
      editDialogVisible: false,
      //修改用户权限
      roleForm: {
        user_role: '',
        user_id: ''
      },
      //显示/隐藏修改权限栏
      roleDialogVisible: false,
      //表单验证
      addFormRules: {
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          // {min: 5, max: 8, message: '长度在5到8个字符', trigger: 'blur'}
        ],
        user_password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        user_phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}
          {validator: contactPhone, required: true, trigger: 'blur'}
        ],
        user_money: [
          {required: true, message: '请输入账户余额', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        user_introduce: [
          //可以为NULL
          // {required: true, message: '请输入手机号', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        user_role: [
          {required: true, message: '请选择用户权限', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
      },
      //修改表单验证
      editFormRules: {
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          // {min: 5, max: 8, message: '长度在5到8个字符', trigger: 'blur'}
        ],
        user_password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        user_phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}
          {validator: contactPhone, required: true, trigger: 'blur'}

        ],
        user_money: [
          {required: true, message: '请输入账户余额', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        user_introduce: [
          //可以为NULL
          // {required: true, message: '请输入手机号', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        user_role: [
          {required: true, message: '请选择用户权限', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
      },
      //权限表单验证
      roleFormRules: {
        user_role: [
          {required: true, message: '请选择用户权限', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
      },

    };
  },

}
</script>

<style scoped>

</style>