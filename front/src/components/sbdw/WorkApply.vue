<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <!--      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>-->
      <el-breadcrumb-item>工作站申报</el-breadcrumb-item>
      <el-breadcrumb-item>工作站报备</el-breadcrumb-item>
    </el-breadcrumb>


    <!--用户列表主体-->
    <el-card>
      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="UserWork" border stripe style="font-size: 18px">
        <el-table-column label="申请单位名称" prop="apply_dwname" align="center"></el-table-column>
        <el-table-column label="申请单位性质" prop="apply_dwxz" align="center"></el-table-column>
        <el-table-column label="技术领域" prop="apply_jsly" align="center"></el-table-column>
        <el-table-column label="社会统一信用代码" prop="apply_scode" align="center"></el-table-column>
        <el-table-column label="联合培养学科或专业类别" prop="apply_lhpydis" align="center" width="140px"></el-table-column>
        <el-table-column label="学科专业所在院系" prop="apply_disyx" align="center"></el-table-column>
        <el-table-column label="高校牵头导师" prop="apply_gxds" align="center"></el-table-column>
        <el-table-column label="所在市" prop="apply_city" align="center"></el-table-column>
        <el-table-column label="工作站类型" prop="apply_worklx" align="center"></el-table-column>
        <el-table-column label="相关附件" align="center">
          <el-button
              type="primary" @click="pdf_click" size="small" plain>
            PDF申请书
          </el-button>
          <el-button style="margin-top: 10px"
                     type="success"
                     @click="zc_click" size="small" plain>
            证明材料
          </el-button>

        </el-table-column>
        <el-table-column label="状态" prop="apply_status" align="center"></el-table-column>
      </el-table>


      <el-row :gutter="120" style="margin-top: 20px">
        <!--        默认显示，不是0时隐藏-->
        <el-col :span="2">
          <el-button type="primary" @click="openAddApply()" style="font-size: medium;"
                     v-if="apply_status_now==3">填写申请
          </el-button>
        </el-col>

        <!--        <el-col :span="2">-->
        <!--          <el-button type="primary" @click="zs_apply()" style="font-size: medium" plain-->
        <!--                     v-if="apply_status_now==0">继续填写-->
        <!--          </el-button>-->
        <!--        </el-col>-->

        <el-col :span="2">
          <el-button type="info" @click="" style="font-size: medium" v-if="apply_status_now==0">
            等待审核
          </el-button>
        </el-col>
        <!--        <el-col :span="2">-->
        <!--          <el-button type="info" @click="openAddUser()" style="font-size: medium">撤销申请</el-button>-->
        <!--        </el-col>-->
        <!--        已退回时显示修改申请按钮-->
        <el-col :span="2">
          <el-button type="warning" @click="updateApply()" style="font-size: medium" v-if="apply_status_now==2">修改申请
          </el-button>
        </el-col>
        <!--       通过或已退回后显示查看意见按钮-->
        <el-col :span="2">
          <el-button :type="apply_status_now==1?'success':'danger'" @click="openReview()" style="font-size: medium"
                     v-if="apply_status_now!=0&&apply_status_now!=3">查看意见
          </el-button>
        </el-col>
      </el-row>

    </el-card>


    <!--填写申请弹窗-->
    <el-dialog title="填写报备信息" :visible.sync="workDialogVisible" width="70%" @close="workDialogClosed">
      <!--内容主体区域-->
      <el-form :model="workForm" :rules="workFormRules" ref="workFormRef" label-width="70px" id="add_Work_from">
        <el-form-item label="申请单位名称" prop="apply_dwname" label-width="250px">
          <el-input v-model="workForm.apply_dwname" clearable :disabled="true" class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="申请单位性质" prop="apply_dwxz" label-width="250px">
          <el-input v-model="workForm.apply_dwxz" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="技术领域" prop="apply_jsly" label-width="250px">
          <el-input v-model="workForm.apply_jsly" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="apply_scode" label-width="250px">
          <el-input v-model="workForm.apply_scode" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="联合培养学科或专业类别" prop="apply_lhpydis" label-width="250px">
          <el-input v-model="workForm.apply_lhpydis" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="学科专业所在院系" prop="apply_disyx" label-width="250px">
          <el-input v-model="workForm.apply_disyx" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="高校牵头导师" prop="apply_gxds" label-width="250px">
          <el-input v-model="workForm.apply_gxds" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="所在市" prop="apply_city" label-width="250px">
          <el-input v-model="workForm.apply_city" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="工作站类型" prop="apply_worklx" label-width="250px">
          <el-select v-model="workForm.apply_worklx" clearable placeholder="请选择工作站类型" :popper-append-to-body="false"
                     style="font-size:1.5em;width: 500px; font-weight: normal">
            <el-option
                v-for="item in lx_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                style="font-size: 20px">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="PDF格式申请书（盖章扫描件）" label-width="320px">
          <el-upload
              ref="upload_pdf"
              :action=upload_pdf_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove2"
              :limit="1"
              :on-success="successUpload1"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload2"
              accept=".pdf" class="user_add_input">
            <el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">只能上传.pdf格式文件（限一项，大小限制10MB），文件上传后点击文件名即可预览</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="证明材料" label-width="320px">
          <el-upload
              ref="upload_file"
              :action=upload_file_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :limit="1"
              :on-success="successUpload2"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload" class="user_add_input">
            <el-button el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">请将证明材料合并为一个压缩包上传（限制文件大小20MB），文件上传后点击文件名即可预览或下载查看</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->

      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="clearData">清空</el-button>-->
        <el-button @click="workDialogVisible=false" style="font-size: 20px">取消</el-button>
        <el-button type="warning" plain @click="workDialogClear" style="font-size: 20px">清空</el-button>
        <!--        <el-button type="primary" plain @click="zstore_Apply" style="font-size: 20px">暂存</el-button>-->
        <el-button type="primary" @click="addApply" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


    <!--继续填写申请弹窗-->
    <el-dialog title="继续填写报备信息" :visible.sync="zs_DialogVisible" width="70%" @close="zs_DialogClosed">
      <!--内容主体区域-->
      <el-form :model="workForm" :rules="workFormRules" ref="workFormRef" label-width="70px" id="zs_Work_from">
        <el-form-item label="申请单位名称" prop="apply_dwname" label-width="250px">
          <el-input v-model="workForm.apply_dwname" clearable :disabled="true" class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="申请单位性质" prop="apply_dwxz" label-width="250px">
          <el-input v-model="workForm.apply_dwxz" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="技术领域" prop="apply_jsly" label-width="250px">
          <el-input v-model="workForm.apply_jsly" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="apply_scode" label-width="250px">
          <el-input v-model="workForm.apply_scode" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="联合培养学科或专业类别" prop="apply_lhpydis" label-width="250px">
          <el-input v-model="workForm.apply_lhpydis" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="学科专业所在院系" prop="apply_disyx" label-width="250px">
          <el-input v-model="workForm.apply_disyx" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="高校牵头导师" prop="apply_gxds" label-width="250px">
          <el-input v-model="workForm.apply_gxds" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="所在市" prop="apply_city" label-width="250px">
          <el-input v-model="workForm.apply_city" clearable class="user_add_input"></el-input>
        </el-form-item>
        <el-form-item label="工作站类型" prop="apply_worklx" label-width="250px">
          <el-select v-model="workForm.apply_worklx" clearable placeholder="请选择工作站类型" :popper-append-to-body="false"
                     style="font-size:1.5em;width: 500px; font-weight: normal">
            <el-option
                v-for="item in lx_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                style="font-size: 20px">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="PDF格式申请书（盖章扫描件）" label-width="320px">
          <el-upload
              ref="upload_rev_pdf"
              :action=upload_pdf_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove2"
              :limit="1"
              :on-success="successUpload1"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload_rev_pdf"
              accept=".pdf">
            <el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">只能上传.pdf格式文件（限一项，大小限制10MB），文件上传后点击文件名即可预览</div>
          </el-upload>
          <el-button
              type="primary" @click="pdf_click" size="small" plain style="font-size: 20px">
            查看上次申请提交的申请书
          </el-button>

        </el-form-item>
        <el-form-item label="证明材料" label-width="320px">
          <el-upload
              ref="upload_rev_file"
              :action=upload_file_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :limit="1"
              :on-success="successUpload2"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload_rev_zc">
            <el-button el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">请将证明材料合并为一个压缩包上传（限制文件大小20MB），文件上传后点击文件名即可预览或下载查看</div>
          </el-upload>

          <el-button style="margin-top: 10px; font-size: 20px"
                     type="success"
                     @click="zc_click" size="small" plain>
            查看上次申请提交的证明材料
          </el-button>

        </el-form-item>
      </el-form>
      <!--内容底部区域-->

      <span slot="footer" class="dialog-footer">
<!--        <el-button @click="clearData">清空</el-button>-->
        <el-button @click="zs_DialogVisible=false" style="font-size: 20px">取消</el-button>
        <el-button type="warning" plain @click="zs_DialogClear" style="font-size: 20px">清空</el-button>
        <el-button type="primary" plain @click="zstore_Apply" style="font-size: 20px">暂存</el-button>
        <el-button type="primary" @click="zs_up_Apply" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


    <el-dialog title="修改报备申请" :visible.sync="rev_DialogVisible" width="70%" @close="rev_DialogClosed">
      <!--内容主体区域-->
      <el-form :model="workForm" :rules="workFormRules" ref="revFormRef" label-width="70px" id="rev_Work_from">
        <el-form-item label="申请单位名称" prop="apply_dwname" label-width="250px">
          <el-input v-model="workForm.apply_dwname" clearable :disabled="true" style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="申请单位性质" prop="apply_dwxz" label-width="250px">
          <el-input v-model="workForm.apply_dwxz" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="技术领域" prop="apply_jsly" label-width="250px">
          <el-input v-model="workForm.apply_jsly" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="apply_scode" label-width="250px">
          <el-input v-model="workForm.apply_scode" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="联合培养学科或专业类别" prop="apply_lhpydis" label-width="250px">
          <el-input v-model="workForm.apply_lhpydis" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="学科专业所在院系" prop="apply_disyx" label-width="250px">
          <el-input v-model="workForm.apply_disyx" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="高校牵头导师" prop="apply_gxds" label-width="250px">
          <el-input v-model="workForm.apply_gxds" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="所在市" prop="apply_city" label-width="250px">
          <el-input v-model="workForm.apply_city" clearable style="font-size: 20px"></el-input>
        </el-form-item>
        <el-form-item label="工作站类型" prop="apply_worklx" label-width="250px">
          <el-select v-model="workForm.apply_worklx" clearable placeholder="请选择工作站类型" :popper-append-to-body="false"
                     style="font-size:1.5em;width: 500px; font-weight: normal">
            <el-option
                v-for="item in lx_options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                style="font-size: 20px">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="PDF格式申请书（盖章扫描件）" label-width="320px">
          <el-upload
              ref="upload_rev_pdf"
              :action=upload_pdf_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove2"
              :limit="1"
              :on-success="successUpload1"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload_rev_pdf"
              accept=".pdf">
            <el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">只能上传.pdf格式文件（限一项，大小限制10MB），文件上传后点击文件名即可预览</div>
          </el-upload>
          <el-button
              type="primary" @click="pdf_click" size="small" plain style="font-size: 20px">
            查看上次申请提交的申请书
          </el-button>

        </el-form-item>
        <el-form-item label="证明材料" label-width="320px">
          <el-upload
              ref="upload_rev_file"
              :action=upload_file_url
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :limit="1"
              :on-success="successUpload2"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload_rev_zc">
            <el-button el-button size="small" type="primary" style="font-size: 20px">上传文件</el-button>
            <div slot="tip" style="font-size: 14px; color: red">请将证明材料合并为一个压缩包上传（限制文件大小20MB），文件上传后点击文件名即可预览或下载查看</div>
          </el-upload>

          <el-button style="margin-top: 10px; font-size: 20px"
                     type="success"
                     @click="zc_click" size="small" plain>
            查看上次申请提交的证明材料
          </el-button>

        </el-form-item>
      </el-form>
      <!--内容底部区域-->

      <span slot="footer" class="dialog-footer">
        <el-button @click="rev_DialogVisible=false" style="font-size: 20px">取消</el-button>
        <el-button type="warning" plain @click="rev_DialogClear" style="font-size: 20px">清空</el-button>
        <el-button type="primary" @click="rev_Apply" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>


    <!--    查看审核意见-->
    <el-dialog title="审核意见" :visible.sync="shyj_DialogVisible" width="50%" @close="shyj_DialogClosed">
      <!--内容主体区域-->
      <el-form>
        <el-form-item>
          <el-input style="font-size: 24px"
                    type="textarea"
                    :autosize="{ minRows: 4, maxRows: 20}"
                    placeholder="无审核意见"
                    v-model="this.data_shyj"
                    :readonly="true">
          </el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="shyj_DialogClosed" style="font-size: 20px">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>


// 手机号码验证
import * as vm from "vm";

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
    this.user_id = localStorage.getItem("user_id");
    this.user_sbdw = localStorage.getItem("user_sbdw");
    console.log(this.user_id);
    this.getUserWork();
    this.getUserFile();
  },

  methods: {


    // 限制PDF文件提交间隔
    throttle_pdf() {
      //获取当前时间的时间戳
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime == 0) {
        console.log('触发事件');
        this.lastTime = now;
      } else {
        if ((now - this.lastTime) > 5000) {

          this.lastTime = now;
          console.log('间隔大于2秒，触发方法');
          //添加自己要调用的方法
        } else {
          console.log('不触发');
        }
      }
    },


    // 限制支撑材料提交间隔
    throttle_zc() {
      //获取当前时间的时间戳
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime == 0) {
        console.log('触发事件');
        this.lastTime = now;
      } else {
        if ((now - this.lastTime) > 5000) {

          this.lastTime = now;
          console.log('间隔大于2秒，触发方法');
          //添加自己要调用的方法
        } else {
          console.log('不触发');
        }
      }
    },


    // 启动提交申请弹窗
    openAddApply() {
      this.getYear();
      this.workForm.apply_dwname = this.user_sbdw;
      console.log(this.user_sbdw);
      this.workDialogVisible = true;
    },


    //开启继续填写弹窗
    async zs_apply() {
      const {data: res0} = await this.$http.get(`getApplyByUserID?user_id=${this.user_id}`);
      this.workForm = res0;
      console.log(this.workForm);

      const {data: res} = await this.$http.get(`getFileByUserID?user_id=${this.user_id}`);
      this.old_file_info = res;
      console.log(this.old_file_info);

      this.zs_DialogVisible = true;
    },


    //修改申请
    async updateApply() {
      const {data: res0} = await this.$http.get(`getApplyByUserID?user_id=${this.user_id}`);
      this.workForm = res0;
      console.log(this.workForm);

      const {data: res} = await this.$http.get(`getFileByUserID?user_id=${this.user_id}`);
      this.old_file_info = res;
      console.log(this.old_file_info);


      console.log(this.fileList_rev_pdf);
      console.log(this.fileList_rev_zc);

      this.rev_DialogVisible = true;
    },


    // 查看评审意见
    openReview() {
      this.shyj_DialogVisible = true;
    },


    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3);
    },

    // 查看上传的文件
    pdf_click() {
      console.log(this.old_file_info.pdf_url);
      window.open(this.old_file_info.pdf_url);
    },

    zc_click() {
      console.log(this.old_file_info.zc_url);
      window.open(this.old_file_info.zc_url);
    },


    //预览PDF
    handlePreview(file) {
      console.log(file);
      //window_open方法预览PDF
      window.open(file.response.review_url);
    },
    handlePreview_rev(file) {
      console.log(file);
      //window_open方法预览PDF
      window.open(file.review_url);
    },


    // 更新文件
    handleExceed_rev(files, fileList) {
      this.$message.success(`更新文件成功`);
    },


    beforeRemove_rev_pdf(file, fileList) {
      var isLt5M = file.size / 1024 / 1024 < 10
      if (!isLt5M || file.size == null) {
        var i = fileList.indexOf(file)
        fileList.splice(i, 1) // 自动删除不符合要求的文件，不让它出现在预览列表中
        return false // 只有return false 才会真的
      } else {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },

    beforeRemove_rev_zc(file, fileList) {
      var isLt5M = file.size / 1024 / 1024 < 20
      if (!isLt5M || file.size == null) {
        var i = fileList.indexOf(file)
        fileList.splice(i, 1) // 自动删除不符合要求的文件，不让它出现在预览列表中
        return false // 只有return false 才会真的
      } else {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },

    beforeUpload_rev_pdf(file) {
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime1 == 0) {
        console.log('触发事件');
        this.lastTime1 = now;
      } else {
        if ((now - this.lastTime1) > this.outTime) {
          this.file_ohuo_1 = '0';
          this.lastTime1 = now;
          //添加自己要调用的方法
          this.$confirm('将修改上次提交的PDF申请书，是否继续？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.log(file);
            this.fileLIst1 = '1';
            const isLt2M = file.size / 1024 / 1024 < 10     //这里做文件大小限制
            if (!isLt2M) {
              this.$message({
                message: '上传文件大小不能超过 10MB',
                type: 'warning'
              });
            }
            return isLt2M
          }).catch(() => {
            this.fileLIst1 = '0';
            this.$refs.upload_rev_pdf.clearFiles();
            vm.$refs.upload_rev_pdf.uploadFiles.length = 0;
          })
        } else {
          this.file_ohuo_1 = '1';
          this.$message({
            message: '10秒内仅允许上传一次文件',
            type: 'warning'
          });
          return false;
        }
      }


    },

    beforeUpload_rev_zc(file) {
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime2 == 0) {
        console.log('触发事件');
        this.lastTime2 = now;
      } else {
        if ((now - this.lastTime2) > this.outTime) {
          this.file_ohuo_2 = '0';
          this.lastTime2 = now;
          //添加自己要调用的方法
          this.$confirm('将修改上次提交的证明材料，是否继续？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.fileLIst2 = '1';
            const isLt2M = file.size / 1024 / 1024 < 20     //这里做文件大小限制
            if (!isLt2M) {
              this.$message({
                message: '上传文件大小不能超过 20MB',
                type: 'warning'
              });
            }
            return isLt2M
          }).catch(() => {
            this.fileLIst2 = '0';
            this.$refs.upload_rev_file.clearFiles();
            vm.$refs.upload_rev_file.uploadFiles.length = 0;
          })
        } else {
          this.file_ohuo_2 = '1';
          this.$message({
            message: '10秒内仅允许上传一次文件',
            type: 'warning'
          });
          return false;
        }
      }
    },


    // 提交数据到数据库，每次打开时都显示提交的数据
    submitUpload() {
      console.log(1)
      // this.$refs.upload.submit();
    },


    successUpload1(response, file, fileList) {
      console.log(response);
      console.log(response.file_name);
      this.file_pdf.file_name = response.file_name;
      this.file_pdf.file_url = response.review_url;
      this.file_pdf.file_path = response.real_Path;
      this.fileLIst1 = '1'
    },

    successUpload2(response, file, fileList) {
      console.log(response);
      console.log(response.file_name);
      this.file_zc.file_name = response.file_name;
      this.file_zc.file_url = response.review_url;
      this.file_zc.file_path = response.real_Path;
      this.file_zc.file_type = '1';
      this.fileLIst2 = '1';
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },


    handleExceed(files, fileList) {
      this.$message.warning(`限制上传 1 个文件`);
    },

    beforeRemove(file, fileList) {
      var isLt5M = file.size / 1024 / 1024 < 20
      if (!isLt5M || this.file_ohuo_2 === '1') {
        var i = fileList.indexOf(file)
        fileList.splice(i, 1) // 自动删除不符合要求的文件，不让它出现在预览列表中
        return false // 只有return false 才会真的
      } else {
        return this.$confirm(`确定移除 ${file.name}？`);
      }


    },

    beforeRemove2(file, fileList) {
      this.fileLIst1 = '0';
      var isLt5M = file.size / 1024 / 1024 < 10
      if (!isLt5M || this.file_ohuo_1 === '1') {
        var i = fileList.indexOf(file)
        fileList.splice(i, 1) // 自动删除不符合要求的文件，不让它出现在预览列表中
        return false // 只有return false 才会真的
      } else {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },

    beforeUpload(file) {
      this.fileLIst2 = '0';
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime2 == 0) {
        console.log('触发事件');
        this.lastTime2 = now;
      } else {
        if ((now - this.lastTime2) > this.outTime) {
          this.file_ohuo_2 = '0';
          //重置上一次点击时间，2000是我自己设置的2秒间隔，根据自己的需要更改
          this.lastTime2 = now;
          //添加自己要调用的方法
          const isLt2M = file.size / 1024 / 1024 < 20     //这里做文件大小限制
          if (!isLt2M) {
            this.$message({
              message: '上传文件大小不能超过 20MB',
              type: 'warning'
            });
          }
          return isLt2M
        } else {
          this.file_ohuo_2 = '1';
          this.$message({
            message: '10秒内仅允许上传一次文件',
            type: 'warning'
          });
          return false;
        }
      }

    },

    beforeUpload2(file) {
      this.fileLIst1 = '0';
      let now = new Date().valueOf();
      //第一次点击
      if (this.lastTime1 == 0) {
        console.log('触发事件');
        this.lastTime1 = now;
      } else {
        if ((now - this.lastTime1) > this.outTime) {
          this.file_ohuo_1 = '0';
          //重置上一次点击时间，2000是我自己设置的2秒间隔，根据自己的需要更改
          this.lastTime1 = now;
          //添加自己要调用的方法
          const isLt2M = file.size / 1024 / 1024 < 10     //这里做文件大小限制
          if (!isLt2M) {
            this.$message({
              message: '上传文件大小不能超过 10MB',
              type: 'warning'
            });
          }
          return isLt2M
        } else {
          this.file_ohuo_1 = '1';
          this.$message({
            message: '10秒内仅允许上传一次文件',
            type: 'warning'
          });
          return false;
        }
      }
    },


    async zs_up_Apply() {
      this.$confirm('是否提交申请？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.revFormRef.validate(async valid => {
          console.log("111");
          if (!valid) return;
          // console.log(this.fileLIst1.length)
          //判断是否完成文件上传
          // if (this.fileLIst1 == '0' || this.fileLIst2 == '0') {
          //   return this.$message.warning("请完成证明材料上传");
          // }


          // // 查询数据库，判定用户已将证明材料提交齐全
          // let pdf_flag = 0;
          // let zc_flag = 0;


          //补齐文本数据
          this.workForm.user_id = this.user_id;
          this.workForm.univ_name = this.self_info.univ_name;
          this.workForm.univ_code = this.self_info.univ_code;
          this.workForm.apply_status = 0;
          this.getTime();
          this.workForm.apply_lastopt = this.nowTime;
          console.log(this.workForm);
          // 更新申请文本数据
          const {data: res} = await this.$http.post("rev_Apply", this.workForm);

          console.log("nm");

          if (this.fileLIst1 == '1') {
            console.log("nm1");
            this.file_pdf.univ_name = this.self_info.univ_name;
            this.file_pdf.univ_code = this.self_info.univ_code;
            this.file_pdf.user_id = this.user_id;
            console.log(this.file_pdf);
            const {data: res1} = await this.$http.post("rev_File", this.file_pdf);
          }

          if (this.fileLIst2 == '1') {
            console.log("nm2");
            this.file_zc.univ_name = this.self_info.univ_name;
            this.file_zc.univ_code = this.self_info.univ_code;
            this.file_zc.user_id = this.user_id;
            console.log(this.file_zc);
            const {data: res2} = await this.$http.post("rev_File", this.file_zc);
          }


          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.$message.success("操作成功");
          this.rev_DialogVisible = false;
          await this.getUserWork();
          await this.getUserFile();
        })
      }).catch(() => {

      })
    },


    //确认更新设站单位申请信息（增强版）
    async rev_Apply() {
      this.$confirm('此操作将更新上次提交的申请数据，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.revFormRef.validate(async valid => {
          console.log("111");
          if (!valid) return;
          console.log(this.fileLIst1.length)
          //判断是否完成文件上传
          // if (this.fileLIst1 == '0' || this.fileLIst2 == '0') {
          //   return this.$message.warning("请完成证明材料上传");
          // }

          //补齐文本数据
          this.workForm.user_id = this.user_id;
          this.workForm.univ_name = this.self_info.univ_name;
          this.workForm.univ_code = this.self_info.univ_code;
          this.workForm.apply_status = 0;
          this.getTime();
          this.workForm.apply_lastopt = this.nowTime;
          console.log(this.workForm);
          // 更新申请文本数据
          const {data: res} = await this.$http.post("rev_Apply", this.workForm);

          console.log("nm");

          if (this.fileLIst1 == '1') {
            console.log("nm1");
            this.file_pdf.univ_name = this.self_info.univ_name;
            this.file_pdf.univ_code = this.self_info.univ_code;
            this.file_pdf.user_id = this.user_id;
            console.log(this.file_pdf);
            const {data: res1} = await this.$http.post("rev_File", this.file_pdf);
          }

          if (this.fileLIst2 == '1') {
            console.log("nm2");
            this.file_zc.univ_name = this.self_info.univ_name;
            this.file_zc.univ_code = this.self_info.univ_code;
            this.file_zc.user_id = this.user_id;
            console.log(this.file_zc);
            const {data: res2} = await this.$http.post("rev_File", this.file_zc);
          }


          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.$message.success("操作成功");
          this.rev_DialogVisible = false;
          await this.getUserWork();
          await this.getUserFile();
        })
      }).catch(() => {

      })
    },


    // 提交之前可以暂存数据，暂存的数据不会显示到高校管理员和省厅管理员界面
    async zstore_Apply() {
      this.$refs.workFormRef.validate(async valid => {
        console.log(this.fileLIst1.length)
        //判断是否完成文件上传
        // if (this.fileLIst1 == '0' || this.fileLIst2 == '0') {
        //   return this.$message.warning("请完成证明材料上传");
        // }
        //补齐文本数据
        this.workForm.user_id = this.user_id;
        this.workForm.univ_name = this.self_info.univ_name;
        this.workForm.univ_code = this.self_info.univ_code;
        this.getTime();
        this.workForm.apply_lastopt = this.nowTime;
        this.workForm.apply_zstore = "1";
        this.apply_zstore = "1";
        // 提交申请文本数据
        const {data: res} = await this.$http.post("rev_Apply", this.workForm);
        //补齐文件数据
        if (this.fileLIst1 == '1') {
          this.file_pdf.univ_name = this.self_info.univ_name;
          this.file_pdf.univ_code = this.self_info.univ_code;
          this.file_pdf.user_id = this.user_id;
          const {data: res1} = await this.$http.post("rev_File", this.file_pdf);
        }

        if (this.fileLIst2 == '1') {
          this.file_zc.univ_name = this.self_info.univ_name;
          this.file_zc.univ_code = this.self_info.univ_code;
          this.file_zc.user_id = this.user_id;
          const {data: res2} = await this.$http.post("rev_File", this.file_zc);
        }

        if (res != "success") {
          return this.$message.error("操作失败");
        }
        this.$message.success("保存成功");
        this.zs_DialogVisible = false;
        await this.getUserWork();
        await this.getUserFile();
      })
    },


    // 再次修改暂存
    async zstore_Apply_re() {
      this.$refs.workFormRef.validate(async valid => {
        console.log(this.fileLIst1.length)
        //判断是否完成文件上传
        // if (this.fileLIst1 == '0' || this.fileLIst2 == '0') {
        //   return this.$message.warning("请完成证明材料上传");
        // }

        //补齐文本数据
        this.workForm.user_id = this.user_id;
        this.workForm.univ_name = this.self_info.univ_name;
        this.workForm.univ_code = this.self_info.univ_code;
        this.workForm.apply_status = 0;
        this.getTime();


        this.workForm.apply_zstore = "1";
        this.apply_zstore = "1";

        this.workForm.apply_lastopt = this.nowTime;
        console.log(this.workForm);
        // 更新申请文本数据
        const {data: res0} = await this.$http.post("rev_Apply", this.workForm);

        console.log("nm");

        if (this.fileLIst1 == '1') {
          console.log("nm1");
          this.file_pdf.univ_name = this.self_info.univ_name;
          this.file_pdf.univ_code = this.self_info.univ_code;
          this.file_pdf.user_id = this.user_id;
          console.log(this.file_pdf);
          const {data: res1} = await this.$http.post("rev_File", this.file_pdf);
        }

        if (this.fileLIst2 == '1') {
          console.log("nm2");
          this.file_zc.univ_name = this.self_info.univ_name;
          this.file_zc.univ_code = this.self_info.univ_code;
          this.file_zc.user_id = this.user_id;
          console.log(this.file_zc);
          const {data: res2} = await this.$http.post("rev_File", this.file_zc);
        }


        if (res0 != "success") {
          return this.$message.error("操作失败");
        }
        this.$message.success("操作成功");
        this.rev_DialogVisible = false;
        await this.getUserWork();
        await this.getUserFile();

      })
    },

    //确认添加设站申请信息（增强版）
    async addApply() {
      this.$confirm('确认添加设站申请信息？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.workFormRef.validate(async valid => {
          if (!valid) return;
          console.log(this.fileLIst1.length)
          //判断是否完成文件上传
          if (this.fileLIst1 == '0' || this.fileLIst2 == '0') {
            return this.$message.warning("请完成证明材料上传");
          }

          //补齐文本数据
          this.workForm.user_id = this.user_id;
          this.workForm.univ_name = this.self_info.univ_name;
          this.workForm.univ_code = this.self_info.univ_code;
          this.getTime();
          this.workForm.apply_lastopt = this.nowTime;
          // 提交申请文本数据
          const {data: res} = await this.$http.post("addApply", this.workForm);

          //补齐文件数据
          this.file_pdf.univ_name = this.self_info.univ_name;
          this.file_pdf.univ_code = this.self_info.univ_code;
          this.file_pdf.user_id = this.user_id;
          this.file_zc.univ_name = this.self_info.univ_name;
          this.file_zc.univ_code = this.self_info.univ_code;
          this.file_zc.user_id = this.user_id;

          const {data: res1} = await this.$http.post("addFile", this.file_pdf);
          const {data: res2} = await this.$http.post("addFile", this.file_zc);
          if (res != "success") {
            return this.$message.error("操作失败");
          }
          this.$message.success("操作成功");
          this.workDialogVisible = false;
          await this.getUserWork();
          await this.getUserFile();
        })
      }).catch(() => {

      })
    },

    // 获取用户上传文件的文件名和服务器URL
    async getUserFile() {
      const {data: res} = await this.$http.get(`getFileByUserID?user_id=${this.user_id}`);
      this.old_file_info = res;
      console.log(this.old_file_info);
    },


    // 获取用户填报信息
    async getUserWork() {
      const {data: res} = await this.$http.get(`getUserWork?user_id=${this.user_id}`);
      // console.log(res);
      if (res == "error") {
        this.apply_status_now = '3';
        return;
      }

      console.log(res);
      this.UserWork = res;// 用户列表数据封装
      this.data_shyj = res[0].apply_shyj;
      this.apply_zstore = res[0].apply_zstore;

      console.log("zstore:" + this.apply_zstore);
      console.log(this.data_shyj);
      // console.log(this.UserWork);
      // console.log(this.UserWork.apply_status);
      let as = this.UserWork[0].apply_status;
      this.apply_status_now = as;
      console.log("审核状态：" + this.apply_status_now);

      if (this.apply_zstore === '1') {
        this.UserWork[0].apply_status = "未提交"
      } else {
        if (as === '0') {
          this.UserWork[0].apply_status = "未审核"
        } else if (as === '1') {
          this.UserWork[0].apply_status = "已通过"
        } else if (as === '2') {
          this.UserWork[0].apply_status = "已退回"
        } else if (as === '4') {
          this.UserWork[0].apply_status = "撤销资格"
        } else {
          this.apply_status_now = '3';
        }
      }
    },


    rev_DialogClosed() {
      // this.$refs.workFormRef.resetFields();
    },

    shyj_DialogClosed() {
      this.shyj_DialogVisible = false;
    },


    rev_DialogClear() {
      this.$confirm('将清除页面内容，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.workForm.apply_dwxz = '';
        this.workForm.apply_jsly = '';
        this.workForm.apply_scode = '';
        this.workForm.apply_lhpydis = '';
        this.workForm.apply_disyx = '';
        this.workForm.apply_gxds = '';
        this.workForm.apply_city = '';
        this.workForm.apply_worklx = '';
        // this.$refs.revFormRef.resetFields();
        this.$refs.upload_rev_file.clearFiles();
        this.$refs.upload_rev_pdf.clearFiles();
        vm.$refs.upload_rev_file.uploadFiles.length = 0;
        vm.$refs.upload_rev_pdf.uploadFiles.length = 0;
      }).catch(() => {
      })
    },


    // //监听添加用户操作
    workDialogClosed() {
      // this.$refs.workFormRef.resetFields();
    },

    zs_DialogClosed() {
      // this.$refs.workFormRef.resetFields();
    },

    workDialogClear() {
      this.$confirm('将清除页面内容，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.workFormRef.resetFields();
        this.$refs.upload_file.clearFiles();
        this.$refs.upload_pdf.clearFiles();
        vm.$refs.upload_file.uploadFiles.length = 0;
        vm.$refs.upload_pdf.uploadFiles.length = 0;
      }).catch(() => {
      })
    },


    zs_DialogClear() {
      this.$confirm('将清除页面内容，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.workForm.apply_dwxz = '';
        this.workForm.apply_jsly = '';
        this.workForm.apply_scode = '';
        this.workForm.apply_lhpydis = '';
        this.workForm.apply_disyx = '';
        this.workForm.apply_gxds = '';
        this.workForm.apply_city = '';
        this.workForm.apply_worklx = '';
        // this.$refs.workFormRef.resetFields();
        this.$refs.upload_file.clearFiles();
        this.$refs.upload_pdf.clearFiles();
        vm.$refs.upload_file.uploadFiles.length = 0;
        vm.$refs.upload_pdf.uploadFiles.length = 0;
      }).catch(() => {
      })
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

    getYear() {
      let date = new Date();//当前时间
      this.workForm.apply_year = date.getFullYear()
    },


  },


  data() {

    // 统一社会信用代码校验
    const validlegalbizLicNum = (rule, value, callback) => {
      console.log(value);
      let Ancode;//统一社会信用代码的每一个值
      let Ancodevalue;//统一社会信用代码每一个值的权重
      let total = 0;
      let weightedfactors = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28];//加权因子
      //不用I、O、S、V、Z
      let str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
      for (let i = 0; i < value.length - 1; i++) {
        Ancode = value.substring(i, i + 1);
        Ancodevalue = str.indexOf(Ancode);
        total = total + Ancodevalue * weightedfactors[i];
        //权重与加权因子相乘之和
      }
      let logiccheckcode = 31 - total % 31;
      if (logiccheckcode == 31) {
        logiccheckcode = 0;
      }
      let Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
      let Array_Str = Str.split(',');
      logiccheckcode = Array_Str[logiccheckcode];

      let checkcode = value.substring(17, 18);
      if (logiccheckcode != checkcode) {
        return callback(new Error('校验失败，请输入正确的统一社会信用代码！'));
      }
      return callback();
    };

    return {

      outTime: 10000,

      lastTime1: 0,
      lastTime2: 0,

      //研究生工作站类别数据
      lx_options: [
        {
          value: '企业',
          label: '企业'
        },
        {
          value: '高等院校',
          label: '高等院校'
        },
        {
          value: '科研机构',
          label: '科研机构'
        },
        {
          value: '党政机关',
          label: '党政机关',
        },

        {
          value: '村镇基层',
          label: '村镇基层'
        }
      ],

      // 判断是否暂存文件
      apply_zstore: '0',


      // 更新文件时的标志位
      flag_rev_pdf: '0',
      flag_rev_zc: '0',

      // 界面内部table的查看文件
      old_file_info: {
        pdf_url: '',
        pdf_name: '',
        zc_url: '',
        zc_name: ''
      },

      //获取文件存放路径
      file_pdf: {
        user_id: '',
        univ_name: '',
        univ_code: '',
        file_name: '',
        file_type: '0',
        file_path: '',
        file_url: '',
      },

      file_zc: {
        user_id: '',
        univ_name: '',
        univ_code: '',
        file_name: '',
        file_type: '',
        file_path: '',
        file_url: '',
      },

      // 判断文件是否连续上传 0可以再传，1不许再传
      file_ohuo_1: '0',
      file_ohuo_2: '0',


      // 判断文件是否上传
      fileLIst1: '0',
      fileLIst2: '0',

      //获取后端接口数据到表格中
      UserWork: [],
      user_id: '',
      nowTime: "",
      apply_status_now: '',
      user_sbdw: '',

      //上传PDF路由
      upload_pdf_url: "http://localhost:8081/upload_pdf",
      //上传证明材料路由
      upload_file_url: "http://localhost:8081/upload_file",

      // 本账号信息实体
      self_info: {
        univ_name: "",
        univ_code: "",
      },
      //查询信息实体
      queryInfo: {
        query: "",//查询信息
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },
      userList: [],//用户列表
      total: 0,//总记录数

      // 保存审核数据
      data_shyj: '',

      //对话框状态
      addDialogVisible: false,
      rev_DialogVisible: false,
      shyj_DialogVisible: false,

      // 添加表单信息
      workForm: {
        user_id: '',
        univ_name: '',
        univ_code: '',
        apply_dwname: '',
        apply_dwxz: '',
        apply_jsly: '',
        apply_scode: '',
        apply_lhpydis: '',
        apply_disyx: '',
        apply_gxds: '',
        apply_city: '',
        apply_worklx: '',
        apply_status: '0',
        apply_del: '0',
        apply_year: '',
        apply_lastopt: '',
        apply_zstore: '',
      },

      //显示/隐藏填写申报信息栏
      workDialogVisible: false,

      //显示和隐藏继续填写弹窗
      zs_DialogVisible: false,


      //添加用户弹窗表单验证
      workFormRules: {
        apply_dwname: [
          {required: true, message: '请输入申报单位名称', trigger: 'blur'},
          {min: 1, message: '请输入正确的申报单位名称', trigger: 'blur'}
        ],
        apply_dwxz: [
          {required: true, message: '请输入单位性质', trigger: 'blur'},
          {min: 1, message: '请输入正确的单位性质', trigger: 'blur'}
        ],
        apply_jsly: [
          {required: true, message: '请输入技术领域', trigger: 'blur'},
          {min: 1, message: '请输入正确的技术领域', trigger: 'blur'}
        ],
        apply_scode: [
          {required: true, message: '请输入申请单位统一社会信用代码', trigger: 'blur'},
          {
            type: "string",
            max: 18,
            message: "统一社会信用代码长度需为18位",
            trigger: "change"
          },
          {
            type: "string",
            /*
           统一社会信用代码由十八位的阿拉伯数字或大写英文字母（不使用I、O、Z、S、V）组成。
            第1位：登记管理部门代码（共一位字符）登记管理部门代码：使用阿拉伯数字或大写英文字母表示。​
            第2位：机构类别代码（共一位字符）
            第3位~第8位：登记管理机关行政区划码（共六位阿拉伯数字）
            第9位~第17位：主体标识码（组织机构代码）（共九位字符）
            第18位：校验码​（共一位字符）
            */
            /* pattern: /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/g,*/
            pattern: /^([1,5,9][1,2,3,9]|[2,3,4,6,7,8,A,B,C,D,E,G,Y]1)\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/,
            message: "请输入正确的统一社会信用代码，由十八位的阿拉伯数字或大写英文字母（不使用I、O、Z、S、V）组成",
            trigger: "blur"
          },
          {validator: validlegalbizLicNum, trigger: 'blur'}
        ],
        apply_lhpydis: [
          {required: true, message: '请输入联合培养学科或专业类别', trigger: 'blur'},
          {min: 1, message: '请输入正确的联合培养学科或专业类别', trigger: 'blur'}
        ],
        apply_disyx: [
          {required: true, message: '请输入学科专业所在院系', trigger: 'blur'},
          {min: 1, message: '请输入正确的学科专业所在院系', trigger: 'blur'}
        ],
        apply_gxds: [
          {required: true, message: '请输入研究生工作站高校牵头导师', trigger: 'blur'},
          {min: 1, message: '请输入正确的研究生工作站高校牵头导师', trigger: 'blur'}
        ],
        apply_city: [
          {required: true, message: '请输入该研究生工作站所在市', trigger: 'blur'},
          {min: 1, message: '请输入正确的研究生工作站所在市', trigger: 'blur'}
        ],
        apply_worklx: [
          {required: true, message: '请输入研究生工作站类型', trigger: 'blur'},
        ],

      },

    };
  },

}
</script>

<style scoped>

#add_Work_from >>> .el-form-item__label {
  font-size: 20px;
}

#rev_Work_from >>> .el-form-item__label {
  font-size: 20px;
}

.user_add_input {
  font-size: 20px;
}

.el-upload-list__item {
  font-size: 20px;
}


</style>
