package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.helper.AdapterRezerv;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Rezervable;
import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageRezerv extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private static AsyncHttpClient client = new AsyncHttpClient();

    String adviseridm="100",placeid="",namemoshaver="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_rezerv);
        Fabric.with(this, new Crashlytics());




        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageRezerv.this , ExplainMoshaver.class);
                intent.putExtra("adviserid",adviseridm);
                startActivity(intent);
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.END);
            }
        });

        if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);

        }else{

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);

        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "";
            } else {
                placeid= extras.getString("placeid");
                adviseridm= extras.getString("adviseridm");
                namemoshaver= extras.getString("namemoshaver");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
            namemoshaver= (String) savedInstanceState.getSerializable("namemoshaver");
        }

        //TextView textView = ( TextView) findViewById(R.id.Titlasdfghgfds);
        //textView.setText(textView.getText()+ " "+ namemoshaver);


        if(isOnline()){
            requestDataa();
        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }



    }


    private void requestDataa() {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestParams params = new RequestParams();
        //‫‪Input:‬‬ ‫‪adviserid‬‬ ‫‪،‬‬ ‫‪deviceid‬‬ ‫‪،placeid‬‬
        params.put("adviserid",adviseridm); //Add the data you'd like to send to the server.
        params.put("placeid", placeid);
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/Show_adviser_time/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    final List<Rezervable> rezervableList=new ArrayList<>();
                    Log.i("wertyuio",new String(responseBody));
                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for(int i = 0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
//set

//
                        /*List<String> RID =new ArrayList<>();
                        List<String> AdviserDate =new ArrayList<>();
                        List<String> AdviserTime =new ArrayList<>();
                        List<String> Free =new ArrayList<>();*/

                        JSONArray times = jsonObject.getJSONArray("Time");
                        for(int j = 0 ; j< times.length() ; j++){

                            Rezervable rezervable = new Rezervable();
                            rezervable.setAdviserID(jsonObject.getString("AdviserID"));
                            rezervable.setName(jsonObject.getString("Name"));
                            rezervable.setPlaceID(jsonObject.getString("PlaceID"));
                            JSONObject time = times.getJSONObject(j);
                            rezervable.setRID(time.getString("RID"));
                            rezervable.setAdviserDate(time.getString("AdviserDate"));
                            rezervable.setAdviserTime(time.getString("AdviserTime"));
                            rezervable.setFree(time.getString("Free"));
                            rezervableList.add(rezervable);

                        }
                    }

                    AdapterRezerv adapterRezerv = new AdapterRezerv(PageRezerv.this,rezervableList);
                    ListView listView = (ListView)findViewById(R.id.Listerezerva);
                    listView.setAdapter(adapterRezerv);
                    //updategraf(Message,s,editText.getText().toString());
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            /*‫‪Reserve‬‬*/
                            /*‫‪Output‬‬ ‫‪:‬‬ ‫‪Message‬‬ ‫‪,‬‬ ‫‪ReserveID‬‬*/
                            String placeid=rezervableList.get(position).getPlaceID();
                            String adviserid=rezervableList.get(position).getAdviserID();
                            String rid=rezervableList.get(position).getRID();
                            //Reserve(placeid,adviserid,rid);
                            Reserve2(placeid,adviserid,rid);
                            //requestData(placeid,adviserid,rid);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("aassdfghjuytrew","1");
                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void Reserve(String placeID, String adviserID, String rid) {

        RequestParams params = new RequestParams();
        //‫‪Input‬‬ ‫‪:‬‬ ‫‪deviceid‬‬ ‫‪,‬‬ ‫‪placeid‬‬ ‫‪,‬‬ ‫‪adviserid‬‬ ‫‪,‬‬ ‫‪userid‬‬ ‫‪,‬‬ ‫‪rid‬‬
        params.put("adviserid",adviserID); //Add the data you'd like to send to the server.
        params.put("rid",rid); //Add the data you'd like to send to the server.
        params.put("placeid", placeID);
        params.put("userid", GlobalVar.getUserID());
        params.put("deviceid", GlobalVar.getDeviceID());
        /*params.put("adviserid","177"); //Add the data you'd like to send to the server.
        params.put("rid","1"); //Add the data you'd like to send to the server.
        params.put("placeid", "2");
        params.put("userid", "2");
        params.put("deviceid", "3");*/
        client.post("http://telyar.dmedia.ir/webservice/Reserve", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    final List<Rezervable> rezervableList=new ArrayList<>();
                    Log.i("wertyuio",new String(responseBody));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("aassdfghjuytrew","1");
            }
        });

    }
    private void Reserve2(final String placeID, final String adviserID, final String rid) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Reserve";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ExplainMoshaver",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

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
                Map<String, String> params = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                params.put("adviserid",adviserID); //Add the data you'd like to send to the server.
                params.put("rid",rid); //Add the data you'd like to send to the server.
                params.put("placeid", placeID);
                params.put("userid", GlobalVar.getUserID());
                params.put("deviceid", GlobalVar.getDeviceID());
                return params;
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


    private void requestData(String placeID, String adviserID, String rid) {

        RequestPackage params = new RequestPackage();
        params.setMethod("POST");
        params.setUri("http://telyar.dmedia.ir/webservice/Reserve/");

        params.setParam("adviserid",adviserID); //Add the data you'd like to send to the server.
        params.setParam("rid",rid); //Add the data you'd like to send to the server.
        params.setParam("placeid", placeID);
        params.setParam("userid", GlobalVar.getUserID());
        params.setParam("deviceid", GlobalVar.getDeviceID());

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(params);

    }


    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("saasasa","asdf") ;
            Log.i("saasasa",result) ;

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            Intent intent =new Intent(this , PageMarakez.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent =new Intent(this , PageVirayesh.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            Intent intent =new Intent(this , PageLogin.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_moshaverin) {
            Intent intent =new Intent(this , PageMoshaverin.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_porseshha) {
            Intent intent =new Intent(this , PagePorseshha.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_logout){
            Intent intent =new Intent(this , PageLogout.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        super.onBackPressed();
    }

}
