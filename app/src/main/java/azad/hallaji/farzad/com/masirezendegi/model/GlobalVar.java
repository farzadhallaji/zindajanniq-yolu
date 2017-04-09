package azad.hallaji.farzad.com.masirezendegi.model;


import java.util.List;

public class GlobalVar {
    private static String DeviceID;
    private static List<Comment> comments ;

    public static List<Comment> getComments() {
        return comments;
    }

    public static void setComments(List<Comment> comments) {
        GlobalVar.comments = comments;
    }



    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }
}
