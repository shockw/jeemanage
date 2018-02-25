<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>停车位管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

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
		<li class="active"><a href="${ctx}/park/parkSpace/">空闲车位列表</a></li>
		<shiro:hasPermission name="park:parkSpace:edit">
			<li><a href="${ctx}/park/parkSpace/form">停车位添加</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>车架</th>
				<th>空闲车位数量</th>
				<th>空闲车位名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dtoList}" var="idleParkSpace">
				<tr>
					<td>${fns:getDictLabel(idleParkSpace.floor, 'park_floor', '')}
					</td>
					<td>${fns:getDictLabel (idleParkSpace.jiffyStand, 'park_jiffy_stand', '')}</td>
					<td>${idleParkSpace.idleCount}</td>
					<td>${idleParkSpace.idleSpaces}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>