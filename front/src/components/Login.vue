<template>
  <div class="login_container">
    <!--登录块-->
    <div class="login_box">
      <!--      &lt;!&ndash;头像logo&ndash;&gt;-->
      <!--      <div class="avatar_box">-->
      <!--        <img src="../assets/img/ncu_log.jpg" alt/>-->
      <!--      </div>-->
      <div class="login_title"><span>江西省研究生工作站报备平台</span></div>
      <!--表单区域-->
      <el-form ref="loginFormRef" :rules="loginRules" :model="loginForm" class="login_form" label-width="0">
        <!--用户名-->
        <el-form-item prop="user_name" label="用户名" label-width="70px">
          <el-input v-model="loginForm.user_name" prefix-icon="iconfont icon-denglu" style="font-size: 20px"></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item prop="user_passwd" label="密码" label-width="70px" class="formLabel">
          <el-input :type="reflag?'password':'text'" v-model="loginForm.user_passwd" prefix-icon="iconfont icon-mima"
                    style="font-size: 20px"><i slot="suffix"
                                               :class="[reflag?'iconfont icon-yincang':'iconfont icon-xianshi']"
                                               style="margin-top:8px; font-size: 20px;"
                                               autocomplete="auto"
                                               @click="reflag=!reflag"/></el-input>
        </el-form-item>
        <el-form-item label="验证码" label-width="70px" class="formLabel">
          <el-col :span="15">
            <el-input
                v-model="loginForm.verifyCode"
                prefix-icon="el-icon-message"
                placeholder="请输入验证码"
                style="font-size: 18px"
            ></el-input>
          </el-col>
          <el-col :span="8" style="margin-left: 10px">
            <img :src="imgUrl" @click="resetImg">
          </el-col>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="btns">
          <el-button type="primary" @click="login" style="font-size: 20px">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>

function getBroswerAndVersion() {//该函数可以判断各种浏览器和版本，最厉害的版本
  var os = navigator.platform;
  var userAgent = navigator.userAgent;
  var info = "";
  var tempArray = "";
  //判断浏览器版本
  var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
  var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
  var isEdge = userAgent.toLowerCase().indexOf("edge") > -1 && !isIE; //判断是否IE的Edge浏览器
  var isIE11 = (userAgent.toLowerCase().indexOf("trident") > -1 && userAgent.indexOf("rv") > -1);

  if (/[Ff]irefox(\/\d+\.\d+)/.test(userAgent)) {
    tempArray = /([Ff]irefox)\/(\d+\.\d+)/.exec(userAgent);
    info += tempArray[1] + tempArray[2];
  } else if (isIE) {

    var version = "";
    var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
    reIE.test(userAgent);
    var fIEVersion = parseFloat(RegExp["$1"]);
    if (fIEVersion == 7) {
      version = "IE7";
    } else if (fIEVersion == 8) {
      version = "IE8";
    } else if (fIEVersion == 9) {
      version = "IE9";
    } else if (fIEVersion == 10) {
      version = "IE10";
    } else {
      version = "0"
    }

    info += version;

  } else if (isEdge) {
    info += "Edge";
  } else if (isIE11) {
    info += "IE11";
  } else if (/[Cc]hrome\/\d+/.test(userAgent)) {
    tempArray = /([Cc]hrome)\/(\d+)/.exec(userAgent);
    info += tempArray[1] + tempArray[2];
  } else if (/[Vv]ersion\/\d+\.\d+\.\d+(\.\d)* *[Ss]afari/.test(userAgent)) {
    tempArray = /[Vv]ersion\/(\d+\.\d+\.\d+)(\.\d)* *([Ss]afari)/.exec(userAgent);
    info += tempArray[3] + tempArray[1];
  } else if (/[Oo]pera.+[Vv]ersion\/\d+\.\d+/.test(userAgent)) {
    tempArray = /([Oo]pera).+[Vv]ersion\/(\d+)\.\d+/.exec(userAgent);
    info += tempArray[1] + tempArray[2];
  } else {
    info += "unknown";
  }
  return info;
}

var bro = getBroswerAndVersion();//上面第一步封装的函数，调用会得到一个返回值
if (bro == "IE5" || bro == "IE6" || bro == "IE7" || bro == "IE8") {
  alert("浏览器版本过低，请下载谷歌浏览器~");//此处应该显示一个漂亮的图片，让用户觉得再不升级就是out了
  window.location.href = "https://www.google.cn/intl/zh-CN/chrome/";
}


export default {
  data() {
    return {
      userToken: '',

      //表单数据
      imgUrl: "http://localhost:8081/verifyCode?time=" + new Date(),

      //隐藏登录密码
      reflag: true,
      //表单数据对象
      loginForm: {
        // user_name: "good",
        // user_passwd: "WenX@good114",
        // user_name: "st",
        // user_passwd: "root",
        // user_name: "wenx",
        // user_passwd: "root"
        verifyCode: ''
      },
      //验证对象
      loginRules: {
        //校验用户名
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},//必填项验证
          {min: 1, max: 20, message: '长度在20个字符以内', trigger: 'blur'}//长度验证
        ],
        //校验密码
        user_passwd: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},//必填项验证
          {min: 1, max: 20, message: '长度在20个字符以内', trigger: 'blur'}//长度验证
        ],
        user_Verify: [
          {required: true, message: '请输入验证码', trigger: 'blur'},//必填项验证
        ]
      },
    };
  },
  methods: {
    // ...mapMutations(['SET_USER_LOGIN_INFO', 'CHANGE_LOGIN']),
    // ...mapActions(['login']),

    //重置表单内容
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },

    // 重新获取验证码
    resetImg() {
      this.imgUrl = "http://localhost:8081/verifyCode?time=" + new Date();
    },


    //处理登录事件
    login() {
      this.$refs.loginFormRef.validate(async valid => {
            //1 验证失败则结束
            if (!valid) return;
            const {data: res} = await this.$http.post("userLogin", this.loginForm);
            if (res.status == "300") {
              this.$message.error("验证码错误");
              return;
            }
            //2 是否进入后台
            if (res.flag == "ok" && res.token != null) {
              // 存储token
              console.log(res.token);
              this.userToken = res.token;
              localStorage.setItem("Authorization", this.userToken);
              // this.CHANGE_LOGIN({Authorization: this.userToken});
              //3 登录成功，跳转到对应权限的首页
              this.$message.success("登录成功");
              //4 存储user信息
              window.sessionStorage.setItem("user", res.user);
              //5 通过本地存储，传递当前用户信息到下一个页面
              localStorage.setItem("univ_name", res.user.univ_name);
              // JSON.stringify(
              localStorage.setItem("univ_code", res.user.univ_code);
              localStorage.setItem("user_id", res.user.user_id);
              localStorage.setItem("user_sbdw", res.user.user_sbdw);
              console.log(res.role);
              localStorage.setItem("user_role", res.role);
              //6 按角色类型进入首页
              if (res.role == "0") {
                console.log("0");
                await this.$router.push({path: "/root_home"});
              } else if (res.role == "1") {
                console.log("1");
                await this.$router.push({path: "/st_home"});
              } else if (res.role == "2") {
                console.log("2");
                await this.$router.push({path: "/gx_home"});
              } else {
                console.log("3");
                await this.$router.push({path: "/sbdw_home"});
              }
            } else {
              this.$message.error("用户名或密码错误");
            }
          }
      )
    }
  },

}
</script>


<style lang="less" scoped>


.login_container {
  background-color: #add2ea;
  height: 100%;
}

.login_title {
  bottom: 20%;
  width: 100%;
  padding: 30px 112px;
  font-size: 25px;
  box-sizing: border-box;
}

.login_box {
  width: 550px;
  height: 350px;
  background-color: #fff;
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 45%;
  transform: translate(-50%, -50%);

  .avatar_box {
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 5px;
    box-shadow: 0 0 2px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #202d56;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.login_form {
  .el-form-item__label {
    font-size: 20px;
  }

  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 80px;
  box-sizing: border-box;
}

.formStar {
  font-style: normal;
  font-size: 22px;
}


</style>
