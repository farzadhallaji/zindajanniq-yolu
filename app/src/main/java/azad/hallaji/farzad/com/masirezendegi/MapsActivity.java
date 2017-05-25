package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Markaz> markazs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String Mainplace="";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                if (isOnline()) {
                    requestData("0", "0", "0", "0", "0");
                } else {
                    Toast.makeText(getApplicationContext(), "internet isn't available !", Toast.LENGTH_LONG).show();
                }
            } else {
                Mainplace= extras.getString("Mainplace");
                Log.i("qwytumnhbgfvcds",Mainplace);
                LAsyncTask task = new LAsyncTask();
                task.execute(Mainplace);
            }
        } else {
            Mainplace= (String) savedInstanceState.getSerializable("Mainplace");
            LAsyncTask task = new LAsyncTask();
            task.execute(Mainplace);
        }

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

            try {
                //Log.i("qweerty",result);
                JSONArray jsonArray = new JSONArray(result);
                JSONObject tmp;
                markazs = new ArrayList<>();
                String PicAddress, MID, Address, AboutMainPlace, MainPlaceName, Telephone, Lat, Long, Distance = "";

                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            tmp = (JSONObject) jsonArray.get(i);
                            PicAddress = tmp.getString("PicAddress");
                            MID = tmp.getString("MID");
                            Address = tmp.getString("Address");
                            AboutMainPlace = tmp.getString("AboutMainPlace");
                            MainPlaceName = tmp.getString("MainPlaceName");
                            Telephone = tmp.getString("Telephone");
                            Lat = tmp.getString("Lat");
                            Long = tmp.getString("Long");
                            Distance = tmp.getString("Distance");

                            Markaz markaz = new Markaz(Lat, Long, PicAddress, MID, Address,
                                    AboutMainPlace, MainPlaceName, Telephone, Distance);
                            markazs.add(markaz);
                        } catch (Exception ignored) {
                        }

                    }

                    for (int i = 0; i < markazs.size(); i++) {


                        try {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(markazs.get(i).getLat()), Double.valueOf(markazs.get(i).getLongg())))
                                    .title(markazs.get(i).getMainPlaceName()));

                        } catch (Exception e) {
                            Log.i("null", "di ona gore");
                        }

                    }
                }


            } catch (JSONException e) {
                //e.printStackTrace();
            }


        }

    }
    private class LAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                //Log.i("qweerty",result);
                JSONArray jsonArray = new JSONArray(s);
                JSONObject tmp;
                markazs= new ArrayList<>();
                String  MID, MainPlaceName,Lat,Long;

                if(jsonArray.length()>0){
                    for(int i = 0 ; i < jsonArray.length() ; i++){

                        try {
                            tmp= (JSONObject) jsonArray.get(i);
                            MID=tmp.getString("MID");
                            MainPlaceName=tmp.getString("Name");
                            Lat=tmp.getString("Lat");
                            Long=tmp.getString("Long");

                            Markaz markaz = new Markaz(Lat, Long, MID, MainPlaceName);
                            markazs.add(markaz);

                        }catch (Exception ignored){}

                    }
                    //Toast.makeText(getApplicationContext(), markazs.size()+"", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), Double.valueOf( , Toast.LENGTH_LONG).show();

                    for(int i= 0 ; i < markazs.size() ; i++) {
                        try {
                            //Toast.makeText(getApplicationContext(),"*"+ markazs.get(0).getLat() + "*", Toast.LENGTH_LONG).show();

                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(markazs.get(i).getLat()),
                                    Double.valueOf(markazs.get(i).getLongg()))).title(markazs.get(i).getMainPlaceName()));

                            //Toast.makeText(getApplicationContext(), markazs.get(i).getMainPlaceName(), Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            /*Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
*/
                            Log.i("null", "di ona gore");
                        }

                    }
                }


            } catch (JSONException e) {
                //e.printStackTrace();
            }

        }
    }

}
