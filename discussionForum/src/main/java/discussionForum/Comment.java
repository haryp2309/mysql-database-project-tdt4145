package discussionForum;

import static discussionForum.DatabaseController.db;

public class Comment extends Post {


    public Comment(int postID, String content, User author, boolean postType) {
        super(postID, content, author, postType);
    }


    public void postComment(String content, User author, DiscussionPost discussionPost) {
        db.postComment(content, author, getPostedTime(), discussionPost);
    }
}
