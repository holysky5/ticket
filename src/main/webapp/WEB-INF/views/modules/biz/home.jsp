<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统首页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").click(function(){$("#searchForm").submit()});
		});
	
		function openNotice(title,url){
			top.$.jBox.open("iframe:"+url, "公告通知", 800, 400, { buttons: { } });
		}
		
		function openRecharge(){
			var url = "${ctx}/biz/balanceHis/recharge";
			top.$.jBox.open("iframe:"+url, "在线充值", 600, 420, {iframeScrolling: 'no', buttons: {} });
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/biz/home">系统首页</a></li>
	</ul>
	<div class="row" style="margin-left:10px;">
 		<div class="row">
        	<div class="span6 home-div" >
        	<div class="header">
        	账户信息
        	</div>
	        <div  style="min-height:150px;">
	       	<table style="width: 100%;">
	              <tbody>
	              	<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>您好，${user.name}</td>
					</tr>
					<c:if test="${not empty balance && user.userType == '3'}">
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>账户余额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${balance}</fmt:formatNumber> &nbsp;&nbsp;&nbsp;
						<a href="javascript:openRecharge()" class="btn btn-mini btn-info">在线充值</a>
						</td>
					</tr>
					</c:if>
					<c:if test="${not empty balance && user.userType == '4'}">
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>账户余额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${balance}</fmt:formatNumber> &nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</c:if>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>上次登录IP：${user.loginIp}</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>上次登录时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></td>
					</tr>
	              </tbody>
	            </table>
	        </div>
        
        	</div>
        	<c:if test="${user.userType != '6'}">
          	<div class="span6 home-div" >
        	<div class="header">
        	最新公告
        	</div>
        	<div  style="min-height:150px;">
		        <table style="width: 100%;">
	              
	              <tbody>
	               <c:forEach items="${notices}" var="notice" varStatus="status">
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td >${status.index+1 }.[<fmt:formatDate value="${notice.createDate}" pattern="yyyy-MM-dd" />]
						<a href="javascript:openNotice('${notice.title}','${ctx}/biz/notice/view/${notice.idKey}')" >${notice.title}</a></td>
					</tr>
					</c:forEach>
	              </tbody>
	            </table>
            </div>
        
        	</div>
        	</c:if>
        	<c:if test="${user.userType == '6'}">
          	<div class="span6 home-div" >
        	<div class="header">
        	订单统计
        	</div>
        	<div  style="min-height:150px;">
		        <table style="width: 100%;">
	        		<tbody>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>1 今日订单总数：${dataList[0]} 单</td>
					</tr>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>2 昨日订单总数：${dataList[1]} 单</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>3 当月订单总数：${dataList[2]} 单</td>
					</tr>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>4 当月订单排行：
						 <c:forEach var="item" items="${dataMap}"> 
						  ${fns:getDictLabel(item.key, 'tuan_type', '')}（${item.value}） &nbsp;
						 </c:forEach>
						</td>
					</tr>
	        		</tbody>
	        	</table>
            </div>
        
        	</div>
        	</c:if>
        </div>
     </div>
     <c:if test="${user.userType == '1'}">
	<div class="row" style="margin-left:10px;margin-top:20px;">
 		<div class="row" >
        	<div class="span6 home-div" >
        	<div class="header">
        	今日统计
        	</div>
	        <div  style="min-height:150px;">
	        	<table style="width: 100%;">
	        		<tbody>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>1 今日客单总数：${dayList[0]} 单</td>
					</tr>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>2 今日交易总金额：<fmt:formatNumber type="currency" pattern="#,##0.00#">${dayList[1]}</fmt:formatNumber> 元</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>3  待处理订单：<a href="${ctx}/biz/orderInfo/list/todo"><span id="todo_span_1">0</span> </a>单</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>4  待审核门票：<a href="${ctx}/biz/supplier/list/product?stopTime=1"><span id="todo_span_2">0</span> </a>个</td>
					</tr>
	        		</tbody>
	        	</table>
	         
	        </div>
        
        	</div>
          	<div class="span6 home-div" >
        	<div class="header">
        	当月统计
        	</div>
	        
         	<div  style="min-height:150px;">
	        	
	        	<table style="width: 100%;">
	        		<tbody>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>1 当月客单总数：${monthList[0]} 单</td>
					</tr>
	        		<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>2 当月交易总金额 ：<fmt:formatNumber type="currency" pattern="#,##0.00#">${monthList[1]}</fmt:formatNumber> 元</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>3 分销商未消费总金额 ：<fmt:formatNumber type="currency" pattern="#,##0.00#">${notUseTotalAmount}</fmt:formatNumber> 元</td>
					</tr>
					<tr style="line-height:28px;border-bottom: 1px dotted #ddd;border-top: 1px dotted #ddd;">
						<td>4  应付供应商总金额 ：<fmt:formatNumber type="currency" pattern="#,##0.00#">${toSuppliereTotalAmount}</fmt:formatNumber> 元</td>
					</tr>
	        		</tbody>
	        	</table>
	        	
	        </div>
        	</div>
        </div>
        
	</div>
	</c:if>
	
	 <c:if test="${user.userType == '4'}">
		 <div class="row" style="margin-left:10px;margin-top:20px;">
	 		<div class="row" >
	        	<div class="span6 home-div" >
		        	<div class="header">
		        	订单查询
		        	</div>
			        <div  style="min-height:150px;">
			        <form id="searchForm"  action="${ctx}/biz/orderInfo/list/all" method="post" class="breadcrumb form-search">
					<input type="hidden" name="query" value="all" >
					<table class="table-condensed">
						<tr>
							<td>
							<label>取&nbsp;&nbsp;票&nbsp;&nbsp;人 ：</label><input name="orderInfo.customerName" maxlength="50" class="input-medium" style="width:100px;"/>
							<label>取票手机 ：</label><input name="orderInfo.customerMobile"  maxlength="50" class="input-medium" style="width:100px;"/>
							
							</td>
						</tr>
						
						<tr>
							<td>
							<label>旅游日期 ：</label>
							<input name="orderInfo.useDate" maxlength="50" readonly="readonly" class="Wdate"  style="width:100px;" onclick="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',isShowClear:false});"/> 
							&nbsp;&nbsp;
							<a href="#" id="btnSubmit"  class="btn btn btn-primary"><i class="icon-search icon-white"></i> 订单快速查询</a>
    				
							
							</td>
						</tr>
						
						
					</table>
					</form>
			        </div>
		       	</div>
		    </div>
		 </div>
	 </c:if>
</body>
</html>
