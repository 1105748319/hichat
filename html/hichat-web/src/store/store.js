import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import mutations from './mutations'
Vue.use(Vuex);

const state={
  user:null,
  essay:null,
  route:null
}

export default new Vuex.Store({
  state,
  actions,
  mutations
})
