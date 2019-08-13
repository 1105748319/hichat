<template>
  <f7-popup id="edit">
    <div class="view">
      <div class="page">
        <f7-navbar>
          <f7-nav-left>
            <f7-link close-popup @click="clearContent"><i class="f7-icons">close</i></f7-link>
          </f7-nav-left>
          <f7-nav-center title="内容"></f7-nav-center>
          <f7-nav-right>
            <f7-link @click="saveContent"><i class="f7-icons">check</i></f7-link>
          </f7-nav-right>
        </f7-navbar>


        <!--<form class="list-block">-->
        <!--<ul>-->
        <!--<li>-->
        <!--<div class="item-content">-->
        <!--<div class="item-inner">-->
        <!--<div class="item-input">-->
        <!--<textarea type="textarea" v-model="content" placeholder="请输入内容..."></textarea>-->
        <!--</div>-->
        <!--</div>-->
        <!--</div>-->
        <!--</li>-->
        <!--</ul>-->

        <!--<ul class="tools flex-row">-->
        <!--<li class="tool" v-show="enableTool('camera')"><i class="iconfont icon-ios7cameraoutline"></i></li>-->
        <!--<li class="tool" v-show="enableTool('album')"><i class="iconfont icon-pic"></i></li>-->
        <!--<li class="tool" v-show="enableTool('emotion')"><i class="iconfont icon-emotion"></i></li>-->
        <!--<li class="tool" v-show="enableTool('at')"><i class="iconfont icon-iosatoutline"></i></li>-->
        <!--<li class="tool" v-show="enableTool('location')"><i class="iconfont icon-location"></i></li>-->
        <!--</ul>-->
        <div class="editor-container">
          <textarea type="textarea" v-model="essay.content" placeholder="请输入内容..."></textarea>
          <ul class="tools flex-row">
            <li class="tool">
              <f7-link @click="openPicture">
                <i class="f7-icons">camera</i>
              </f7-link>
              <input id="openCamera" type="file" accept="image/*" hidden @change="uploadImg"/>
            </li>
          </ul>
        </div>
        <imgs :essay-imgs="essay.imgs" :essay-small-imgs="essay.smallImgs" :is-edit="'yes'" @delete-img="deleteImgs"/>


        <!--</form>-->

      </div>
    </div>

  </f7-popup>
</template>

<script>
  import {getBinaryData} from '../util/utils'
  import {getFileMd5} from '../util/utils'
  import {uploadEssayImg} from '../api/api'
  import {isEmpty} from '../util/utils'
  import imgs from './imgs.vue'
  import lrz from 'lrz'
  export default {
    data() {
      return {
        essay: {
          content: "",
          imgs: "",
          smallImgs: ""
        }
      }
    },
    methods: {
      deleteImgs(index){
        let imgs = this.essay.imgs.split(";");
        let smallImgs = this.essay.smallImgs.split(";");
        let imgsUrl = imgs[index];
        let smallImgsUrl = smallImgs[index];
        this.essay.imgs = this.essay.imgs.replace(imgsUrl + ";", "");
        this.essay.smallImgs = this.essay.smallImgs.replace(smallImgsUrl + ";", "");
      },
      uploadImg(event) {
        let files = event.target.files
        if (files.length > 9) {
          this.$f7.alert("最多只能选择9张图片", "hichat");
          return;
        }
        this.$f7.showIndicator();
        var _self = this;
        for (var i = 0; i < files.length; i++) {
          (function (i) {
            var file = files[i];
            var binaryData = null;
            var md5 = null;
            var imgType = file.type;
            imgType = imgType.split("/")[1];
            lrz(file)
              .then(function (rst) {
                getBinaryData(rst.file, function (result2) {
                  binaryData = result2;
                  getFileMd5(rst.file, function (result3) {
                    md5 = result3;
                    uploadEssayImg(imgType, md5, binaryData).then(data => {
                      if (i == (files.length - 1)) {
                        _self.$f7.hideIndicator();
                      }
                      if (data.status == 200) {
                        let urls = data.data;
                        _self.essay.smallImgs += urls.smallUrl + ";";
                        _self.essay.imgs += urls.imgUrl + ";";
                      } else {
                        _self.$f7.alert(data.msg, "hichat");
                      }
                    })
                  })
                })
              })
              .catch(function (err) {
                // 处理失败会执行
              })
              .always(function () {
                // 不管是成功失败，都会执行
              });
          })(i);
        }
      },
      openPicture() {
        let array = this.essay.imgs.split(";");
        if (array.length == 10) {
          this.$f7.alert("最多只能选择9张图片", "hichat");
          return;
        }
        document.getElementById("openCamera").click();
      },
      saveContent() {
        if (isEmpty(this.essay.content)) {
          this.$f7.alert("请输入内容", "hichat");
          return;
        }
        this.$emit("save-content", this.essay);
      },
      clearContent() {
        this.essay.content = "";
        this.essay.imgs = "";
        this.essay.smallImgs = "";
      }
    },
    components: {
      imgs
    }
  }
</script>

<style lang="less" scoped>

  .editor-container {

    > textarea {
      width: 100%;
      height: 250px;
      font-size: 15px;
      border: none;
      color: #444;
      margin: 0;
      resize: none;
      box-sizing: border-box;
      padding-top: 55px;
    }

    .tools {
      width: 100%;
      height: 40px;
      background-color: #f9f9f9;
      border-bottom: 1px solid #dadada;
      border-top: 1px solid #dadada;
      list-style: none;
      margin: 0;
      padding: 0;
      margin-top: -5px;

      .tool {
        width: 50px;
        height: 100%;
        text-align: center;
        line-height: 40px;

        .iconfont {
          color: #666;
        }

      }
    }
  }
</style>
