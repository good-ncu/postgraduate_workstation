import Vue from 'vue'
import App from './App.vue'
import router from './router'
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

//引入element-ui
import './plugins/element.js'
//引入iconfont
import './assets/font/iconfont.css'
//添加全局样式
import './assets/css/global.css'


//导入axios
import axios from 'axios'
//挂载axios
Vue.prototype.$http = axios
//设置访问根路径
axios.defaults.baseURL = "http://localhost:8081"
axios.defaults.withCredentials = true

// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
    config => {
        if (localStorage.getItem('Authorization')) {
            config.headers.Authorization = localStorage.getItem('Authorization');
        }
        console.log(config)
        return config;
    },
    error => {
        return Promise.reject(error);
    });


Vue.config.productionTip = false


new Vue({
    router,
    render: h => h(App)
}).$mount('#app')


import onceClick from '../src/assets/js/onceClick'
Vue.use(onceClick)
