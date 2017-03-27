<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Register</title>
	 
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta charset="utf-8">
	<meta name="author" content="Karen Luong">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="resources/bootstrap.css">
	<link href="resources/myMangaList.css" rel="stylesheet"/>
	
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- this is the nav bar!!-->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${contextPath}/">MyMangaList</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${contextPath}/manga">Manga</a></li>
				<li><a href="${contextPath}/forumThread1"> Forums</a></li>
			</ul>		
			
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${contextPath}/register"><span class="glyphicon glyphicon-user"></span>Register</a></li>
				<li><a href="${contextPath}/login"><span class="glyphicon glyphicon-log-in"></span>Login</a></li>
			</ul>
			<form class="navbar-form navbar-right">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search">
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
	</nav>

<h1 style="text-align:center">Register</h1>

	<!-- img src="<c:url value="/resources/image.jpg" />"> -->
	
	<p style="text-align: center; color: red">
		<%
					out.print(session.getAttribute("error2"));
					session.setAttribute("error1", "");
		%>
	</p>
	
	<sf:form action="submitRegister" method="POST" modelAttribute="user" id="form">
		<sf:label path="firstName">First Name:</sf:label>
		<br />
		<sf:input path="firstName"  size="30" required="required"/>
		<br />
		<sf:label path="lastName">Last Name:</sf:label>
		<br />
		<sf:input path="lastName"  size="30" required="required"/>
		<br />
		<sf:label path="username">Username:</sf:label>
		<br />
		<sf:input path="username"  size="30" required="required"/>
		<br />
		<sf:label path="password">Password:</sf:label>
		<br />
		<sf:input path="password" size="30" required="required" type="password"/>
		<br />
		<sf:label path="email">Email:</sf:label>
		<br />
		<sf:input path="email" size="30" required="required" type="email"/>
		<br />
		<input type="submit" name="commit" value="submit" />	
	</sf:form>
	
	<br/>
	
	<!--This is the footer!-->
	
	<footer class="container-fluid">
		<p>&copy; Karen Luong 2016</p>
		<div>		
			<a href="https://uk.linkedin.com/in/karen-luong-a959b2116">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-linkedin"></span>LinkedIn
				</button>
			</a>
			<a href="https://github.com/kl1111">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-github"></span>GitHub
				</button>
			</a>
			<a href="http://codepen.io/kl1111/#">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-codepen"></span>Codepen
				</button>
			</a>
		</div>
	</footer> 
	
</body>
</html>