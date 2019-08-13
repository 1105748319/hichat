<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户信息管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-input v-model="querys.nickname" placeholder="请输入用户昵称" style="width:200px;"></el-input>
        <el-date-picker
          v-model="querys.dateTime"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
        <el-button icon="el-icon-search" @click="queryUser" circle></el-button>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-button type="warning" v-if="hasPerm('userinfo:send')">发送消息</el-button>
        <el-button type="danger" v-if="hasPerm('userinfo:delete')">删除</el-button>
      </el-row>

      <el-table
        :data="tableData"
        stripe
        v-loading="loading"
        style="width: 100%;margin-top: 15px">
        <el-table-column
          prop="account"
          label="账号"
        >
        </el-table-column>
        <el-table-column
          prop="nickname"
          label="昵称"
        >
        </el-table-column>
        <el-table-column
          label="头像"
        >
          <template slot-scope="scope">
            <el-popover
              width="200"
              trigger="click">
              <img :src="imgUrl+'/'+scope.row.avatar" width="200"/>
              <el-button slot="reference">查看</el-button>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          label="性别"
        >
          <template slot-scope="scope">
            <span>{{scope.row.sex == 1 ? '男' : '女'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="description"
          label="个性签名"
        >
        </el-table-column>
        <el-table-column
          label="注册时间"
          width="200"
        >
          <template slot-scope="scope">
            <span>{{scope.row.createTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="最新修改时间"
          width="200"
        >
          <template slot-scope="scope">
            <span>{{scope.row.updateTime | formatDate}}</span>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :page-size="10"
        :current-page.sync="pageNum"
        layout="total, prev, pager, next"
        @current-change="queryUser('page')"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import {getUserList} from '../api/api'
  import {IMG_URL} from '../api/url'
  import {isEmpty} from '../util/utils'
  import base from './base.vue'
  export default {
    extends: base,
    data() {
      return {
        querys: {
          nickname: "",
          dateTime:""
        },
        tableData: [],
        total: 0,
        pageNum: 1,
        loading: false,
        imgUrl: IMG_URL
      }
    },
    mounted(){
      this.queryUser();
    },
    methods: {
      queryUser(type){
        this.loading = true;
        var dataCondition = [];
        if (!isEmpty(this.querys.nickname)) {
          dataCondition.push({
            "condition": "nickname like ",
            "value": "%" + this.querys.nickname + "%",
            "singleValue": true
          })
        }
        if(this.querys.dateTime.length>0){
          dataCondition.push({
            "condition": " create_time >= ",
            "value": this.querys.dateTime[0] ,
            "singleValue": true
          })
          dataCondition.push({
            "condition": " create_time <= ",
            "value": this.querys.dateTime[1] ,
            "singleValue": true
          })
        }
        if(isEmpty(type)){
          this.pageNum=1;
        }
        var obj = {
          pageInfo: {
            pageNum: this.pageNum,
            pageSize: 10
          },
          param: {
            oredCriteria: [
              {
                criteria: dataCondition
              }
            ]
          }
        }
        var json = JSON.stringify(obj);
        var that = this;
        getUserList(json).then((res) => {
          that.loading = false;
          if (res.status == 200) {
            that.tableData = res.data.list;
            that.total = res.data.total;
            that.pageNum = res.data.pageNum;
          } else {
            that.$message.error(res.msg);
          }
        })

      }
    }
  }
</script>

<style>
</style>
