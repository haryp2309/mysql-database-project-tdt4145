package discussionForum;

import java.time.LocalDateTime;
import static discussionForum.DatabaseController.db;

public abstract class Post{
    private int postID;
    private String content;
    private int authorID;
    private LocalDateTime postedTime;
    private boolean postType;

    public Post(int postID, String content, int authorID, LocalDateTime postedTime, boolean postType) {
        this.postID = postID;
        this.content = content;
        this.authorID = authorID;
        this.postedTime = postedTime;
        this.postType = postType;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public void setPostType(boolean postType) {
        this.postType = postType;
    }

    public int getAuthorID() {
        return authorID;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public boolean isPostType() {
        return postType;
    }

    public static void main(String[] args) {

    }

    public int getPostID() {
        return postID;
    }


    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }

    public String getContent() {
        return content;
    }

    public int likedCount(Post post){
        return db.likedCount(post);
    }

    public int viewedCount(Post post){
        return db.viewedCount(post);
    }

}