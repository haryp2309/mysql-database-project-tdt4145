package discussionForum;

import static discussionForum.DatabaseController.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Folder {

    private int folderID;
    private String name;
    private Collection<Folder> subfolders = new ArrayList<Folder>();
    private Collection<Thread> threads = new ArrayList<Thread>();

    public Folder(int folderID, String name, Collection<Folder> subfolders, Collection<Thread> threads) {
        this.folderID = folderID;
        this.name = name;
        this.subfolders = subfolders;
        this.threads = threads;
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


    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }

    public String getName() {
        return name;
    }


    public void addSubfolder(Folder subfolder) {
        if (!this.subfolders.contains(subfolder)) {
            this.subfolders.add(subfolder);
        }
    }

    public Collection<Folder> getSubfolders() {
        return subfolders;
    }

    public void postThread(String title, String content, LocalDateTime postedTimed, User author) {
        db.postThread(title, content, author, postedTimed, this);
    }

    @Override
    public String toString() {
        return name;
    }
}