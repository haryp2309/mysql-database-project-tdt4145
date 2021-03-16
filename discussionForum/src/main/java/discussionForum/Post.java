package discussionForum;

import java.time.LocalDateTime;

import static discussionForum.DatabaseController.db;

public abstract class Post{
    private int postID;
    private String content;
    private User author;
    private LocalDateTime postedTime;
    private boolean postType;

    public Post(int postID, String content, User author, boolean postType) {
        this.postID = postID;
        this.content = content;
        this.author = author;
        this.postedTime = LocalDateTime.now();
        this.postType = postType;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorID(User author) {
        this.author = author;
    }

    public void setPostType(boolean postType) {
        this.postType = postType;
    }

    public User getAuthor() {
        return author;
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