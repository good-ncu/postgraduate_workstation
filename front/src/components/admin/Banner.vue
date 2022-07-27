<template>
  <div>

    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>广告管理</el-breadcrumb-item>
      <el-breadcrumb-item>轮播图管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <div style="width: 60%;float: left">
        <el-table
            height="600px"
            :data="imgList"
            style="width: 100%">
          <el-table-column
              label="编号"
              width="150">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.img_id }}</span>
            </template>
          </el-table-column>
          <el-table-column
              label="图片"
              width="200">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <img :src="scope.row.img_src" width="135px" height="135px">
              </div>
            </template>
          </el-table-column>
          <el-table-column
              label="简介"
              width="150">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.img_text }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="showImgDialog(scope.row.img_id,scope.row.img_src,scope.row.img_text)">编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deleteImg(scope.row.img_id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>


      <div style="float: left; margin-top: 20px;margin-left: 25px">
        <el-form label-position="top" label-width="80px" :model="addImg">
          <el-form-item label="图片路径">
            <el-input v-model="addImg.img_src"></el-input>
          </el-form-item>
          <el-form-item label="图片描述">
            <el-input v-model="addImg.img_text"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addImgInfo">确定添加</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-card>


    <!--修改分类名对话框-->
    <el-dialog title="修改轮播图" :visible.sync="imgDialogVisible" width="50%" @close="imgDialogClosed">
      <!--内容主体区域-->
      <el-form :model="imgForm" :rules="imgFormRules" ref="imgFormRef" label-width="70px">
        <el-form-item label="图片路径" prop="img_src" label-width="100px">
          <el-input v-model="imgForm.img_src"></el-input>
        </el-form-item>
        <el-form-item label="图片描述" prop="img_text" label-width="100px">
          <el-input v-model="imgForm.img_text"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="imgDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="imgInfo">确定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  created() {
    this.getImgList();
  },
  methods: {
    //获取权限表
    async getImgList() {
      const {data: res} = await this.$http.get("getImg");
      this.imgList = res;
    },


    async addImgInfo() {
      if (this.addImg.img_src == ''||this.addImg.img_text=='') {
        return this.$message.error("请输入图片信息");
      }
      const {data: res} = await this.$http.get("addImg?img_src=" + this.addImg.img_src+"&img_text="+this.addImg.img_text);
      if (res != "success") {
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
      this.addImg.img_src='';
      this.addImg.img_text='';
      this.getImgList();
    },

    //显示分类修改对话框
    async showImgDialog(img_id,img_src,img_text) {
      this.imgForm.img_id=img_id;
      this.imgForm.img_src=img_src;
      this.imgForm.img_text=img_text;
      this.imgDialogVisible = true;
    },
    //关闭窗口
    imgDialogClosed() {
      // this.$refs.classFormRef.resetField();//重置信息
    },



    //确认修改轮播图
    imgInfo() {
      this.$refs.imgFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.get(`updateImg?img_id=${this.imgForm.img_id}&img_src=${this.imgForm.img_src}&img_text=${this.imgForm.img_text}`);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        //隐藏
        this.imgDialogVisible = false;
        this.getImgList();
      })
    },


    //删除图片
    async deleteImg(img_id) {
      const confirmResult = await this.$confirm('此操作将永久删除改图片，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteImg?img_id=" + img_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getImgList();
    },



  },
  data() {
    return {
      //轮播图数据
      imgList:[],
      addImg:{
        img_src:'',
        img_text:''
      },

      //对话框数据
      imgDialogVisible:false,
      imgForm:{
        img_id:'',
        img_src:'',
        img_text:''
      },
      //验证数据
      imgFormRules: {
        img_src: [
          {required: true, message: '请填写图片路径', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        img_text: [
          {required: true, message: '请填写图片简介', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
      }

    }
  }
}
</script>

<style scoped>

</style>