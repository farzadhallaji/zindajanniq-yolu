package azad.hallaji.farzad.com.masirezendegi.internet;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by fayzad on 4/13/17.
 */

public class MyLocationListener implements LocationListener {

    Location location;

    public void onLocationChanged(Location loc) {

        if (loc != null) {
            location = loc;

        }
    }

    public void onProviderDisabled(String provider) {

    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}