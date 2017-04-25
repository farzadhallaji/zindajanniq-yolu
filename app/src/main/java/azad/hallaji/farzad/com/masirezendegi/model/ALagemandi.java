package azad.hallaji.farzad.com.masirezendegi.model;


/**
 * Created by fayzad on 4/23/17.
 */

public class ALagemandi {

    private String ID;
    private String UserName;
    private String Type;
    private String PicAddress;

    public ALagemandi(String ID, String userName, String type, String picAddress) {
        this.ID = ID;
        UserName = userName;
        Type = type;
        PicAddress = picAddress;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPicAddress() {
        return PicAddress;
    }

    public void setPicAddress(String picAddress) {
        PicAddress = picAddress;
    }
}
