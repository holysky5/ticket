<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门票详情管理</title>
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
		<li class="active"><a href="${ctx}/biz/product/list?supplierid=${supplierid}">门票列表</a></li>
		<shiro:hasPermission name="biz:product:edit"><li><a href="${ctx}/biz/product/form?supplierid=${supplierid}">门票添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/biz/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>门票名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>上下架 ：</label>
		<form:select path="auditFlag" style="width:100px;">
			<form:option value="">--请选择--</form:option>
			<form:option value="0">仓库待上架</form:option>
			<form:option value="1">已上架销售</form:option>
			<form:option value="2">上架未通过</form:option>
		</form:select>
		&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<div style="float:right;">
		<div class="alert alert-success" style="margin-left:20px;margin-right:20px;">
             	提示：门票上架通过之后不能删除，修改需重上架审核！
   		 </div>
    	</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="width:20px;">#</th><th>门票编号</th><th>门票名称</th><th>挂牌价</th><th>指导价</th><th>采购价</th><th>状态</th><th>上下架</th><th>发布时间</th><th>更新时间</th><shiro:hasPermission name="biz:product:edit"><th style="width:150px;">操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${product.no}</td>
				<td>
					${product.name}
				</td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.originalPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.recommendPrice}</fmt:formatNumber> </td>
				<td style="text-align:right;"><fmt:formatNumber type="currency" pattern="#,##0.00#">${product.purchasePrice}</fmt:formatNumber> </td>
			
				<td>
					
					<c:if test="${product.status==0}">
						<img src="${ctxStatic}/images/true.png" title="正常">
						
					</c:if>
					<c:if test="${product.status==1}">
						<img src="${ctxStatic}/images/false.png" title="停用">
					</c:if>
				</td>
				<td >
					<c:if test="${product.startAndStopTimeStatus==true}">
					<c:if test="${product.auditFlag==1}">
						已上架销售
					</c:if>
					<c:if test="${product.auditFlag==0}">
						<c:if test="${product.updateBy.userType == 1}">
						上架未通过
						</c:if>
						<c:if test="${product.updateBy.userType == 4}">
						仓库待上架
						</c:if>
					</c:if>
					</c:if>
					<c:if test="${product.startAndStopTimeStatus==false}">
					<span style="color:red;">已过期下架</span>
					</c:if>
				</td>
				<td><fmt:formatDate value="${product.createDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${product.updateDate}" type="both"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="biz:product:edit"><td>
    				<a href="${ctx}/biz/product/form?no=${product.no}" class="btn btn-mini btn-primary"><i class="icon-edit icon-white"></i> 修改</a>
    				<c:if test="${product.auditFlag==0}">
    					<a href="${ctx}/biz/product/delete?no=${product.no}" onclick="return confirmx('确认要删除该门票详情吗？', this.href)" class="btn btn-mini btn-warning"><i class="icon-trash icon-white"></i> 删除</a>
					</c:if>
					<c:if test="${product.auditFlag==1}">
					<a href="#" class="btn btn-mini disabled"><i class="icon-ok-circle"></i> 已审</a>
					</c:if>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<c:if test="${empty page.list}">
			<tr><td colspan="100">没有相关信息</td></tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
