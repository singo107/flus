<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<script type="text/javascript" src="${static_path}/js/jquery/jquery-1.11.1.min.js"></script>
</head>

<body>
	<form id="loginForm" method="post" action="${rc.getContextPath()}/signin">
		<input id="loginname" name="loginname" type="text" /><br />
		<input id="password" name="password" type="password" /><br />
		<input id="dest" name="dest" value="${dest!}" type="hidden" />
		<input id="submit_login" value="登录" type="submit" />
	</form>
</body>
</html>