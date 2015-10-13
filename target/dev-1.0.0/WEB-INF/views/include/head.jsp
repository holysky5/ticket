<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" /><meta http-equiv="Pragma" content="no-cache" /><meta http-equiv="Expires" content="0" />
<meta name="author" content="友鹏软件"/><meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value:'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" media='screen,print'/>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/dev.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/dev.js" type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${user.userType == '1'}">
			 $.ajax({
	             type: "post",
	             url: "${ctx}/biz/orderInfo/queryTodoOrders",
	             dataType: "json",
	             data:'',
	             success: function(data){
	            	if(data[0] > 0 || data[1] > 0 ){
			            top.$.jBox.messager( '您有待处理订单：<font color=red> '+data[0] +' </font> 个；<br>待上架门票产品：<font color=red> '+data[1] +' </font> 个；','任务提示',0, { width: 200 });
					}
	            	$("#todo_span_1").text(data[0]);
	            	$("#todo_span_2").text(data[1]);
			     }
	         });
			 </c:if>
		});
/script>
