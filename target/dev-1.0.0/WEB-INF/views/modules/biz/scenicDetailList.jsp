<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>景区详情管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">景区详情</a></li>
	</ul>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped  table-bordered  table-condensed">
		<thead><tr><th style="width:20px;">#</th><th style="width:60px;">类型</th><th>内容</th><shiro:hasPermission name="biz:scenicDetail:edit"><th  style="width:60px;">操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="scenicDetail" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td><b>${fns:getDictLabel(scenicDetail.type, 'scenic_info_type', '')}</b></td>
				<td><div>${scenicDetail.content}</div></td>
				<shiro:hasPermission name="biz:scenicDetail:edit"><td>
    				<a href="${ctx}/biz/scenicDetail/form?id=${scenicDetail.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
