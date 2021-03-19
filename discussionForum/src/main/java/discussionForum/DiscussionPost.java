package discussionForum;

import static discussionForum.DatabaseController.db;

import java.util.ArrayList;
import java.util.Collection;

public class DiscussionPost extends Post {


    public DiscussionPost(int postID, String content, User author, Collection<Comment> comments) {
        super(postID, content, author);
    }

    public Collection<Comment> getComments() {
        return db.getComments(this);
    }

    public void postDiscussionPost(String content, User author, Thread thread) {
        db.postDiscussionPost(content, author, getPostedTime(), thread);
    }

    @Override
    public String toString() {
        return getContent();
    }
}