import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import login from '@/components/login'
import indexmain from '@/components/indexmain'
import userinfo from '@/components/userinfo'
import chatrecord from '@/components/chatrecord'
import essayinfo from '@/components/essayinfo'
import essaydetail from '@/components/essaydetail'
import employee from '@/components/employee'
import role from '@/components/role'
import system from '@/components/system'
Vue.use(Router)
export const constantRouterMap = [
  {
    path:'/login',
    name:'login',
    component:login
  },
  {
    path:'/',
    redirect:'/login',
  }
]
export default new Router({
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/index',
    name: 'index',
    component: index,
    children:[
      {
        path: '',
        name: 'indexmain',
        component: indexmain,
        meta:{
          title:"首页",
          icon:"el-icon-location"
        },
      },
      {
        path: 'userinfo',
        name: 'userinfo',
        component: userinfo,
        menu:"userinfo",
        meta:{
          title:"用户信息管理",
          icon:"el-icon-info"
        },
      },
      {
        path: 'chatrecord',
        name: 'chatrecord',
        component: chatrecord,
        menu:"chatrecord",
        meta:{
          title:"聊天记录管理",
          icon:"el-icon-message"
        }
      },
      {
        path: 'essayinfo',
        name: 'essayinfo',
        component: essayinfo,
        menu:"essayinfo",
        meta:{
          title:"文章管理",
          icon:"el-icon-document"
        },
      },
      {
        path: 'essaydetail',
        name: 'essaydetail',
        component: essaydetail,
        hidden:true
      },
      {
        path:'system',
        name: 'system',
        component:system,
        redirect:"system/employee",
        meta:{
          title:"系统管理",
          icon:"el-icon-setting"
        },
        children:[
          {
            path: 'employee',
            name: 'employee',
            component: employee,
            menu:"employee",
            meta:{
              title:"人员管理",
              icon:"el-icon-setting"
            },
          },
          {
            path: 'role',
            name: 'role',
            component: role,
            menu:"role",
            meta:{
              title:"角色管理",
              icon:"el-icon-setting"
            },
          }
        ]
      },

    ]
  },

]



