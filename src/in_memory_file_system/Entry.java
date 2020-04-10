package in_memory_file_system;

public abstract class Entry {

    protected Directory parent;
    protected long created;
    protected long lastUpdated;
    protected long lastAccessed;
    protected String name;

    public Entry(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.created = System.currentTimeMillis();
    }

    public abstract int size();

    public boolean delete() {
        if (parent==null){
            return false;
        }
        return parent.deleteEntry(this);
    }

    public String getFullPath() {
        if (parent==null){
            return name;
        }else {
            return parent.getFullPath()+"/"+name;
        }
    }

    public void changeName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public long getCreated() {
        return created;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }
}