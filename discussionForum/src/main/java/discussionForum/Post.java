package discussionForum;

import static discussionForum.DatabaseController.db;

import java.time.LocalDateTime;

public abstract class Post {
    private int postID;
    private String content;
    private User author;
    private LocalDateTime postedTime;

    public Post(int postID, String content, User author) {
        this.postID = postID;
        this.content = content;
        this.author = author;
        this.postedTime = LocalDateTime.now();
    }


    public User getAuthor() {
        return author;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }


    public static void main(String[] args) {

    }

    public int getPostID() {
        return postID;
    }


    public String getContent() {
        return content;
    }



}