package discussionForum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Thread extends Post{

    private Collection<DiscussionPost> discussionPosts = new ArrayList<>();

    public Thread(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType, Collection<DiscussionPost> discussionPosts) {
        super(postID, content, authorID, postedTime, postType);
        this.discussionPosts = discussionPosts;
    }

    public Collection<DiscussionPost> getDiscussionPosts() {
        return discussionPosts;
    }

    public void addDiscussionPost(DiscussionPost discussionPost){
        this.discussionPosts.add(discussionPost);
    }

    public void deleteDiscussionPost(DiscussionPost discussionPost){
        this.discussionPosts.remove(discussionPost);
    }
}