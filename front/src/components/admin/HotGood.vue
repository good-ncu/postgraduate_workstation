<template>
  <div>


    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>广告管理</el-breadcrumb-item>
      <el-breadcrumb-item>热门商品</el-breadcrumb-item>
    </el-breadcrumb>

    <!--商品列表主体-->
    <el-card>


      <el-row :gutter="120">
        <!--搜索区域-->
        <el-col :span="10">
          <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getGoodList">
            <el-button slot="append" icon="el-icon-search" @click="getGoodList"></el-button>
          </el-input>
        </el-col>
        <!--搜索按钮-->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加商品</el-button>
        </el-col>
      </el-row>


      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="hotGoodList" border stripe>
        <el-table-column label="收藏排行" type="index"></el-table-column><!--索引列-->
        <el-table-column label="商品图片" prop="good_image" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.good_image" width="105px" height="105px">
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="good_name"></el-table-column>
        <el-table-column label="分类" prop="class_name"></el-table-column>
        <el-table-column label="单价" prop="good_price"></el-table-column>
        <el-table-column label="商品简介" prop="good_introduce"></el-table-column>
        <el-table-column label="上架状态" prop="good_state">
          <!--作用域插槽-->
          <template slot-scope="scope">
            <el-switch v-model="scope.row.good_state" @change="goodStateChanged(scope.row)"
                       :active-value="1" :inactive-value="0"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="收藏用户数" prop="good_star">
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!--修改-->
            <el-tooltip effect="dark" content="修改信息" placement="top-start" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" size="mini"
                         @click="showEditDialog(scope.row.good_id)"></el-button>
            </el-tooltip>
            <!--删除-->
            <el-tooltip effect="dark" content="删除商品" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                         @click="deleteGood(scope.row.good_id)"></el-button>
            </el-tooltip>
            <!--分类修改-->
            <el-tooltip effect="dark" content="修改分类" placement="top-start" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini"
                         @click="showClassDialog(scope.row.class_name,scope.row.good_id)"></el-button>
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


    <!--新增商品区域-->
    <el-dialog title="添加商品" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--内容主体区域-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="名称" prop="good_name">
          <el-input v-model="addForm.good_name"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="class_name">
          <el-select v-model="addForm.class_name" placeholder="请选择">
            <el-option
                v-for="item in classList"
                :key="item.class_id"
                :label="item.class_name"
                :value="item.class_name">
              <span style="float: left">{{ item.class_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="good_image">
          <el-input v-model="addForm.good_image"></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="good_price">
          <el-input v-model="addForm.good_price"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="good_introduce">
          <el-input v-model="addForm.good_introduce"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="addGood">确定</el-button>
      </span>
    </el-dialog>

    <!--修改商品信息区域-->
    <el-dialog title="修改商品信息" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <!--内容主体区域-->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="名称" prop="good_name">
          <el-input v-model="editForm.good_name"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="class_name">
          <el-select v-model="editForm.class_name" placeholder="请选择">
            <el-option
                v-for="item in classList"
                :key="item.class_id"
                :label="item.class_name"
                :value="item.class_name">
              <span style="float: left">{{ item.class_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图片" prop="good_image">
          <el-input v-model="editForm.good_image"></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="good_price">
          <el-input v-model="editForm.good_price"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="good_introduce">
          <el-input v-model="editForm.good_introduce"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="editGoodInfo">确定</el-button>
      </span>
    </el-dialog>


    <!--修改商品分类对话框-->
    <el-dialog title="修改商品分类" :visible.sync="classDialogVisible" width="50%" @close="classDialogClosed">
      <!--内容主体区域-->
      <el-form :model="classForm" :rules="classFormRules" ref="classFormRef" label-width="70px">
        <el-form-item label="分类" prop="class_name">
          <el-select v-model="classForm.class_name" placeholder="请选择">
            <el-option
                v-for="item in classList"
                :key="item.class_id"
                :label="item.class_name"
                :value="item.class_name">
              <span style="float: left">{{ item.class_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
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
    this.getGoodList();
    this.getClassList();
  },
  methods: {

    //获取分类表
    async getClassList() {
      const {data: res} = await this.$http.get("getGoodClass");
      this.classList = res;
    },


    // 最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getGoodList();
    },
    // pageNum的触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getGoodList();
    },
    async goodStateChanged(goodInfo) {
      //写入新状态的值
      const {data: res} = await this.$http.put(`goodState?good_id=${goodInfo.good_id}&good_state=${goodInfo.good_state}`);
      if (res != "success") {
        goodInfo.good_id = !goodInfo.good_id;
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
    },
    // 按star数量获取所有商品
    async getGoodList() {
      const {data: res} = await this.$http.get("getGoodByStar", {params: this.queryInfo});
      this.hotGoodList = res.data;// 数据封装
      this.total = res.number;// 总数封装
      this.good_state = res.data.good_state;
    },
    // 根据主键删除商品信息
    async deleteGood(good_id) {
      const confirmResult = await this.$confirm('此操作将永久删除用户，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteGood?good_id=" + good_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getGoodList();
    },


    //添加商品对话框事件
    //监听添加用户操作
    //显示对话框
    async showEditDialog(good_id) {
      const {data: res} = await this.$http.get("getUpdateGood?good_id=" + good_id);
      this.editForm = res;//查询出的用户信息反填回编辑表单中
      this.editDialogVisible = true;//开启编辑对话框
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    addGood() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("addGood", this.addForm);
        if (res != "success") {
          return this.$message.error("操作失败");
        }
        this.$message.success("操作成功");
        this.addDialogVisible = false;
        this.getGoodList();
      });
    },


    //修改商品信息
    //关闭窗口
    editDialogClosed() {
      this.$refs.editFormRef.resetField();//重置信息
    },
    //确认修改用户信息
    editGoodInfo() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        //验证成功 发起修改请求
        const {data: res} = await this.$http.put("editGood", this.editForm);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        //隐藏
        this.editDialogVisible = false;
        this.getGoodList();
      })
    },


    //修改商品分类
    //显示分类修改对话框
    async showClassDialog(class_name, good_id) {
      this.classForm.class_name = class_name;
      this.classForm.good_id = good_id;
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
        const {data: res} = await this.$http.get(`updateGoodClass?class_name=${this.classForm.class_name}&good_id=${this.classForm.good_id}`);
        if (res != "success") return this.$message.error("操作失败");
        this.$message.success("操作成功");
        //隐藏
        this.classDialogVisible = false;
        this.getGoodList();
      })
    },

  },

  data() {
    return {


      //修改商品信息
      editForm: {},
      //显示/隐藏修改商品栏
      editDialogVisible: false,


      //修改对应id商品分类
      classForm: {
        class_name: '',
        good_id: ''
      },
      //显示/隐藏修改权限栏
      classDialogVisible: false,


      //分类列表
      classList: [],
      //商品列表
      hotGoodList: [],
      //查询信息实体
      queryInfo: {
        query: "",//查询信息
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },
      total: 0,//总记录数

      //添加商品
      addDialogVisible: false,//对话框状态
      // 添加表单信息
      addForm: {
        good_name: '',
        class_name: '',
        good_image: '',
        good_price: '',
        good_introduce: '',
      },

      //表单验证
      addFormRules: {
        good_name: [
          {required: true, message: '请输入商品名称', trigger: 'blur'},
          // {min: 5, max: 8, message: '长度在5到8个字符', trigger: 'blur'}
        ],
        class_name: [
          {required: true, message: '请选择分类名称', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        good_image: [
          {required: true, message: '请上传图片', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}
        ],
        good_price: [
          {required: true, message: '请输入商品价格', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        good_introduce: [
          {required: true, message: '请输入商品简介', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
      },
      //修改表单验证
      editFormRules: {
        good_name: [
          {required: true, message: '请输入商品名称', trigger: 'blur'},
          // {min: 5, max: 8, message: '长度在5到8个字符', trigger: 'blur'}
        ],
        class_name: [
          {required: true, message: '请选择分类名称', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
        good_image: [
          {required: true, message: '请上传图片', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}
        ],
        good_price: [
          {required: true, message: '请输入商品价格', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
        good_introduce: [
          {required: true, message: '请输入商品简介', trigger: 'blur'},
          // {min: 5, max: 15, message: '请输入正确的邮箱地址', trigger: 'blur'}

        ],
      },
      classFormRules: {
        class_name: [
          {required: true, message: '请选择分类名称', trigger: 'blur'},
          // {min: 6, max: 8, message: '长度在6到8个字符', trigger: 'blur'}
        ],
      }

    }
  }

}
</script>

<style scoped>

</style>