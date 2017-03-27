<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Girls of the Wild's</title>

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
			
			<!--This is the main body, center-->
			
			<div class="col-sm-8">
				<h1 class="mangaTitle">Girls of the Wild's</h1>	
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
					
				<div class="headPost">
					
						<div class="mangaDetails">
							<div>
								<img src="resources/images/gotw.jpg" height="300" width="200"/><br>
							</div>
							<div>
								<span> Rating: 88% </span><br>
								<span> Author: <c:out value="${gotwDetails.author}"/> </span><br>
								<span> Genre: <c:out value="${gotwDetails.genre}"/> </span><br>
								<span> Publish Date: <c:out value="${gotwDetails.pubDate}"/> </span>
							</div>
						</div>
						
						<div class="summary">
							<h3>Summary</h3>
							<p>
								<c:out value="${gotwDetails.summary}"/>
							</p>
						</div>
						<div>
							<div style="clear: both"></div>
						</div>
			
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
		<div class="row">
			<!--This is the left hand side!-->
			<div class="col-sm-2 col-md-2 left-bar">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, fro</p>-->
			</div>
				
				
				<div class="col-sm-8 col-md-8 central">	
					<h4>Reviews</h4>
					
					<span style="color:red">${mangaError}</span>
					
						<c:forEach var="gotwReview" items="${gotwReviews}">
							<TR> <TD>
								<div class="review">
									<span style="float: left">Rating: ${gotwReview.review.rating}/10</span>
									<span style="float: right">
										<sf:form action="updateReview/${gotwReview.review.reviewID}" method="POST">
											<input type="submit" name="commit" value="Update" />
										</sf:form>	
										<sf:form action="deleteReview/${gotwReview.review.reviewID}/${gotwReview.review.postID}" method="POST">
											<input type="submit" name="commit" value="Delete" />
										</sf:form>								
									</span>
									<div style="clear: both"></div>
									<p>${gotwReview.review.reviewContent}
									</p><br><br>
									<span style="float: left">User: ${gotwReview.username}</span>
									<span style="float: right">Date posted: ${gotwReview.datePosted}</span>
									<div style="clear: both"></div>
								</div>	
							</TD> </TR>
						</c:forEach>	
						
						
				</div>
			
			<!--This is the left hand side!-->
			<div class="col-sm-2 col-md-2 left-bar">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, fro</p>-->
			</div>
			
		</div>
		
		<div class="row">
			<div class="col-sm-2">		
			</div>
			
			<div class="col-sm-8 central">
			
				 <div>
					<sf:form action="submitReview" method="post" modelAttribute="review">
						<sf:label path="reviewContent">Rating: </sf:label>
						<sf:select items="${ratingList}" path="rating" width="20"/>
						<sf:label path="reviewContent">Leave a Review!</sf:label>
						<sf:input path="reviewContent" size="50" />
						
						<input type="submit" name="commit" value="submit" />
					</sf:form>				
				</div>	
					
			</div>
			
			<div class="col-sm-2">		
			</div>
		
		</div>
		
		
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
			<h4>Discussions</h4>

				<c:forEach var="gotwDis" items="${gotwDis}">
						<TR> <TD>
							<div class="postComments">
							<span style="float: left">Posted by: ${gotwDis.datePosted} </span>
							<div style="clear: left"></div>
							<p>${gotwDis.discussion.postContent}
							</p>
	
							<span style="float: left">Posted by: ${gotwDis.username} </span>
							<span style="float: right">Likes: ${gotwDis.discussion.likeContent}</span>
							<div style="clear: both"></div>
							</div>
						</TD></TR>	
				</c:forEach>
				
				
			</div>
			
			<!--This is the left hand side!-->
			<div class="col-sm-2 col-md-2 left-bar">
				<!--<p>Contrary to popular belief, Lorem Ipsum is not simply random
				text. It has roots in a piece of classical Latin literature from 
				45 BC, making it over 2000 years old. Richard McClintock, a Latin
				professor at Hampden-Sydney College in Virginia, looked up one of 
				the more obscure Latin words, consectetur, fro</p>-->
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