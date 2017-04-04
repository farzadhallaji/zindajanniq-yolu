package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;

public class ExplainMoshaver extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_moshaver);



        requestData();


        /*TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("nazar");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("map");

        tabSpec1.setIndicator("نظرات");
        tabSpec1.setContent(new Intent(this, MapsActivity.class));

        tabSpec2.setIndicator("مدارک");
        tabSpec2.setContent(new Intent(this, ListeMoshaverin.class));

        tabSpec2.setIndicator("نقشه");
        tabSpec2.setContent(new Intent(this, MapsActivity.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);*/



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_adviser_profile");
        p.setUri("http://telyar.dmedia.ir/webservice/Get_mainplace");

        //Output : AID,  AdviserName ,  Telephone , License , Tag , Dialect , Rating , PicAddress , CostPerMin, Gender , AboutMe , UniqueID , Comment : [comment,  UserName , UserFamilyName, RegTime]

        /*p.setParam("adviserid",  "1");
        p.setParam("deviceid", "3");*/

        p.setParam("lat",  "10");
        p.setParam("long", "22");
        p.setParam("start",  "0");
        p.setParam("adviserid", "1");
        p.setParam("adviserid",  "1");
        p.setParam("subjectid", "2");
        p.setParam("deviceid", "2");


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

            Toast.makeText(getApplicationContext(), "+"+result+"+", Toast.LENGTH_LONG).show();

        }

    }

}
