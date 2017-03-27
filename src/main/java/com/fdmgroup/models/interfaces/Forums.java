package com.fdmgroup.models.interfaces;

import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Forum;

public interface Forums {

	void startForum(int userID, String forumName);
	
	void deleteForum(int forum_id);
	
	void updateForum(Forum forum);
	
	List<Forum> findForumByUsername(String username);
	
	List<Forum> findForumByDate(Date startDate);

	List<Forum> findAllForums();
}
