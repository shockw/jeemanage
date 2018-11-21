<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>停车费用管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	 $(function(){  
	        $(".imgcode").click(function(){  
	            var _this = $(this);//将当前的pimg元素作为_this传入函数  
	            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);  
	        });  
	    });  

	    function imgShow(outerdiv, innerdiv, bigimg, _this){  
	        var src = _this.attr("src");//获取当前点击的pimg元素中的src属性  
	        $(bigimg).attr("src", src);//设置#bigimg元素的src属性  
	      
	            /*获取当前点击图片的真实大小，并显示弹出层及大图*/  
	        $("<img/>").attr("src", src).load(function(){  
	            var windowW = $(window).width();//获取当前窗口宽度  
	            var windowH = $(window).height();//获取当前窗口高度  
	            var realWidth = this.width;//获取图片真实宽度  
	            var realHeight = this.height;//获取图片真实高度  
	            var imgWidth, imgHeight;  
	            var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放  
	              
	            if(realHeight>windowH*scale) {//判断图片高度  
	                imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放  
	                imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度  
	                if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度  
	                    imgWidth = windowW*scale;//再对宽度进行缩放  
	                }  
	            } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度  
	                imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放  
	                            imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度  
	            } else {//如果图片真实高度和宽度都符合要求，高宽不变  
	                imgWidth = realWidth;  
	                imgHeight = realHeight;  
	            }  
	                    $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放  
	              
	            var w = (windowW-imgWidth)/2;//计算图片与窗口左边距  
	            var h = (windowH-imgHeight)/2;//计算图片与窗口上边距  
	            $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性  
	            $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg  
	        });  
	          
	        $(outerdiv).click(function(){//再次点击淡出消失弹出层  
	            $(this).fadeOut("fast");  
	        });  
	    }
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/park/parkPay/">停车费用列表</a></li>
		<%-- 		<shiro:hasPermission name="park:parkPay:edit"><li><a href="${ctx}/park/parkPay/form">停车费用添加</a></li></shiro:hasPermission>
 --%>
	</ul>
	<form:form id="searchForm" modelAttribute="parkPay"
		action="${ctx}/park/parkPay/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<label>楼层：</label>
		<form:select id="floor" path="floor" class="input-small">
			<form:option value="" label="" />
			<form:options items="${fns:getDictList('park_floor')}"
				itemValue="value" itemLabel="label" htmlEscape="false" />
		</form:select>
		<label>车架：</label>
		<form:select id="jiffyStand" path="jiffyStand" class="input-small">
			<form:option value="" label="" />
			<form:options items="${fns:getDictList('park_jiffy_stand')}"
				itemValue="value" itemLabel="label" htmlEscape="false" />
		</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit"
			value="查询" />&nbsp;&nbsp;
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>停车架</th>
				<th>车位</th>
				<th>车牌号</th>
				<th>停车开始时间</th>
				<th>停车结束时间</th>
				<th>停车费</th>
				<th>付款码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="parkPay">
				<tr>
					<td>${fns:getDictLabel(parkPay.floor, 'park_floor', '')}</td>
					<td>${fns:getDictLabel(parkPay.jiffyStand, 'park_jiffy_stand', '')}</td>
					<td>${parkPay.space}</td>
					<td>${parkPay.number}</td>
					<td><fmt:formatDate value="${parkPay.startDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${parkPay.endDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${parkPay.cost}</td>
					<td><img class="imgcode" height="100" width="100"src="${ctx}/../userfiles/1/files/${parkPay.cost}.png"></td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	<div id="outerdiv"
						style="position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.7); z-index: 2; width: 100%; height: 100%; display: none;">
						<div id="innerdiv" style="position: absolute;">
							<img id="bigimg" style="border: 5px solid #fff;" src="" />
						</div>
					</div>
	<div class="pagination">${page}</div>
</body>
</html>