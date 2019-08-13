var socket = function ($f7, token) {
  var url = "ws://127.0.0.1:8082/";
  var ws = null;
  function initWs($f7, token) {
    if (!!window['WebSocket']) {
      ws = new WebSocket(url + token);
    } else {
      $f7.alert("不支持websocket通信，请联系管理员！", "hichat");
    }
  };
  this.getWebSocket = function () {
    return ws
  };
  initWs($f7, token);
}
export default socket;
