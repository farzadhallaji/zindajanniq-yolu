package azad.hallaji.farzad.com.masirezendegi;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;

@SuppressWarnings("deprecation")
public class MarakeZ extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView imageView = (ImageView)findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.END);

            }
        });

        TabHost tabHost =(TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Map");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Partners");

        tabSpec1.setIndicator("نقشه");
        //tabSpec1.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));
        tabSpec1.setContent(new Intent( this , MapsActivity.class));

        tabSpec2.setIndicator("همکاران");
        //tabSpec2.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));
        tabSpec2.setContent(new Intent( this , Tab1.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);





        if (isOnline()) {
            requestData();
        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            // Handle the camera action
            Intent intent = new Intent(MarakeZ.this , MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_profile) {

            Intent intent = new Intent(MarakeZ.this , MapsActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/get_mainplace");

        p.setParam("lat", "1");
        p.setParam("long", "1");
        p.setParam("start", "1");
        p.setParam("adviserid", "1");
        p.setParam("subjectid", "1");
        p.setParam("deviceid", "1");


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

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            JSONObject jsonObject = null;
            String MID, MainPlaceName, Address, Telephone, PicAddress, AboutMainplace, Lat, Long, Distance = "";
            try {

                jsonObject = new JSONObject(result);
                MID = jsonObject.getString("MID");
                MainPlaceName = jsonObject.getString("MainPlaceName");
                Address = jsonObject.getString("Address");
                Telephone = jsonObject.getString("Telephone");
                PicAddress = jsonObject.getString("PicAddress");
                AboutMainplace = jsonObject.getString("AboutMainplace");
                Lat = jsonObject.getString("Lat");
                Long = jsonObject.getString("Long");
                Distance = jsonObject.getString("Distance");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
