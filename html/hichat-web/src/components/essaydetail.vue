<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path:'/index/essayinfo'}">文章管理</el-breadcrumb-item>
      <el-breadcrumb-item>文章详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 15px">
      <el-row>
        <el-col :span="14">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-row>
                <el-col :span="4">
                  <img height="70" width="70" style="border-radius: 35px"
                       :src="imgUrl+'/'+essay.user.avatar">
                </el-col>
                <el-col :span="12">
                  <div style="font-size: 25px;font-weight: 100">{{essay.user.nickname}}</div>
                  <div style="margin-top: 15px;color: #E6A23C">{{essay.createTime | formatDate}}</div>
                </el-col>
              </el-row>
            </div>
            <div>
              {{essay.content}}
            </div>
            <div style="margin-top: 20px" v-if="showVacar">
              <VaCarousel  :items="pictures" prevText="<" nextText=">" total="3" @selectedItem="choosePic"></VaCarousel>


            </div>
        <div style="display: none">
            <img v-for="items,index in bigPictures" v-bind:class="'img_'+(index+1)" @click="checkBig(items.src)" :src="items.src" preview="1" preview-text="">
        </div>
          </el-card>

        </el-col>


        <el-col :span="10">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>评论</span>
            </div>

            <div>
              <el-table
                :show-header="false"
                :data="tableData"
                v-loading="loading"
                style="width: 100%">
                <el-table-column
                  width="70">
                  <template slot-scope="scope">
                    <img width="50" height="50" :src="imgUrl+'/'+scope.row.user.avatar" style="border-radius: 35px"/>
                  </template>
                </el-table-column>
                <el-table-column>
                  <template slot-scope="scope">
                    <div>{{scope.row.user.nickname}}&nbsp;&nbsp;<span
                      style="font-size: 10px;color: chocolate">{{scope.row.createTime | formatDate}}</span></div>
                    <div>{{scope.row.content}}</div>
                  </template>
                </el-table-column>
              </el-table>


              <el-pagination
                :page-size="10"
                layout="total, prev, pager, next"
                :current-page.sync="pageNum"
                @current-change="queryComment"
                :total="total">
              </el-pagination>
            </div>

          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import VaCarousel from 'va-carousel/src/components/VaCarousel'
  import {mapState} from 'vuex'
  import base from './base.vue'
  import {IMG_URL} from '../api/url'
  import {getCommentByEssayId} from '../api/api'
  import forEach from 'lodash/forEach'
  import {isEmpty} from '../util/utils'
  import $ from 'jquery'
  export default {
    extends: base,
    computed: {
      ...mapState({
        essay: state => state.essay
      })
    },
    mounted(){
      if (!isEmpty(this.essay.smallImgs)) {
        var imgs = this.essay.smallImgs.split(";");
        var bigImgs = this.essay.imgs.split(";");
        var that = this;
        forEach(imgs, (m, i) => {
          if (!isEmpty(m)) {
            that.pictures.push({
              id: i + 1,
              src: that.imgUrl + '/' + m
            })
          }
          if (i == imgs.length - 1) {
            that.showVacar = true;
          }
        })
        forEach(bigImgs, (m, i) => {
          if (!isEmpty(m)) {
            that.bigPictures.push({
              id: i + 1,
              src: that.imgUrl + '/' + m
            })
          }
        })
      }

      this.queryComment();
    },
    data(){
      return {
        total: 0,
        pageNum: 1,
        loading: false,
        showVacar: false,
        tableData: [],
        imgUrl: IMG_URL,
        pictures: [],
        bigPictures:[]
      }
    },
    methods: {
      choosePic(node, index){
          $(".img_"+node.id).trigger("click");
      },
      queryComment(){
        var json = JSON.stringify({
          pageNum: this.pageNum,
          pageSize: 10
        })
        var that = this;
        this.loading = true;
        getCommentByEssayId(this.essay.id, json).then((res) => {
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
    },
    components: {
      VaCarousel
    }
  }
</script>

<style>
</style>
