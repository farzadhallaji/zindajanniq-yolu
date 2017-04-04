package azad.hallaji.farzad.com.masirezendegi.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class Moshaver implements Serializable {

    String AID;
    String AdviserName;
    List<String> Tag;
    String PicAddress;
    String CommentCount;

    public Moshaver(String AID, String adviserName, List<String> tag, String picAddress, String commentCount) {
        this.AID = AID;
        AdviserName = adviserName;
        Tag = tag;
        PicAddress = picAddress;
        CommentCount = commentCount;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getAdviserName() {
        return AdviserName;
    }

    public void setAdviserName(String adviserName) {
        AdviserName = adviserName;
    }

    public List<String> getTag() {
        return Tag;
    }

    public void setTag(List<String> tag) {
        Tag = tag;
    }

    public String getPicAddress() {
        return PicAddress;
    }

    public void setPicAddress(String picAddress) {
        PicAddress = picAddress;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(String commentCount) {
        CommentCount = commentCount;
    }


}
