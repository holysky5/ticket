<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分销商查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				$(this).click(function(){
					var id = $(this).val(), title = $(this).attr("title");
					if (top.mainFrame.cmsMainFrame){
						top.mainFrame.cmsMainFrame.articleSelectAddOrDel(id, title);
					}else{
						top.mainFrame.articleSelectAddOrDel(id, title);
					}
				});
			});
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
	<div style="margin:10px;">
	<form:form id="searchForm" modelAttribute="distributor" action="${ctx}/biz/distributor/selectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>分销商名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th style="text-align:center;width:50px;">选择</th>
			<th>帐号</th>
			<th>名称</th>
			<th>公司</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${page.list}" var="distributor" varStatus="status">
			<tr>
				<td width="60" style="text-align:center;"><input type="radio" name="id" value="${distributor.id}" title="${fns:abbr(distributor.name,40)}" /></td>
				<td>${distributor.username}</td>
				<td>${distributor.name}</td>
				<td>${distributor.company}</td>
			</tr>
			</c:forEach>
			<c:if test="${empty page.list}">
				<tr><td colspan="100">没有相关信息，请输入分销商名称重试</td></tr>
			</c:if>
		</tbody>
	</table>
	
	</div>
</body>
</html>
