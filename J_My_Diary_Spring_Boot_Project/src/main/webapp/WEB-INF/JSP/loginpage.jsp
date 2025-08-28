<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<link rel="stylesheet" href="<c:url value="styles/style.css"/>"></link>
</head>
<body>
<div class="header"> 
<div class="firstpart">
<img src="<c:url value="/images/dairyimage.jpg"/>" width="70" height="70">
</div>
<div class="secondpart">MyDiary Application</div>

</div>

<hr>
<hr>
<br/>
<div class="bodypart">
<div class="bodypart2">

<h1 align="center"><br/>Login Here</h1>
<br/>
<form action="./authenticate" method="POST">

<label class="label">UserName</label> <input type="text" name="username" class="formcontrol" ><br/><br/>
<label class="label">Password</label> <input type="password" name="password" class="formcontrol" ><br/><br/>
<button type="submit" class="button">LOGIN</button><br/><br/>
New User ? <a href="./register" >Register</a> here<br/><br/>

</form>
</div>
<div class="bodypart1">
<img src="<c:url value="/images/dairyimage.mainjpg.jpg"/>"  width="200" height="200">
</div>
</div>
</body>
</html>