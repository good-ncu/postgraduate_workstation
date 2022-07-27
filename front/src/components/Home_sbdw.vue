<template>
  <!--引入container布局-->
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <div>
        <img src="../assets/img/ncu_log.jpg" alt/>
        <span style="font-size: 24px">江西省研究生工作站报备平台</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏 若内容多可以使用 unique-opened 属性控制仅展开一个侧边栏-->
      <el-aside :width="isCollapase?'64px':'230px'">
        <div class="toggle-button" @click="toggleCollapase" style="font-size: large"><a class="iconfont icon-icon-shouqi" style="font-size: 20px"></a></div>
        <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#409eff"
            unique-opened :collapse="isCollapase"
            :collapse-transition="false" :router="true"
            :default-active="activePath" style="font-size: large">
          <!--一级菜单item.id+''转数字为字符 数据绑定一定要加上:-->
          <!--<el-submenu :index="item.mainmenu_id+''" v-for="item in menuList" :key="item.mainmenu_id">-->
          <el-submenu index="100" key="100" >
            <template slot="title">
              <i :class="iconsObject[100]" style="font-size: 20px"></i>&nbsp;
              <span style="font-size: 20px">工作站申报</span>
            </template>
            <!--二级菜单-->
            <!--<el-menu-item :index="it.submenu_path" v-for="it in item.mainmenu_sList" :key="it.submenu_id" @click="saveNavState(it.submenu_path)">-->
            <el-menu-item index="/workApply" key="101" @click="saveNavState('/workApply')">
              <template slot="title">
                <i :class="iconsObject[101]" style="font-size: 20px"></i>&nbsp;
                <span style="font-size: 20px">工作站报备</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="200" key="200">
            <template slot="title">
              <i :class="iconsObject[200]" style="font-size: 20px"></i>&nbsp;
              <span style="font-size: 20px">用户设置</span>
            </template>
            <!--二级菜单-->
            <!--<el-menu-item :index="it.submenu_path" v-for="it in item.mainmenu_sList" :key="it.submenu_id" @click="saveNavState(it.submenu_path)">-->
            <el-menu-item index="/sbdwInfo" key="201" @click="saveNavState('/sbdwInfo')">
              <template slot="title">
                <i :class="iconsObject[201]" style="font-size: 20px"></i>&nbsp;
                <span style="font-size: 20px">用户信息设置</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/sbdwReview" key="202" @click="saveNavState('/sbdwPasswd')">
              <template slot="title">
                <i :class="iconsObject[202]" style="font-size: 20px"></i>&nbsp;
                <span style="font-size: 20px">密码修改</span>
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
      isCollapase:false,//伸缩属性
      iconsObject:{
        '100':'iconfont icon-icon-wenjian2',
        '200':'iconfont icon-icon-zhanghao',


        '101':'iconfont icon-icon-shujuanquan',

        '201':'iconfont icon-yuanshuju-xiugairen',
        '202':'iconfont icon-icon-bianji2',
      },
      activePath:'',// 默认路径
    }
  },
  created() {
    this.activePath=window.sessionStorage.getItem('activePath');  //取出session里的path 动态修改 activePath
  },
  methods: {
    logout() {
      localStorage.clear();
      window.sessionStorage.clear();//清除session
      this.$router.push("/login");//回到首页
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
  font-size: 20px;
  line-height: 32px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;// 显示小手
}
</style>
