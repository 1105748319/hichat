import * as types from './mutations-types'
import Vue from 'vue'
export default {
  [types.INIT_USER](state,user){
    Vue.set(state,'user',user);
  },
  [types.INIT_ESSAY](state,essay){
    Vue.set(state,'essay',essay);
  },
  [types.INIT_CHAT_USER](state,chatUser){
    Vue.set(state,'chatUser',chatUser);
  },
  [types.INIT_WEBSOCKET](state,websocket){
    Vue.set(state,'websocket',websocket);
  },
  [types.INIT_USERINFO](state,userinfo){
    Vue.set(state,'userinfo',userinfo);
  }
}
