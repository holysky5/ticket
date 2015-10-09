<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直通景区管理</title>
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
		<li class="active"><a href="${ctx}/biz/direct/">直通景区列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="direct" action="${ctx}/biz/direct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>景区名称 ：</label><form:input path="supplier.name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>景区名称</th><th style="width:100px;">景区账户余额</th><th style="width:100px;">状态</th><th style="width:250px;">操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="direct" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>
				<td><a href="javascript:openView('${direct.supplier.name}','${direct.supplier.id}')" >${direct.supplier.name}</a></td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${direct.balance}</fmt:formatNumber>
					
				</td>
				
				<td>
					<c:if test="${direct.status==0}">
						<img src="${ctxStatic}/images/true.png">
					</c:if>
					<c:if test="${direct.status==1}">
						<img src="${ctxStatic}/images/false.png">
					</c:if>
				</td>
				<td>
					<c:if test="${direct.status==0}">
		  			<a href="javascript:openView('${direct.supplier.name}','${direct.supplier.id}')" class="btn btn-mini btn-primary"><i class="icon-search icon-white"></i> 详情</a>
					<a href="${ctx}/biz/directBalanceHis/list/view?direct.id=${direct.id}" class="btn btn-mini btn-primary"><i class="icon-search  icon-white"></i> 账户明细</a>
				
					<a href="${ctx}/biz/direct/product?id=${direct.id}" class="btn btn-mini btn-primary"><i class="icon-circle-arrow-down icon-white"></i> 预订</a>
					</c:if>
					<c:if test="${direct.status==1}">
					已被景区暂停直通预订
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
