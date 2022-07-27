<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <!--      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>-->
      <el-breadcrumb-item>系统用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>高校用户管理</el-breadcrumb-item>
    </el-breadcrumb>


    <!--用户列表主体-->
    <el-card>


      <!--      <el-row :gutter="120">-->
      <!--        &lt;!&ndash;搜索区域&ndash;&gt;-->
      <!--        <el-col :span="8">-->
      <!--          <el-input placeholder="请输入搜索关键字" v-model="queryInfo.query" clearable @clear="getUserList"-->
      <!--                    style="font-size: 18px">-->
      <!--            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>-->
      <!--          </el-input>-->
      <!--        </el-col>-->
      <!--      </el-row>-->
      <el-row style="margin-top: 20px; font-size: 20px">
        <el-col :span="8">
          <el-select v-model="queryInfo.query_univ" clearable placeholder="请选择要查询的院校" style="font-size: 18px">
            <el-option
                v-for="item in gxInfo"
                :key="item.univ_code"
                :label="item.univ_name"
                :value="item.univ_code" style="font-size: 18px">
            </el-option>

          </el-select>
          <el-button type="primary" style="font-size: 20px; margin-left: 20px" @click="getUserList">查询</el-button>
        </el-col>
        <!--搜索按钮-->
        <el-col :span="6">
          <el-button type="primary" @click="openAddUser()" style="font-size: 20px">添加用户</el-button>
        </el-col>
      </el-row>


      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="userList" border stripe style="font-size: 18px" id="user_table">
        <el-table-column type="index" label="序号" width="120px"></el-table-column><!--索引列-->
        <el-table-column label="账号" prop="user_name"></el-table-column>
        <el-table-column label="高校名称" prop="univ_name"></el-table-column>
        <el-table-column label="院校代码" prop="univ_code"></el-table-column>
        <el-table-column label="账号状态" prop="user_status" align="center">
          <!--作用域插槽-->
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="scope.row.user_status?'点击禁用':'点击启用'" placement="top-start"
                        :enterable="false">
              <el-switch v-model="scope.row.user_status" @change="userStateChanged(scope.row)" :active-value="1"
                         :inactive-value="0" active-color="#13ce66"
                         inactive-color="#ff4949"></el-switch>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <!--修改用户密码-->
            <el-button type="warning" size="Medium" style="font-size: 18px"
                       @click="passwdEditDialog(scope.row.user_id)">修改密码
            </el-button>
          </template>
        </el-table-column>
      </el-table>


      <!--分页组件 size-change 每页最大变化数 current-change 当前最大变化 layout功能组件-->
      <div>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum"
            :page-sizes="[1,2,5,10,20,30,100]"
            :page-size="queryInfo.pageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total">
        </el-pagination>
      </div>

      <!--      <el-button style="margin-top: 20px" type="primary" @click="exportExcel">导出用户信息</el-button>-->
      <el-button type="primary" @click="getExcel()" style="font-size: 20px; margin-top: 18px">导出数据</el-button>

    </el-card>


    <!--新增用户区域-->
    <el-dialog title="添加高校用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px" id="add_user_form">
        <el-form-item label="高校名称" prop="univ_code" label-width="230px">
          <el-select v-model="addForm.univ_code" clearable placeholder="请选择院校名称"
                     style="font-size: 18px; width: 400px">
            <el-option
                v-for="item in gxInfo2"
                :key="item.univ_code"
                :label="item.univ_name"
                :value="item.univ_code" style="font-size: 20px"
                :onchange="logchange()">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账号" disabled label-width="230px">
          <el-input v-model="addForm.user_name" class="user_add_input" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="密码" disabled label-width="230px">
          <el-input v-model="addForm.user_passwd" class="user_add_input" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="clearData">清空</el-button>-->
        <el-button @click="addDialogVisible=false" style="font-size: 20px">取消</el-button>
        <el-button type="primary" @click="addUser" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


    <!--修改对话框-->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible" width="60%" @close="editDialogClosed">
      <!--内容主体区域-->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px" id="update_from">
        <el-form-item label="工作站名称" prop="user_sbdw" label-width="230px">
          <el-input v-model="editForm.user_sbdw" class="user_add_input"></el-input>
        </el-form-item>

        <el-form-item label="高校联系人及电话" prop="user_gxphone" label-width="230px">
          <el-input v-model="editForm.user_gxphone" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="申报单位联系人及电话" prop="user_sbdwphone" label-width="230px">
          <el-input v-model="editForm.user_sbdwphone" clearable class="user_add_input"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible=false" style="font-size: 20px">取消</el-button>
        <el-button type="primary" @click="editUserInfo" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


    <el-dialog title="修改用户密码" :visible.sync="passwdDialogVisible" width="50%" @close="passwdDialogClosed">
      <!--内容主体区域-->
      <el-form :model="passwdForm" :rules="passwdFormRules" ref="passwdFormRef" label-width="70px" id="rePasswd_from">
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelPasswd" style="font-size: 20px">取消</el-button>
        <el-button type="primary" @click="UpdatePasswd" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>


//补零函数
function zeroFill(i) {
  if (i >= 0 && i <= 9) {
    return "0" + i;
  } else {
    return i;
  }
}

export default {
  created() {
    this.getAllUniv();
    this.getUserList();
  },

  methods: {

    logchange() {
      console.log(this.addForm);
    },

    // 查询高校数据
    async getAllUniv() {
      const {data: res} = await this.$http.post("getAllUniv");
      this.gxInfo = res;
      this.gxInfo2 = res;
      console.log(this.gxInfo);
    },


    //table导出为Excel代码
    async getExcel() {
      let old_size = this.queryInfo.pageSize;
      let old_num  = this.queryInfo.pageNum;
      console.log("==============" + old_size);
      this.queryInfo.pageNum = 1;
      this.queryInfo.pageSize = 5000;
      await this.getUserList();

      let arrData = this.userList;

      this.queryInfo.pageNum = old_num;
      this.queryInfo.pageSize = old_size;
      console.log("==============" + this.queryInfo.pageSize);
      await this.getUserList();

      console.log(arrData);
      if (!arrData || !arrData.length) return;

      // const self = this;
      require.ensure([], () => {
        //导入expor2Excel， 写绝对路径
        const {export_json_to_excel} = require("../../excel/expor2Excel.js");
        // const arrData = arrData.map((item) => item.prop);
        // const tHeader = arrData.map((item) => item.label);
        // const json = arrData;
        const tHeader = ['高校管理员账号系统编号', '高校管理员账号', '账号密码', '高校名称', '院校代码', '账号状态（1：启用，0：禁用）'];
        const filterVal = ['user_id', 'user_name', 'user_passwd', 'univ_name', 'univ_code', 'user_status'];
        const json = arrData;

        const data = this.formatJson(filterVal, json);
        // console.log(data, "data");
        export_json_to_excel(tHeader, data, `高校用户账号信息表`);
      });

    },
    //保持输出顺序与表头一致
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]));
    },


    downloadTxt(text, fileName) {
      let element = document.createElement('a')
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text))
      element.setAttribute('download', fileName)
      element.style.display = 'none'
      element.click()
    },
    // 导出用户信息
    exportExcel() {
      // 1.生成Excel工作簿对象
      var wb = XLSX.utils.table_to_book(document.querySelector('#user_table'));
      // 2.手机号，身份证号等会默认使用科学计数法表示，则需要这样设置
      var wb = XLSX.utils.table_to_book(
          document.querySelector("#user_table"),
          {raw: true}
      );
      // 获取二进制字符串作为输出
      var wbout = XLSX.write(wb, {
        bookType: 'xlsx',
        book: true,
        type: 'array',
      })
      try {
        FileSaver.saveAs(
            // Blob: 对象表示一个不可变 原始数据的类文件对象,不一定是JS原生格式的数据。
            // File: 基于Blob，继承了blob的功能并将其扩展使其支持用户系统上的文件。
            new Blob([wbout], {type: 'appliction/octet-stream'}),
            // 设置导出的文件名称可随意
            '申请设站单位账户信息.xlsx'
        )
      } catch (e) {
        if (typeof console != 'undefined') console.log(e, wbout);
      }
      // 返回一个新创建的Blob对象，其内容由参数中给定的数组串联组成。
      return wbout
    },


    //确认修改用户信息（增强版）
    editUserInfo() {
      this.$confirm('此操作将直接修改申报单位用户账号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return;
          //验证成功 发起修改请求
          const {data: res} = await this.$http.put("editUser", this.editForm);
          console.log(res);
          console.log(this.editForm);
          if (res != "success") return this.$message.error("操作失败");
          this.$message.success("操作成功");
          //隐藏
          this.editDialogVisible = false;
          await this.getUserList();
        })
      }).catch(() => {

      })
    },


    //确认修改用户密码（增强版）
    UpdatePasswd() {
      this.$confirm('确认修改申报单位密码?', '提示', {
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
          //隐藏
          this.passwdDialogVisible = false;
          await this.getUserList();
        })
      }).catch(() => {

      })
    },


    //确认添加用户信息（增强版）
    addUser() {
      this.$confirm('确认添加用户信息？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.addFormRef.validate(async valid => {
          if (!valid) return;
          // for (let i = 0; i < this.gxInfo.length; i++) {
          //   console.log(this.gxInfo);
          //   console.log(this.addForm);
          //   if (this.gxInfo.univ_code === this.addForm.univ_code) {
          //     console.log(this.addForm.univ_code);
          //     this.addForm.univ_name = this.gxInfo.univ_name;
          //     break;
          //   }
          // }

          console.log(this.addForm.univ_code);

          const {data: res0} = await this.$http.post(`getUnivByCode?univ_code=${this.addForm.univ_code}`);

          this.addForm.univ_name = res0.univ_name;

          console.log(this.addForm);
          const {data: res} = await this.$http.post("addUser", this.addForm);
          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.downloadTxt(`高校名称：${this.addForm.univ_name}\n用户名：${this.addForm.user_name}\n密码：${this.addForm.user_passwd}`, `${this.addForm.univ_name}-管理员账号文件`)
          this.$message.success("操作成功！请保存账号文件");
          this.addDialogVisible = false;
          await this.getUserList();
        })

      }).catch(() => {

      })
    },

    //获取所有高校用户
    async getUserList() {
      console.log("????????????" + this.queryInfo.query_univ);
      const {data: res} = await this.$http.get(`allUser_gx?query=${this.queryInfo.query_univ}&pageNum=${this.queryInfo.pageNum}&pageSize=${this.queryInfo.pageSize}`);
      // const {data: res} = await this.$http.get(`allUser?query=${this.queryInfo}&univ_code=${this.self_info.univ_code}` );
      this.userList = res.data;// 用户列表数据封装
      console.log(this.userList)
      this.total = res.number;// 总用户数封装
      this.queryInfo.pageNum = 1;
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
      console.log(userInfo)
      //写入新状态的值
      const {data: res} = await this.$http.put(`userState?user_id=${userInfo.user_id}&user_status=${userInfo.user_status}`);
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

    //显示编辑用户信息对话框
    async showEditDialog(user_id) {
      this.getTime()
      console.log(this.nowTime)
      const {data: res} = await this.$http.get("getUpdate?user_id=" + user_id);
      this.editForm = res;//查询出的用户信息反填回编辑表单中
      this.editDialogVisible = true;//开启编辑对话框
    },

    //关闭编辑用户信息窗口
    editDialogClosed() {
      // this.$refs.editFormRef.resetField();//重置信息
    },


    //显示修改用户密码对话框
    async passwdEditDialog(user_id) {
      this.getTime()
      this.user_id = user_id;
      console.log(this.user_id);
      console.log(this.nowTime);
      // const {data: res} = await this.$http.get("getUserPasswd?user_id=" + user_id);
      // this.passwdForm = res;//查询出的用户信息反填回编辑表单中
      this.passwdDialogVisible = true;//开启编辑对话框
    },

    //关闭修改用户密码对话框
    passwdDialogClosed() {
      // this.$refs.editFormRef.resetField();//重置信息
    },


    //清空密码输入框
    cancelPasswd() {
      this.passwdDialogVisible = false;
      this.reflag = false;
      this.reflag2 = false;
      this.passwdForm.user_passwd = '';
      this.passwdForm.user_re_passwd = '';
    },


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

    // 启动添加用户弹窗
    openAddUser() {
      this.genUserNameAndPasswd();
      this.addDialogVisible = true;
    },


    // 生成随机用户名和密码并显示到弹窗上
    async genUserNameAndPasswd() {
      // console.log(univ_code);
      let date = new Date();//当前时间
      let flag = 0;
      let rdNum = "";
      let result = "";


      while (flag == "0") {
        rdNum = Math.random().toFixed(6).slice(-6);
        result = date.getFullYear() + "gx" + rdNum;
        console.log(result);
        const {data: res} = await this.$http.put(`searchUserName?user_name=${result}`);
        if (res == null) {
          return this.$message.error("服务器异常，请联系管理员");
        } else if (res == "error") {
          continue;
        } else {
          break;
        }
      }
      this.addForm.user_name = result;

      //生成院校代码加3位随机密码
      let passNum = Math.random().toFixed(6).slice(-6)
      this.addForm.user_passwd = passNum;
    },


    //密码框显示与隐藏
    changeType: function () {
      this.seen = !this.seen;   //小眼睛的变化
      this.pwdType = !this.pwdType;   //跟着小眼睛变化，密码框隐藏，显示文本框 内容就显示了
    },
    hoverEye: function (event) {
      this.seen = !this.seen;
    },
    outEye: function (event) {
      this.seen = !this.seen;
    }


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
      //密码显示与隐藏功能
      seen: "",
      unseenImg: require("../../assets/img/yc.svg"),   //看不见密码时小眼睛的显示图片地址
      seenImg: require("../../assets/img/xs.svg"),   //看得见密码时小眼睛的显示图片地址
      eyeTxt: "",
      pwdType: false,   //此时文本框隐藏，显示密码框

      reflag: false,
      reflag2: false,

      //修改密码使用的user_id
      user_id: '',
      nowTime: "",


      // 高校信息数据
      gxInfo: [],

      gxInfo2: [],


      //查询信息实体
      queryInfo: {
        query_univ: '',
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
        user_passwd: '',
        univ_name: '',
        univ_code: '',
        user_role: '2',
        user_status: '1'
      },
      //修改用户信息
      editForm: {
        user_sbdw: '',
        user_gxphone: '',
        user_sbdwphone: '',
        user_passwd: '',
        user_re_passwd: '',
      },

      //修改用户密码
      passwdForm: {
        user_passwd: '',
        user_re_passwd: ''
      },

      //显示/隐藏修改用户栏
      editDialogVisible: false,

      //显示和隐藏修改用户密码对话框
      passwdDialogVisible: false,

      //添加用户弹窗表单验证
      addFormRules: {
        univ_code: [
          {required: true, message: '请选择院校名称', trigger: 'blur'},
        ],
        // user_gxphone: [
        //   {required: true, message: '请输入高校联系人及手机号', trigger: 'blur'},
        //   {min: 1, message: '请输入正确的申报单位名称', trigger: 'blur'}
        // ],
        // user_sbdwphone: [
        //   {required: true, message: '请输入申请设站单位联系人及手机号', trigger: 'blur'},
        //   {min: 1, message: '请输入正确的申请设站单位联系人及手机号', trigger: 'blur'}
        // ],
      },
      //修改表单验证
      editFormRules: {
        user_sbdw: [
          {required: true, message: '请输入工作站名称', trigger: 'blur'},
          {min: 1, message: '请输入工作站名称', trigger: 'blur'}
        ],
        user_gxphone: [
          {required: true, message: '请输入高校联系人及联系电话', trigger: 'blur'},
          {min: 1, message: '请输入正确的高校联系人及联系电话', trigger: 'blur'}

        ],
        user_sbdwphone: [
          {required: true, message: '请输入申请设站单位联系人及联系电话', trigger: 'blur'},
          {min: 1, message: '请输入正确的申请设站单位联系人及联系电话', trigger: 'blur'}
        ],
      },
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
#add_user_form >>> .el-form-item__label {
  font-size: 20px;
}

#update_from >>> .el-form-item__label {
  font-size: 20px;
}

#rePasswd_from >>> .el-form-item__label {
  font-size: 20px;
}

.user_add_input {
  font-size: 20px;
}

</style>
