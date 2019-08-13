<template>
  <f7-popup id="userInfo">
    <div class="navbar">
      <div class="navbar-inner">
        <div class="left"><a href="#" class="link close-popup"><span>close</span></a></div>

      </div>
    </div>
    <div class="list-block media-list">
      <ul>
        <li>
          <a href="#" class="item-link item-content">
            <div class="item-media"><img v-bind:src="imgUrl+'/'+user.avatar" width="80" height="80"
                                         @click="openPhotoBrowser"></div>
            <div class="item-inner">
              <div class="item-title">{{user.nickname}}<img v-bind:src="[user.sex===1 ? maleImg : femaleImg]"
                                                            width="17" height="17"></div>
              <div class="item-subtitle">账号:<span>{{user.account}}</span></div>
              <div class="item-text"></div>
            </div>
          </a>
        </li>

      </ul>
    </div>
    <div class="list-block">
      <ul>
        <li>
          <div class="item-content">
            <div class="item-inner">
              <div class="item-title label">个性签名</div>
              <div class="item-input">
                <textarea>{{user.description}}</textarea>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </f7-popup>

</template>

<script>
  import {IMG_URL} from '../api/url'
  import {isEmpty} from '../util/utils'
  import {mapState} from 'vuex'
  export default{
    computed:{
      ...mapState({
        user:state=>state.userinfo
      })
    },
    data(){
      return {
        imgUrl: IMG_URL,
        maleImg: require("../assets/male.png"),
        femaleImg: require("../assets/female.png"),
        photoBrowser:null
      }
    },
    methods: {
      openPhotoBrowser () {
        if(isEmpty(this.photoBrowser)){
          this.photoBrowser = this.$f7.photoBrowser({
            zoom: 400,
            theme:'dark',
            swipeToClose:true,
            lazyLoading: true,
            photos: [
              {
                url:this.imgUrl+"/"+this.user.avatar,
                caption:this.user.nickname
              }

            ]
          });
        }
        this.photoBrowser.open();
      }
    }
  }
</script>

<style scoped>
  .list-block {
    margin-top: 15px;
  }

  #userInfo {
    background-color: #f7f7f8;
  }
</style>
