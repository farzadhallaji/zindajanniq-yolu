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

import azad.hallaji.farzad.com.masirezendegi.helper.ListeAlagemandiHaAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.ALagemandi;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Markaz;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageAlaghemandiha extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static AsyncHttpClient client = new AsyncHttpClient();
    RequestQueue MyRequestQueue ;
    List<ALagemandi> aLagemandis =new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaghemandiha_page);

        Fabric.with(this, new Crashlytics());



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

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageAlaghemandiha.this , Pagemenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        listView = (ListView)findViewById(R.id.ListeAlagemandiHaListView);

        //Get_favourite
        //input : userid , contenttype , deviceid

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

        if(isOnline()){
            postgetData1();

            //Log.i("elabella1",aLagemandis.size()+ " ");
            //postgetData2();
            //postgetData3();
            //requestDataa();

            //requestDatsssa();

            Log.i("tutal",aLagemandis.size()+ " ");

            //requestData();

        }else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
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
    private void postgetData1(){

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://telyar.dmedia.ir/webservice/Get_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahmadabiabiabri",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray ads = new JSONArray(jsonObject.getString("Adviser"));
                    JSONArray mns = new JSONArray(jsonObject.getString("MainPlace"));
                    JSONArray qus = new JSONArray(jsonObject.getString("Question"));

                    Log.i("qwertyuiosdvgnghnghnghng",ads.toString());
                    Log.i("qwertyuiosdvgnghnghnghng",qus.toString());

                    Log.i("qwertyuiosdvgnghnghnghng",ads.length()+" ");

                    Log.i("qwertyuiosdvgnghnghnghng",mns.toString());

                    for(int i = 0 ; i < qus.length() ; i++){

                        JSONObject object =qus.getJSONObject(i);
                        String QID =object.getString("QID");
                        String QuestionSubject = object.getString("QuestionSubject");
                        //String spic =object.getString("PicAddress");
                        ALagemandi aLagemandi = new ALagemandi(QID,QuestionSubject,"پرسش","");
                        aLagemandis.add(aLagemandi);

                    }

                    for(int i = 0 ; i < ads.length() ; i++){

                        JSONObject object =ads.getJSONObject(i);
                        String QID =object.getString("AID");
                        String QuestionSubject = object.getString("UserName");
                        String spic =object.getString("PicAddress");
                        ALagemandi aLagemandi = new ALagemandi(QID,QuestionSubject,"مشاور",spic);
                        aLagemandis.add(aLagemandi);

                    }

                    for(int i = 0 ; i < mns.length() ; i++){

                        JSONObject object =mns.getJSONObject(i);
                        String QID =object.getString("MID");
                        String QuestionSubject = object.getString("MainPlaceName");
                        String spic =object.getString("PicAddress");
                        ALagemandi aLagemandi = new ALagemandi(QID,QuestionSubject,"مرکز",spic);
                        aLagemandis.add(aLagemandi);

                    }



                    //Log.i("qwertyuiosdvgnghnghnghng",mns.toString());
                    Log.i("qwertyuiosdvgnghnghnghng",aLagemandis.size()+" ");




                    /*updatelistview1(qus);
                    updatelistview2(ads);
                    updatelistview3(mns);*/




                    ListeAlagemandiHaAdapter listeAlagemandiHaAdapter = new ListeAlagemandiHaAdapter(PageAlaghemandiha.this,aLagemandis);
                    listView.setAdapter(listeAlagemandiHaAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ALagemandi aLagemandi = aLagemandis.get(position);
                            String typee = aLagemandi.getType();
                            if(typee.equals("مشاور")){
                                Intent intent = new Intent(PageAlaghemandiha.this,ExplainMoshaver.class);
                                intent.putExtra("adviserid",aLagemandi.getID());
                                finish();
                                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                                startActivity(intent);
                            }else if (typee.equals("پرسش")){
                                Intent intent = new Intent(PageAlaghemandiha.this,PasoxePorsesh.class);
                                intent.putExtra("questionid",aLagemandi.getID());
                                finish();
                                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                                startActivity(intent);
                            }else if (typee.equals("مرکز")){
                                Intent intent = new Intent(PageAlaghemandiha.this,ExplainMarkaz.class);
                                intent.putExtra("placeid",aLagemandi.getID());
                                finish();
                                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                                startActivity(intent);
                            }

                        }
                    });




                    ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                    progressbarsandaha.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                //MyData.put("contenttype", "question"); //Add the data you'd like to send to the server.
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview1(JSONArray jsonArray) {

        try {
            //JSONArray jsonArray = new JSONArray(response);
            for(int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String QID =jsonObject.getString("QID");
                String QuestionSubject = jsonObject.getString("QuestionSubject");
                /*String Text= jsonObject.getString("Text");
                String RegTime=jsonObject.getString("RegTime");
                String LikeCount=jsonObject.getString("LikeCount");
                String DisLikeCount=jsonObject.getString("DisLikeCount");
                String AnswerCount=jsonObject.getString("AnswerCount");*/
                ALagemandi aLagemandi = new ALagemandi(QID,QuestionSubject,"پرسش","");
                aLagemandis.add(aLagemandi);

            }

            Log.i("elabella",aLagemandis.size()+ " ");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void updatelistview2(JSONArray jsonArray) {

        Log.i("elabella12",aLagemandis.size()+ " "+ jsonArray.length());


        try {
            //JSONArray jsonArray = new JSONArray(response);
            for(int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String AID =jsonObject.getString("AID");
                String UserName = jsonObject.getString("UserName");
                String PicAddress= jsonObject.getString("PicAddress");
                String Telephone=jsonObject.getString("Telephone");

                List<String>Tags=new ArrayList<>();
                JSONArray tags = new JSONArray(jsonObject.getJSONArray("Tag").toString());
                try {
                    for (int ii= 0 ; ii < tags.length() ; ii++){
                        Tags.add(tags.getString(ii));
                    }
                }catch (Exception ignored){}

                String Rating=jsonObject.getString("Rating");
                String AdviserMaxTime=jsonObject.getString("AdviserMaxTime");
                String RegTime=jsonObject.getString("RegTime");
                String CommentCount=jsonObject.getString("CommentCount");

                ALagemandi question = new ALagemandi(AID,UserName,"مشاور",PicAddress);
                aLagemandis.add(question);

            }
            Log.i("elabella2",aLagemandis.size()+ " "+ jsonArray.length());

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        Log.i("elabella2",aLagemandis.size()+ " "+ jsonArray.length());

    }



    private void updatelistview3(JSONArray jsonArray) {

        Log.i("elabella34",jsonArray.length()+ " ");

        for(int i = 0 ; i < jsonArray.length() ; i++) {
            JSONObject jsonObject;
            String AID="";
            String UserName="";
            String PicAddress="";
            try {
                jsonObject= jsonArray.getJSONObject(i);
                AID= jsonObject.getString("MID");
                UserName = jsonObject.getString("MainPlaceName");
                PicAddress = jsonObject.getString("PicAddress");
            }catch (Exception ignored){}
            /*String Rating=jsonObject.getString("Rating");
            String AdviserMaxTime=jsonObject.getString("AdviserMaxTime");
            String RegTime=jsonObject.getString("RegTime");
            String CommentCount=jsonObject.getString("CommentCount");*/

            ALagemandi mush = new ALagemandi(AID, UserName, "مرکز", PicAddress);
            aLagemandis.add(mush);


        }
        Log.i("elabella3",aLagemandis.size()+ " "+ jsonArray.length());


        ListeAlagemandiHaAdapter listeAlagemandiHaAdapter = new ListeAlagemandiHaAdapter(PageAlaghemandiha.this,aLagemandis);
        listView.setAdapter(listeAlagemandiHaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ALagemandi aLagemandi = aLagemandis.get(position);
                String typee = aLagemandi.getType();
                if(typee.equals("مشاور")){
                    Intent intent = new Intent(PageAlaghemandiha.this,ExplainMoshaver.class);
                    intent.putExtra("adviserid",aLagemandi.getID());
                    finish();
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);
                }else if (typee.equals("پرسش")){
                    Intent intent = new Intent(PageAlaghemandiha.this,PasoxePorsesh.class);
                    intent.putExtra("questionid",aLagemandi.getID());
                    finish();
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);
                }else if (typee.equals("مرکز")){
                    Intent intent = new Intent(PageAlaghemandiha.this,ExplainMarkaz.class);
                    intent.putExtra("placeid",aLagemandi.getID());
                    finish();
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);
                }

            }
        });

    }


    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        p.setUri("http://telyar.dmedia.ir/webservice/Get_favourite/");

        p.setParam("contenttype", "question");
        p.setParam("userid", "100");
        p.setParam("deviceid", GlobalVar.getDeviceID());

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

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

    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
            Log.i("elabella",result);


        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void requestDataa() {
        RequestParams params = new RequestParams();

        params.put("contenttype", "mainplace"); //Add the data you'd like to send to the server.
        params.put("userid", GlobalVar.getUserID());
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/Get_favourite/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));

                    Log.i("aassdfghjuytrew",new String(responseBody));

                    JSONArray advisers =new JSONArray(jsonObject.getString("Adviser"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.i("aassdfghjuytrew","1");


            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        Intent intent =new Intent(this , Pagemenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }
}
