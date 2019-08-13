<script>
  import {getUserList} from '../api/api'
  import {formatDateTimestamp} from '../util/utils'
  import $ from 'jquery'
  import {isEmpty} from '../util/utils'
  export default {
    data() {
      return {
        selectLoading: false,
        userSelect: [],
        imgs: []
      }
    },
    filters: {
      formatDate(time) {
        if(isEmpty(time)){
          return "";
        }
        return formatDateTimestamp(time);
      },
      limitText(text) {
        let back = text;
        text = text.slice(0, 20);
        if (back == text) {
          return text;
        }

        return text + "...";
      }
    },
    methods: {
      checkBig(img) {
        this.imgs = [];
        $(".pswp").css("z-index", "99999");
        this.imgs.push(img);
        this.$previewRefresh();
      },
      querySelectUser(keyword) {
        var dataCondition = [];
        dataCondition.push({
          "condition": "nickname like ",
          "value": "%" + keyword + "%",
          "singleValue": true
        })
        var obj = {
          pageInfo: {
            pageNum: this.pageNum,
            pageSize: 1000
          },
          param: {
            oredCriteria: [
              {
                criteria: dataCondition
              }
            ]
          }
        }
        var json = JSON.stringify(obj);
        var that = this;
        this.selectLoading = true;
        getUserList(json).then((res) => {
          that.selectLoading = false;
          if (res.status == 200) {
            that.userSelect = res.data.list;
          }
        })
      }
    }
  }
</script>

<style>
</style>
