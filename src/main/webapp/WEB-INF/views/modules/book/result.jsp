<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html>
<head>
<title>易往票务团购自助预约服务平台</title>
<link href="${ctxStatic}/common/book_index.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/cityselect/js/jquery.cityselect.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	


	<script type="text/javascript">
		$(document).ready(function() {
			if('${message}' == ''){
				//window.location.href="${ctx}";
			}
			
			$("#submit").click(function(){window.location.href="${ctx}/"});
		});
		
		
	</script>
	<style type="text/css">
   
    
	</style>
</head>
<body id="login">
	
	<div id="mainbox">
		<div class="head">
			<div class="logo">
				<img src="${ctxStatic}/images/logo.png" height="50" />
			</div>
			<div class="des" style="padding-top:12px;">
				易往票务团购自助预约服务平台<br/>
			</div>
			<div class="text">
			
			服务热线：${fns:getConfig('comapny.phone')}
			</div>
		</div>
		
		<div class="content .index_box .box_main" style="min-height:450px;">
			<div style="font-size:16px;padding-top:100px;padding-left:50px;padding-right:50px;color:green;line-height:30px;">
				您的团购预约已经提交成功，客服会马上处理您的团购订单，请保持联系方式畅通，稍后（2小时内）会收到确认短信，请您耐心等待。如有疑问请致电${fns:getConfig('comapny.phone')}。:-)
      			
      			<br></br>
      			<input id="submit" class="btn_ok" type="button" value="返回首页" >
      			
			</div>
			
			
		</div>
		<div class="copyright">
			${fns:getConfig('company.name')}  &copy; 2014-${fns:getConfig('copyrightYear')}  ${fns:getConfig('icp')}
		</div>
	</div>
	

</body>
</html>
