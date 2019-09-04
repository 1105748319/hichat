<template>
  <f7-page pull-to-refresh @ptr:refresh="onRefresh" toolbar-fixed navbar-fixed>
    <f7-navbar :title="chatUser.nickname" back-link="返回" sliding></f7-navbar>


    <f7-messages :init="true" :scrollMessages="scrollAuto" ref="f7messages">
    </f7-messages>

    <f7-toolbar class="messagebar">
      <div class="toolbar-inner">
        <f7-link @click="openImg">
          <i class="f7-icons">camera</i>
        </f7-link>
        <textarea placeholder="Message" v-model="content" style="width: 70%;"></textarea>
        <a href="#" class="send" @click="onSubmit">发送</a>
        <input id="uploadImg" type="file" accept="image/*" @change="uploadMessageImg" hidden/>
      </div>
    </f7-toolbar>
    <!--<f7-messagebar placeholder="Message" send-link="Send" @submit="onSubmit"></f7-messagebar>-->

  </f7-page>
</template>

<script>
  import {mapState} from 'vuex'
  import {IMG_URL} from '../api/url'
  import singleChat from '../websocket/msgObj'
  import {isEmpty} from '../util/utils'
  import {saveMessage} from '../api/api'
  import {getMessage} from '../api/api'
  import forEach from 'lodash/forEach'
  import {formatDate} from '../util/utils'
  import lrz from 'lrz'
  import {getBinaryData} from '../util/utils'
  import {getFileMd5} from '../util/utils'
  import {uploadMessageImg} from '../api/api'
  import $ from 'jquery'
  export default {
    computed: {
      ...mapState({
        chatUser: state => state.chatUser,
        websocket: state => state.websocket,
        user: state => state.user
      })
    },
    data() {
      return {
        imgUrl: IMG_URL,
        pageNum: 1,
        pages: null,
        scrollAuto: true,
        messages: [],
        chooseUser: {},
        content: "",
        photoBrowserCount: 0
      }
    },
    mounted() {
      var _self = this;
      this.websocket.onmessage = function (event) {
        var result = JSON.parse(event.data);
        if (result.status == 100) {
          _self.$f7.hideIndicator();
          result = result.data;
          let obj = JSON.parse(result);
          _self.scrollAuto = true;
          if (obj.msgType == 1) {
            _self.messages.push(_self.generateMessage(obj.message, _self.user, 'sent'))
            _self.$refs.f7messages.appendMessage(_self.generateMessage(obj.message, _self.user, 'sent'));
          } else {
            //图片
            _self.messages.push(_self.generateMessage("", _self.user, 'sent'))
            _self.$refs.f7messages.appendMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + obj.smallImg + "'  class='message-img'>", _self.user, 'sent'));
            $(".message-img").click(() => {
              _self.onMessageClick(_self.imgUrl + '/' + obj.message);
            })
          }
        }
        else if (result.status == 101) {
          //接收到消息
          let message = result.data;
          let content = message.message;
          _self.scrollAuto = true;
          if (message.msgType == 1) {
            _self.$refs.f7messages.appendMessage(_self.generateMessage(content, _self.chatUser, 'received'));
            _self.messages.push(_self.generateMessage(content, _self.chatUser, 'received'))
          } else {
            //图片
            _self.messages.push(_self.generateMessage("", _self.chatUser, 'received'))
            _self.$refs.f7messages.appendMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + message.smallImg + "'  class='message-img'>", _self.chatUser, 'received'));
            $(".message-img").click(() => {
              _self.onMessageClick(_self.imgUrl + '/' + message.message);
            })
          }
        }
        else {
          _self.$f7.alert("发送消息失败", "医患平台");
        }
      };
      //获取双方聊天记录
      this.getBothMessage(new Date(), 0);
    },
    methods: {
      uploadMessageImg(event){
        let files = event.target.files
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
                    uploadMessageImg(imgType, md5, binaryData).then(data => {
                      if (i == (files.length - 1)) {
                        _self.$f7.hideIndicator();
                      }
                      if (data.status == 200) {
                        let urls = data.data;
                        _self.content = urls.imgUrl + ";" + urls.smallUrl;
                        _self.onSubmit("img");
                      } else {
                        _self.$f7.alert(data.msg, "医患平台");
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
      openImg(){
        document.getElementById("uploadImg").click();
      },
      getBothMessage(time, type){
        getMessage(this.user.id, this.chatUser.id, time, JSON.stringify({
          pageNum: this.pageNum,
          pageSize: 10
        })).then(data => {
          if (data.status == 200) {
            data = data.data;
            this.pages = data.pages;
            var _self = this;
            if (data.list.length > 0) {
              if (type == 0) {
                _self.scrollAuto = true;
                forEach(data.list, (n) => {
                  if (n.msgType == 1) {
                    if (n.fromUserId == _self.user.id) {
                      _self.$refs.f7messages.prependMessage(_self.generateMessage(n.message, _self.user, 'sent', _self.formatDate(n.createTime)));
                      _self.messages.push(_self.generateMessage(n.message, _self.user, 'sent', n.createTime));
                    } else {
                      _self.$refs.f7messages.prependMessage(_self.generateMessage(n.message, _self.chatUser, 'received', _self.formatDate(n.createTime)));
                      _self.messages.push(_self.generateMessage(n.message, _self.chatUser, 'received', n.createTime));
                    }
                  } else {
                    //图片
                    if (n.fromUserId == _self.user.id) {
                      _self.messages.push(_self.generateMessage("", _self.user, 'sent', n.createTime))
                      _self.$refs.f7messages.prependMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + n.smallImg + "'  class='message-img'>", _self.user, 'sent'));
                      $(".message-img").click(() => {
                        _self.onMessageClick(_self.imgUrl + '/' + n.message);
                      })
                    } else {
                      _self.messages.push(_self.generateMessage("", _self.chatUser, 'received', n.createTime))
                      _self.$refs.f7messages.prependMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + n.smallImg + "'  class='message-img'>", _self.chatUser, 'received'));
                      $(".message-img").click(() => {
                        _self.onMessageClick(_self.imgUrl + '/' + n.message);
                      })
                    }
                  }
                })
                _self.messages.reverse();
              } else {
                _self.scrollAuto = false;
                forEach(data.list, (n) => {
                  if (n.msgType == 1) {
                    if (n.fromUserId == _self.user.id) {
                      _self.$refs.f7messages.prependMessage(_self.generateMessage(n.message, _self.user, 'sent', _self.formatDate(n.createTime)));
                      _self.messages.unshift(_self.generateMessage(n.message, _self.user, 'sent', n.createTime));
                    } else {
                      _self.$refs.f7messages.prependMessage(_self.generateMessage(n.message, _self.chatUser, 'received', _self.formatDate(n.createTime)));
                      _self.messages.unshift(_self.generateMessage(n.message, _self.chatUser, 'received', n.createTime));
                    }
                  } else {
                    //图片
                    if (n.fromUserId == _self.user.id) {
                      _self.messages.unshift(_self.generateMessage("", _self.user, 'sent', n.createTime))
                      _self.$refs.f7messages.prependMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + n.smallImg + "'  class='message-img'>", _self.user, 'sent'));
                      $(".message-img").click(() => {
                        _self.onMessageClick(_self.imgUrl + '/' + n.message);
                      })
                    } else {
                      _self.messages.unshift(_self.generateMessage("", _self.chatUser, 'received', n.createTime))
                      _self.$refs.f7messages.prependMessage(_self.generateImgMessage("<img src='" + _self.imgUrl + '/' + n.smallImg + "'  class='message-img'>", _self.chatUser, 'received'));
                      $(".message-img").click(() => {
                        _self.onMessageClick(_self.imgUrl + '/' + n.message);
                      })
                    }
                  }

                })
              }

            }
            this.$f7.pullToRefreshDone();
          } else {
            this.$f7.alert("获取聊天记录失败", "医患平台");
          }
        })
      },
      onMessageClick(url) {
          var _self=this;
        if (this.photoBrowserCount == 0) {
          var photoBrowser = this.$f7.photoBrowser({
            zoom: 400,
            theme: 'dark',
            swipeToClose: true,
            lazyLoading: true,
            photos: [url],
            onClose(){
              _self.photoBrowserCount=0;
            },
            onSwipeToClose(){
              _self.photoBrowserCount=0;
            }
          });
          photoBrowser.open();
          this.photoBrowserCount=1;
        }
      },
      onTextClick() {
        console.log("text click");
      },
      onNameClick() {
        console.log("name click");
      },
      onAvatarClick() {
        console.log("avatar click");
      },
      onSubmit(type) {
        if (isEmpty(this.content)) {
          this.$f7.alert("不能发送空内容", "hichat");
          return;
        }
        if (this.chatUser.id == this.user.id) {
          //自己的聊天窗口
          if (type == "img") {
            this.$refs.f7messages.appendMessage(this.generateImgMessage("<img src='" + this.imgUrl + '/' + this.content.split(";")[1] + "' class='message-img'>", this.user, 'sent'));
            var _self = this;
            let bigImg = this.content.split(";")[0];
            $(".message-img").click(() => {
              _self.onMessageClick(this.imgUrl + '/' + bigImg);
            })
          } else {
            this.$refs.f7messages.appendMessage(this.generateMessage(this.content, this.user, 'sent'))
          }
        } else {
          //先保存到数据库
          var obj = null;
          if (type == "img") {
            obj = new singleChat(
              2, this.content.split(";")[0], this.user.id, this.chatUser.id, this.content.split(";")[1]
            );
          } else {
            obj = new singleChat(
              1, this.content, this.user.id, this.chatUser.id, null
            );
          }

          var json = JSON.stringify(obj);
          this.$f7.showIndicator();
          saveMessage(json).then(data => {
            if (data.status == 200) {
              this.websocket.send(json);
            } else {
              this.$f7.alert(data.msg, "医患平台");
            }
          })

        }
        this.content = "";
      },
      generateMessage(text, user, type, date) {
        return {
          name: user.nickname,
          avatar: this.imgUrl + '/' + user.avatar,
          text,
          type,
          date: isEmpty(date) ? (function () {
            let now = new Date()
            let years = now.getFullYear();
            let month = now.getMonth() + 1;
            let day = now.getDate();
            let hours = now.getHours()
            let minutes = now.getMinutes()
            let second = now.getSeconds()
            return years + "-" + month + "-" + day + " " + hours + ':' + minutes + ':' + second
          })() : date
        }
      },
      generateImgMessage(text, user, type, date) {
        return {
          name: user.nickname,
          avatar: this.imgUrl + '/' + user.avatar,
          text,
          type
        }
      },
      onRefresh(){
        let length = this.messages.length;
        if (length > 0) {
          if (this.pageNum < this.pages) {
            this.pageNum++;
            let date = this.messages[length - 1].date;
            this.getBothMessage(new Date(date), 1);

          } else {
            this.$f7.pullToRefreshDone();
          }

        }

      },
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
      }
    },
    filters: {
      formatDates(time) {
        return this.formatDate(time);
      }
    }


  }


</script>

<style scoped>
</style>
