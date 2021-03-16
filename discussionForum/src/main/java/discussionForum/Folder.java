package discussionForum;

import java.util.Collection;

public class Folder{

    private String name;
    private String folderType;
    private Collection<Folder> subfolders = new ArrayList<Folder>();
    private Collection<Thread> threads = new ArrayList<Thread>();


}