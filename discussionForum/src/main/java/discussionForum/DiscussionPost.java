package discussionForum;

import java.time.LocalDateTime;

public class DiscussionPost extends Post{


    public DiscussionPost(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType) {
        super(postID, content, authorID, postedTime, postType);
    }
}