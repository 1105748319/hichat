import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import mutations from './mutations'
Vue.use(Vuex);

const state={
  user:{},
  essay:{},
  chatUser:{},
  websocket:{},
  userinfo:{}
}

export default new Vuex.Store({
  state,
  actions,
  mutations
})
