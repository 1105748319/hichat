<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>人员管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-input placeholder="请输入用户名称" v-model="querys.nickname" style="width:200px;"></el-input>
        <el-input placeholder="请输入账号" v-model="querys.account" style="width:200px;"></el-input>
        <el-button icon="el-icon-search" @click="queryEmployee" circle></el-button>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-button type="primary" @click="dialogFormVisible=true" v-if="hasPerm('employee:edit')">新增</el-button>
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
          prop="roles"
          label="拥有角色"
          width="200"
        >
        </el-table-column>
        <el-table-column
          label="创建时间"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.createTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="修改时间"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{scope.row.updateTime | formatDate}}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          fixed="right"
          width="300"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="toEdit(scope.row,1)"  v-if="hasPerm('employee:edit')">编辑</el-button>
            <el-button type="text" size="small" @click="deleteObj(scope.row.id)"  v-if="hasPerm('employee:delete')">删除</el-button>
            <el-button type="text" size="small" @click="toEdit(scope.row,2)"  v-if="hasPerm('employee:reset')">重置密码</el-button>
            <el-button type="text" size="small" @click="toEdit(scope.row,3)"  v-if="hasPerm('employee:assign')">分配角色</el-button>
          </template>
        </el-table-column>

      </el-table>

      <el-pagination
        :page-size="10"
        :current-page.sync="pageNum"
        layout="total, prev, pager, next"
        @current-change="queryEmployee('page')"
        :total="total">
      </el-pagination>
    </div>

    <el-dialog title="人员编辑" :visible.sync="dialogFormVisible">
      <el-form :model="employee"  ref="ruleForm" :rules="rules">
        <el-form-item label="账号" prop="account">
          <el-input v-model="employee.account"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="employee.nickname"></el-input>
        </el-form-item>
        <el-form-item label="密码" v-if="isEmpty(employee.id)" prop="password">
          <el-input type="password" v-model="employee.password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm(1)">取 消</el-button>
        <el-button type="primary" @click="submitEmployee()" v-loading.fullscreen.lock="submitLoading">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="重置密码" :visible.sync="resetDialogFormVisible">
      <el-input type="password" placeholder="请输入密码" v-model="resetpassword"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm(2)">取 消</el-button>
        <el-button type="primary" @click="resetPass()" v-loading.fullscreen.lock="submitLoading">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="分配角色" width="30%"  :visible.sync="roleDialogFormVisible">

      <el-transfer v-model="employee.roleIds" :data="roleList">

      </el-transfer>

      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm(3)">取 消</el-button>
        <el-button type="primary" @click="assignRole" v-loading.fullscreen.lock="submitLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import base from './base.vue'
  import {getEmployeeList,saveUpdateEmployee,employeeDelete,getRoleList,employeeAssignRole} from '../api/api'
  import {isEmpty} from '../util/utils'
  import forEach from 'lodash/forEach'
  export default {
    extends:base,
    data() {
      return {
        dialogFormVisible: false,
        resetDialogFormVisible:false,
        roleDialogFormVisible:false,
        resetpassword:"",
        rules: {
          account: [
            {required: true, message: '请输入账号'}
          ],
          password: [
            {required: true, message: '请输入密码'}
          ],
          nickname: [
            {required: true, message: '请输入昵称'}
          ]
        },
        employee: {
          account: "",
          password: "",
          nickname:"",
          id:"",
          roleIds:[]
        },
        tableData: [],
        total: 0,
        pageNum: 1,
        loading: false,
        submitLoading:false,
        querys:{
          nickname:"",
          account:""
        },
        roleList:[]
      }
    },
    mounted(){
      this.queryEmployee();
      this.queryRoleSelect();
    },
    methods:{
      assignRole(){
        var json=JSON.stringify(this.employee);
        this.submitLoading=true;
        var that=this;
        employeeAssignRole(json).then((res)=>{
          that.submitLoading=false;
          if(res.status==200){
            that.resetForm(3);
            that.queryEmployee();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      queryRoleSelect(){
        var obj = {
          pageInfo: null,
          param: {
            oredCriteria: [
              {
                criteria: []
              }
            ],
          }
        }
        var json =JSON.stringify(obj);
        var that=this;
        getRoleList(json).then((res)=>{
          if(res.status==200){
            forEach(res.data,(n)=>{
              that.roleList.push({
                key: n.id,
                label:n.roleName
              });
            })
          }
        })
      },
      deleteObj(id){
        var that=this;
        employeeDelete(id).then((res)=>{
          if(res.status==200){
            that.queryEmployee();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      isEmpty(m){
        return isEmpty(m);
      },
      toEdit(obj,type){
        this.employee=JSON.parse(JSON.stringify(obj));
        if(type==1){
          //修改
          this.dialogFormVisible=true;
        }else if(type==2){
          //重置密码
          this.resetDialogFormVisible=true;
        }else{
          //分配角色
          this.roleDialogFormVisible=true;
        }

      },
      resetForm(type){
        if(type==1){
          this.dialogFormVisible=false;
          this.$refs.ruleForm.resetFields();
        }else if(type==2){
          this.resetpassword="";
          this.resetDialogFormVisible=false;
        }else{
          this.roleDialogFormVisible=false;
        }
        this.employee=Object.assign({},resetObj.employee);
      },
      resetPass(){
        if(isEmpty(this.resetpassword)){
          this.$message.error("请输入密码");
          return ;
        }
        this.employee.password=this.resetpassword;
        this.confirmEmp(2);
      },
      submitEmployee(){
        var that=this;
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            that.confirmEmp(1);
          } else {
            return false;
          }
        });
      },
      confirmEmp(type){
        var that=this;
        if(!isEmpty(that.employee.id)&&type==1){
          that.employee.password=null;
        }
        var json=JSON.stringify(that.employee);
        that.submitLoading=true;
        saveUpdateEmployee(json).then((res)=>{
          that.submitLoading=false;
          if(res.status==200){
            that.resetForm(type);
            that.queryEmployee();
          }else{
            that.$message.error(res.msg);
          }
        })
      },
      queryEmployee(type){
        this.loading = true;
        var dataCondition = [];
        if (!isEmpty(this.querys.nickname)) {
          dataCondition.push({
            "condition": " webUser.nickname like ",
            "value": "%" + this.querys.nickname + "%",
            "singleValue": true
          })
        }
        if (!isEmpty(this.querys.account)) {
          dataCondition.push({
            "condition": " webUser.account like ",
            "value": "%" + this.querys.account + "%",
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
            orderByClause:" webUser.id desc "
          }
        }
        var json = JSON.stringify(obj);
        var that = this;
        getEmployeeList(json).then((res) => {
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
    employee: {
      account: "",
      password: "",
      nickname:"",
      id:"",
      roleIds:[]
    }
  }
</script>

<style>
</style>
