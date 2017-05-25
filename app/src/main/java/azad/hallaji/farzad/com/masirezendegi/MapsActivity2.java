package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Markaz;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Markaz> markazs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Fabric.with(this, new Crashlytics());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String Mainplace="";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

            }
            else {
                Mainplace= extras.getString("Mainplace");
            }
        } else {
            Mainplace= (String) savedInstanceState.getSerializable("Mainplace");
        }

        //Toast.makeText(getApplicationContext(), Mainplace, Toast.LENGTH_LONG).show();
        LAsyncTask task = new LAsyncTask();
        task.execute(Mainplace);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.67, 51.41),10));
    }


    private class LAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                //Toast.makeText(getApplicationContext(), jsonObject.getString("Lat") +","+ jsonObject.getString("Long")+ "\n"+jsonObject.getString("MainPlaceName"), Toast.LENGTH_LONG).show();

                try {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(
                            jsonObject.getString("Lat")), Double.valueOf(jsonObject.getString("Long"))))
                            .title(jsonObject.getString("MainPlaceName")));

                    //Toast.makeText(getApplicationContext(), "hallidi", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    Log.i("null", "di ona gore");
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }
    }
}