<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" >
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-select
          v-model="querys.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户名"
          :remote-method="querySelectUser"
          :loading="selectLoading">
          <el-option
            v-for="item in userSelect"
            :label="item.nickname"
            :value="item.id">
          </el-option>
        </el-select>
        <el-date-picker
          v-model="querys.dateTime"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
        <el-button icon="el-icon-search" @click="queryEssay" circle></el-button>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-button type="danger"  v-if="hasPerm('essayinfo:delete')">删除</el-button>
      </el-row>

      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%;margin-top: 15px">
        <el-table-column
          prop="user.nickname"
          label="用户"
        >
        </el-table-column>
        <el-table-column
          label="内容"
        >
          <template slot-scope="scope">
            <span>
              {{scope.row.content | limitText}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          label="时间"
        >
          <template slot-scope="scope">
            <span>
              {{scope.row.createTime | formatDate}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
        >
          <template slot-scope="scope">
            <el-button @click="toEssayDetail(scope.row)" v-if="hasPerm('essayinfo:load')">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :page-size="10"
        layout="total, prev, pager, next"
        :current-page.sync="pageNum"
        @current-change="queryEssay('page')"
        :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import base from './base.vue'
  import {isEmpty} from '../util/utils'
  import {getEssay} from '../api/api'

  export default {
    extends: base,
    data() {
      return {
        tableData: [],
        total: 0,
        pageNum: 1,
        loading: false,
        querys:{
          userId: "",
          dateTime: []
        }
      }
    },
    mounted(){
      this.queryEssay();
    },
    methods: {
      queryEssay(type){
        this.loading = true;
        var dataCondition = [];
        if (!isEmpty(this.querys.userId)) {
          dataCondition.push({
            "condition": " hessay.user_id = ",
            "value": this.userId ,
            "singleValue": true
          })
        }
        if(this.querys.dateTime.length>0){
            dataCondition.push({
              "condition": " hessay.create_time >= ",
              "value": this.querys.dateTime[0] ,
              "singleValue": true
            })
          dataCondition.push({
            "condition": " hessay.create_time <= ",
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
        getEssay(json).then((res) => {
          that.loading = false;
          if (res.status == 200) {
            that.tableData = res.data.list;
            that.total = res.data.total;
            that.pageNum = res.data.pageNum;
          } else {
            that.$message.error(res.msg);
          }
        })
      },
      toEssayDetail(node){
        this.$store.dispatch('initEssay', node);
        this.$router.push("/index/essaydetail");
      }
    }
  }
</script>

<style>
</style>
