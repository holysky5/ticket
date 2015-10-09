<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html>
<head>
<title>${fns:getConfig('productName')} 登录</title>
<link rel="icon" href="${ctxStatic}/images/favicon.ico" type="image/ICO" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>

<link href="${ctxStatic}/common/login.css" type="text/css"
	rel="stylesheet" />

	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				}
			});
			
			$("#registerButton").click(function(){
				window.location.href = '${ctx}/biz/distributor/register';
				//window.open('${ctx}/biz/distributor/register');
			});
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
				<div class="title">易往——您的旅行伴侣</div>
				<div class="header"><%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);%>
					<div id="messageBox" style="<%=error==null?"display:none;":""%>">
						<label id="loginError" class="error"><%=error==null?"":"com.ourlife.dev.modules.sys.security.CaptchaException".equals(error)?"验证码错误, 请重试.":"用户或密码错误, 请重试." %></label>
					</div>
				</div>
				<form method="post"
					action="${ctx}/login"
					name="loginForm" id="loginForm">
					<p>
						<label class="label-title">用户名：</label><input name="username" type="text" size="25"
							id="username" value="${username}" class="input-text required">
					</p>
					<p>
						<label class="label-title">密&nbsp;&nbsp;码：</label><input name="password"
							type="password" size="25" id="password"
							class="input-text required"
							onfocus="this.select();">
					</p>
					<p>
						<c:if test="${isValidateCodeLogin}">
						<span class="vertify">
							<tags:validateCode name="validateCode" />
						</span>
						</c:if>
					</p>
					
					<p>
						<input name="Submit2" type="submit" id="dosubmit" class="submit"
							value="确认登陆"><input name="sellerRegister" type="button"
							id="registerButton" class="submit" value="用户注册">
					</p>
				</form>
			</div>
			
			<div class="picbox">
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