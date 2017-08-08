package azad.hallaji.farzad.com.masirezendegi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import azad.hallaji.farzad.com.masirezendegi.helper.ListeTaxassoshaAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Taxassos;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListeTaxassussss extends AppCompatActivity {

    View ftView;
    ListView listView;
    ListeTaxassoshaAdapter adapter;
    List<Taxassos> totalList=new ArrayList<>();
    int tempcount=0;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_taxassussss);
        Fabric.with(this, new Crashlytics());

        listView=(ListView)findViewById(R.id.ListeTxassoshaListView);
        activity=this;

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);
        //mHandler = new MyHandler();

        if (isOnline()) {


            postgetData("0","0", GlobalVar.getDeviceID());


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
                    if(totalList.get(position).getHasChild().equals("1")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("آیا میخواهید خود موضوع را انتخاب کنید یا از بین زیر شاخه های آن؟");

                        builder.setPositiveButton(" موضوع اصلی", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListeTaxassussss.this,PageMoshaverin.class);
                                intent.putExtra("subjectid",totalList.get(position).getSID());
                                intent.putExtra("soallllllll","1");

                                Allllert(intent);
                            }
                        });
                        builder.setNegativeButton("از بین زیرشاخه ها ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s =totalList.get(position).getSID();
                                totalList=new ArrayList<Taxassos>();
                                postgetData(s,"0",GlobalVar.getDeviceID());
                                adapter=new ListeTaxassoshaAdapter(getApplicationContext(),totalList);
                                listView.setAdapter(adapter);
                                GlobalVar.moshaverindataxassusvirmisham=true;

                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();



                    }else {
                        //postgetData(totalList.get(position).getSID(),"0",GlobalVar.getDeviceID());
                        Intent intent = new Intent(ListeTaxassussss.this,PageMoshaverin.class);
                        intent.putExtra("subjectid",totalList.get(position).getSID());
                        intent.putExtra("soallllllll","1");

                        Allllert(intent);
                    }


                }
            });

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(totalList.size())+" : "+String.valueOf(view.getLastVisiblePosition()), Toast.LENGTH_LONG).show();

                    if(view.getLastVisiblePosition() == totalList.size()-1 ) {
                        //Toast.makeText(getApplicationContext(), view.getLastVisiblePosition(), Toast.LENGTH_LONG).show();

                        if(totalList.size()<19){}
                        else{
                            listView.addFooterView(ftView);
                            postgetData("0",String.valueOf((totalList.size()/20+1)*20),GlobalVar.getDeviceID());

                        }
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

    private void Allllert(final Intent intent) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(dialogView);

        TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
        textView.setText("تخصص مورد نظر شما انتخاب شد.");

        Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                //changeui(phone);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);


            }
        });

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

    void postgetData(final String pid , final String start , final String deviceid){

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Get_all_subject";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahsdfghmad",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                updatelistview(response);
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
                MyData.put("pid", pid); //Add the data you'd like to send to the server.
                MyData.put("start", start); //Add the data you'd like to send to the server.
                MyData.put("deviceid",deviceid); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview(String response) {


        List<Taxassos> templist=new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);

            for(int i= 0 ; i<jsonArray.length() ; i++){
                JSONObject s = (JSONObject) jsonArray.get(i);
                Taxassos temp = new Taxassos(s.get("SID").toString(),s.get("Name").toString(),
                        s.get("HasChild").toString());
                templist.add(temp);
            }

            totalList.addAll(templist);

            //Log.i("lisrtt",templist.toString());

            if(tempcount==0){
                adapter =new ListeTaxassoshaAdapter(ListeTaxassussss.this,totalList);
                listView.setAdapter(adapter);
                tempcount++;
            }
            adapter.notifyDataSetChanged();
            listView.removeFooterView(ftView);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {

        /*Intent intent = new Intent(this , Pagemenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/

        if(!GlobalVar.moshaverindataxassusvirmisham){
            Intent intent = new Intent(this , Pagemenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
        }else {
            Intent intent = new Intent(this , PageMoshaverin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("subjectid","0");
            GlobalVar.moshaverindataxassusvirmisham=false;
            //Toast.makeText(getApplicationContext() , "156156516", Toast.LENGTH_LONG).show();

            startActivity(intent);
        }
    }
}
