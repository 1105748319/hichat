var singleChat=function(type,message,fromUserId,toUserId,smallImg){
  this.msgType=type;
  this.message=message;
  this.fromUserId=fromUserId;
  this.toUserId=toUserId;
  this.type="single";
  this.smallImg=smallImg;
}
export default singleChat;
