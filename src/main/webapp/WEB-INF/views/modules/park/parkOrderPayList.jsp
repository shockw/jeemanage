<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>停车费用管理</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/jquery/jquery.qrcode.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	setTimeout(function(){location.reload()},1000); 
	$(document).ready(
			function() {
				var personId = $("#personId").val();
				jQuery('#qrcode').qrcode(
						"http://192.168.43.24:8080/jeemanage/a/park/api/pay?personId="
								+ personId);
			});
	$(function() {
		$(".imgcode").click(function() {
			var _this = $(this);//将当前的pimg元素作为_this传入函数  
			imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
		});
	});

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
		<li class="active"><a href="${ctx}/park/parkOrder/pay">待缴费列表</a></li>
	</ul>
	<div>
		<sys:message content="${message}" />
		用户id:<input id="personId" name="personId"
			value="${parkOrder.personId}" />
		<table id="contentTable"
			class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>存车照片</th>
					<th>取车照片</th>
					<th>停车开始时间</th>
					<th>停车结束时间</th>
					<th>停车时长</th>
					<th>停车费</th>
					<th>付款码</th>
					<th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty parkOrder.id }">
					<tr>
						<td><img src="data:image/png;base64,${parkOrder.inPic}"
							height="100" width="100" /></td>
						<td><img src="data:image/png;base64,${parkOrder.outPic}"
							height="10	0" width="100" /></td>
						<td><fmt:formatDate value="${parkOrder.startTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatDate value="${parkOrder.endTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>

						<td><c:set var="interval"
								value="${parkOrder.endTime.time - parkOrder.startTime.time}" />
							<fmt:formatNumber value="${interval/1000/3600}" pattern="#0" />小时</td>
						<td>${parkOrder.cost}</td>
						<td><div id="qrcode"></div></td>
						<td>${fns:getDictLabel(parkOrder.status, 'park_order_status', '')}</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div id="outerdiv"
			style="position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.7); z-index: 2; width: 100%; height: 100%; display: none;">
			<div id="innerdiv" style="position: absolute;">
				<img id="bigimg" style="border: 5px solid #fff;" src="" />
			</div>
		</div>
	</div>
</body>
</html>