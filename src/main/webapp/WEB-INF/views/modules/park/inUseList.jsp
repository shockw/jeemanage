<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>停车费用结算</title>
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
		<li class="active"><a href="${ctx}/park/parkSpace/pay">在用车位列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="parkSpace" action="${ctx}/park/parkSpace/pay" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>车牌号：</label>
				<form:input path="number" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>楼层：</label>
				<form:select path="floor" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('park_floor')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>停车架：</label>
				<form:select path="jiffyStand" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('park_jiffy_stand')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>停车架</th>
				<th>车位</th>
				<th>车牌号</th>
				<th>停车时间</th>
				<th>使用时间</th>
				<th>停车费用</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="inuseSpace">
			<tr><td>
					${fns:getDictLabel(inuseSpace.floor, 'park_floor', '')}
				</td>
				<td>
					${fns:getDictLabel(inuseSpace.jiffyStand, 'park_jiffy_stand', '')}
				</td>
				<td>
					${inuseSpace.space}
				</td>
				<td>
					${inuseSpace.number}
				</td>
				<td>
					<fmt:formatDate value="${inuseSpace.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				${inuseSpace.times}分钟
				</td>
				<td>
				${inuseSpace.pay}元
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>