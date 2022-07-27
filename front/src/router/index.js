import Vue from 'vue'
import VueRouter from 'vue-router'


//引入各组件
import Login from "../components/Login.vue";
import Home_root from "../components/Home_root";
import Home_st from "../components/Home_st";
import Home_gx from "../components/Home_gx";
import Home_sbdw from "../components/Home_sbdw";


// gx 高校用户子组件
// 欢迎页
import Welcome_gx from "../components/welcome/Welcome_gx";
import UserManager from "../components/gx/UserManager";
import GxSH from "../components/gx/GxSH";
import GxPasswd from "../components/gx/GxPasswd";
import GxInfo from "../components/gx/GxInfo";
import ApplySearch from "../components/gx/ApplySearch";

// sbdw 申报单位用户子组件
import WorkApply from "../components/sbdw/WorkApply";
import SbdwInfo from "../components/sbdw/SbdwInfo";
import SbdwPasswd from "../components/sbdw/SbdwPasswd";
import Welcome_sbdw from "../components/welcome/Welcome_sbdw";

// st 省厅用户子组件
import Welcome_st from "../components/welcome/Welcome_st";
import AllApply from "../components/st/AllApply";
import DwManager from "../components/st/DwManager";
import GxManager from "../components/st/GxManager";
import StPasswd from "../components/st/StPasswd";

// root 超级管理员用户子组件

//admin子组件
import UserList from "../components/admin/UserList.vue";
import GoodList from "../components/admin/GoodList.vue";
import UserRole from "../components/admin/UserRole.vue";
import GoodClass from "../components/admin/GoodClass.vue";
import Banner from "../components/admin/Banner.vue";
import OrderList from "../components/admin/OrderList.vue";
import OrderNew from "../components/admin/OrderNew.vue";
import OrderOld from "../components/admin/OrderOld.vue";
import SystemCfg from "../components/admin/SystemCfg.vue";
import HotGood from "../components/admin/HotGood.vue";
import {compile} from "vue-template-compiler";


Vue.use(VueRouter)

const routes = [
    {
        path: "/",
        redirect: "/login"
    },
    {
        path: "/login",
        component: Login
    },
    {
        path: "/root_home",
        component: Home_root,
        children: [
            {
                path: "/userList",
                component: UserList
            },
            {
                path: "/userRole",
                component: UserRole
            },
            {
                path: "/goodList",
                component: GoodList
            },
            {
                path: "/goodClass",
                component: GoodClass
            },
            {
                path: "/banner",
                component: Banner
            },
            {
                path: "/orderList",
                component: OrderList
            },
            {
                path: "/orderNew",
                component: OrderNew
            },
            {
                path: "/orderOld",
                component: OrderOld
            },
            {
                path: "/system",
                component: SystemCfg
            },
            {
                path: "/hotGood",
                component: HotGood
            }
        ]
    },
    {
        path: "/st_home",
        component: Home_st,
        redirect: "/welcome_st",
        children: [
            {
                path: "/welcome_st",
                component: Welcome_st
            },
            {
                path: "/gxManager",
                component: GxManager
            },
            {
                path: "/dwManager",
                component: DwManager
            },
            {
                path: "/allApply",
                component: AllApply
            },
            {
                path: "/stPasswd",
                component: StPasswd
            }
        ]
    },
    {
        path: "/gx_home",
        component: Home_gx,
        redirect: "/welcome_gx",
        children: [
            {
                path: "/welcome_gx",
                component: Welcome_gx
            },
            {
                path: "/gxSH",
                component: GxSH
            },
            {
                path: "/userManager",
                component: UserManager
            },
            {
                path: "/gxPasswd",
                component: GxPasswd
            },
            {
                path: "/gxInfo",
                component: GxInfo
            },
            {
                path: "/applySearch",
                component: ApplySearch
            }
        ]
    },
    {
        path: "/sbdw_home",
        component: Home_sbdw,
        redirect: "/welcome_sbdw",
        children: [
            {
                path: "/workApply",
                component: WorkApply
            },
            {
                path: "/welcome_sbdw",
                component: Welcome_sbdw
            },
            {
                path: "/sbdwReview",
                component: SbdwPasswd
            },
            {
                path: "/sbdwInfo",
                component: SbdwInfo
            }
        ]
    },
]

const router = new VueRouter({
    routes
})

//出现问题时使用
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}


//挂载路由导航守卫
router.beforeEach((to, from, next) => {
    // 取出用户权限
    let user_role = localStorage.getItem("user_role");
    console.log("role:"+user_role);

    console.log(111);
    //to 将要访问
    //from 从哪访问
    //next 接着干的事 next(url)重定向到url上 next()继续访问 to路径
    if (to.path == '/login') return next();

    let token = localStorage.getItem('Authorization');
    console.log("emmm:"+token);
    if (token === null || token === '') {
        next('/login');
    } else {
        let n = to.path;
        if (user_role === '2'){
            if(n === '/welcome_gx'||n==='/gxSH'||n==='/userManager'||n==='/gxPasswd'||n==='/applySearch'){
                next();
            }
        }else if(user_role === '3'){
            if(n === '/welcome_sbdw' || n==='/workApply' || n==='/sbdwInfo' || n==='/sbdwReview'){
                next();
            }
        }else if(user_role === '1'){
            next();
        }
    }
})


export default router
