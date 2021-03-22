package discussionForum;

import static discussionForum.DatabaseController.db;

import java.util.ArrayList;
import java.util.Collection;

public class DiscussionPost extends Post {


    public DiscussionPost(int postID, String content, User author, Collection<Comment> comments) {
        super(postID, content, author);
    }

    public void postComment(String content, User author) {
        db.postComment(content, author, getPostedTime(), this);
    }

    public Collection<Comment> getComments() {
        return db.getComments(this);
    }

    @Override
    public String toString() {
        return getContent();
    }
}