package discussionForum;

import static discussionForum.DatabaseController.db;

public class Comment extends Post {


    public Comment(int postID, String content, User author) {
        super(postID, content, author);
    }


    public void postComment(String content, User author, DiscussionPost discussionPost) {
        db.postComment(content, author, getPostedTime(), discussionPost);
    }

    public String toString() {
        return getContent();
    }
}
