<template>
  <f7-views id="app">
      <f7-view id="main-view" main>
        <f7-pages>
          <f7-page  toolbar-fixed navbar-fixed>
            <f7-navbar :title="activeTab">
              <f7-nav-right>
                <f7-link v-if="currentTab===1" open-popup="#edit"><i class="f7-icons">compose</i></f7-link>
              </f7-nav-right>
            </f7-navbar>
            <f7-toolbar tabbar labels>
              <f7-link tab-link="#home" active @click="changeActiveTab('主页',1)"><i class="f7-icons">home</i><span
                class="tabbar-label">主页</span></f7-link>
              <f7-link tab-link="#contacts" @click="changeActiveTab('通讯录',2)"><i class="f7-icons">chats_fill</i><span
                class="tabbar-label">通讯录</span></f7-link>
              <f7-link tab-link="#my" @click="changeActiveTab('我的',3)"><i class="f7-icons">person</i><span
                class="tabbar-label">我的</span></f7-link>
            </f7-toolbar>

            <f7-tabs>
              <f7-tab id="home" active>
                <home ref="homeChild"/>
              </f7-tab>
              <f7-tab id="contacts">
                <contact ref="contactChild"/>
              </f7-tab>
              <f7-tab id="my">
                <my/>
              </f7-tab>
            </f7-tabs>
          </f7-page>
        </f7-pages>
      </f7-view>

    <login-pop @init-app="initApp"/>
    <register-pop/>
    <edit ref="editChild" @save-content="saveContent"/>
    <userinfo/>
    </f7-views>
</template>

<script>
  import home from './components/home.vue'
  import contact from './components/contact.vue'
  import my from './components/my.vue'
  import {isEmpty} from './util/utils'
  import {getByToken} from './api/api'
  import Cookies from 'cookies-js'
  import axiosIns from './api/axios-ins'
  import {sendEssay} from './api/api'
  import edit from './components/edit.vue'
  import socket from './websocket/socket'
  import {mapState} from 'vuex'
  export default {
    data() {
      return {
        activeTab: "主页",
        currentTab: 1
      }
    },
    computed: {
      ...mapState({
        user: state => state.user,
        websocket:state=>state.websocket
      })
    },
    mounted() {
      this.$nextTick(() => {
        this.$f7.showIndicator();
        if (isEmpty(Cookies.get("token"))) {
          this.$f7.hideIndicator();
          this.$f7.popup("#login");
        } else {
          axiosIns.defaults.headers.common['Authorization'] = Cookies.get("token");
          getByToken().then(data => {
            this.$f7.hideIndicator();
            if (data.status == 200) {
              let user = data.data;
              this.$store.dispatch('initUser', user);
              this.initApp();
            } else {
              this.$f7.alert(data.msg);
              this.$f7.popup("#login");
            }
          })
        }
      })
    },
    methods: {
      saveContent(essay){
        this.$f7.showIndicator();
        sendEssay(JSON.stringify(essay)).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {
            this.$refs.editChild.clearContent();
            this.$f7.closeModal('#edit');
            this.$refs.homeChild.listEssay(1,0);
          } else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      },
      initApp() {
        this.$refs.homeChild.listEssay(1,0);
        this.$refs.contactChild.getContacts();
        let soc=new socket(this.$f7,Cookies.get("token"));
        let webSocket = soc.getWebSocket();
        webSocket.onopen=function(){
          console.log("webscoket连接");
        }
        webSocket.onerror=function(){
          this.$f7.alert("连接通讯服务器失败，请重新登录或者联系管理员","hichat");
        }
        this.$store.dispatch('initWebsocket',webSocket);

      },
      changeActiveTab(tab, current) {
        this.activeTab = tab;
        this.currentTab = current;
        if(current==2){
          this.$refs.contactChild.getContacts();
        }
      }
    },
    components: {
      home,
      contact,
      my,
      edit
    }
  }
</script>

<style>
  .f7-icons {
    font-size: 20px;
    margin-top: 1px
  }
</style>
