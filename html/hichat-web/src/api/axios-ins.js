import axios from 'axios'
import {BASE_URL} from "./url";

const axiosIns=axios.create({
  baseURL: BASE_URL,
  transformRequest: [function (data) {
    // 对 data 进行任意转换处理
    return data;
  }],
  transformResponse: [function (data) {
    // 对 data 进行任意转换处理
    return data;
  }],

  // `headers` 是即将被发送的自定义请求头
  headers: {'Content-Type': 'application/json'},

})
const axiosForm=axios.create({
  baseURL: BASE_URL,
  transformRequest: [function (data) {
    // 对 data 进行任意转换处理
    var i;
    var dataStr = "";
    for (i in data) {
      dataStr += i + "=" + data[i] + "&";
    }
    return dataStr;
  }],
  transformResponse: [function (data) {
    // 对 data 进行任意转换处理
    return data;
  }],

  // `headers` 是即将被发送的自定义请求头
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},

})


// 添加请求拦截器
axiosIns.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
axiosIns.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  let obj = JSON.parse(response.data);
  if(obj.status==500){
      location.reload();
  }
  return obj;
}, function (error) {

  // 对响应错误做点什么
  return Promise.reject(error);
})

export default axiosIns;
