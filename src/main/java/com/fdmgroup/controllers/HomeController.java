package com.fdmgroup.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.classes.Discussion;
import com.fdmgroup.classes.DiscussionView;
import com.fdmgroup.classes.Manga;
import com.fdmgroup.classes.Review;
import com.fdmgroup.classes.ReviewView;
import com.fdmgroup.classes.User;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.DiscussionsDAO;
import com.fdmgroup.models.daos.FavDAO;
import com.fdmgroup.models.daos.MangaDAO;
import com.fdmgroup.models.daos.ReviewsDAO;
import com.fdmgroup.models.daos.UsersDAO;

@Controller
public class HomeController {
	
	// ALL requests for "/" sent to login.jsp
	// http://localhost:8088/SpringForm/ url... --> Returns user to login page
	@RequestMapping("/")
	public String HomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		//Favs
		FavDAO favDao = new FavDAO(Connections.getConnection());
		List<Manga> listFavs = favDao.top5FavMangas();
		session.setAttribute("TopFavs", listFavs);
		
		// REVIEW
		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());
		List<ReviewView> allReviews = reviewsDao.getTopThreeReviewsByDate();
		session.setAttribute("theReviews", allReviews);	
		
		// Forum Discussions
		DiscussionsDAO disDao = new DiscussionsDAO(Connections.getConnection());
		List<DiscussionView> allForumPosts = disDao.getTopThreeForumDiscussionsByDate();
		session.setAttribute("theForumStuff", allForumPosts);	
		
		// Manga Discussions
		List<DiscussionView> allMangaDiscussions = disDao.getTopThreeMangaDiscussionsByDate();
		session.setAttribute("theMangaStuff", allMangaDiscussions);	
		
		Connections.CloseConnection();

		return "home";
	}
	

	
	@RequestMapping(value = "/login")
	public String LoginHandler(Model model, HttpServletRequest request) {
		model.addAttribute("user", new User());
		HttpSession session = request.getSession();
		session.setAttribute("error1", "");
		session.setAttribute("error2", "");
		return "login";
	}

	// All POST requests for "/submitLogin" sent to loginSuccess.jsp
	// and add the user attribute and pass it to the page

	@RequestMapping(value = "/submitLogin", method = POST)
	public String submitHandler(Model model, User user, HttpServletRequest request) {
		UsersDAO usersDao = new UsersDAO(Connections.getConnection());
		model.addAttribute("user", user);
		
		HttpSession session = request.getSession();
		session.setAttribute("error1", "");
		session.setAttribute("error2", "");
		User getUser = usersDao.findByUsername(user.getUsername());
		Connections.CloseConnection();
		if (getUser != null) {
			if (user.getPassword().equals(getUser.getPassword())) {
				session.setAttribute("user", getUser);
				session.setAttribute("userId", (Integer)getUser.getUserID());
				session.setAttribute("isLoggedOut", false);
				return "home";
			} else {
				session.setAttribute("error1", "Invalid password");
				return "login";
			}
		} else {
			session.setAttribute("error2", "User does not exist");
			return "register";
		}
	}
	
	// log out
	@RequestMapping(value = "/logout")
	public String LogoutHandler(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.setAttribute("user", new User());
		session.setAttribute("userId", new Integer(-1));
		session.setAttribute("isLoggedOut", true);
		
		return "home";
	}

	// register
	// ALL requests for "/register" sent to register.jsp
	@RequestMapping(value = "/register")
	public String RegistrationHandler(Model model, HttpServletRequest request) {
		model.addAttribute("user", new User());
		return "register";
	}

	// register
	@RequestMapping(value = "/submitRegister")
	public String RegistrationWelcome(Model model, User user, HttpServletRequest request) {
		model.addAttribute("user", user);
		HttpSession session = request.getSession();
		UsersDAO usersDao = new UsersDAO(Connections.getConnection());
		User foundUser = usersDao.findByUsername(user.getUsername());
		session.setAttribute("error1", "");
		session.setAttribute("error2", "");
		if(foundUser == null){
			foundUser = usersDao.findByEmail(user.getEmail());
			if(foundUser == null){
				usersDao.addUsers(user);
				session.setAttribute("error2", "");
				model.addAttribute("user", new User());
				Connections.CloseConnection();
				return "login";
			} else {
				session.setAttribute("error2", "There is already an account associated with that email address, please try again.");
				return "register";
			}
		} else {
			session.setAttribute("error2", "Username already taken, please try again.");
			Connections.CloseConnection();
			return "register";
		}
	}
	
	@RequestMapping(value = "/manga")
	public String MangaController(Model model, HttpServletRequest request) {
		model.addAttribute("user", new User());
		return "manga";
	}
	
	@RequestMapping(value = "/forumThread1")
	public String discussionController(Model model, String value, HttpServletRequest request) {
		Discussion discussion = new Discussion();
		model.addAttribute("discussion", discussion);
		//HttpSession session = request.getSession();
//		DiscussionsDAO disDao = new DiscussionsDAO(Connections.getConnection());
		String action = value;
//		System.out.println(action);
//		List<DiscussionView> disList = disDao.getDiscussionByForum(action);
//		DiscussionView mainPost = disList.get(0);
		model.addAttribute("mainPost", action);
//		
//		disList.remove(mainPost);
//		model.addAttribute("responses", disList);
		
		return "forumThread1";
	}

	@RequestMapping(value = "/submitForumDiscussion")
	public String discussionHandler(Model model, Discussion discussion, HttpServletRequest request) {
		model.addAttribute("discussion", discussion);	
		return "forumThread1";
	}
	
	@RequestMapping(value = "/gotw")
	public String GamerHandler(Model model, HttpServletRequest request) {
		model.addAttribute("review", new Review());
		HttpSession session = request.getSession();
		session.setAttribute("mangaError", "");
		updateReviewsPage(request, session);
		return "gotw";
	}
	
	@RequestMapping(value = "/submitReview", method=POST)
	public String reviewHandler(Model model, Review review, HttpServletRequest request) {
		model.addAttribute("review", review);
		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());
		HttpSession session = request.getSession(); 

		int mangaId = (Integer) session.getAttribute("mangaId");
		review.setMangaID(mangaId);
				
		int userId = (Integer) session.getAttribute("userId");
		if(userId > 0) {
			reviewsDao.addReview(review, userId);
		} else {
			session.setAttribute("mangaError", "You have not logged in, please log in to submit a review.");
		}
		
		updateReviewsPage(request, session);
		
		return "gotw";
	}
	
	@RequestMapping(value = "/toForum/{buttonCaption}")
	public String sendToForumHandler(Model model, HttpServletRequest request, @PathVariable String buttonCaption) {
		model.addAttribute("review", new Review());
		model.addAttribute("buttonCaption", buttonCaption);
		return "updateReview";
	}
	
	@RequestMapping(value = "/updateReview/{buttonCaption}", method = RequestMethod.POST)
	public String updateReviewHandler(Model model, HttpServletRequest request, @PathVariable int buttonCaption) {
		HttpSession session = request.getSession();
		model.addAttribute("review", new Review());
		model.addAttribute("buttonCaption", buttonCaption);

		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());
		int reviewId = buttonCaption;
		Review review = reviewsDao.getReviewById(reviewId);
		
		if(review != null) {
			model.addAttribute("review", review);
			session.setAttribute("reviewId", (Integer)reviewId);
		}

		updateReviewsPage(request, session);
		
		return "updateReview";
	}
	
	@RequestMapping(value = "/submitUpdateReview", method = POST) 
	public String submitUpdatedReviewHandler(Model model, Review review, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("mangaError", "");
		model.addAttribute("review", review);
		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());

		Review updateReview = reviewsDao.getReviewById((Integer)session.getAttribute("reviewId"));
		
		if(review != null) {
			
			updateReview.setRating(review.getRating());
			updateReview.setReviewContent(review.getReviewContent());
			
			reviewsDao.updateReview(updateReview);
		} else {

			session.setAttribute("mangaError", "Review does not exist");
		}
		
		updateReviewsPage(request, session);
		return "gotw";
	}
	
	@RequestMapping(value = "/deleteReview/{review_Id}/{post_Id}", method = RequestMethod.POST)
	public String deleteReviewHandler(Model model, HttpServletRequest request, @PathVariable("review_Id") int reviewId, @PathVariable("post_Id") int postId) {
		model.addAttribute("review", new Review());
		model.addAttribute("buttonCaption", reviewId);
		HttpSession session = request.getSession();

		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());
		int review_Id = reviewId;
		int post_Id = postId;
		
		reviewsDao.deleteReview(review_Id, post_Id);
		updateReviewsPage(request, request.getSession());
		
		// check if review has been deleted
		Review review = reviewsDao.getReviewById(review_Id);
		
		if(review == null) {
			session.setAttribute("mangaError", "Review has not been deleted");
		} else {
			session.setAttribute("mangaError", "Review has been deleted");
		}
		
		
		
		return "gotw";
	}
	
	private void updateReviewsPage(HttpServletRequest request, HttpSession session){
		//Make review rating list
		List<Integer> ratings = new ArrayList<Integer>();
		for(int i = 1; i < 11; i++){
			ratings.add(i);
		}
		session.setAttribute("ratingList", ratings);
		
		//MANGA
		MangaDAO mangaDao = new MangaDAO(Connections.getConnection());
		Manga gotw = mangaDao.findMangaByTitle("Girls of the Wilds");
		session.setAttribute("gotwDetails", gotw);
		session.setAttribute("mangaId", (Integer)gotw.getMangaID());
		
		// REVIEW
		ReviewsDAO reviewsDao = new ReviewsDAO(Connections.getConnection());
		List<ReviewView> listReviews = reviewsDao.getReviewsByManga("Girls of the Wilds");
		session.setAttribute("gotwReviews", listReviews);
		
		//Discussions
		DiscussionsDAO disDao = new DiscussionsDAO(Connections.getConnection());
		List<DiscussionView> gotwComments = disDao.getDiscussionByManga("Girls of the Wilds");
		session.setAttribute("gotwDis", gotwComments);
		Connections.CloseConnection();
	}
	
	
	
}
