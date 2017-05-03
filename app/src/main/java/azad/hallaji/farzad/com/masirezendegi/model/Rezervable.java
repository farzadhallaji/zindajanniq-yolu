package azad.hallaji.farzad.com.masirezendegi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayzad on 5/3/17.
 */

public class Rezervable {

    String AdviserID,PlaceID,Name,RID,AdviserDate,AdviserTime,Free;

    public Rezervable() {

    }

    public String getAdviserID() {
        return AdviserID;
    }

    public void setAdviserID(String adviserID) {
        AdviserID = adviserID;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String placeID) {
        PlaceID = placeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getAdviserDate() {
        return AdviserDate;
    }

    public void setAdviserDate(String adviserDate) {
        AdviserDate = adviserDate;
    }

    public String getAdviserTime() {
        return AdviserTime;
    }

    public void setAdviserTime(String adviserTime) {
        AdviserTime = adviserTime;
    }

    public String getFree() {
        return Free;
    }

    public void setFree(String free) {
        Free = free;
    }
}
