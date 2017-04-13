package azad.hallaji.farzad.com.masirezendegi;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.Provider;

import azad.hallaji.farzad.com.masirezendegi.internet.GPSTracker;
import azad.hallaji.farzad.com.masirezendegi.internet.GpsTracker3;
import azad.hallaji.farzad.com.masirezendegi.internet.MyLocationListener;

public class Tab1 extends Activity {

    private Object lov;

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }


    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        //getlov();

        //setLocation();

        get2();
    }

    public void setLocation() {




        LocationManager LM;
        String provider;
        Location Loc;

        LM = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        // adjust ur criteria with ur desired features

        provider = LM.getBestProvider(criteria, true);

        try {
            if(provider!=null) {
                Location location = LM.getLastKnownLocation(provider);
            }

            MyLocationListener locationListener = new MyLocationListener();

            LM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, locationListener);

            //LM.requestLocationUpdates(provider, 1000, 1, (LocationListener) this);

            Loc = LM.getLastKnownLocation(provider);

            if (Loc != null) {

                Toast.makeText(getApplicationContext(), (Loc.getLatitude())+" : "+(Loc.getLongitude()) , Toast.LENGTH_LONG).show();



            }

        } catch (Exception e) {
            //DisplayUnexpected();
            e.printStackTrace();
            return;
        }

        LM.removeUpdates((LocationListener) this);
    }

    private void get2() {

        GpsTracker3 gps = new GpsTracker3(Tab1.this);
        if(gps.canGetLocation()){
            String latitude = Double.toString(gps.getLatitude());
            String longitude = Double.toString(gps.getLongitude());
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude + " " , Toast.LENGTH_LONG).show();



        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showAlertDialog();
        }

    }


    public void getlov() {

        // create class object
        gps = new GPSTracker(Tab1.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            double Distance;
            Distance = distFrom((float) 36.5925907, 2.9051544f, 36.5805505f, 2.914749f);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude + " " +
                    "  diastance " + Distance, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}