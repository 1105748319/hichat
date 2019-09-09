<template>
  <f7-popup id="register">
    <div class="view">
      <div class="page">
        <div class="page-content login-screen-content">
          <div class="login-screen-title">注册账号</div>
          <form>
            <div class="list-block">
              <ul>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">昵称</div>
                    <div class="item-input">
                      <input type="text" v-model="user.nickname" placeholder="昵称">
                    </div>
                  </div>
                </li>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">账号</div>
                    <div class="item-input">
                      <input type="text" v-model="user.account" placeholder="账号">
                    </div>
                  </div>
                </li>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">密码</div>
                    <div class="item-input">
                      <input type="password" v-model="user.password" placeholder="密码">
                    </div>
                  </div>
                </li>
                <li>
                  <div class="item-content">
                    <div class="item-inner">
                      <div class="item-title label">性别</div>
                      <div class="item-input">
                        <select type="select" v-model="user.sex">
                          <option value="">请选择</option>
                          <option value="1">男</option>
                          <option value="2">女</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </li>
                <li class="item-content">
                  <div class="item-inner">
                    <div class="item-title label">上传头像</div>
                    <div class="item-input">
                      <table>
                        <tr>
                          <td width="50%"><f7-link open-popup="#registerUpload">上传</f7-link></td>
                          <td v-if="avatarImg!==''"><img style="margin-left: 20px;width:50px;height: 50px;border-radius: 25px" :src="avatarImg"></td>
                          <input type="hidden" v-model="user.avatar"/>
                        </tr>
                      </table>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="list-block">
              <div class="list-block-label">
                <f7-link @click="register">注册</f7-link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <pictureimg :from="'registerUpload'" :from-clip="'registerClip'" :from-file="'registerFile'" :from-view="'registerView'"
                :from-clip-area="'registerClipArea'"
                @upload-img="uploadImgHead"/>
  </f7-popup>
</template>

<script>
  import {register} from '../api/api'
  import {uploadHead} from '../api/api'
  import pictureimg from './picture.vue'
  import {IMG_URL} from "../api/url"
  export default {
    data() {
      return {
        user: {
          nickname: "",
          account: "",
          password: "",
          sex: "",
          avatar:"",
          description:"1002"
        },
        avatarImg:""
      }
    },
    methods: {
      uploadImgHead(imgType,md5,data){
        this.$f7.showIndicator();
        uploadHead(imgType,md5,data).then(data=>{
          this.$f7.hideIndicator();
          if(data.status==200){
            let headUrl=data.data;
            this.user.avatar=headUrl;
            this.avatarImg=IMG_URL+"/"+headUrl
            this.$f7.closeModal('#registerUpload');
          }else{
            this.$f7.alert(data.msg,"hichat");
          }
        })
      },
      register() {
        let json = JSON.stringify(this.user);
        this.$f7.showIndicator();
        register(json).then(data => {
          this.$f7.hideIndicator();
          if(data.status==200){
            this.user=Object.assign({},resetObj.user);
            this.$f7.closeModal('#register');
          }else{
            this.$f7.alert(data.msg,"医患平台");
          }

        })
      }
    },
    components:{
      pictureimg
    }
  }

  var resetObj={
    user: {
      nickname: "",
      account: "",
      password: "",
      sex: ""
    }
  }

</script>

<style scoped>
</style>
