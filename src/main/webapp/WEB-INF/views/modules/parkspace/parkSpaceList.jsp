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
		<li class="active"><a href="${ctx}/park/parkSpaceManager/">停车位列表</a></li>
		<shiro:hasPermission name="parkspace:parkSpace:edit"><li><a href="${ctx}/park/parkSpaceManager/form">停车位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parkSpace" action="${ctx}/park/parkSpaceManager/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>车架：</label><form:select id="jiffyStand" path="jiffyStand" class="input-small"><form:option value="" label=""/><form:options items="${fns:getDictList('park_jiffy_stand')}" itemValue="value" itemLabel="label" htmlEscape="false"/></form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>车架</th>
			<th>楼层</th>
			<th>车位</th>
				<shiro:hasPermission name="parkspace:parkSpace:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkSpace">
			<tr>
			<td>
					${fns:getDictLabel(parkSpace.jiffyStand, 'park_jiffy_stand', '')}
				</td>
				<td>
					${fns:getDictLabel(parkSpace.floor, 'park_floor', '')}
				</td>
				<td><a href="${ctx}/parkspace/parkSpace/form?id=${parkSpace.id}">
					${parkSpace.space}
				</a></td>
				
				
				<shiro:hasPermission name="parkspace:parkSpace:edit"><td>
    				<a href="${ctx}/park/parkSpaceManager/form?id=${parkSpace.id}">修改</a>
					<a href="${ctx}/park/parkSpaceManager/delete?id=${parkSpace.id}" onclick="return confirmx('确认要删除该停车位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>