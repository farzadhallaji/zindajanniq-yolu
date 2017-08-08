package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeMoshaverinAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import azad.hallaji.farzad.com.masirezendegi.model.Subject;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

public class ListeMoshaverin extends AppCompatActivity {

    View ftView;
    ListView listView;
    ListeMoshaverinAdapter adapter;
    List<Moshaver> totalList=new ArrayList<>();
    int tempcount=0;
    String subjectid="";
    private RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_moshaverin);
        Fabric.with(this, new Crashlytics());

        //totalList=new ArrayList<>();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) subjectid = "0";
            else {
                subjectid= extras.getString("subjectid");
                //Toast.makeText(getApplicationContext(), subjectid, Toast.LENGTH_SHORT).show();
            }
        } else {
            subjectid= (String) savedInstanceState.getSerializable("subjectid");
        }




        listView=(ListView)findViewById(R.id.ListeMoshaverinListView);

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);


        if (isOnline()) {
            //Toast.makeText(getApplicationContext(), "Network is available", Toast.LENGTH_LONG).show();
            //requestData(subjectid,0);
            method2(subjectid,"0");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), totalList.get(position).getAID(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication() , ExplainMoshaver.class);
                    intent.putExtra("adviserid",totalList.get(position).getAID());
                    //finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);

                }
            });


            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(totalList.size())+" : "+String.valueOf(view.getLastVisiblePosition()), Toast.LENGTH_LONG).show();
                    //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                    if(view.getLastVisiblePosition() == totalList.size()-1 ) {
                        //Toast.makeText(getApplicationContext(), view.getLastVisiblePosition(), Toast.LENGTH_LONG).show();

                        if(totalList.size()<19){

                        }else{
                            listView.addFooterView(ftView);
                            method2(subjectid,String.valueOf((totalList.size()/20)*20));
                        }       //TODO TEST sine den yazma
                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //Toast.makeText(getApplicationContext(), "onScroll", Toast.LENGTH_LONG).show();

                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }


    void method2(final String subid ,final String start) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://telyar.dmedia.ir/webservice/get_adviser/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("qwertyuioaladfffgree", response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                List<Moshaver> templist=new ArrayList<>();
                Log.i("saasasa",response) ;
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i= 0 ; i<jsonArray.length() ; i++){
                        JSONObject s = (JSONObject) jsonArray.get(i);
                        List<String>tag=new ArrayList<>();
                        JSONArray tags = (JSONArray) s.get("Tag");
                        //Toast.makeText(getApplicationContext(),String.valueOf(tags), Toast.LENGTH_LONG).show();

                        for(int j= 0 ;j < tags.length() ; j++){

                            tag.add(tags.get(j).toString());
                            //Toast.makeText(getApplicationContext(),tags.get(j).toString(), Toast.LENGTH_LONG).show();

                        }
                        Moshaver temp = new Moshaver(s.get("AID").toString(),s.get("AdviserName").toString(),
                                tag,s.get("PicAddress").toString(),s.get("CommentCount").toString());
                        temp.setUniqueID(s.getString("UniqueID"));
                        templist.add(temp);
                    }
                    totalList.addAll(templist);
                    if(tempcount==0){
                        adapter =new ListeMoshaverinAdapter(ListeMoshaverin.this,totalList);
                        listView.setAdapter(adapter);
                        tempcount++;
                    }
                    adapter.notifyDataSetChanged();
                    listView.removeFooterView(ftView);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);


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
                MyData.put("subjectid", subid);
                MyData.put("start", String.valueOf(start));
                MyData.put("deviceid", GlobalVar.getDeviceID());
                //Toast.makeText(getApplicationContext(), subid, Toast.LENGTH_SHORT).show();
                Log.i("qwertyuioaladfffgree",MyData.toString());//
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
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


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this , Pagemenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }
}
