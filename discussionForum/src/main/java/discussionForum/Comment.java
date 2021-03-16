package discussionForum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Comment extends Post{

    public Comment(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType) {
        super(postID, content, authorID, postedTime, postType);
    }




}
