<template>
  <div id="app">
    <el-container>
      <el-header id="header-css">
        <span>hichat后台管理系统</span>
        <span style="float: right;cursor: pointer" @click="logout">退出</span>
      </el-header>
      <el-container :style="containercss">
        <el-aside width="200px">
          <el-menu
            router
            class="el-menu-vertical-demo"
          >
            <el-menu-item v-for="item in routes[0].children" v-if="!item.hidden" :index="routes[0].path+'/'+item.path">
                <i :class="item.meta.icon"></i>
                <span slot="title">{{item.meta.title}}</span>
            </el-menu-item>


          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import {logout, isOnline} from '../api/api'
  import {isEmpty} from '../util/utils'
  import {mapState} from 'vuex'

  export default {
    data() {
      return {
        containercss: "",
        loading: false
      }
    },
    computed: {
      ...mapState({
        routes: state => state.route
      })
    },
    methods: {
      logout() {
        logout().then((res) => {
        })
      }
    },
    mounted() {
      console.log(this.routes);
      let clientHeight = window.innerHeight;
      let headerHeight = document.getElementById("header-css").offsetHeight;
      this.containercss = "height: " + (clientHeight - headerHeight - 20) + "px; border: 1px solid #eee";
    }
  }
</script>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;

  }

  .el-aside {
    color: #333;
    line-height: 200px;
  }
</style>
