<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>停车位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/park/parkSpace/">停车位列表</a></li>
		<shiro:hasPermission name="park:parkSpace:edit"><li><a href="${ctx}/park/parkSpace/form">停车位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parkSpace" action="${ctx}/park/parkSpace/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>车架</th>
				<th>停车位</th>
				<th>状态</th>
				<shiro:hasPermission name="park:parkSpace:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkSpace">
			<tr>
				<td>${fns:getDictLabel(parkSpace.floor, 'park_floor', '')}
				</td>
				<td>${fns:getDictLabel (parkSpace.jiffyStand, 'park_jiffy_stand', '')}</td>
				<td>${parkSpace.space}</td>
				<td>
					${fns:getDictLabel (parkSpace.isuse, 'park_isuse', '')}
				</td>
				<shiro:hasPermission name="park:parkSpace:edit"><td>
    				<a href="${ctx}/park/parkSpace/form?id=${parkSpace.id}">修改</a>
					<a href="${ctx}/park/parkSpace/delete?id=${parkSpace.id}" onclick="return confirmx('确认要删除该停车位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>