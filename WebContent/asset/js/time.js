/**
 * JavaScript Date 对象处理，转换成时间戳
 */
function setTime(selfvalue){
	var stopDate = new Date();   //获取当前时间
	var stopTimeStamp = Date.parse(stopDate);  //当前时间的时间戳（默认：以毫秒为单位）
	stopTimeStamp = stopTimeStamp / 1000;   //切换当前时间戳为以秒为单位
	
	var startTimeStamp = stopTimeStamp - 86400*selfvalue; //86400秒=1天
	var startDate = new Date();
	startDate.setTime(startTimeStamp * 1000);
	
	//格式或输出
		Date.prototype.format = function(format) {
	       var date = {
	              "M+": this.getMonth() + 1,
	              "d+": this.getDate(),
	              "h+": this.getHours(),
	              "m+": this.getMinutes(),
	              "s+": this.getSeconds(),
	              "q+": Math.floor((this.getMonth() + 3) / 3),
	              "S+": this.getMilliseconds()
	       };
	       if (/(y+)/i.test(format)) {
	              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	       }
	       for (var k in date) {
	              if (new RegExp("(" + k + ")").test(format)) {
	                     format = format.replace(RegExp.$1, RegExp.$1.length == 1
	                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
	              }
	       }
	       return format;
	};
	
	console.log(startDate.format('yyyy-MM-dd h:m:s'));
	console.log(stopDate.format('yyyy-MM-dd h:m:s'));
			$("#startTime").val(startDate.format('yyyy-MM-dd h:m:s'));
			$("#stopTime").val(stopDate.format('yyyy-MM-dd h:m:s'));
}