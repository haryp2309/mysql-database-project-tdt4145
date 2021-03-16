package discussionForum;

import java.util.Collection;

import static discussionForum.DatabaseController.db;

public class Folder{

    private int folderID;
    private String name;
    private Collection<Folder> subfolders = new ArrayList<Folder>();
    private Collection<Thread> threads = new ArrayList<Thread>();

    public Folder(int folderID, String name, Collection<Folder> subfolders, Collection<Thread> threads){
        this.folderID=folderID;
        this.name=name;
        this.subfolders=subfolders;
        this.threads=threads;
    }

    public static void createFolder(String name, Collection<Folder> subfolders, Collection<Thread> threads){
        db.createFolder(name, subfolders, threads);
    }

}