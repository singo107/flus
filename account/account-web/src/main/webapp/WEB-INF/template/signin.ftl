<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<script type="text/javascript" src="${static_path}/js/jquery/jquery-1.11.1.min.js"></script>
</head>

<body>
	<form id="loginForm" method="post" action="${rc.getContextPath()}/signin">
		用户名：<input id="loginname" name="loginname" type="text" /><br />
		密码：<input id="password" name="password" type="password" /><br />
		验证码：<input id="code" name="code" type="text" /><br />
		<img src="${rc.getContextPath()}/captcha" alt="" /><br />
		<input id="dest" name="dest" value="${dest!}" type="hidden" />
		<input id="submit_login" value="登录" type="submit" />
	</form>
</body>
</html>