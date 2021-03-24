package discussionForum;

import static discussionForum.DatabaseController.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Folder {

    private int folderID;
    private String name;
    private Collection<Folder> subfolders;

    public Folder(int folderID, String name, Collection<Folder> subfolders) {
        this.folderID = folderID;
        this.name = name;
        this.subfolders = subfolders;
    }

    public int getFolderID() {
        return folderID;
    }

    public Collection<Thread> getThreads(){
        return db.getThreads(this);
    }

    public static void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads, Course course) {
        db.createFolder(name, subfolders, threads, course);
    }

    public String getName() {
        return name;
    }


    public Collection<Folder> getSubfolders() {
        return subfolders;
    }

    public void postThread(String title, String content, User author, Collection<Tag> tags) {
        db.postThread(title, content, author, LocalDateTime.now(), this, tags);
    }

    @Override
    public String toString() {
        return name;
    }
}