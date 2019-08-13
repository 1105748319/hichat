<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>聊天记录</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-select
          v-model="querys.users"
          multiple
          :multiple-limit="2"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户名, 最多可选择两个用户"
          :remote-method="querySelectUser"
          :loading="selectLoading">
          <el-option
            v-for="item in userSelect"
            :label="item.nickname"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button icon="el-icon-search" @click="queryUsersMessage" circle></el-button>
      </el-row>
      <el-row style="margin-top: 10px">
      </el-row>

      <el-table
        :data="outTableData"
        stripe
        v-loading="outLoading"
        style="width: 100%;margin-top: 15px">
        <el-table-column
          prop="fromUser"
          label="发话用户"
        >
        </el-table-column>
        <el-table-column
          prop="toUser"
          label="对话用户"
        >
        </el-table-column>
        <el-table-column
          label="最新聊天时间"
        >
          <template slot-scope="scope">
            <span>{{scope.row.chatTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="对话记录"
        >
          <template slot-scope="scope">
            <el-button @click="checkChatRecord(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        :page-size="10"
        :current-page.sync="outPageNum"
        layout="total, prev, pager, next"
        @current-change="queryUsersMessage('page')"
        :total="outTotal">
      </el-pagination>


      <el-dialog :title="currentNode.fromUser+'与'+currentNode.toUser+'的聊天记录'" :visible.sync="dialogTableVisible">
        <el-table :data="innerTableData" v-loading="innerLoading" style="width: 100%;">
          <el-table-column label="时间">
            <template slot-scope="scope">
              <span>{{scope.row.createTime | formatDate}}</span>
            </template>
          </el-table-column>
          <el-table-column property="fromUserName" label="用户"></el-table-column>
          <el-table-column  label="内容">
            <template slot-scope="scope">
              <span v-if="scope.row.msgType==1">{{scope.row.message}}</span>
              <img v-else :src="imgUrl+'/'+scope.row.message" style="width: 50px;cursor: pointer" :preview="scope.$index" preview-text="" v-on:click="checkBig(imgUrl+'/'+scope.row.message)">
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          :page-size="3"
          :current-page.sync="innerPageNum"
          layout="total, prev, pager, next"
          @current-change="queryMessage"
          :total="innerTotal">
        </el-pagination>
      </el-dialog>

    </div>
  </div>
</template>

<script>
  import {getUserMessage, getUserList, getMessage} from '../api/api'
  import {formatDateTimestamp,isEmpty} from '../util/utils'
  import base from './base.vue'
  import {IMG_URL} from '../api/url'
  export default {
    extends: base,
    data() {
      return {
        querys:{
          users: [],
        },
        dialogTableVisible: false,
        outTotal: 0,
        outPageNum: 1,
        outLoading: false,
        outTableData: [],
        innerTotal: 0,
        innerPageNum: 1,
        innerLoading: false,
        innerTableData: [],
        currentNode: "",
        imgUrl:IMG_URL
      }
    },
    mounted(){
      this.queryUsersMessage();
    },
    methods: {
      queryUsersMessage(type){
        var obj = {
          fromId: null,
          toId: null
        }
        if (this.querys.users.length == 1) {
          obj.fromId = this.querys.users[0];
        }
        if (this.querys.users.length == 2) {
          obj.fromId = this.querys.users[0];
          obj.toId = this.querys.users[1];
        }
        this.outLoading = true;
        if(isEmpty(type)){
          this.pageNum=1;
        }
        var obj = {
          pageInfo: {
            pageNum: this.pageNum,
            pageSize: 10
          },
          param: obj
        }
        var json = JSON.stringify(obj);
        var that = this;
        getUserMessage(json).then((res) => {
          that.outLoading = false;
          if (res.status == 200) {
            that.outTableData = res.data.list;
            that.outTotal = res.data.total;
            that.outPageNum = res.data.pageNum;
          } else {
            that.$message.error(res.msg);
          }
        })
      },
      checkChatRecord(node){
        this.dialogTableVisible = true;
        this.currentNode = node;

        this.queryMessage();
      },
      queryMessage(){
        let fromId = this.currentNode.fromId;
        let toId = this.currentNode.toId;
        let time = null;
        if (this.innerTableData.length == 0) {
          time = this.currentNode.chatTime;
        } else {
          time = this.innerTableData[this.innerTableData.length - 1].createTime;
        }
        var date = new Date(time);
        let json = JSON.stringify({
          pageNum: this.innerPageNum,
          pageSize: 3
        })
        this.innerLoading = true;
        var that = this;
        getMessage(fromId, toId, date, json).then((res) => {
          that.innerLoading = false;
          if (res.status == 200) {
            that.innerTableData = res.data.list;
            that.innerTotal = res.data.total;
            that.innerPageNum = res.data.pageNum;
            that.$previewRefresh();
          } else {
            that.$message.error(res.msg);
          }
        })
      }
    }
  }
</script>

<style scoped>
</style>
