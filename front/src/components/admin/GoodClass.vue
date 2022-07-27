<template>
  <div>
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>分类管理</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card>

      <div style="width: 60%;float: left">
        <el-table
            height="600px"
            :data="classList"
            style="width: 100%">
          <el-table-column
              label="编号"
              width="180">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.class_id }}</span>
            </template>
          </el-table-column>
          <el-table-column
              label="分类名称"
              width="300">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.class_name }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="showClassDialog(scope.row.class_name)">编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deleteClass(scope.row.class_id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>


      <div style="float: left; margin-top: 20px;margin-left: 25px">
        <el-form label-position="top" label-width="80px" :model="addClass">
          <el-form-item label="分类名称">
            <el-input v-model="addClass.class_name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addClassInfo">确定添加</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-card>


    <!--修改分类名对话框-->
    <el-dialog title="修改分类名称" :visible.sync="classDialogVisible" width="50%" @close="classDialogClosed">
      <!--内容主体区域-->
      <el-form :model="classForm" :rules="classFormRules" ref="classFormRef" label-width="70px">
        <el-form-item label="分类名称" prop="class_name" label-width="100px">
          <el-input v-model="classForm.class_name"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="classDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="classGoodInfo">确定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  created() {
    this.getClassList();
  },
  methods: {
    //获取分类表
    async getClassList() {
      const {data: res} = await this.$http.get("getGoodClass");
      this.classList = res;
    },


    async addClassInfo() {
      if (this.addClass.class_name == '') {
        return this.$message.error("请输入分类");
      }
      const {data: res} = await this.$http.get("addClass?class_name=" + this.addClass.class_name);
      if (res != "success") {
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
      this.getClassList();
    },

    //显示分类修改对话框
    async showClassDialog(class_name) {
      this.old_class = class_name;
      this.classForm.class_name = class_name;
      this.classDialogVisible = true;
    },
    //关闭窗口
    classDialogClosed() {
      // this.$refs.classFormRef.resetField();//重置信息
    },

    //确认修改分类
    classGoodInfo() {
      this.$refs.classFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.get(`updateGoodClassAndOld?new_class=${this.classForm.class_name}&old_class=${this.old_class}`);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        this.old_class = '',
            //隐藏
            this.classDialogVisible = false;
        this.getClassList();
      })
    },

    //删除
    async deleteClass(class_id) {
      const confirmResult = await this.$confirm('此操作将永久删除用户，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteClass?class_id=" + class_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getClassList();
    },
  },
  data() {
    return {
      //暂存分类
      old_class: '',
      //分类列表
      classList: [],

      classForm: {
        class_name: [],
      },

      //添加分类表单信息
      addClass: {
        class_name: '',
      },

      //修改分类弹窗
      classDialogVisible: false,
      //分类验证
      classFormRules: {
        class_name: [
          {required: true, message: '请填写新分类名称', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
      }
    }
  }
}
</script>

<style scoped>

</style>