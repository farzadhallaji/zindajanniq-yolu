package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.security.Provider;
import azad.hallaji.farzad.com.masirezendegi.internet.GPSTracker;
import azad.hallaji.farzad.com.masirezendegi.internet.GpsTracker;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class ListeMarakez extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_marakez);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        Intent intent = new Intent(ListeMarakez.this,Tab1.class);
        startActivity(intent);

        /*LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        //LatLng userLocation = new LatLng();*/

        //Log.i("asaas",location.getLatitude()+" / " +location.getLongitude());
        if (isOnline()) {
            requestData("0", "0", "0", "0", "0");
//            getLocation();
        } else {
            Toast.makeText(getApplicationContext(), "internet isn't available !", Toast.LENGTH_LONG).show();
        }


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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

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
            Log.i("ahmadigum","0000ahmadigum");
            Log.i("ahmadigum",result);

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject tmp;
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

                        }catch (Exception ignored){}

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



}
