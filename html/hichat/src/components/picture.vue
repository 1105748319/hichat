<template>
  <f7-popup :id="from">
    <div class="toolbar toolbar-bottom">
      <div class="toolbar-inner">
        <template v-if="clip===false">
          <a href="#" @click="openPicture" class="link">打开</a>
          <a href="#" @click="closeUploadHead" class="link">关闭</a>
          <a href="#" :id="fromClip" class="link">截取</a>
        </template>
        <template v-else>
          <a href="#" @click="replay" hidden class="link">重新</a>
          <a href="#" @click="closeUploadHead" class="link">关闭</a>
          <a href="#" @click="upload" hidden class="link">上传</a>
        </template>
      </div>
      <input :id="fromFile" type="file" accept="image/*" hidden/>

    </div>
    <div class="page-content">

      <div :id="fromClipArea" class="clip-area" v-if="clip===false"></div>
      <div :id="fromView" class="view-view" v-else></div>

    </div>

  </f7-popup>
</template>

<script>
  import {dataURLtoBlob} from '../util/utils'
  import {getBinaryData} from '../util/utils'
  import {getFileMd5} from '../util/utils'
  import PhotoClip from 'photoclip'
  export default {
    props:["from","fromClip","fromFile","fromView","fromClipArea"],
    data() {
      return {
        clip: false,
        dataUrl: "",
        clipArea: ""
      }
    },
    mounted() {
      var _self=this;
      var clipArea = new PhotoClip('#'+_self.fromClipArea, {
        size: [300, 300], // 截取框的宽和高组成的数组。默认值为[260,260]
        outputSize: [300, 300], // 输出图像的宽和高组成的数组。默认值为[0,0]，表示输出图像原始大小
        file: "#"+_self.fromFile,
        view: "#"+_self.fromView,
        ok: "#"+_self.fromClip,
        loadStart: function () {
          console.log("照片读取中");
        },
        loadComplete: function () {
          console.log("照片读取完成");
        },
        done: function (dataURL) {
          _self.dataUrl = dataURL;
          _self.clipImage();
        }
      });
      this.clipArea = clipArea;
    },
    methods: {
      openPicture: function () {
        document.getElementById(this.fromFile).click();
      },
      clipImage: function () {
        if (this.dataUrl == "") {
          this.$f7.alert("您还没有选择照片上传", "医患平台");
          return;
        }
        this.clip = true;
      },
      replay: function () {
        this.clip = false;
        this.dataUrl = "";
        this.clipArea.clear();
      },
      upload: function () {
        var _self=this;
        var file = dataURLtoBlob(this.dataUrl);
        var binaryData = null;
        var md5 = null;
        var imgType = file.type;
        imgType = imgType.split("/")[1];
        getBinaryData(file, function (result) {
          binaryData = result;
          getFileMd5(file, function (result) {
            md5 = result;
            _self.$emit("upload-img",imgType,md5,binaryData);
          })
        })
      },
      closeUploadHead: function () {
        this.$f7.closeModal('#'+this.from);
      }
    }
  }
</script>

<style scoped>
  .view-view{
    height: 300px;
    width: 300px;
    margin:0 auto;
    margin-top:150px;
  }
  .toolbar{
    position: absolute;
    bottom: 0px;
  }
  .clip-area {
    height: 100%;
  }
</style>
