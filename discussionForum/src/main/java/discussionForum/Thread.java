package discussionForum;

import static discussionForum.DatabaseController.db;

import java.time.LocalDateTime;
import java.util.Collection;

public class Thread extends Post {

    private String title ;

    public Thread(int postID, String title, String content, User author) {
        super(postID, content, author);
        this.title = title;
    }

    public Collection<DiscussionPost> getDiscussionPosts() {
        return db.getDiscussionPosts(this);
    }

    public void postDiscussionPost(String content, User author) {
        db.postDiscussionPost(content, author, LocalDateTime.now(), this);
    }

    public String getTitle() {
        return title;
    }

    public Collection<Tag> getTags() {
        return db.getAllTags(this);
    }

    @Override
    public String toString() {
        return getPostID() + ": " + title+": "+getContent()+" - by "+getAuthor().getFirstName()+" "+getAuthor().getLastName();
    }
}