<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html>
<head>
<title>${fns:getConfig('productName')} 用户注册</title>
<link href="${ctxStatic}/common/login.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>



	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					username: {remote: "${ctx}/biz/distributor/register/checkUsername?oldUsername=" + encodeURIComponent('${distributor.username}')},
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名.",remote: "此帐号已被注册"},
					password: {required: "请填写密码."},
					confirmPassword: {equalTo: "输入与上面相同的密码"},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				}
			});
			
			$("#returnLogin").click(function(){
				window.location.href = '${ctx}/';
			});
			
			<c:if test="${not empty message}">
				alert('${message}')
			</c:if>
		});
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
		
	</script>
	<style type="text/css">
   
    
	</style>
</head>
<body id="login">
	
	<div id="mainbox">
		<div class="head">
			<div class="logo">
				<img src="${ctxStatic}/images/logo.png" height="50">
			</div>
			<div class="des">
				${fns:getConfig('productName')}<br>
				<span>${fns:getConfig('productNameEn')}</span>
			</div>
			<div class="text">
			<div>
			<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=${fns:getConfig('comapny.qq')}&amp;site=qq&amp;menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${fns:getConfig('comapny.qq')}:41" alt="点击这里给我发消息" title="在线客服"></a>
			<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=${fns:getConfig('company.qun.idkey')}"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="易往QQ群${fns:getConfig('comapny.qun')}" title="易往QQ群${fns:getConfig('comapny.qun')}"></a>			
			</div>
			服务热线：${fns:getConfig('comapny.phone')}
			</div>
		</div>
		<div class="content">
			<div class="form" id="sellerLogin">
				<form:form id="inputForm" modelAttribute="distributor" action="${ctx}/biz/distributor/register" method="post" class="form-horizontal" enctype="multipart/form-data">
					<form:hidden path="id"/>
					
					<div class="title">用户注册</div>
	
					<p>
						<label  class="label-title"><span class="asterisk">*</span>用户名：</label>
						<form:input path="username" htmlEscape="false" minlength="3" maxlength="20"  class="input-text  required"/>
					</p>
					
					<p>
						<label class="label-title"><span class="asterisk">*</span>密码：</label>
						<input id="password" name="password" type="password" maxlength="50" minlength="3" class="input-text  required"/>
					</p>
					<p>
						<label  class="label-title"><span class="asterisk">*</span>确认密码：</label>
						<input id="confirmPassword" name="confirmPassword" type="password" maxlength="50" minlength="3" equalTo="#password" class="input-text "/>
						
					</p>
					<p>
						<label  class="label-title"><span class="asterisk">*</span>姓名：</label>
						<form:input path="name" htmlEscape="false" minlength="1" maxlength="20"  class="input-text  required"/>
					</p>
					<p>
						<label  class="label-title"><span class="asterisk">*</span>手机：</label>
						<form:input path="mobile" htmlEscape="false" minlength="1" maxlength="20"  class="input-text mobile required"/>
					</p>
					<p>
						<label  class="label-title">电话：</label>
						<form:input path="phone" htmlEscape="false" minlength="1" maxlength="20"  class="input-text  simplePhone "/>
					</p>
					<p>
						<label  class="label-title">邮箱：</label>
						<form:input path="email" htmlEscape="false" minlength="1" maxlength="20"  class="input-text  email "/>
					</p>
					<p>
						<label  class="label-title"><span class="asterisk">*</span>QQ：</label>
						<form:input path="qq" htmlEscape="false" minlength="1" maxlength="20"  class="input-text  required"/>
					</p>
					<p>
						<label  class="label-title"><span class="asterisk">*</span>公司名称：</label>
						<form:input path="company" htmlEscape="false" minlength="1" maxlength="20"  class="input-text  required"/>
					</p>
					<p>
						<label  class="label-title">联系地址：</label>
						<form:input path="address" htmlEscape="false" maxlength="200" class="input-text "/>
					</p>
					<p>
						<label  class="label-title">有效证件：</label>
						<input type="file" id="businessLicenseFile" name="businessLicenseFile" />
						<br>
						<span style="color: #b94a48;">请上传旅游行业有效资质扫描件或复印件（只支持旅游行业从事者）</span>
					</p>
					<p>
						<label  class="label-title" style="vertical-align:top;">业务简介：</label>
						<form:textarea path="introduce" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge" style="width:300px;height:110px"/>
					</p>
					
					<p>
						<span class="vertify">
							<tags:validateCode name="validateCode" />
						</span>
					</p>
					<p>
						<input name="Submit2" type="submit" id="dosubmit" class="submit" value="确认注册">
						<input  type="button" id="returnLogin" class="submit" value="返回登录">
					</p>
			</form:form>
			</div>
			
			<div class="picbox" style="height:850px;">
				<img src="${ctxStatic}/images/huangshan.jpg" >
			</div>
			<div class="bot"></div>
		</div>
		<div class="copyright">
			${fns:getConfig('company.name')}  &copy; 2014-${fns:getConfig('copyrightYear')}  ${fns:getConfig('icp')}
		</div>
	</div>
	

</body>
</html>
