package azad.hallaji.farzad.com.masirezendegi.model;

/**
 * Created by fayzad on 3/24/17.
 */

public class Subject {

    private String SID , Name , HasChild;

    public Subject(String SID, String name, String hasChild) {
        this.SID = SID;
        Name = name;
        HasChild = hasChild;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHasChild() {
        return HasChild;
    }

    public void setHasChild(String hasChild) {
        HasChild = hasChild;
    }
}
