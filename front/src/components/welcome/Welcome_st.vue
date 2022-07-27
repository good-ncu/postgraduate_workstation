<template>
  <div id="login">
    <div id="bgd">
      <canvas
          id='myCanvas'
          :width='width'
          :height='height'
      >
      </canvas>
    </div>
    <div id="loginBox">
      <h4 style="position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: 40px 0;text-align: center;font-size: 28px">您好！江西省教育厅的领导</h4>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    // this.self_info.univ_name = localStorage.getItem("univ_name");
    // this.self_info.univ_code = localStorage.getItem("univ_code");
  },
  data() {
    return {
      // 本账号信息实体
      // self_info: {
      //   univ_name: "",
      //   univ_code: "",
      // },

    };
  },
  methods: {},

};
</script>

<style lang='less' scoped>
#login {
  width: 100vw;
  padding: 0;
  margin: 0;
  height: 100vh;
  font-size: 16px;
  background-repeat: no-repeat;
  background-position: left top;
  background-color: #4882f1;
  color: #fff;
  font-family: "Source Sans Pro";
  background-size: 30%;
  //background-image: url("/src/assets/img/bg.png");
  //position: relative;

  #loginBox {
    width: 540px;
    height: 140px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;

    box-shadow: -15px 15px 15px rgba(6, 17, 47, 0.7);
    opacity: 1;
    background: linear-gradient(230deg,
    rgba(53, 57, 74, 0) 0%,
    rgb(0, 0, 0) 100%);

    /deep/ .inps input {
      border: none;
      color: #fff;
      background-color: transparent;
      font-size: 24px;
    }

    .submitBtn {
      background-color: transparent;
      color: #39f;
      width: 200px;
    }

    .iconfont {
      color: #fff;
    }
  }
}
</style>
