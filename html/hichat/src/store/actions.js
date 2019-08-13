import * as types from './mutations-types'

export function initUser({commit},user){
  commit(types.INIT_USER,user);
}
export function initEssay({commit},essay){
  commit(types.INIT_ESSAY,essay);
}
export function initChatUser({commit},chatUser){
  commit(types.INIT_CHAT_USER,chatUser);
}
export function initWebsocket({commit},websocket){
  commit(types.INIT_WEBSOCKET,websocket);
}
export function initUserinfo({commit},userinfo){
  commit(types.INIT_USERINFO,userinfo);
}
