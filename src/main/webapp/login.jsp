<%--
  Created by IntelliJ IDEA.
  User: guoru
  Date: 18-7-15
  Time: 上午6:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h4>登录</h4>
<form action="/login" method="post">
    用户名：<input name="userName" type="text" />
    <br><br>
    密码： <input name="passWord" type="password" />
    <br><br>
    <input value="登录" type="submit" />
</form>
</body>
</html>
