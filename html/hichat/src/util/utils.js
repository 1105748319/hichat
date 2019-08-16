import browserMD5File from 'browser-md5-file'
import MD5File from './spark-md5.min'
export function isEmpty(value){
  if (value == "" || value == null || value == undefined||value=={}||value=="undefined") {
    return true;
  }
  return false;
}
export function formatDate (date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  };
  for (let k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      let str = o[k] + '';
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
    }
  }
  return fmt;
};

function padLeftZero (str) {
  return ('00' + str).substr(str.length);
};


export function dataURLtoBlob(dataurl) {
  var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new Blob([u8arr], {type: mime});
}

export  function getBinaryData(file, callback) {
  var reader = new FileReader();
  reader.readAsArrayBuffer(file);
  reader.onload = function (e) {
    callback(reader.result);
  };
}
export  function getFileMd5(file, callback) {


    var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
      chunkSize = 2097152, // read in chunks of 2MB
      chunks = Math.ceil(file.size / chunkSize),
      currentChunk = 0,
      spark = new SparkMD5.ArrayBuffer(),
      frOnload = function(e){
        //  log.innerHTML+="\nread chunk number "+parseInt(currentChunk+1)+" of "+chunks;
        spark.append(e.target.result); // append array buffer
        currentChunk++;
        if (currentChunk < chunks)
          loadNext();
        else
          callback(spark.end());
          //log.innerHTML+="\n加载结束 :\n\n计算后的文件md5:\n"+spark.end()+"\n\n现在你可以选择另外一个文件!\n";
      },
      frOnerror = function () {
        //log.innerHTML+="\糟糕，好像哪里错了.";
      };
    function loadNext() {
      var fileReader = new FileReader();
      fileReader.onload = frOnload;
      fileReader.onerror = frOnerror;
      var start = currentChunk * chunkSize,
        end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
      fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    };

    loadNext();


  /*browserMD5File(file, function (err, md5) {
    callback(md5);
  });*/
}


export function compress(file, quality, callback) {
  if (!window.FileReader || !window.Blob) {
    return errorHandler('您的浏览器不支持图片压缩')();
  }

  var reader = new FileReader();
  var mimeType = file.type || 'image/jpeg';

  reader.onload = createImage;
  reader.onerror = errorHandler('图片读取失败！');
  reader.readAsDataURL(file);

  function createImage() {
    var dataURL = this.result;
    var image = new Image();
    image.onload = compressImage;
    image.onerror = errorHandler('图片加载失败');
    image.src = dataURL;
  }

  function compressImage() {
    var canvas = document.createElement('canvas');
    var ctx;
    var dataURI;
    var result;

    canvas.width = this.naturalWidth;
    canvas.height = this.naturalHeight;
    ctx = canvas.getContext('2d');
    ctx.drawImage(this, 0, 0);
    dataURI = canvas.toDataURL(mimeType, quality);
    result = dataURIToBlob(dataURI);

    callback(null, result);
  }

  function dataURIToBlob(dataURI) {
    var type = dataURI.match(/data:([^;]+)/)[1];
    var base64 = dataURI.replace(/^[^,]+,/, '');
    var byteString = atob(base64);

    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ia], {type: type});
  }

  function errorHandler(message) {
    return function () {
      var error = new Error('Compression Error:', message);
      callback(error, null);
    };
  }
}

