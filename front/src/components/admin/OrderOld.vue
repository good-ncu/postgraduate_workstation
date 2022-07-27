<template>
  <div>

    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>已发货订单</el-breadcrumb-item>
    </el-breadcrumb>

    <!--订单列表主体-->
    <el-card>


      <el-row :gutter="120">
        <!--搜索区域-->
        <el-col :span="10">
          <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getOrderListOld">
            <el-button slot="append" icon="el-icon-search" @click="getOrderListOld"></el-button>
          </el-input>
        </el-col>
        <!--搜索按钮-->
      </el-row>


      <!--用户列表 border边框 stripe隔行变色-->
      <el-table :data="orderList" border stripe>
        <el-table-column type="index"></el-table-column><!--索引列-->
        <el-table-column label="收货人" prop="user_name"></el-table-column>
        <el-table-column label="联系电话" prop="user_phone"></el-table-column>
        <el-table-column label="收货地址" prop="order_address"></el-table-column>
        <el-table-column label="订单内容" prop="order_text" width="310px"></el-table-column>
        <el-table-column label="订单金额" prop="order_value" width="120px"></el-table-column>
        <el-table-column label="订单状态" prop="order_state" width="120px"></el-table-column>
        <el-table-column label="操作" width="140px" align="center">
          <template slot-scope="scope">
            <!--删除-->
            <el-tooltip effect="dark" content="删除过期订单" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" size="mini"
                         @click="deleteOrder(scope.row.order_id)"></el-button>
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


  </div>
</template>

<script>
export default {

  created() {
    this.getOrderListOld();
  },

  methods:{
    // 获取所有商品
    async getOrderListOld() {
      const {data: res} = await this.$http.get("getAllOrderOld", {params: this.queryInfo});
      this.orderList = res.data;// 用户列表数据封装
      this.total = res.number;// 总用户数封装
    },

    // 最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getOrderListOld();
    },
    // pageNum的触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getOrderListOld();
    },
    // 根据主键删除订单信息
    async deleteOrder(order_id) {
      const confirmResult = await this.$confirm('此操作将永久删除用户，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      if (confirmResult != 'confirm') {//取消
        return this.$message.info("操作取消");
      }
      const {data: res} = await this.$http.delete("deleteOrder?order_id=" + order_id);
      if (res != "success") {
        return this.$message.error("删除失败");
      }
      this.$message.success("删除成功");
      this.getOrderListOld();
    },




  },

  data(){
    return{
      //订单列表
      orderList:[],

      //查询信息实体
      queryInfo: {
        query: "",//查询信息
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },
      total: 0,//总记录数
    }
  }
}
</script>

<style scoped>

</style>