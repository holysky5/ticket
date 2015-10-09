<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直通景区管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/direct/">直通景区列表</a></li>
		<li  class="active"><a href="${ctx}/biz/direct/form?id=${direct.id}">直通景区介绍</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="direct" action="${ctx}/biz/direct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">1</span> 直通景区是为方便同行订票，采用同行直接与景区订票的方式，无需经第三方订票的功能；</p>
  		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">2</span> 如果您与某景区另有协议，当月散客预定量较多，可自行与景区沟通申请开通该权限；</p>
 		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">3</span> 如果该景区尚未开通易往平台，请与客服联系，将景区负责人联系方式告知客服，由易往与景区沟通；</p>
		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">4</span> 易往平台直通景区功能只提供预定和预订单管理、查看、统计等功能，如对有关不明白的地方可咨询客服；</p>
 		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">5</span> 易往平台不涉及您与景区之间结算、价格体系等，平台不负责您与景区因为结算、价格体系等问题所产生各种争议；</p>
		<p style="border-bottom: 1px dotted #ddd;"><span class="badge badge-info">6</span> 易往平台目前暂定收取 <span class="label label-important">${fns:getConfig('order_opt_fee')} 元 / 票 </span> 通信费。（通信费会按设备提供商的价格不定时浮动）</p>
		
		
	</form:form>
</body>
</html>
