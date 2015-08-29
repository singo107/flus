<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<link href="${static_path}/css/reset.css" rel="stylesheet" type="text/css"/>
<link href="${static_path}/css/merge/signin.merge.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${static_path}/js/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${static_path}/js/merge/signin.merge.js"></script>
</head>

<body>
	<div class="header">
		
	</div>
	<div class="signin_box">
		<div class="signin_body">
			<form id="loginForm" method="post" action="${rc.getContextPath()}/signin">
				<input id="dest" name="dest" value="${dest!}" type="hidden" />
				<div class="signin_inline"><input id="loginname" name="loginname" type="text" placeholder="Email" class="signin_loginname" /></div>
				<div class="signin_inline"><input id="password" name="password" type="password" placeholder="密码"  class="signin_password" /></div>
				<div class="signin_inline clr">
					<div class="f-l">
						<label class="checkbox">
                            <input type="checkbox" name="rememberMe">
							一个月内自动登录
                        </label>
					</div>
					<div class="f-r">
						<a href="#">忘记密码？</a>
					</div>
				</div>
				<div class="signin_inline"><input id="submit_login" value="登录" type="submit" class="button-primary signin_button" /></div>
				<div class="signin_inline t-c">
					<a href="signup">注册新账号</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>