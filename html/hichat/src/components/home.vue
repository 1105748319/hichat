<template>
  <div>
    <f7-button round @click="listEssay(1,0)" class="more">查看最新...</f7-button>
    <card ref="cardChild" v-for="item in list" :essay="item" :is-detail="'no'"
          @card:content-click="routeToDetail"></card>
    <f7-button v-if="pageInfo.pages>pageInfo.pageNum" @click="listEssay(pageInfo.pageNum+1,1)" round class="more">
      查看更多...
    </f7-button>
    <f7-button v-else disabled round class="more">没有更多了...</f7-button>

  </div>
</template>

<script>
  import {getEssay} from '../api/api'
  import card from './card.vue'
  import {sendEssay} from '../api/api'
  import forEach from 'lodash/forEach'
  export default {
    data() {
      return {
        pageInfo: "",
        list: []
      }
    },
    methods: {
      routeToDetail(data) {
        this.$store.dispatch("initEssay", data);
        this.$f7.mainView.router.load({url: `/essaydetail`})
      },
      listEssay(pageNum, type) {
        this.$f7.showIndicator();
        var obj={
            param:null,
            pageInfo:{
                pageNum,
                pageSize:10
            }
        }
        var json=JSON.stringify(obj);
        getEssay(json).then(data => {
          this.$f7.hideIndicator();
          if (data.status == 200) {

            data = data.data;
            this.pageInfo = data;
            if (type == 0) {
              this.list = [];
            }
            var _self = this;
            forEach(data.list, (n) => {
              _self.list.push(n);
            })
            forEach(this.$refs.cardChild,(n)=>{
                n.clearComments();
            })
          } else {
            this.$f7.alert(data.msg, "hichat");
          }
        })
      }
    },
    components: {
      card
    }
  }
</script>

<style lang="less" scoped>
  .more {
    margin-top: 10px;
    color: #9facb6;
    border: solid 1px #e9e9e9;
  }

</style>
