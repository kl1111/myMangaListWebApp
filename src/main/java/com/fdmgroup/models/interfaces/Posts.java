package com.fdmgroup.models.interfaces;

import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Post;

public interface Posts {
	
	int addPost(int user_id, Date postDate);
	
	void deletePost(int post_id);
	
	Post findPost(int post_id);
	
	List<Post> findPostByDate(Date postDate);
	
	List<Post> listAllPosts();
	
	List<Post> listPostsByUsername(String username);

}
