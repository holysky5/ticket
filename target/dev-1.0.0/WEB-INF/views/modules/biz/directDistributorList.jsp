<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直通分销商管理</title>
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
		
		function openView(name,id){
			var url = "${ctx}/biz/supplier/view?id="+id;
			top.$.jBox.open("iframe:"+url, name+"景区详情", 950, 500, {top:60, buttons: { } });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/direct/list/distributor">直通分销商列表</a></li>
		<shiro:hasPermission name="biz:direct:edit"><li><a href="${ctx}/biz/direct/supplier/form">分销商权限开通</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="direct" action="${ctx}/biz/direct/list/distributor" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>分销商 ：</label><form:input path="distributor.name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>分销商名称</th><th>所在公司</th><th>联系电话</th><th style="width:100px;">景区账户余额</th><th style="width:100px;">状态</th><th style="width:200px;">操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="direct" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td>${direct.distributor.name}</td>
				<td>${direct.distributor.company}</td>
				<td>${direct.distributor.phone}</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${direct.balance}</fmt:formatNumber></td>
				
				<td>
					<c:if test="${direct.status==0}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${direct.status==1}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
				</td>
				<td>
					<a href="${ctx}/biz/direct/supplier/form?id=${direct.id}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
		  			<a href="${ctx}/biz/directBalanceHis?direct.id=${direct.id}" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 余额</a>
		  			<c:if test="${direct.balance == 0 && direct.status==1}">
		  			<a href="${ctx}/biz/direct/delete?id=${direct.id}" onclick="return confirmx('确认要删除该分销商直通权限吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-trash icon-white"></i> 删除</a>
		  			</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
</body>
</html>
