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
		<li class="active"><a href="${ctx}/park/parkSpace/">停车费用</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>楼层</th>
				<th>停车架</th>
				<th>车位</th>
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