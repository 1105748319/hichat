<template>
  <f7-popup id="login">
    <div class="view">
      <div class="page">
        <div class="page-content login-screen-content">
          <div class="login-screen-title">医患平台</div>
          <form>
            <div class="list-block">
              <ul>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">账号</div>
                    <div class="item-input">
                      <input type="text"  v-model="account" placeholder="账号">
                    </div>
                  </div>
                </li>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">密码</div>
                    <div class="item-input">
                      <input type="password" v-model="password" placeholder="密码">
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="list-block">
              <div class="list-block-label">
                <p>
                  <f7-link @click="login">登陆</f7-link>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <f7-link open-popup="#register">注册</f7-link>
                </p>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </f7-popup>
</template>

<script>
  import {loginUserUrl} from "../api/api"
  import axiosIns from '../api/axios-ins'
  import Cookies from 'cookies-js'
  import {getByToken} from '../api/api'
  export default {
    data() {
      return {
        account: "",
        password: ""
      }
    },
    methods: {
      login() {
        this.$f7.showIndicator();
        loginUserUrl(JSON.stringify({
            account: this.account,
            password: this.password
          }
        )).then((data => {
          if(data.status==200){
            let token=data.data;
            Cookies.set("token",token,{ expires: 7200 });
            axiosIns.defaults.headers.common['Authorization'] = token;
            getByToken().then(data2=>{
              this.$f7.hideIndicator();
              if(data2.status==200){
                this.account=""
                this.password="";
                let user=data2.data;
                this.$store.dispatch('initUser',user);
                this.$emit("init-app");
                this.$f7.closeModal('#login');
              }else{
                this.$f7.alert(data2.msg,"hichat");
              }
            })

          }else{
            this.$f7.hideIndicator();
            this.$f7.alert(data.msg,"hichat");
          }
        }))
      }
    }
  }

</script>

<style scoped>
</style>
