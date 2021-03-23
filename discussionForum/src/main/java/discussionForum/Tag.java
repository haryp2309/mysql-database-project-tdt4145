package discussionForum;

import java.util.Collection;

import static discussionForum.DatabaseController.db;

public class Tag {

    private String value;

    public Tag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Collection<Tag> getAllTags() {
        return db.getTags();
    }
}
