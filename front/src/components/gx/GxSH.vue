<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <!--      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>-->
      <el-breadcrumb-item>工作站报备</el-breadcrumb-item>
      <el-breadcrumb-item>报备单位审核</el-breadcrumb-item>
    </el-breadcrumb>


    <!--用户列表主体-->
    <el-card>


      <table class="search_box">
        <tr style="height: 80px; text-align: right;border: 1px solid;font-size: 16px" align="center">
          <td width="100px">
            联合培养学科或专业类别：
          </td>
          <td>
            <el-input v-model="queryInfo.query_lhpy" clearable style="font-size: 18px"/>
          </td>
          <td width="70px">
            所在市：
          </td>
          <td>
            <el-input v-model="queryInfo.query_city" clearable style="font-size: 18px"/>
          </td>
          <td width="100px">
            工作站类型：
          </td>
          <td width="200px">
            <el-select v-model="queryInfo.query_lx" clearable placeholder="请选择" style="font-size: 18px">
              <el-option
                  v-for="item in lx_options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  @change="log_select" style="font-size: 18px">
              </el-option>
            </el-select>
          </td>
          <td width="90px">
            审核状态：
          </td>
          <td width="200px">
            <el-select v-model="queryInfo.query_status" clearable placeholder="请选择">
              <el-option
                  v-for="item in status_options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" style="font-size: 18px">
              </el-option>
            </el-select>

          </td>

        </tr>
        <tr align="center">
          <td align="center" colspan="8">
            <el-button type="primary" @click="getApplyList" style="font-size: 20px">搜索</el-button>
          </td>
        </tr>
      </table>


      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="ApplyList" border stripe style="font-size: 16px" ref="multipleTable">
        <!--        <el-table-column type="selection" @selection-change="handleSelectionChange"></el-table-column>-->
        <el-table-column type="index" label="序号" width="120px" align="center"></el-table-column><!--索引列-->
        <el-table-column label="申请单位名称" prop="apply_dwname" align="center"></el-table-column>
        <el-table-column label="申请单位性质" prop="apply_dwxz" align="center"></el-table-column>
        <el-table-column label="技术领域" prop="apply_jsly" align="center"></el-table-column>
        <el-table-column label="统一社会信用代码" prop="apply_scode" align="center"></el-table-column>
        <el-table-column label="联合培养学科或专业类别" prop="apply_lhpydis" align="center"></el-table-column>
        <el-table-column label="学科专业所在院系" prop="apply_disyx" align="center"></el-table-column>
        <el-table-column label="高校牵头导师" prop="apply_gxds" align="center"></el-table-column>
        <el-table-column label="所在市" prop="apply_city" align="center"></el-table-column>
        <el-table-column label="工作站类型" prop="apply_worklx" align="center"></el-table-column>
        <el-table-column label="状态" prop="apply_status" align="center">
          <template slot-scope="scope">
            <p>
              {{
                scope.row.apply_status == '0' ? '未审核' : scope.row.apply_status == '1' ? '已通过' : scope.row.apply_status == '2' ? '已退回': scope.row.apply_status == '4'? '撤销资格':'服务器错误'
              }}
            </p>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <!--审核报备信息-->
            <el-button type="primary" style=" font-size: 18px"
                       @click="SH_Dialog(scope.row.apply_id, scope.row.user_id)" v-if="scope.row.apply_status==0">审核信息
            </el-button>
            <el-button type="warning" style=" font-size: 18px"
                       @click="xgyj_Dialog(scope.row.apply_id, scope.row.user_id)" v-if="scope.row.apply_status==1 || scope.row.apply_status==2">修改意见
            </el-button>
            <el-button type="info" style=" font-size: 18px"
                         v-if="scope.row.apply_status==4">撤销资格
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
            :page-sizes="[1,2,5,10,20,30]"
            :page-size="queryInfo.pageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total">
        </el-pagination>
      </div>

      <!--      <div style="margin-top: 20px">-->
      <!--        <el-button type="primary" @click="table_all_select()" style="width: 100px">全选</el-button>-->
      <!--        <el-button type="info" plain @click="toggleSelection()" style="width: 100px">取消选择</el-button>-->
      <!--      </div>-->

      <el-button type="primary" @click="getExcel()" style="font-size: 20px; margin-top: 18px">导出数据</el-button>


    </el-card>


    <!--审核表单主体-->
    <el-dialog title="审核设站单位申请" :visible.sync="SH_DialogVisible" width="70%" @close="SH_DialogClosed">
      <!--内容主体区域-->
      <el-form :model="SH_Form" :rules="SH_FormRules" ref="SH_FormRef" id="SH_form">
        <el-form-item label="申请单位名称" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_dwname" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="申请单位性质" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_dwxz" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="技术领域" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_jsly" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_scode" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="联合培养学科或专业类别" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_lhpydis" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="学科专业所在院系" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_disyx" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="高校牵头导师" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_gxds" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="所在市" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_city" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="工作站类型" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_worklx" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="查看申请书及证明材料" label-width="240px">
          <el-button class="user_SH_input"
                     type="primary" @click="pdf_click" size="small" plain>
            申请书（盖章扫描件）
          </el-button>
          <el-button style="margin-top: 10px; font-size: 20px"
                     type="success"
                     @click="zc_click" size="small" plain>
            证明材料
          </el-button>
        </el-form-item>
        <el-form-item label=" " label-width="240px">
          <span style="color: red; font-size: 18px">请高校认真审核工作站信息，确保信息无误，若后期形式审查不通过或发现虚假信息将直接撤销研究生工作站资格</span>
        </el-form-item>
        <!--     不一致问题   必须使用rules进行绑定，以及同时使用prop绑定数据-->
        <el-form-item label="审核结果" label-width="240px" :rules="SH_FormRules.SH_radio" prop="apply_status">
          <el-radio v-model="SH_Form.apply_status" label="1" style="margin-left: 50px; font-size: 20px"><span
              style="font-size: 20px">通过</span></el-radio>
          <el-radio v-model="SH_Form.apply_status" label="2" class="user_SH_input"><span
              style="font-size: 20px">退回</span></el-radio>
        </el-form-item>

        <el-form-item label="审核意见" label-width="240px" prop="apply_shyj">
          <el-input class="user_SH_input"
                    type="textarea"
                    :autosize="{ minRows: 4, maxRows: 10}"
                    placeholder="请输入审核意见（200字以内）"
                    v-model="SH_Form.apply_shyj">
          </el-input>
        </el-form-item>

      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="clearData">清空</el-button>-->
        <el-button @click="SH_DialogClosed()" style="font-size: 20px">取消</el-button>
        <el-button type="primary" @click="SH_Apply" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>

    <!--撤销通过表单主体-->
    <el-dialog title="修改审核意见" :visible.sync="xgyj_DialogVisible" width="70%" @close="xgyj_DialogClosed">
      <!--内容主体区域-->
      <el-form :model="SH_Form" :rules="SH_FormRules" ref="xgyj_FormRef" id="rev_form">
        <el-form-item label="申请单位名称" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_dwname" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="申请单位性质" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_dwxz" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="技术领域" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_jsly" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_scode" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="联合培养学科或专业类别" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_lhpydis" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="学科专业所在院系" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_disyx" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="高校牵头导师" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_gxds" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="所在市" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_city" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="工作站类型" disabled label-width="240px">
          <el-input v-model="SH_Form.apply_worklx" :readonly="true" class="user_SH_input"></el-input>
        </el-form-item>
        <el-form-item label="查看申请书及证明材料" label-width="240px">
          <el-button
              type="primary" @click="pdf_click" size="small" plain class="user_SH_input">
            申请书（盖章扫描件）
          </el-button>
          <el-button style="margin-top: 10px; font-size: 20px"
                     type="success"
                     @click="zc_click" size="small" plain>
            证明材料
          </el-button>
        </el-form-item>
        <el-form-item label=" " label-width="240px">
          <span style="color: red; font-size: 18px">请高校认真审核工作站信息，确保信息无误，若后期形式审查不通过或发现虚假信息将直接撤销研究生工作站资格</span>
        </el-form-item>
        <!--     不一致问题   必须使用rules进行绑定，以及同时使用prop绑定数据-->
        <el-form-item label="审核结果" label-width="240px" :rules="SH_FormRules.SH_radio" prop="apply_status">
          <el-radio v-model="SH_Form.apply_status" label="1" style="margin-left: 50px; font-size: 20px"><span
              style="font-size: 20px">通过</span></el-radio>
          <el-radio v-model="SH_Form.apply_status" label="2"><span style="font-size: 20px">退回</span></el-radio>
        </el-form-item>

        <el-form-item label="审核意见" label-width="240px" prop="apply_shyj">
          <el-input
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 10}"
              placeholder="请输入审核意见（200字以内）"
              v-model="SH_Form.apply_shyj"
              class="user_SH_input">
          </el-input>
        </el-form-item>

      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="clearData">清空</el-button>-->
        <el-button @click="xgyj_DialogClosed()" style="font-size: 20px">取消</el-button>
        <el-button type="primary" @click="xgyj_Apply" style="font-size: 20px">确定</el-button>
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
    // 页面创建时获取缓存中的账户信息数据
    this.self_info.univ_name = localStorage.getItem("univ_name");
    this.self_info.univ_code = localStorage.getItem("univ_code");

    //获取申请记录列表
    this.getApplyList();
  },

  methods: {

    // 撤销通过的申请
    async xgyj_Dialog(apply_id, user_id) {
      console.log(apply_id);
      console.log(user_id);
      // 查询申请数据
      const {data: res0} = await this.$http.get("getApplyByID?apply_id=" + apply_id);
      const {data: res1} = this.getUserFile(user_id);
      this.SH_Form = res0;//查询出的用户信息反填回编辑表单中
      console.log(this.SH_Form);
      this.xgyj_DialogVisible = true;//开启编辑对话框
    },


    //table导出为Excel代码
    async getExcel() {

      let old_size = this.queryInfo.pageSize;
      let old_num  = this.queryInfo.pageNum;
      console.log("==============" + old_size);
      this.queryInfo.pageNum = 1;
      this.queryInfo.pageSize = 10000;
      await this.getApplyList();

      let arrData = this.ApplyList;

      this.queryInfo.pageNum = old_num;
      this.queryInfo.pageSize = old_size;
      console.log("==============" + this.queryInfo.pageSize);
      await this.getApplyList();

      console.log(arrData);
      if (!arrData || !arrData.length) return;
      // const self = this;
      require.ensure([], () => {
        //导入expor2Excel， 写绝对路径
        const {export_json_to_excel} = require("../../excel/expor2Excel.js");
        // const arrData = arrData.map((item) => item.prop);
        // const tHeader = arrData.map((item) => item.label);
        // const json = arrData;
        const  tHeader = ['申请编号','申请单位名称', '申请单位性质', '技术领域', '统一社会信用代码', '联合培养学科或专业类别', '学科专业所在院系','高校牵头导师', '所在市', '工作站类型', '审核状态码（0：未审核，1：已通过，2：已退回）'];
        const filterVal = ['apply_id','apply_dwname','apply_dwxz', 'apply_jsly', 'apply_scode', 'apply_lhpydis', 'apply_disyx', 'apply_gxds','apply_city', 'apply_worklx', 'apply_status' ];
        const json = arrData;

        const data = this.formatJson(filterVal, json);
        // console.log(data, "data");
        export_json_to_excel(tHeader, data, "报备审核情况数据表");
      });
    },
    //保持输出顺序与表头一致
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]));
    },


    //确认审核用户申请（增强版）
    SH_Apply() {
      this.$confirm('确认提交？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.SH_FormRef.validate(async valid => {
          console.log("ohuo");
          if (!valid) return;
          this.getTime();
          this.SH_Form.apply_lastopt = this.nowTime;
          console.log(this.SH_Form.apply_lastopt);
          console.log(666);
          console.log(this.SH_Form.apply_status);
          const {data: res} = await this.$http.post("/SH_Apply", this.SH_Form);
          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.$message.success("操作成功");
          this.SH_DialogVisible = false;
          await this.getApplyList();
        })
      }).catch(() => {

      })
    },

    //修改审核意见
    xgyj_Apply() {
      this.$confirm('确认修改审核意见？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.xgyj_FormRef.validate(async valid => {
          console.log("oooooohhh");
          if (!valid) return;
          this.getTime();
          this.SH_Form.apply_lastopt = this.nowTime;
          console.log(this.SH_Form.apply_lastopt);
          console.log(666);
          console.log(this.SH_Form.apply_status);
          const {data: res} = await this.$http.post("/SH_Apply", this.SH_Form);
          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.$message.success("操作成功");
          this.xgyj_DialogVisible = false;
          await this.getApplyList();
        })
      }).catch(() => {

      })
    },


    log_select() {
      console.log(this.queryInfo.query_status);
      console.log(this.queryInfo);
    },

    clearSearchBox() {
      this.queryInfo.query_lhpy = '';
      this.queryInfo.query_status = '';
      this.queryInfo.query_lx = '';
      this.queryInfo.query_city = '';
    },

    // 获取所有用户申请的列表
    async getApplyList() {
      const {data: res} = await this.$http.get(`getApply_gx?query_lhpy=${this.queryInfo.query_lhpy}&query_city=${this.queryInfo.query_city}&query_lx=${this.queryInfo.query_lx}&query_status=${this.queryInfo.query_status}&pageNum=${this.queryInfo.pageNum}&pageSize=${this.queryInfo.pageSize}&univ_code=${this.self_info.univ_code}`);
      // this.queryInfo.query_lhpy = '';
      // this.queryInfo.query_status = '';
      // this.queryInfo.query_lx = '';
      // this.queryInfo.query_city = '';
      this.ApplyList = res.data;// 用户列表数据封装
      console.log(this.ApplyList)
      this.total = res.number;// 总用户数封装
      this.queryInfo.pageNum = 1;
    },

    // 最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getApplyList();
    },
    // pageNum的触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getApplyList();
    },

    //监听添加用户关闭操作
    SH_DialogClosed() {
      this.SH_Form.apply_shyj = '';
      this.SH_radio = '';
      this.old_file_info = '';
      this.SH_DialogVisible = false;
      this.$refs.SH_FormRef.resetFields();
    },

    // 关闭修改弹窗
    xgyj_DialogClosed() {
      this.SH_Form.apply_shyj = '';
      this.SH_radio = '';
      this.old_file_info = '';
      this.xgyj_DialogVisible = false;
      this.$refs.xgyj_FormRef.resetFields();
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


    // 获取用户上传文件的文件名和服务器URL
    async getUserFile(user_id) {
      const {data: res} = await this.$http.get(`getFileByUserID?user_id=${user_id}`);
      this.old_file_info = res;
      console.log(this.old_file_info);
    },


    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },

    table_all_select() {
      this.$refs.multipleTable.toggleAllSelection();
    },

    async SH_Dialog(apply_id, user_id) {
      console.log(apply_id);
      console.log(user_id);
      // 查询申请数据
      const {data: res0} = await this.$http.get("getApplyByID?apply_id=" + apply_id);
      const {data: res1} = this.getUserFile(user_id);
      this.SH_Form = res0;//查询出的用户信息反填回编辑表单中
      console.log(this.SH_Form);
      this.SH_DialogVisible = true;//开启编辑对话框
    },

    pdf_click() {
      console.log(this.old_file_info.pdf_url);
      window.open(this.old_file_info.pdf_url);
    },

    zc_click() {
      console.log(this.old_file_info.zc_url);
      window.open(this.old_file_info.zc_url);
    },


  },


  data() {
    // 审核验证
    var validate_shjg = (rule, value, callback) => {
      // console.log(this.SH_Form.apply_status);
      // if (this.SH_Form.apply_status.valueOf() === '0' || this.SH_Form.apply_status.valueOf() === '') {
      //   callback(new Error('请选择审核结果'))
      //
      if (value) {
        // 当值为0的时候当做没选择
        if (value == 0) {
          callback(new Error('请选择工单状态'))
        } else {
          callback();
        }
      } else {
        callback(new Error('请选择工单状态'));
      }
    };

    return {
      //修改密码使用的user_id
      user_id: '',
      nowTime: "",
      // 本账号信息实体
      self_info: {
        univ_name: "",
        univ_code: "",
      },

      // 查询信息实体
      queryInfo: {
        //查询联合培养学科或类别
        query_lhpy: "",
        //查询所在市
        query_city: "",
        //查询工作站类型
        query_lx: "",
        //审核状态
        query_status: "",
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },

      // 工作站类型下拉菜单
      lx_options: [
        {
          value: '企业',
          label: '企业'
        },
        {
          value: '校企合作',
          label: '校企合作',
        },
        {
          value: '研究院',
          label: '研究院'
        }
      ],

      // 审核状态下拉菜单
      status_options: [{
        value: '0',
        label: '未审核'
      }, {
        value: '1',
        label: '已通过'
      }, {
        value: '2',
        label: '已退回'
      }, {
        value: '4',
        label: '撤销资格'
      }],

      // 审核用户提交的材料
      old_file_info: {
        pdf_url: '',
        pdf_name: '',
        zc_url: '',
        zc_name: ''
      },

      // 请求列表
      ApplyList: [],

      total: 0,//总记录数


      //审核对话框状态
      SH_DialogVisible: false,

      // 修改对话框状态
      xgyj_DialogVisible: false,

      // 审核表单信息
      SH_Form: {
        apply_city: '',
        apply_del: '',
        apply_disyx: '',
        apply_dwname: '',
        apply_dwxz: '',
        apply_gxds: '',
        apply_id: '',
        apply_jsly: '',
        apply_lastopt: '',
        apply_lhpydis: '',
        apply_scode: '',
        apply_status: '',
        apply_worklx: '',
        univ_code: '',
        univ_name: '',
        user_id: '',
        apply_shyj: '',
      },

      //添加用户弹窗表单验证
      SH_FormRules: {
        SH_radio: [
          // {required: true, validator: "请选择审核结果", trigger: 'blur'}
          {required: true, validator: validate_shjg, trigger: 'blur'}
        ],
        apply_shyj: [
          {required: true, min: 1, max: 200, message: '请输入200字以内审核意见', trigger: 'blur'},
        ]
      },


    };
  },

}
</script>

<style scoped>
.search_box {
  width: 100%;
}

#rev_form >>> .el-form-item__label {
  font-size: 20px;
}

#SH_form >>> .el-form-item__label {
  font-size: 20px;
}

.user_SH_input {
  font-size: 20px;
}


</style>
