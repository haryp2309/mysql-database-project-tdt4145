package discussionForum;

public class Comment extends Post {


    public Comment(int postID, String content, User author) {
        super(postID, content, author);
    }

    public String toString() {
        return getContent();
    }
}
