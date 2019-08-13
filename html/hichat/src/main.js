import Vue from 'vue'
import App from './App'
import 'framework7'
import Framework7Vue from 'framework7-vue'
import route from './router/route'
import 'framework7/dist/css/framework7.ios.min.css'
import 'framework7/dist/css/framework7.ios.colors.min.css'
import 'framework7-icons/css/framework7-icons.css'
import login from './components/login.vue'
import register from './components/register.vue'
import store from './store/store'
import userinfo from './components/userinfo.vue'
import VueLazyload from 'vue-lazyload'
Vue.use(Framework7Vue);
Vue.component("login-pop",login);
Vue.component("register-pop",register);
Vue.component("userinfo",userinfo);

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: require("./assets/errorimg.png"),
  loading: require("./assets/default.png"),
  attempt: 1,
  listenEvents: [ 'scroll' ]
})
new Vue({
  el: '#app',
  template: '<App/>',
  components: { App },
  store,
  framework7:{
    root: '#app',
    routes: route
  }
})


