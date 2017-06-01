package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListemarakezAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Markaz;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ListeMarakez extends AppCompatActivity {

    boolean isGPSEnabled=false;
    boolean isNetworkEnabled=false;
    boolean canGetLocation=false;
    Location location;
    double longitude,latitude;
    int MIN_TIME_BW_UPDATES=1000,MIN_DISTANCE_CHANGE_FOR_UPDATES=2000;

    ListView ListeMarakezListView;
    List <Markaz> markazs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_marakez);
        Fabric.with(this, new Crashlytics());


        //getLocation();

        ListeMarakezListView= (ListView)findViewById(R.id.ListeMarakezListView);
        //Log.i("asaas",location.getLatitude()+" / " +location.getLongitude());
        if (isOnline()) {
            requestData("0", "0", "0", "0", "0");

            ListeMarakezListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ListeMarakez.this,ExplainMarkaz.class);
                    intent.putExtra("placeid",markazs.get(position).getMID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    //intent.putExtra("Mainplace",markazs.get(position));
                    startActivity(intent);

                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "internet isn't available !", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void requestData(String lat ,String lon ,String start , String adviserid , String subjectid ) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        p.setUri("http://telyar.dmedia.ir/webservice/Get_mainplace");

        p.setParam("lat",  String.valueOf(lat));
        p.setParam("lon", String.valueOf(lon));
        p.setParam("adviserid", String.valueOf(adviserid));
        p.setParam("subjectid", String.valueOf(subjectid));
        p.setParam("start", String.valueOf(start));
        p.setParam("deviceid", GlobalVar.getDeviceID());



        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }

    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
            /*Log.i("ahmadigum","0000ahmadigum");
            Log.i("ahmadigum",result);
*/
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject tmp;
                markazs= new ArrayList<>();
                String PicAddress , MID, Address, AboutMainPlace,MainPlaceName,Telephone,Lat,Long,Distance="";
                if(jsonArray.length()>0){
                    for(int i = 0 ; i < jsonArray.length() ; i++){

                        try {
                            tmp= (JSONObject) jsonArray.get(i);
                            PicAddress=tmp.getString("PicAddress");
                            MID=tmp.getString("MID");
                            Address=tmp.getString("Address");
                            AboutMainPlace=tmp.getString("AboutMainPlace");
                            MainPlaceName=tmp.getString("MainPlaceName");
                            Telephone=tmp.getString("Telephone");
                            Lat=tmp.getString("Lat");
                            Long=tmp.getString("Long");
                            Distance=tmp.getString("Distance");
                            Log.i("asdfghfgfvdcynbfv",result);
                            Markaz markaz = new Markaz(Lat,  Long,  PicAddress,  MID,  Address,
                                    AboutMainPlace,  MainPlaceName, Telephone,  Distance);
                            markazs.add(markaz);
                        }catch (Exception ignored){}
                        ListemarakezAdapter listemarakezAdapter = new ListemarakezAdapter(ListeMarakez.this,markazs);
                        ListeMarakezListView.setAdapter(listemarakezAdapter);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
            progressbarsandaha.setVisibility(View.INVISIBLE);


        }

    }

    public Location getLocation() {
        try {
            LocationManager locationManager = (LocationManager) getBaseContext()
                    .getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get destination from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    //  Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(
                                LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                        // Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(
                                    LocationManager.GPS_PROVIDER);
                            if (location != null) {

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                //Toast.makeText(getApplicationContext(),latitude + " : "+ longitude, Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


}
