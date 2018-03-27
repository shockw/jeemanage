/*!
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 		  __													   *
 *  __   (__`\             000000000     00000000     000000000    *
 * (__`\   \\`\            00      00    00     00    00      00   *
 *  `\\`\   \\ \           00      00    00      00   00      00   *
 *    `\\`\  \\ \          00     00     00       00  00     00    *
 *      `\\`\#\\ \#        00000000      00       00  00000000     *
 *        \_ ##\_ |##      00     00     00       00  00           *
 *        (___)(___)##     00      00    00      00   00           *
 *         (0)  (0)`\##    00      00    00     00    00           *
 *          |~   ~ , \##   000000000     00000000     00           *
 *          |      |  \##                                          *
 *          |     /\   \##         __..---'''''-.._.._             *
 *          |     | \   `\##  _.--'                _  `.           *
 *          Y     |  \    `##'                     \`\  \          *
 *         /      |   \                             | `\ \         *
 *        /_...___|    \                            |   `\\        *
 *       /        `.    |                          /      ##       *
 *      |          |    |                         /      ####      *
 *      |          |    |                        /       ####      *
 *      | () ()    |     \     |          |  _.-'         ##       *
 *      `.        .'      `._. |______..| |-'|                     *
 *        `------'           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                           | | | |    | || |                     *
 *                     _____ | | | |____| || |                     *
 *                    /     `` |-`/     ` |` |                     *
 *                    \________\__\_______\__\                     *
 *                     """""""""   """""""'"""                     *
 *_________________________________________________________________*
 *                                                                 *
 *         We don't produce code, we're just code porters.         *
 *_________________________________________________________________*           
 *******************************************************************
 * 通用公共方法
 * @author BDP	
 * @date 2017年10月17日 下午9:43:05
 * @version 1.0.0
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select").select2();
	}catch(e){
		// blank
	}
});

// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			if (typeof href == 'function') {
				href();
			}else{
				resetTip(); //loading();
				location = href;
			}
		}
	},{buttonsFocus:1, top: '35%', closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}

// 提示输入对话框
function promptx(title, lable, href, closed){
	top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
	    if (f.txt == '') {
	        top.$.jBox.tip("请输入" + lable + "。", 'error');
	        return false;
	    }
		if (typeof href == 'function') {
			href();
		}else{
			resetTip(); //loading();
			location = href + encodeURIComponent(f.txt);
		}
	},closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

/**
 * 将一个平平无奇的表格转化为卡片的展示形式
 * @param tableId：需要转化的table的ID
 * @param colNum：转化后每行显示的个数
 */
function rebuildTable(tableId, colNum) {
    var thArray = new Array();
    $("#" + tableId + " thead tr th").each(function () {
        thArray.push($(this).html());
    });
    var html = "<div class='row-fluid'>";
    var colIndex = 0;
    if (!colNum) {
        colNum = 4;
    }
    var spanSize = ( 12 / colNum) | 0 || 0;
    $("#" + tableId + " tbody tr").each(function () {
        var valueArray = new Array();
        $(this).find("td").each(function () {
            valueArray.push($(this).html());
        });
        if (colIndex % colNum == 0) {
            html += "<div class='container-fluid' style='margin: 30px 0 30px 0;'>";
        }
        html += "<div ";
        if (colNum == 5) {
            html += "style='width:18%;'";
        }
        html += " class='span" + spanSize + "'>";
        html += "<div style='width: 100%; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);text-align: right;'>";
        html += "<div style='background-color: #f5f5f5;color: white;padding:10px 10px 10px 10px;font-size: 20px;font-weight: bold;'>";
        html += valueArray[0];
        html += " </div>";
        for (var index = 1; index < thArray.length; index++) {
            html += "<div style='padding:2px 10px 4px 10px; height: 24px;'><p style='font-size: 13px; margin-top: 6px;'><span class='label label-success' style='float: left;'>";
            html += thArray[index];
            html += "</span>";
            if ($.trim(valueArray[index]) == "") {
                html += "-";
            } else {
                html += valueArray[index];
            }
            html += "</p></div><li style=' width: 100%; height: 1px; margin: 9px 1px; margin: 0px 0 -5px; overflow: hidden;background-color: #e5e5e5; border-bottom: 1px solid #fff;'></li>";
        }
        html += "</div></div>";
        colIndex++;
        if (colIndex % colNum == 0) {
            html += "</div>";
        }
    });
    html += "</div>";
    $("#" + tableId).replaceWith(html);
}
