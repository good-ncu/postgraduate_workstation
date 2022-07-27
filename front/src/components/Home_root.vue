<template>
  <!--引入container布局-->
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <div>
        <img src="../assets/img/ncu_log.jpg" alt/>
        <span>江西省研究生工作站报备平台rootssss</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏 若内容多可以使用 unique-opened 属性控制仅展开一个侧边栏-->
      <el-aside :width="isCollapase?'64px':'200px'">
        <div class="toggle-button" @click="toggleCollapase"><a class="iconfont icon-daohanglan"></a></div>
        <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#409eff"
            unique-opened :collapse="isCollapase"
            :collapse-transition="false" :router="true"
            :default-active="activePath">
          <!--一级菜单item.id+''转数字为字符 数据绑定一定要加上:-->
          <el-submenu :index="item.mainmenu_id+''" v-for="item in menuList" :key="item.mainmenu_id">
            <template slot="title">
              <i :class="iconsObject[item.mainmenu_id]"></i>&nbsp;
              <span>{{item.mainmenu_title}}</span>
            </template>
            <!--二级菜单-->
            <el-menu-item :index="it.submenu_path" v-for="it in item.mainmenu_sList" :key="it.submenu_id" @click="saveNavState(it.submenu_path)">
              <template slot="title">
                <i :class="iconsObject[it.submenu_id]"></i>&nbsp;
                <span>{{ it.submenu_title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--主体内容-->
      <el-main>
        <router-view></router-view><!--显示Main router-view-->
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
export default {
  //类比onLoad事件
  data() {
    return {
      //菜单列表
      menuList: [],
      isCollapase:true,//伸缩属性
      iconsObject:{
        '100':'iconfont icon-27',
        '200':'iconfont icon-shangpin1',
        '300':'iconfont icon-order',
        '400':'iconfont icon-guanggao',
        '500':'iconfont icon-shezhi1',
        '600':'iconfont icon-xitong',

        '101':'iconfont icon-yonghu',
        '102':'iconfont icon--quanxian',
        '201':'iconfont icon-iconfontshangpin2',
        '202':'iconfont icon-fenlei',
        '301':'iconfont icon-layer',
        '302':'iconfont icon-yifahuo1',
        '303':'iconfont icon-yifahuo',
        '401':'iconfont icon-guanggao1',
        '402':'iconfont icon-remen',
        '501':'iconfont icon-shezhi',
        '601':'iconfont icon-tongji',
      },
    }
  },
  created() {
    // 查询menuList
    this.getMenuList();
    this.activePath=window.sessionStorage.getItem('activePath');  //取出session里的path 动态修改 activePath
  },
  methods: {
    logout() {
      window.sessionStorage.clear();//清除session
      this.$router.push("/login");//回到首页
    },
    // 获取导航菜单方法
    async getMenuList() {
      const {data: res} = await this.$http.get("getAllMenus");
      console.log(res);
      if (res.flag != 200) return this.$message.error("获取列表数据失败");
      this.menuList = res.menus;//数据回填
    },
    // 控制菜单伸缩
    toggleCollapase(){
      this.isCollapase=!this.isCollapase;
    },
    // 保存路径 锁定当前路由 刷新不改变
    saveNavState(activePath){
      window.sessionStorage.setItem('activePath',activePath);// 存放在session中
      this.activePath=activePath;
    },
  }
};
</script>


<style lang="less" scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between; // 左右贴边
  align-items: center;
  padding-left: 0%; // 左边界
  color: #fff;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #333744;
  .el-menu{
    border-right: none;
  }
}

.el-main {
  background-color: #eaedf1;
}

img {
  width: 55px;
  height: 55px;
}
//伸缩按钮样式
.toggle-button{
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;// 显示小手
}
</style>
