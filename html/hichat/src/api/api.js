import axiosIns from './axios-ins'
import * as url from './url'

/*
 用户登录接口
 */
export function loginUserUrl(json) {
  return axiosIns.post(url.LOGIN_USER_URL, json);
}

/*
  用户注册接口
 */
export function register(json) {
  return axiosIns.post(url.REGISTER_USER_URL,json);
}

/*
  根据token获取用户信息
 */
export function getByToken(){
  return axiosIns.get(url.GET_USER_BY_TOKEN);
}
/*
发送文章
 */
export function sendEssay(json) {
  return axiosIns.post(url.SEND_ESSAY_URL,json);
}

/*
获取文章列表
 */
export function getEssay(json){
  return axiosIns.post(url.GET_ESSAY_URL,json);
}

/*
获取所有用户列表
 */
export function getUserList(json){
  return axiosIns.post(url.GET_CONTACTS_URL,json);
}
/*
获取特定分组的用户列表
 */
export function getUserRoleList(json){
  return axiosIns.post(url.GET_ROLR_URL,json);
}

/*
发表评论
 */
export  function publishComment(json) {
  return axiosIns.post(url.PUBLISH_COMMENT_URL,json);
}

/*
获取评论列表
 */
export function getEssayComment(id,json) {
  return axiosIns.post(url.GET_ESSAY_COMMENT_URL+id,json);
}


/*
上传用户头像
 */
export function uploadHead(imgType,md5,data){
  return axiosIns.post(url.SAVE_USER_UPLOAD_HEAD_URL+imgType+"/"+md5,data);
}

/*
更新用户信息
 */
export function updateUser(json){
  return axiosIns.post(url.UPDATE_USER_URL,json);
}

/*
退出登录
 */
export function logout(){
  return axiosIns.get(url.LOGOUT_USER_URL);
}

/*
保存聊天记录
 */
export function saveMessage(json){
  return axiosIns.post(url.SAVE_MESSAGE_URL,json);
}

/*
获取聊天记录
 */
export function getMessage(fromUserId,toUserId,date,json) {
  return axiosIns.post(url.GET_MESSAGE_URL + fromUserId + "/" + toUserId + "/" + date, json);
}

/*
上传软文图片
 */
export function uploadEssayImg(imgType,md5,data) {
  return axiosIns.post(url.UPLOAD_ESSAY_IMG+imgType+"/"+md5,data);
}

/*
上传消息图片
 */
export function uploadMessageImg(imgType,md5,data) {
  return axiosIns.post(url.UPLOAD_MESSAGE_IMG+imgType+"/"+md5,data);
}


