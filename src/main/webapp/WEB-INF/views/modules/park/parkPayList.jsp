<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>停车费用管理</title>
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
		<li class="active"><a href="${ctx}/park/parkPay/">停车费用列表</a></li>
<%-- 		<shiro:hasPermission name="park:parkPay:edit"><li><a href="${ctx}/park/parkPay/form">停车费用添加</a></li></shiro:hasPermission>
 --%>	</ul>
	<form:form id="searchForm" modelAttribute="parkPay" action="${ctx}/park/parkPay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>楼层：</label><form:select id="floor" path="floor" class="input-small"><form:option value="" label=""/><form:options items="${fns:getDictList('park_floor')}" itemValue="value" itemLabel="label" htmlEscape="false"/></form:select>
		<label>车架：</label><form:select id="jiffyStand" path="jiffyStand" class="input-small"><form:option value="" label=""/><form:options items="${fns:getDictList('park_jiffy_stand')}" itemValue="value" itemLabel="label" htmlEscape="false"/></form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>停车架</th>
				<th>车位</th>
				<th>车牌号</th>
				<th>停车开始时间</th>
				<th>停车结束时间</th>
				<th>停车费用</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkPay">
			<tr>
				<td>${fns:getDictLabel(parkPay.floor, 'park_floor', '')}</td>
				<td>${fns:getDictLabel(parkPay.jiffyStand, 'park_jiffy_stand', '')}</td>
				<td>${parkPay.space}</td>
				<td>${parkPay.number}</td>
				<td><fmt:formatDate value="${parkPay.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${parkPay.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${parkPay.cost}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>