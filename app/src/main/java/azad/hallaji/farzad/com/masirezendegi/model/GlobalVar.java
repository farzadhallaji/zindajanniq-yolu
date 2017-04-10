package azad.hallaji.farzad.com.masirezendegi.model;


import java.util.ArrayList;
import java.util.List;

public class GlobalVar {
    private static String DeviceID;
    private static List<Comment> comments =new ArrayList<>();

    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public static List<Comment> getComments() {
        return comments;
    }

    public static void setComments(List<Comment> comments) {
        GlobalVar.comments = comments;
    }
}
