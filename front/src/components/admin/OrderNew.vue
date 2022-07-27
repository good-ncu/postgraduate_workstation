<template>
  <div>

    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{path:'/home'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>未发货订单</el-breadcrumb-item>
    </el-breadcrumb>

    <!--订单列表主体-->
    <el-card>


      <el-row :gutter="120">
        <!--搜索区域-->
        <el-col :span="10">
          <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getOrderList">
            <el-button slot="append" icon="el-icon-search" @click="getOrderList"></el-button>
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
            <!--修改发货状态-->
            <el-tooltip effect="dark" content="商品发货" placement="top-start" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini"
                         @click="orderGiveGood(scope.row.order_id,scope.row.order_state)"></el-button>
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
    this.getOrderListNew();
  },

  methods:{
    // 获取所有商品
    async getOrderListNew() {
      const {data: res} = await this.$http.get("getAllOrderNew", {params: this.queryInfo});
      this.orderList = res.data;// 用户列表数据封装
      this.total = res.number;// 总用户数封装
    },

    // 最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getOrderListNew();
    },
    // pageNum的触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getOrderListNew();
    },

    //订单发货
    async orderGiveGood(order_id,order_state){
      if(order_state=="已发货"){
        return this.$message.error("该订单已发货");
      }
      const confirmResult = await this.$confirm('是否确认发货？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);

      const {data: res} = await this.$http.delete("orderGiveGood?order_id=" + order_id);
      if (res != "success") {
        return this.$message.error("发货失败");
      }
      this.$message.success("发货成功");
      this.getOrderListNew();

    }



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