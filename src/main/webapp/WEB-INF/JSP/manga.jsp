<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Manga</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="author" content="Karen Luong">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="resources/bootstrap.css">
<link href="resources/myMangaList.css" rel="stylesheet" />
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<!-- this is the nav bar!!-->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" onclick="location.href=''">MyMangaList</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${contextPath}/manga">Manga</a></li>
				<li><a href="${contextPath}/forumThread1"> Forums</a></li>
			</ul>	

			<ul class="nav navbar-nav navbar-right">
			<c:choose>
				<c:when test="${isLoggedOut}">
					<li><a href="${contextPath}/register"><span class="glyphicon glyphicon-user"></span>Register</a></li>
					<li><a href="${contextPath}/login"><span
							class="glyphicon glyphicon-log-in"></span>Login</a></li>
				</c:when>
				<c:otherwise>
				<li><a onclick="location.href='logout'"><span
						class="glyphicon glyphicon-log-out"></span>Logout</a></li>
				</c:otherwise>
			</c:choose>
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

	<div class="container-fluid text-center">
		<div class="row">
		
			<!--This is the left bar!-->
			<div class="col-sm-2">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, from a Lorem Ipsum 
				passage, and going </p>-->
			</div>
			
			<!--This is the main body, center-->
			<div class="col-sm-8">	
				<br><div style="text-align: center; font-size:24px">All Webtoons</div><br>
				<div class="flex-container">
					
						<img src="resources/images/gamer.png" height="200" width="150" style="border:1px solid black" />	
					<a href="${contextPath}/gotw">
						<img src="resources/images/gotw.jpg" height="200" width="150" style="border:1px solid black" />
					</a>
						<img src="resources/images/Bastard.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/dice.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/hive.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/Hooky.jpg" height="200" width="150" style="border:1px solid black" />	
						<img src="resources/images/gamer.png" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/gotw.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/Bastard.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/dice.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/hive.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/Hooky.jpg" height="200" width="150" style="border:1px solid black" />	
						<img src="resources/images/gamer.png" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/gotw.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/Bastard.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/dice.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/hive.jpg" height="200" width="150" style="border:1px solid black" />
						<img src="resources/images/Hooky.jpg" height="200" width="150" style="border:1px solid black" />		
												
				</div>
			</div>
			
			
			<!--This is the right hand side!-->
			<div class="col-sm-2 col-md-2 right-bar">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, fro</p>-->
			</div>
			
		</div>
		
				
	</div>
			

	<br>

	<!--This is the footer!-->

	<footer class="container-fluid">
		<p>&copy; Karen Luong 2016</p>
		<div>
			<a href="https://uk.linkedin.com/in/karen-luong-a959b2116">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-linkedin"></span>LinkedIn
				</button>
			</a> <a href="https://github.com/kl1111">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-github"></span>GitHub
				</button>
			</a> <a href="http://codepen.io/kl1111/#">
				<button type="button" class="btn btn-info">
					<span class="glyphicon glyphicon-codepen"></span>Codepen
				</button>
			</a>
		</div>
	</footer>

</body>
</html>