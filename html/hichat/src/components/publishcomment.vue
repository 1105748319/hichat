<template>
  <div class="item-content publish-comment">
    <div class="item-inner">
      <div class="item-input">
        <div class="row no-gutter">
          <div class="col-80">
            <input type="text" v-model="comment" placeholder="请输入你的评论...">
          </div>
          <div class="col-15">
            <f7-button round color="blue"  @click="sendComment">发送</f7-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      data(){
        return{
          comment:"",
        }
      },
      methods:{
        sendComment(){
          this.$emit("send-comment",this.comment);
        },
        clearComment(){
          this.comment="";
        }
      }
    }
</script>

<style lang="less" scoped>
  .publish-comment{
    width: 100%;
    .item-input{
      input{
        font-size: 12px;
        border: solid 1px #e9e9e9;
        border-radius: 20px;
        height: 29px;
        padding-left: 15px;
        color: #9facb6;
        width: 100%;
      }
      a{
        font-size: 12px;
        height: 29px;
        width: 50px;
      }
    }
  }
</style>
