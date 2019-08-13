import * as types from './mutations-types'
import {generateRoutes} from '../router/makeroute'
import {isEmpty} from "../util/utils";
import Vue from 'vue'
export default {
  [types.INIT_USER](state,user){
    sessionStorage.setItem("user",JSON.stringify(user));
    //生成路由
    if(!isEmpty(user)){
      generateRoutes(user.userPermission,state);
    }
    Vue.set(state,'user',user);
  },
  [types.INIT_ESSAY](state,essay){
    sessionStorage.setItem("essay",JSON.stringify(essay));
    Vue.set(state,'essay',essay);
  },
  [types.INIT_ROUTE](state,route){
    sessionStorage.setItem("route",JSON.stringify(route));
    Vue.set(state,'route',route);
  },
}
