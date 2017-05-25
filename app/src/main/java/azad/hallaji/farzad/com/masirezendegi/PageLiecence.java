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
import com.crashlytics.android.Crashlytics;

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
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageLiecence extends AppCompatActivity {
    String adviseridm,commentscomments="";
    ListView listView;

    List<String> comments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liecence);

        Fabric.with(this, new Crashlytics());



        listView= (ListView)findViewById(R.id.lisenseid);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adviseridm= "0";
                commentscomments= "";
            } else {
                adviseridm= extras.getString("adviseridm");
                commentscomments= extras.getString("License");
            }
        } else {
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
            commentscomments= (String) savedInstanceState.getSerializable("License");
        }
        Log.i("asadasasfasadfd","asdfghjjhyrwefasdsvttbf");
        Log.i("asadasasfasadfd",commentscomments);

        updatelistview(commentscomments);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void updatelistview(String response) {


        try {

            JSONArray jsonArray = new JSONArray(response);

            Log.i("asadasasfasadfd",jsonArray.toString());

            for(int i=0 ; i<jsonArray.length() ; i++){

                String License = jsonArray.get(i).toString();
                comments.add(License);
                Log.i("asadasasfasadfd",jsonArray.get(i).toString());


            }
            Log.i("asadasasfasadfd",jsonArray.length()+"");


            LisenseAdapter listeAlagemandiHaAdapter = new LisenseAdapter(PageLiecence.this,comments);
            listView.setAdapter(listeAlagemandiHaAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}


        /*try {
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
        }*/



