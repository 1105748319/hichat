<template>
  <div class="list-block" >
    <ul>
      <li>
        <a href="#" class="item-link item-content" @click="openUploadHead">

          <div class="item-inner">
            <div class="item-title-row">
              <div class="item-title">头像</div>
            </div>
            <div class="item-media"><img v-bind:src="imgUrl+'/'+currentUser.avatar" width="80" height="80"></div>
          </div>
        </a>
      </li>

      <!-- Text inputs -->
      <li>
        <div class="item-content">
          <div class="item-inner">
            <div class="item-title label">账号</div>
            <div class="item-input">
              <input type="text" disabled v-model="currentUser.account" >
            </div>
          </div>
        </div>
      </li>
      <!-- Text inputs -->
      <li>
        <div class="item-content">
          <div class="item-inner">
            <div class="item-title label">昵称</div>
            <div class="item-input">
              <input type="text" v-model="currentUser.nickname">
            </div>
          </div>
        </div>
      </li>


      <!-- Select -->
      <li>
        <div class="item-content">
          <div class="item-inner">
            <div class="item-title label">性别</div>
            <div class="item-input">
              <select v-model="currentUser.sex">
                <option value="1">男</option>
                <option value="0">女</option>
              </select>
            </div>
          </div>
        </div>
      </li>

      <li>
        <div class="item-content">
          <div class="item-inner">
            <div class="item-title label">个性签名</div>
            <div class="item-input">
              <textarea v-model="currentUser.description">{{currentUser.description}}</textarea>
            </div>
          </div>
        </div>
      </li>

    </ul>
    <p><a href="#" class="button button-big button-fill color-gray" v-on:click="saveUser">保 存</a></p>
    <p><a href="#" class="button button-fill button-big color-red" v-on:click="quit">退 出</a></p>

    <pictureimg :from="'myUpload'" :from-clip="'myClip'" :from-file="'myFile'" :from-view="'myView'"
                :from-clip-area="'myClipArea'"
                @upload-img="uploadImgHead"/>

  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {updateUser} from '../api/api'
  import {IMG_URL} from '../api/url'
  import {logout} from '../api/api'
  import pictureimg from './picture.vue'
  import {uploadHead} from '../api/api'
  import Cookies from 'cookies-js'
  export default {
    computed: {
      ...mapState({
        user: state => state.user,
        websocket:state=>state.websocket
      })
    },
    data(){
        return {
            imgUrl:IMG_URL,
            currentUser:{}
        }
    },
    watch:{
      user(val){
          this.currentUser=JSON.parse(JSON.stringify(val));
      }
    },
    methods:{
      uploadImgHead(imgType,md5,data){
        this.$f7.showIndicator();
        uploadHead(imgType,md5,data).then(data=>{
          this.$f7.hideIndicator();
          if(data.status==200){
            let headUrl=data.data;
            this.currentUser.avatar=headUrl;
            this.$f7.closeModal('#myUpload');
          }else{
            this.$f7.alert(data.msg,"hichat");
          }
        })
      },
      saveUser(){
          this.$f7.showIndicator();
        updateUser(JSON.stringify(this.currentUser)).then(data=>{
            this.$f7.hideIndicator();
            if(data.status==200){
              this.$f7.alert("更新成功","hichat");
              this.$store.dispatch("initUser",this.currentUser);
            }else{
                this.$f7.alert(data.msg,"hichat");
            }
        })
      },
      quit(){
        this.$f7.showIndicator();
        logout().then(data=>{
          this.$f7.hideIndicator();
            if(data.status==200){
              Cookies.set("token","");
                window.location.reload();
            }else{
                this.$f7.alert(data.msg,"hichat");
            }
        })
      },
      openUploadHead(){
        this.$f7.popup("#myUpload");
      }
    },
    components:{
      pictureimg
    }
  }
</script>

<style scoped>
  .list-block textarea{
    height: auto;
  }
</style>
