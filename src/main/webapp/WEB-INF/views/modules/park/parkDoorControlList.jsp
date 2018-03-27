<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门禁日志管理</title>
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
		<li class="active"><a href="${ctx}/park/parkDoorControl/">门禁日志列表</a></li>
		<shiro:hasPermission name="park:parkDoorControl:edit"><li><a href="${ctx}/park/parkDoorControl/form">门禁日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parkDoorControl" action="${ctx}/park/parkDoorControl/" method="post" class="breadcrumb form-search">
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
				<th>门卡编号</th>
				<th>车牌号</th>
				<th>楼层</th>
				<th>停车架</th>
				<th>停车位</th>
				<th>停车/取车</th>
				<th>操作时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkDoorControl">
			<tr>
				<td><a href="${ctx}/park/parkDoorControl/form?id=${parkDoorControl.id}">
					${parkDoorControl.doorControlId}
				</a></td>
				<td>
					${parkDoorControl.number}
				</td>
				<td>
					${fns:getDictLabel(parkDoorControl.floor, 'park_floor', '')}
				</td>
				<td>
					${fns:getDictLabel(parkDoorControl.jiffyStand, 'park_jiffy_stand', '')}
				</td>
				<td>
					${parkDoorControl.space}
				</td>
				<td>
					${fns:getDictLabel(parkDoorControl.isuse, 'park_isuse', '')}
				</td>
				<td>
					<fmt:formatDate value="${parkDoorControl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>