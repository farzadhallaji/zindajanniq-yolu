package azad.hallaji.farzad.com.masirezendegi.model;


public class GlobalVar {
    private static String DeviceID;

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    private static String UserID="100";


    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

}
