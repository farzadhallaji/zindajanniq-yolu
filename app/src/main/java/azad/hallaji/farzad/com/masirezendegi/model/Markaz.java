package azad.hallaji.farzad.com.masirezendegi.model;

import java.util.List;

public class Markaz {


    String lat;
    String longg;
    List<Moshaver> Moshaverin;
    String subject;
    String PicAddress;
    String MID;
    String Address;
    String AboutMainPlace;
    String MainPlaceName;
    String Telephone;
    String Distance;

    public Markaz(String lat, String longg, String picAddress, String MID, String address,
                  String aboutMainPlace, String mainPlaceName, String telephone, String distance) {
        this.lat = lat;
        this.longg = longg;
        PicAddress = picAddress;
        this.MID = MID;
        Address = address;
        AboutMainPlace = aboutMainPlace;
        MainPlaceName = mainPlaceName;
        Telephone = telephone;
        Distance = distance;
    }

    public Markaz(String latr, String aLong, String mid, String mainPlaceName) {
        lat = latr;
        longg = aLong;
        MID = mid;
        MainPlaceName = mainPlaceName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public List<Moshaver> getMoshaverin() {
        return Moshaverin;
    }

    public void setMoshaverin(List<Moshaver> moshaverin) {
        Moshaverin = moshaverin;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPicAddress() {
        return PicAddress;
    }

    public void setPicAddress(String picAddress) {
        PicAddress = picAddress;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAboutMainPlace() {
        return AboutMainPlace;
    }

    public void setAboutMainPlace(String aboutMainPlace) {
        AboutMainPlace = aboutMainPlace;
    }

    public String getMainPlaceName() {
        return MainPlaceName;
    }

    public void setMainPlaceName(String mainPlaceName) {
        MainPlaceName = mainPlaceName;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
