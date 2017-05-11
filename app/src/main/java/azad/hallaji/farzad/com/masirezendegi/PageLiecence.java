package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.helper.LisenseAdapter;
import azad.hallaji.farzad.com.masirezendegi.helper.ListeAlagemandiHaAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageLiecence extends AppCompatActivity {
    String adviseridm,License;
    ListView listView;

    List<String> comments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liecence);

        listView= (ListView)findViewById(R.id.lisenseid);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adviseridm= "0";
            } else {
                adviseridm= extras.getString("adviseridm");
                License= extras.getString("License");
            }
        } else {
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
        }

        if(isOnline()){

            try {
                JSONArray jsonArray = new JSONArray(License);
                for (int i= 0 ; i < jsonArray.length() ; i++){
                    comments.add(jsonArray.get(i).toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            LisenseAdapter listeAlagemandiHaAdapter = new LisenseAdapter(PageLiecence.this,comments);
            listView.setAdapter(listeAlagemandiHaAdapter);

            //postgetData();
        }else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
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
    void postgetData(){


        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Get_adviser_profile/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //Log.i("ahmad",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                updatelistview(response);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                MyData.put("adviserid", adviseridm); //Add the data you'd like to send to the server.
                MyData.put("deviceid",GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview(String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray= new JSONArray(jsonObject.getJSONArray("PicAddress").toString());

            for(int i=0 ; i<jsonObject.length() ; i++){

                String License = jsonArray.get(i).toString();
                comments.add(License);

            }
            LisenseAdapter listeAlagemandiHaAdapter = new LisenseAdapter(PageLiecence.this,comments);
            listView.setAdapter(listeAlagemandiHaAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
