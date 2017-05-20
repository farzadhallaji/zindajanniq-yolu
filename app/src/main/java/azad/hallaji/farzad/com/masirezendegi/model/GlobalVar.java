package azad.hallaji.farzad.com.masirezendegi.model;


import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.Menu;

import azad.hallaji.farzad.com.masirezendegi.R;

public class GlobalVar {

    private static String DeviceID;
    private static String UserType="user";
    private static String PicAddress="";
    private static String UserID="100";

    public static String getUserType() {
        return UserType;
    }

    public static void setUserType(String userType) {
        UserType = userType;
    }

    public static String getPicAddress() {
        return PicAddress;
    }

    public static void setPicAddress(String picAddress) {
        PicAddress = picAddress;
    }

    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    public static void Jirmiyib(Activity activity)
    {
        NavigationView navigationView = (NavigationView)activity.findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
        nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
        nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);

    }
    public static void Jirib(Activity activity)
    {
        NavigationView navigationView = (NavigationView)activity.findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
        nav_Menu.findItem(R.id.nav_profile).setVisible(true);
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
        nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
        nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);

    }

}
