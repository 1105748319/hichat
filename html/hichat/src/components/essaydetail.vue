<template>
  <f7-page class="detail-page"  navbar-fixed toolbar-fixed infinite-scroll :infinite-scroll-preloader="false"
           :infinite-scroll-distance="1" @infinite="onRefresh">
    <f7-navbar title="详情" back-link="返回" sliding>
    </f7-navbar>
    <card :essay="essay" :is-detail="'yes'"></card>
    <comment v-for="item in commentList" :comment="item"></comment>
    <f7-toolbar>
      <publishcomment ref="publishChild" @send-comment="sendComment"/>
    </f7-toolbar>
  </f7-page>
</template>

<script>
  import card from './card.vue'
  import {mapState} from 'vuex'
  import {getEssayComment} from '../api/api'
  import comment from './comment.vue'
  import {isEmpty} from '../util/utils'
  import forEach from 'lodash/forEach'
  import {publishComment} from '../api/api'
  import publishcomment from './publishcomment.vue'
  export default {
    computed: {
      ...mapState({
        essay: state => state.essay
      })
    },
    data() {
      return {
        commentList: [],
        commentData: "",
      }
    },
    mounted() {
      this.getEssayComment(this.essay.id, 1, 20);
    },
    methods: {
      sendComment(value) {
        if (isEmpty(value)) {
          this.$f7.alert("请输入评论内容", "hichat");
          return;
        }
        this.$f7.showIndicator();
        publishComment(JSON.stringify({
          content: value,
          essayId: this.essay.id
        })).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {
            let _self = this;
            _self.$f7.alert("评论成功", "hichat", function () {
              _self.$refs.publishChild.clearComment();
              _self.commentList = [];
              _self.getEssayComment(_self.essay.id, 1, 20);
            });
          }
          else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      },
      onRefresh() {
        let pageNum = this.commentData.pageNum;
        let pages = this.commentData.pages;
        if (pageNum < pages) {
          this.getEssayComment(this.essay.id, pageNum + 1, 20);
        }
      },
      getEssayComment(essayId, pageNum, pageSize) {
        this.$f7.showIndicator();
        getEssayComment(essayId, JSON.stringify({
          pageNum: pageNum,
          pageSize: pageSize
        })).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {
            data = data.data;
            if (this.commentList.length == 0) {
              this.commentList = data.list;
            } else {
              forEach(data.list, (n => {
                this.commentList.push(n);
              }))
            }

            this.commentData = data;
          } else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      }
    },
    components: {
      card,
      comment,
      publishcomment
    }
  }
</script>

<style scoped>
</style>
