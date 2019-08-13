<template>
  <div class="comment">
    <f7-grid no-gutter>
      <f7-col width="15">
        <img :src="imgUrl+'/'+comment.user.avatar" alt="avatar" @click="openUserInfo">
      </f7-col>
      <f7-col width="85">
        <div class="name-time">
          <span class="name">{{comment.user.nickname}}</span>
          <span class="time">{{comment.createTime | formatDates}}</span>
        </div>

        <div class="content">{{comment.content}}</div>
      </f7-col>
    </f7-grid>
  </div>
</template>

<script>
  import {formatDate} from '../util/utils'
  import {IMG_URL} from '../api/url'
  export default{
    props: ["comment"],
    data(){
      return {
        imgUrl:IMG_URL
      }
    },
    methods:{
      openUserInfo(){
        this.$store.dispatch("initUserinfo",this.comment.user);
        this.$f7.popup("#userInfo");
      }
    },
    filters: {
      formatDates(time) {
        var date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
      }
    }

  }
</script>

<style lang="less" scoped>
  .comment {
    background-color: white;
    border-top: rgba(0, 0, 0, 0.12) 1px solid;
    padding-top: 5px;
    img {
      width: 35px;
      height: 35px;
      border-radius: 4px;
      margin-left: 15px;
    }
    .content {
      font-size: 11px;
      color: #8999a5;
      margin-top: 3px;
    }
    .name-time {
      .time {
        font-size: 9px;
        color: #8999a5;
        float: right;
        margin-right: 8px;
      }
      .name {
        color: #ff9630;
        font-weight: bold;
        font-size: 12px;
      }
    }

  }
</style>
