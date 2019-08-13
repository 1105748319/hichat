<template>
  <div class="login_box">
    <div class="login_l_img"><img src="../assets/login-img.png"/></div>
    <div class="login">
      <div class="login_logo"><a href="#"><img src="../assets/login_logo.png"/></a></div>
      <div class="login_name">
        <p>hichat后台管理系统</p>
      </div>
      <form>
        <input v-model="user.account" type="text" placeholder="用户名"/>

        <input v-model="user.password" type="password" placeholder="密码"/>

        <span>
          <input v-model="user.loginCode" placeholder="验证码" style="width: 50%;" type="text"/>
          <img :src="baseUrl+identifyCodeUrl" id="kaptchaImage" @click="changeImg" style="margin-left: 20px;width: 100px;height: 30px;"/>
        </span>

        <input value="登录" style="width:100%;" type="button" v-loading.fullscreen.lock="loading" @click="loginSubmit">


      </form>
    </div>
  </div>
</template>

<script>
  import {BASE_URL,IDENTI_CODE} from '../api/url'
  import {login,isOnline} from '../api/api'
  import {isEmpty} from '../util/utils'
  import $ from 'jquery'
  export default {
    data() {
      return {
        baseUrl:BASE_URL,
        identifyCodeUrl:IDENTI_CODE,
        loading:false,
        user:{
          account:"",
          password:"",
          loginCode:""
        }
      }
    },
    mounted(){
    },
    methods: {
      changeImg(){
        $('#kaptchaImage').hide().attr('src', this.baseUrl+this.identifyCodeUrl).fadeIn();
      },
      loginSubmit() {
        if(isEmpty(this.user.account)){
          this.$message.error("用户名为空!");
          return ;
        }
        if(isEmpty(this.user.password)){
          this.$message.error("密码为空!");
          return ;
        }
        if(isEmpty(this.user.loginCode)){
          this.$message.error("验证码为空!");
          return ;
        }
        let that = this;
        this.loading=true;
        login(this.user).then((res)=>{
          that.loading=false;
          if(res.status==200){
            that.$store.dispatch('initUser',res.data);
            that.$router.push("/index");
          }else{
            that.$message.error(res.msg);
          }
        })
      }
    }
  }
</script>

<style scoped>

  * {
    font: 13px/1.5 '微软雅黑';
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -box-sizing: border-box;
    padding: 0;
    margin: 0;
    list-style: none;
    box-sizing: border-box;
  }

  a {
    color: #27A9E3;
    text-decoration: none;
    cursor: pointer;
  }

  img {
    border: none;
  }

  .login_box {
    width: 1100px;
    background-color: #93defe;
    background-size: cover;
    margin: 120px auto 0;
  }

  .login_box .login_l_img {
    float: left;
    width: 432px;
    height: 440px;
    margin-left: 50px;
  }

  .login_box .login_l_img img {
    width: 500px;
    height: 440px;
  }

  .login {
    height: 360px;
    width: 400px;
    padding: 50px;
    background-color: #ffffff;
    border-radius: 6px;
    box-sizing: border-box;
    float: right;
    margin-right: 50px;
    position: relative;
    margin-top: 50px;
  }

  .login_logo {
    width: 120px;
    height: 120px;
    border: 5px solid #93defe;
    border-radius: 100px;
    background: #fff;
    text-align: center;
    line-height: 110px;
    position: absolute;
    top: -60px;
    right: 140px;
  }

  .login_name {
    width: 100%;
    float: left;
    text-align: center;
    margin-top: 20px;
  }

  .login_name p {
    width: 100%;
    text-align: center;
    font-size: 18px;
    color: #444;
    padding: 10px 0 20px;
  }

  .login_logo img {
    width: 60px;
    height: 60px;
    display: inline-block;
    vertical-align: middle;
  }

  input[type=text], input[type=file], input[type=password], input[type=email], select {
    border: 1px solid #DCDEE0;
    vertical-align: middle;
    border-radius: 3px;
    height: 50px;
    padding: 0px 16px;
    font-size: 14px;
    color: #555555;
    outline: none;
    width: 100%;
    margin-bottom: 15px;
    line-height: 50px;
    color: #888;
  }

  input[type=text]:focus, input[type=file]:focus, input[type=password]:focus, input[type=email]:focus, select:focus {
    border: 1px solid #27A9E3;
  }

  input[type=submit], input[type=button] {
    display: inline-block;
    vertical-align: middle;
    padding: 12px 24px;
    margin: 0px;
    font-size: 16px;
    line-height: 24px;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    color: #ffffff;
    background-color: #27A9E3;
    border-radius: 3px;
    border: none;
    -webkit-appearance: none;
    outline: none;
    width: 100%;
  }

  #password_text {
    border: 1px solid #DCDEE0;
    vertical-align: middle;
    border-radius: 3px;
    height: 50px;
    padding: 0px 16px;
    font-size: 14px;
    color: #888;
    outline: none;
    width: 100%;
    margin-bottom: 15px;
    display: block;
    line-height: 50px;
  }

</style>
