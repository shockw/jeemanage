<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程数据可视化管理</title>
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
		<li class="active"><a href="${ctx}/flow/flowData/">流程数据可视化列表</a></li>
		<shiro:hasPermission name="flow:flowData:edit"><li><a href="${ctx}/flow/flowData/form">流程数据可视化添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flowData" action="${ctx}/flow/flowData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="beginApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${flowData.beginApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${flowData.endApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>流水号</th>
				<th>摘要</th>
				<th>种类</th>
				<th>时间</th>
				<th>状态</th>
				<shiro:hasPermission name="flow:flowData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flowData">
			<tr>
				<td><a href="${ctx}/flow/flowData/form?id=${flowData.id}">
					${flowData.flowId}
				</a></td>
				<td>
					${flowData.summary}
				</td>
				<td>
					${flowData.type}
				</td>
				<td>
					<fmt:formatDate value="${flowData.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${flowData.status}
				</td>
				<shiro:hasPermission name="flow:flowData:edit"><td>
    				<a href="${ctx}/flow/flowData/form?id=${flowData.id}">修改</a>
					<a href="${ctx}/flow/flowData/delete?id=${flowData.id}" onclick="return confirmx('确认要删除该流程数据可视化吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>