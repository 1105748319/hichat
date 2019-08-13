<template>
  <div>
    <el-row>
      <el-col :span="12">
        <ve-line :data="chartData"></ve-line>
      </el-col>
      <el-col :span="12">
        <ve-pie :data="chartData2"></ve-pie>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <ve-histogram :data="chartData3"></ve-histogram>
      </el-col>
      <el-col :span="12">
        <ve-bar :data="chartData4" :settings="chartSettings"></ve-bar>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
      data(){
        this.chartSettings = {
          metrics: ['聊天记录'],
          dataOrder: {
            label: '聊天记录',
            order: 'desc'
          }
        }
        return{
          chartData: {
            columns: ['日期', '注册用户'],
            rows: [
              { '日期': '1/1', '注册用户': 1393},
              { '日期': '1/2', '注册用户': 1500},
              { '日期': '1/3', '注册用户': 1230},
              { '日期': '1/4', '注册用户': 1689},
              { '日期': '1/5', '注册用户': 2500},
              { '日期': '1/6', '注册用户': 500},
              { '日期': '1/7', '注册用户': 200},
              { '日期': '1/8', '注册用户': 800}
            ]
          },
          chartData2: {
            columns: ['性别', '用户数量'],
            rows: [
              { '性别': '男', '用户数量': 1393 },
              { '性别': '女', '用户数量': 1000 }
            ]
          },
          chartData3: {
            columns: ['日期', '文章发表数','评论'],
            rows: [
              { '日期': '1/1', '图文发表数': 1393 ,'评论':1000},
              { '日期': '1/2', '图文发表数': 3530,'评论':324},
              { '日期': '1/3', '图文发表数': 2923,'评论':654},
              { '日期': '1/4', '图文发表数': 1723 ,'评论':889},
              { '日期': '1/5', '图文发表数': 3792,'评论':1500},
              { '日期': '1/6', '图文发表数': 4593 ,'评论':120}
            ]
          },
          chartData4: {
            columns: ['日期', '聊天记录'],
            rows: [
              { '日期': '1/1', '聊天记录': 1393 },
              { '日期': '1/2', '聊天记录': 3530 },
              { '日期': '1/3', '聊天记录': 2923 },
              { '日期': '1/4', '聊天记录': 1723 },
              { '日期': '1/5', '聊天记录': 3792 },
              { '日期': '1/6', '聊天记录': 4593 }
            ]
          }
        }
      }
    }
</script>

<style>

</style>
