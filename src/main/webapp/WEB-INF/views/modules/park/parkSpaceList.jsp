<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>停车位管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/common/bdp.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctxStatic}/common/bdp.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
            rebuildTable("contentTable", 4);
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
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="table-layout:fixed;">
		<thead>
			<tr>
				<th>楼层</th>
				<th>车架</th>
				<th>总数</th>
				<th>空闲数</th>
				<th>空闲车位</th>
				<th>在用车位</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dtoList}" var="idleParkSpace">
				<tr>
					<td><font size="5" color="blue">${fns:getDictLabel(idleParkSpace.floor, 'park_floor', '')}</font>
					</td>
					<td>${fns:getDictLabel (idleParkSpace.jiffyStand, 'park_jiffy_stand', '')}</td>
					<td>${idleParkSpace.count}</td>
					<td>${idleParkSpace.idleCount}</td>
					 <td>
					 <c:forEach items="${idleParkSpace.idleSpaces}" var="idleSpace">  
                                              <span><a href="${ctx}/park/parkSpace/form?id=${idleSpace.id}" style="color:red">${idleSpace.space}</a></span>  
                                        </c:forEach> </td> 
					<td ><c:forEach items="${idleParkSpace.inuseSpaces}" var="inuseSpace">  
                                              <span ><a href="${ctx}/park/parkSpace/form?id=${inuseSpace.id}" >${inuseSpace.space}</a></span>  
                                        </c:forEach>  </td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>