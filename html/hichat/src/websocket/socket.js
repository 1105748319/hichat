var socket = function ($f7, token) {
 // var ips = returnCitySN["cip"];
  var url = "ws://60.165.143.216:8079/";
  var ws = null;
  function initWs($f7, token) {
    if (!!window['WebSocket']) {

      //ws = new WebSocket("ws://" + url?token + "/webSocketServer");
      ws = new WebSocket(url+token);
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
