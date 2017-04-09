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

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getCostPerMin() {
        return CostPerMin;
    }

    public void setCostPerMin(String costPerMin) {
        CostPerMin = costPerMin;
    }

    public String getIsFavourite() {
        return IsFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        IsFavourite = isFavourite;
    }

    String Telephone;
    String UniqueID;
    String CostPerMin;
    String IsFavourite;
    String Dialect;

    public String getDialect() {
        return Dialect;
    }

    public void setDialect(String dialect) {
        Dialect = dialect;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    String Rating ="";


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
