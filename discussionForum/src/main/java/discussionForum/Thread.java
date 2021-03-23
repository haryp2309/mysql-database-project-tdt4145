package discussionForum;

import static discussionForum.DatabaseController.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Thread extends Post {

    private Collection<Tag> tags = new ArrayList<>();
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

    public void addTag(Tag tag) {
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }

    public void deleteTag(Tag tag) {
        if (this.tags.contains(tag)) {
            this.tags.remove(tag);
        }
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return title+": "+getContent()+" - by "+getAuthor().getFirstName()+" "+getAuthor().getLastName();
    }
}