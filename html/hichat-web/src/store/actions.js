import * as types from './mutations-types'

export function initUser({commit},user){
  return new Promise((resolve) => {
    commit(types.INIT_USER,user);
    resolve();
  });
}
export function initEssay({commit},essay){
  commit(types.INIT_ESSAY,essay);
}
export function initRoute({commit},route){
  commit(types.INIT_ROUTE,route);
}
