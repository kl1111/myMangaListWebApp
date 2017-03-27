package com.fdmgroup.models.interfaces;

import java.util.List;

import com.fdmgroup.classes.Discussion;
import com.fdmgroup.classes.DiscussionView;

public interface Discussions {

	void addMangaDiscussion(String postContent, int user_id, int manga_id);
	
	void addForumDiscussion(String postContent, int user_id, int forum_id);
	
	void deleteDiscussion(int forum_post_id, int post_id);
	
	void updateDiscussion(Discussion discussion);
	
	//List<Discussion> getDiscussionsByUsername(String username);
	
	List<DiscussionView> getDiscussionByForum(String forumName);
	
	List<DiscussionView> getDiscussionByManga(String title);
	
	List<DiscussionView> getTopThreeForumDiscussionsByDate();
	
	List<DiscussionView> getTopThreeMangaDiscussionsByDate();
}
