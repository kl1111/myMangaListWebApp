<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Discussions</title>

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
				<a class="navbar-brand" href="${contextPath}/">MyMangaList</a>
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
			
			<div class="col-sm-8 central">
			
				<div class="postTitle">
					<h3>Opinion on ending of Girls of the Wilds?</h3>
				</div>
				
				
				<div class="headPost">			
				
					<span style="float: left">Posted by: tomatoes</span>
					<span style="float: right">Date posted: 2016-12-13</span>
					<div style="clear: both"></div>
					
					<div style="float:left; margin-right:5px; border:1px solid black">
						<img src="resources/images/gotw.jpg" height="300" width="200"/><br>
					</div>
					
					<div>
						<h4>Contrary to popular belief, Lorem Ipsum is not simply random 
						text. It has roots in a piece of classical Latin literature from 45 
						BC, making it over 2000 years old. Richard McClintock, a Latin 
						professor at Hampden-Sydney College in Virginia, looked up one of the
						more obscure Latin words, consectetur, from a Lorem Ipsum passage,
						and going through the cites of the word in classical literature, 
						discovered the undoubtable source. Lorem Ipsum comes from sections 
						1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes 
						of Good and Evil) by Cicero, written in 45 BC. This book is a 
						treatise on the theory of ethics, very popular during the Renaissance.
						The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes
						from a line in section 1.10.32.</h4>
					</div>
					
					<div>
						<span style="float: left">Shares</span>
						<span style="float: right">Likes&nbsp</span>
						<div style="clear: both"></div>
					</div>
					
				</div>
				
				<div class="postComments">
				
					<span style="float: left">Posted by: tomatoes</span>
					<span style="float: right">Likes</span>
					<div style="clear: both"></div>
					
					<p>Contrary to popular belief, Lorem Ipsum is not simply random 
					text. It has roots in a piece of classical Latin literature from 45 
					BC, making it over 2000 years old. Richard McClintock, a Latin 
					professor at Hampden-Sydney College in Virginia, looked up one of the
					more obscure Latin words, consectetur, 
					</p>
					
					<span style="float: left">Shares</span>
					<span style="float: right">Likes&nbsp</span>
					<div style="clear: both"></div>
					
				</div>

				
				
			</div>
			
			<!--This is the right hand side!-->
			<div class="col-sm-2 right-bar">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, from a Lorem Ipsum 
				passage, and going </p>-->
			</div>
		</div>
		
		</div>
		
	
	<sf:form action="submitDiscussion" method="POST" modelAttribute="discussion" id="form">
		<sf:label path="postContent">Write a comment!</sf:label>
		<sf:input path="postContent" size="30" required="required" />
		<br />
		<input type="submit" name="commit" value="submit" />
	</sf:form>
	
	
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