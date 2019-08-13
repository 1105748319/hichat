<template>
  <f7-card>
    <div class="card-header" @click="toDetail">
      <div class="avatar">
        <img :src="imgUrl+'/'+essay.user.avatar" alt="avatar" @click="openUserInfo()">
      </div>
      <div class="user">
        <div class="name">{{essay.user.nickname}}</div>
        <div class="time">{{essay.createTime | formatDates}}</div>
      </div>
    </div>
    <div class="card-content" :inner="false" @click="toDetail()">
      {{essay.content}}
    </div>
    <div class="imgs">
      <imgs :essay-imgs="essay.imgs" :essay-small-imgs="essay.smallImgs" :is-edit="'no'"/>
    </div>
    <f7-card-footer v-if="isDetail==='no'">
      <f7-list accordion>
        <f7-list-item accordion-item @accordion:opened="getEssayComment()" @accordion:closed="commentList=[]"
                      title="评论">
          <f7-accordion-content>

            <publishcomment ref="publishChild" @send-comment="sendComment"/>

            <comment v-for="item in commentList.list" :comment="item"></comment>
            <f7-button v-if="commentList.total>10" round class="more" @click="toDetail()" color="#c4cbd2">查看更多
            </f7-button>
          </f7-accordion-content>
        </f7-list-item>
      </f7-list>
    </f7-card-footer>


  </f7-card>
</template>

<script>
  import comment from './comment.vue'
  import {isEmpty} from '../util/utils'
  import {formatDate} from '../util/utils'
  import {publishComment} from '../api/api'
  import {getEssayComment} from '../api/api'
  import {IMG_URL} from '../api/url'
  import publishcomment from './publishcomment.vue'
  import imgs from './imgs.vue'
  export default{
    props: ["essay", "isDetail"],
    data(){
      return {
        commentList: "",
        imgUrl: IMG_URL,
      }
    },
    methods: {
      clearComments(){
          this.commentList=[];
      },
      openUserInfo(){
        if(this.isDetail=="yes"){
          this.$store.dispatch("initUserinfo",this.essay.user);
          this.$f7.popup("#userInfo");
        }
      },
      toDetail(){
        this.$emit("card:content-click", this.essay);
      },
      sendComment(comment){
        if (isEmpty(comment)) {
          this.$f7.alert("请输入评论内容", "hichat");
          return;
        }
        this.$f7.showIndicator();
        publishComment(JSON.stringify({
          content: comment,
          essayId: this.essay.id
        })).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {
            let _self = this;
            _self.$f7.alert("评论成功", "hichat", function () {
              _self.$refs.publishChild.clearComment();
              _self.getEssayComment(_self.essay.id);
            });

          }
          else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      },
      getEssayComment(){
        this.commentList = [];
        this.$f7.showIndicator();
        getEssayComment(this.essay.id, JSON.stringify({
          pageNum: 1,
          pageSize: 10
        })).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {
            this.commentList = data.data;
          } else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      }
    },
    filters: {
      formatDates(time) {
        var date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
      }
    },
    components: {
      comment,
      publishcomment,
      imgs
    }
  }
</script>

<style lang="less" scoped>
  .more {
    color: #9facb6;
    border: solid 1px #e9e9e9;
  }

  /deep/ .accordion-list {
    width: 100%;
    .item-link {
      font-size: 13px;
      width: 80px;
      display: flex;
    }
  }

  .card-header {
    padding: 10px;
    padding-bottom: 5px;
    justify-content: inherit;
    &:after {
      height: 0;
    }
    .avatar > img {
      width: 40px;
      height: 40px;
      border-radius: 4px;
      margin-right: 9px;
    }
    .user {
      float: left;
      .time {
        font-size: 12px;
        color: #8999a5;
        margin-top: 3px;
      }
      .name {
        color: #ff9630;
        font-weight: bold;
        font-size: 14px;
      }
    }
  }

  .card-content {
    padding-left: 10px;
    padding-bottom: 5px;
  }

  .card-footer {
    padding: 0;
  }

</style>
