package in_memory_file_system;

import java.util.List;

public class FileSystem {

    private final Directory root;

    public FileSystem() {
        this.root = new Directory("/", null);
    }

    public void mkdir(String path) {
        assert path.startsWith("/");
        String[] components = path.substring(1).split("/");

        Directory entry = root;
        for (String component: components){
            if (!component.isEmpty()){
                Entry son = entry.getChild(component);
                if (son==null){
                    son = new Directory(component, entry);
                    entry.addEntry(son);
                }
                entry = (Directory) son;
            }
        }
    }

    public void createFile(String path) {
        assert !path.endsWith("/");
        String[] components = path.substring(1).split("/");
        Entry entry = root;

        for (int i=0; i<components.length-1; i++){
            if (!components[i].isEmpty()){
                Entry son = ((Directory) entry).getChild(components[i]);
                if (son==null){
                    son = new Directory(components[i], (Directory) entry);
                    ((Directory) entry).addEntry(son);
                }
                entry = son;
            }
        }
        Entry son = ((Directory) entry).getChild(components[components.length-1]);
        if (son==null){
            son = new File(components[components.length-1], (Directory) entry,0);
            ((Directory) entry).addEntry(son);
        }
    }

    public void delete(String path) {
        String[] components = path.substring(1).split("/");
        Entry entry = root;

        for (int i=0; i<components.length; i++){
            if (!components[i].isEmpty()){
                Entry son = ((Directory) entry).getChild(components[i]);
                assert son!=null;
                entry = son;
            }
        }
        entry.delete();
    }

    public List<Entry> list(String path) {
        assert path.startsWith("/");
        String[] components = path.substring(1).split("/");

        Entry entry = root;
        for (String component: components){
            if (entry==null||!(entry instanceof Directory)){
                throw new IllegalArgumentException("invalid path: "+path);
            }
            if (!component.isEmpty()){
                entry = ((Directory) entry).getChild(component);
            }
        }
        return ((Directory)entry).getContents();
    }

    public int count() {
        int count = 0;
        for (Entry e: root.getContents()){
            if (e instanceof Directory){
                count++;
                Directory d = (Directory) e;
                count += d.numberOfFiles();
            }else if (e instanceof File){
                count++;
            }
        }
        return count;
    }
}