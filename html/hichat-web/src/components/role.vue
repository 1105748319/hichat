<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-input placeholder="请输入角色名称" v-model="querys.roleName" style="width:200px;"></el-input>
        <el-button icon="el-icon-search" @click="queryRole" circle></el-button>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-button type="primary" @click="dialogFormVisible=true" v-if="hasPerm('role:edit')">新增</el-button>
      </el-row>

      <el-table
        :data="tableData"
        stripe
        v-loading="loading"
        style="width: 100%;margin-top: 15px">
        <el-table-column
          prop="roleName"
          label="角色名字"
          >
        </el-table-column>
        <el-table-column
          label="创建时间"
        >
          <template slot-scope="scope">
            <span>{{scope.row.createTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="修改时间"
        >
          <template slot-scope="scope">
            <span>{{scope.row.updateTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          fixed="right"
          >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="toEdit(scope.row,1)" v-if="hasPerm('role:edit')">编辑</el-button>
            <el-button type="text" size="small" @click="deleteObj(scope.row.id)" v-if="hasPerm('role:delete')">删除</el-button>
            <el-button type="text" size="small" @click="toEdit(scope.row,2)" v-if="hasPerm('role:assign')">权限分配</el-button>
          </template>
        </el-table-column>

      </el-table>

      <el-pagination
        :page-size="10"
        :current-page.sync="pageNum"
        layout="total, prev, pager, next"
        @current-change="queryRole('page')"
        :total="total">
      </el-pagination>

      <el-dialog title="角色编辑" :visible.sync="dialogFormVisible">
        <el-input placeholder="请输入角色名称" v-model="role.roleName"></el-input>
        <div slot="footer" class="dialog-footer">
          <el-button @click="resetDialog(1)">取 消</el-button>
          <el-button type="primary" @click="submitRole()" v-loading.fullscreen.lock="submitLoading">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="权限分配" :visible.sync="permDialogFormVisible" :close-on-press-escape="false" @close="resetDialog(2)" :close-on-click-modal="false" width="30%">
        <el-tree
          :data="permList"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="role.pPermIds"
          highlight-current
          :props="defaultProps">
        </el-tree>
        <div slot="footer" class="dialog-footer">
          <el-button @click="resetDialog(2)">取 消</el-button>
          <el-button type="primary" @click="assignPerm" v-loading.fullscreen.lock="submitLoading">确 定</el-button>
        </div>
      </el-dialog>


    </div>
  </div>
</template>

<script>
  import {getRoleList,saveUpdateRole,roleDelete,permissionList,assignPerm} from '../api/api'
  import {isEmpty} from '../util/utils'
  import base from './base.vue'
  export default {
    extends:base,
    data() {
      return {
        dialogFormVisible:false,
        permDialogFormVisible:false,
        tableData: [],
        total: 0,
        pageNum: 1,
        loading: false,
        querys:{
          roleName:""
        },
        role:{
          id:"",
          roleName:"",
          permIds:[],
          pPermIds:[]
        },
        submitLoading:false,
        permList:[],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    mounted(){
      this.queryRole();
      this.queryPermission();
    },
    methods:{
      assignPerm(){
        var that=this;
        let checkedKeys = this.$refs.tree.getCheckedKeys();
        this.role.permIds=checkedKeys;
        this.role.permIdStr=null;
        this.submitLoading=true;
        var json=JSON.stringify(this.role);
        assignPerm(json).then((res)=>{
          that.submitLoading=false;
          if(res.status==200){
            that.resetDialog(2);
            that.queryRole();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      queryPermission(){
        var that=this;
        permissionList().then((res)=>{
          if(res.status==200){
            that.permList=res.data;
          }
        })
      },
      deleteObj(id){
        var that=this;
        roleDelete(id).then((res)=>{
          if(res.status==200){
            that.queryRole();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      toEdit(obj,type){
        this.role=JSON.parse(JSON.stringify(obj));
        if(type==1){
          //编辑
          this.dialogFormVisible=true;
        }else{
          //权限分配
          this.permDialogFormVisible=true;
          var that=this;
          this.$nextTick(()=>{
            that.$refs.tree.setCheckedKeys(this.role.permIds);
          })

        }
      },
      resetDialog(type){
        this.role=Object.assign({},resetObj.role);
        this.$refs.tree.setCheckedKeys([]);
        for(var i=0;i<this.$refs.tree.store._getAllNodes().length;i++){
          this.$refs.tree.store._getAllNodes()[i].expanded=false;
        }
        if(type==1){
          //编辑
          this.dialogFormVisible=false;
        }else{
          //权限分配
          this.permDialogFormVisible=false;
        }
      },
      submitRole(){
        if(isEmpty(this.role.roleName)){
          this.$message.error("请输入角色名称");
          return ;
        }
        var json=JSON.stringify(this.role);
        var that=this;
        this.submitLoading=true;
        saveUpdateRole(json).then((res)=>{
          that.submitLoading=false;
          if(res.status==200){
            that.resetDialog(1);
            that.queryRole();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      queryRole(type){
        this.loading = true;
        var dataCondition = [];
        if (!isEmpty(this.querys.roleName)) {
          dataCondition.push({
            "condition": " role.role_name like ",
            "value": "%" + this.querys.roleName + "%",
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
            ],
            orderByClause:" role.id desc "
          }
        }
        var json = JSON.stringify(obj);
        var that = this;
        getRoleList(json).then((res) => {
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
  var resetObj={
    role:{
      id:"",
      roleName:"",
      permIds:[],
      pPermIds:[]
    }
  }
</script>

<style>
</style>
