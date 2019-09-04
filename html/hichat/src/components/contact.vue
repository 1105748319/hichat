<template>
  <div>

    <f7-list contacts>
      <f7-list-group v-for="(group, key) in contacts">
        <f7-list-item :title="key" group-title></f7-list-item>
        <f7-list-item v-for="item in group"
                      :title="item.nickname"
                      :media="getAvatarMedia(item)"
                      :link="toMessage()"
                      @click="setChatUser(item)"
        ></f7-list-item>
      </f7-list-group>
    </f7-list>

  </div>
</template>

<script>
  import {getUserList} from '../api/api'
  import groupBy from 'lodash/groupBy'
  import {IMG_URL} from '../api/url'
  export default {
    data(){
      return {
        contacts: []
      }
    },
    mounted(){
    },
    methods: {
      getContacts(){
        this.$f7.showIndicator();
        var obj = {
          param: null,
          pageInfo: {
            pageNum:1,
            pageSize:200
          }
        }
        var json=JSON.stringify(obj);
        getUserList(json).then(data => {
          if (data.status == 200) {
            this.$f7.hideIndicator();
            this.contacts = groupBy(data.data.list, 'header');
          } else {
            this.$f7.alert(data.msg, "医患平台");
          }
        });
      },
      getAvatarMedia(item){
        return "<img class='avatar' src='" + IMG_URL + "/" + item.avatar + "'/>";
      },
      toMessage(){
        return "/message"
      },
      setChatUser(item){
        this.$store.dispatch("initChatUser", item);
      }
    }

  }
</script>

<style lang="less" scoped>
  /deep/ .avatar {
    width: 50px;
    height: 50px;
  }
</style>
