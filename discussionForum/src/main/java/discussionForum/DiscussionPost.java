package discussionForum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class DiscussionPost extends Post{

    private Collection<Comment> comments = new ArrayList<>();


    public DiscussionPost(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType, Collection<Comment> comments) {
        super(postID, content, authorID, postedTime, postType);
        this.comments = comments;
    }

    public Collection<Comment> getDiscussionPost() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }


}