package discussionForum;

import static discussionForum.DatabaseController.db;

import java.util.ArrayList;
import java.util.Collection;

public class DiscussionPost extends Post {

    private Collection<Comment> comments = new ArrayList<Comment>();

    public DiscussionPost(int postID, String content, User author, Collection<Comment> comments) {
        super(postID, content, author);
        this.comments = comments;
    }

    public void addComments(Comment comment) {
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
        }
    }


    public void postDiscussionPost(String content, User author, Thread thread) {
        db.postDiscussionPost(content, author, getPostedTime(), thread);
    }


}