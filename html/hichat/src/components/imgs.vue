<template>
  <div>
    <div v-if="smallImgs.length<3">
      <div class="row" v-if="smallImgs.length===1">
        <div>
          <span v-for="item,index in smallImgs">
          <img v-lazy="imgUrl+'/'+item"   v-bind:width="widthHeight1" v-bind:height="widthHeight1"
               @click="openPhotoBrowser(index)">
           <i class="f7-icons" v-if="isEdit==='yes'" @click="deleteImg(index)">close_round_fill</i>
          </span>
        </div>
      </div>
      <div class="row" v-if="smallImgs.length===2">
        <div>
          <span v-for="item,index in smallImgs">
            <img v-lazy="imgUrl+'/'+item"   v-bind:width="widthHeight2" v-bind:height="widthHeight2"
                 @click="openPhotoBrowser(index)"/>
           <i class="f7-icons" v-if="isEdit==='yes'" @click="deleteImg(index)">close_round_fill</i>
          </span>
        </div>
      </div>
    </div>
    <div v-if="smallImgs.length>=3">
      <div class="row" v-if="smallImgs.length>0">
        <div>
          <span v-for="item,index in smallImgs">
            <img v-lazy="imgUrl+'/'+item"  v-if="index<=2&&item!==''" v-bind:width="widthHeight3"
                 v-bind:height="widthHeight3" @click="openPhotoBrowser(index)"/>
            <i class="f7-icons" v-if="index<=2&&item!==''&&isEdit==='yes'" @click="deleteImg(index)">close_round_fill</i>
          </span>
        </div>
      </div>
      <div class="row" v-if="smallImgs.length>3">
        <div>
          <span v-for="item,index in smallImgs">
          <img v-lazy="imgUrl+'/'+item"  v-if="index>2&&index<=5&&item!==''" v-bind:width="widthHeight3"
               v-bind:height="widthHeight3"
               @click="openPhotoBrowser(index)"/>
             <i class="f7-icons" v-if="index>2&&index<=5&&item!==''&&isEdit==='yes'" @click="deleteImg(index)">close_round_fill</i>
          </span>
        </div>
      </div>
      <div class="row" v-if="smallImgs.length>6">
        <div>
          <span v-for="item,index in smallImgs">
          <img v-lazy="imgUrl+'/'+item"   v-if="index>5&&index<=8&&item!==''" v-bind:width="widthHeight3"
               v-bind:height="widthHeight3"
               @click="openPhotoBrowser(index)"/>
             <i class="f7-icons" v-if="index>5&&index<=8&&item!==''&&isEdit==='yes'" @click="deleteImg(index)">close_round_fill</i>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {IMG_URL} from '../api/url'
  import forEach from 'lodash/forEach'
  import {isEmpty} from '../util/utils'
  export default {
    props: ["essayImgs", "isEdit","essaySmallImgs"],
    computed: {
      smallImgs(){
        let array = this.essaySmallImgs.split(";");
        array.splice(array.length - 1, 1);
        return array;
      },
      imgs(){
        let array = this.essayImgs.split(";");
        array.splice(array.length - 1, 1);
        return array;
      },
      browserPhotos(){
        let array = [];
        var _self = this;
        forEach(this.imgs, (n) => {
          if (!isEmpty(n)) {
            array.push({
              url: _self.imgUrl + "/" + n,
              caption: ''
            })
          }
        })
        return array;
      }
    },
    data(){
      return {
        imgUrl: IMG_URL,
        widthHeight3: "",
        widthHeight2: "",
        widthHeight1: ""
      }
    },
    mounted(){
      let clientWidth = document.body.clientWidth;
      this.widthHeight3 = parseInt(clientWidth / 3) - 35;
      this.widthHeight2 = parseInt(clientWidth / 2) - 30;
      this.widthHeight1 = clientWidth - 80;

    },
    methods: {
      deleteImg(index){
        this.$emit("delete-img", index);
      },
      openPhotoBrowser (num) {
        var photoBrowser = this.$f7.photoBrowser({
          zoom: 400,
          theme: 'dark',
          swipeToClose: true,
          initialSlide:num,
          lazyLoading:true,
          photos: this.browserPhotos
        });
        photoBrowser.open();
      }
    }
  }
</script>

<style lang="less" scoped>
  .row {
    img {
      padding: 15px 3px 0px 5px;
    }
    i {
      margin-left: -18px;
    }
  }
</style>
