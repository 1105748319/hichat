<template>
  <div>
    <el-menu router :default-active="activeIndex"  class="el-menu-demo" mode="horizontal" v-for="item in routes[0].children" v-if="item.name=='system'">
      <el-menu-item :index="routes[0].path+'/'+item.path+'/'+child.path"  v-for="child in item.children" v-if="!item.hidden">
        {{child.meta.title}}
      </el-menu-item>
    </el-menu>
    <div style="margin-top: 15px;">
      <router-view></router-view>
    </div>
  </div>

</template>

<script>
  import {mapState} from 'vuex'
    export default {
      computed: {
        ...mapState({
          routes: state => state.route
        })
      },
      data(){
        return {
          activeIndex:"/index/system/employee"
        }
      },
      methods:{
      }
    }
</script>

<style>
</style>
