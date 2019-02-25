<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>停车订单管理</title>
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
		<li class="active"><a href="${ctx}/park/parkOrder/">停车订单列表</a></li>
		<shiro:hasPermission name="park:parkOrder:edit"><li><a href="${ctx}/park/parkOrder/form">停车订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="parkOrder" action="${ctx}/park/parkOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
			<li><label>用户id：</label>
				<form:input path="personId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkOrder.beginStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkOrder.endStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>用户id</th>
				<th>用户照片</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>订单状态</th>
				<shiro:hasPermission name="park:parkOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="parkOrder">
			<tr>
				<td><a href="${ctx}/park/parkOrder/form?id=${parkOrder.id}">
					${fns:getDictLabel(parkOrder.floor, 'park_floor', '')}
				</a></td>
				<td>
					${fns:getDictLabel(parkOrder.jiffyStand, 'park_jiffy_stand', '')}
				</td>
				<td>
					${parkOrder.personId}
				</td>
				<td><img class="imgcode" height="50" width="50"
							src="${parkOrder.path}">
				</td>
				<td>
					<fmt:formatDate value="${parkOrder.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${parkOrder.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(parkOrder.status, 'park_order_status', '')}
				</td>
				<shiro:hasPermission name="park:parkOrder:edit"><td>
    				<a href="${ctx}/park/parkOrder/form?id=${parkOrder.id}">修改</a>
					<a href="${ctx}/park/parkOrder/delete?id=${parkOrder.id}" onclick="return confirmx('确认要删除该停车订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>