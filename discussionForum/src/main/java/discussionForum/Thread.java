package discussionForum;

import java.time.LocalDateTime;

public class Thread extends Post{


    public Thread(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType) {
        super(postID, content, authorID, postedTime, postType);
    }
}