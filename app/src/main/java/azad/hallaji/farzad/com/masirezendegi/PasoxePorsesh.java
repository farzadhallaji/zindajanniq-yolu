package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import azad.hallaji.farzad.com.masirezendegi.helper.ListePasoxhayeksoalAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Pasox;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.id.message;


public class PasoxePorsesh extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView MozueSoalTextView ;
    TextView OnvaneSoalTextView;
    ListView listView;
    String qid="";
    ImageView sabizshey;
    LinearLayout likedisalage;
    boolean IsFavourite=false;
    RequestQueue MyRequestQueue;
    boolean josadsin=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasoxe_soal_ad);
        Fabric.with(this, new Crashlytics());


        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasoxePorsesh.this , PagePorseshha.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

        if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {
            josadsin=true;
        }
        likedisalage=(LinearLayout)findViewById(R.id.layeyelikalage);

        likedisalage.setVisibility(View.GONE);

        /*Bundle bundle = getIntent().getExtras();
        qid = bundle.getString("questionid");
*/
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) qid = "0";
            else {
                qid= extras.getString("questionid");
            }
        } else {
            qid= (String) savedInstanceState.getSerializable("questionid");
        }

        Log.i("lohsasasisankjnkjndjdjj",qid);


        MozueSoalTextView = (TextView)findViewById(R.id.MozueSoalTextView);
        OnvaneSoalTextView = (TextView)findViewById(R.id.OnvaneSoalTextView);
        listView=(ListView)findViewById(R.id.ListepasoxhaListView);



        //Toast.makeText(getApplicationContext(), qid, Toast.LENGTH_LONG).show();
        sabizshey= (ImageView)findViewById(R.id.sabizshey);
        Log.i("aasasasasaaaaa",qid);

        if(isOnline()){
            //requestData();
            postgetData(qid,GlobalVar.getDeviceID());


            ImageView afzudne=( ImageView)findViewById(R.id.afzudanepasox);
            if(GlobalVar.getUserType().equals("adviser")){
                afzudne.setVisibility(View.VISIBLE);
                afzudne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final LinearLayout afzundanejavab=(LinearLayout)findViewById(R.id.afzudanejavab);
                        afzundanejavab.setVisibility(View.VISIBLE);
                        final EditText matneafzundanejavab =(EditText)findViewById(R.id.matneafzundanejavab);
                        ImageView closeafzundanejavab = (ImageView)findViewById(R.id.closeafzundanejavab);
                        Button afzundanejavabbutton = (Button)findViewById(R.id.afzundanejavabbutton);
                        afzundanejavabbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendjavabejadid(matneafzundanejavab.getText().toString());
                            }
                        });
                        closeafzundanejavab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                afzundanejavab.setVisibility(View.GONE);
                            }
                        });

                    }
                });
            }


        }else{
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }

    }

    private void sendjavabejadid(final String s) {

        String url = "http://telyar.dmedia.ir/webservice/Set_answer/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("dfvflgnkjdfddfvflgnkjdfd", response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                responsesetfavor(response);



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
                MyData.put("text",s); //Add the data you'd like to send to the server.
                MyData.put("pid", qid); //Add the data you'd like to send to the server.
                MyData.put("adviserid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                Log.i("dfvflgnkjdfddfvflgnkjdfd", MyData.toString());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

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


    void postgetData(final String qid , final String deviceid){

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://telyar.dmedia.ir/webservice/get_question_answer";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("dfvflgnkjdfddfvflgnkjdfd",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                try {
                    updateview(response);
                }catch (Exception e){}

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected void onFinish() {

                sabizshey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(josadsin){
                            likedisalage.setVisibility(View.VISIBLE);
                        }else{

                            /*LayoutInflater inflater = getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());

                            dialogBuilder.setView(dialogView);

                            TextView textView =(TextView)dialogView.findViewById(R.id.aT);
                            TextView textView2 =(TextView)dialogView.findViewById(R.id.aaT);
                            textView.setText("");
                            textView2.setText("ابتدا باید وارد سیستم شوید");

                            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.cancel();

                                }
                            });*/
                            Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                ImageView startalagemandiImageView=(ImageView)findViewById(R.id.startalagemandiImageView);
                ImageView dislikeimmmm=(ImageView)findViewById(R.id.dislikeimmmm);
                ImageView likeimmmm=(ImageView)findViewById(R.id.likeimmmm);

                if(IsFavourite)
                    startalagemandiImageView.setImageResource(R.drawable.alage1);
                else
                    startalagemandiImageView.setImageResource(R.drawable.alage0);

                startalagemandiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(IsFavourite){
                            IsFavourite=false;
                            setAlage("-1");
                        }
                        else{
                            IsFavourite=true;
                            setAlage("1");
                        }
                    }
                });
                dislikeimmmm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likedislike(false ,"question",qid);
                    }
                });
                likeimmmm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likedislike(true,"question",qid);
                    }
                });
                ImageView closeinvisibleimag = (ImageView)findViewById(R.id.closeinvisibleimag);
                closeinvisibleimag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likedisalage.setVisibility(View.GONE);
                    }
                });

            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("questionid", qid); //Add the data you'd like to send to the server.
                MyData.put("deviceid",GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                MyData.put("userid",GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                Log.i("dfvflgnkjdfddfvflgnkjdfd",MyData.toString());

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }

    private void updateview(String response) {

        final List<Pasox> templist=new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.i("ahmadolgohuum",response.toString());
            MozueSoalTextView.setText(jsonObject.get("SName").toString());
            OnvaneSoalTextView.setText(jsonObject.get("QuestionSubject").toString());
            //OnvaneSoalTextView.setText(jsonObject.get("TextText").toString());
            Log.i("asastuikjhg",jsonObject.toString());
            IsFavourite = jsonObject.getString("IsFavourite").equals("1");
            ImageView startalagemandiImageView=(ImageView)findViewById(R.id.startalagemandiImageView);
            if(IsFavourite){
                startalagemandiImageView.setImageResource(R.drawable.alage1);
            }else {
                startalagemandiImageView.setImageResource(R.drawable.alage0);
            }
            Log.i("werhm,jhmghfgbf",IsFavourite + "sdfvd");
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("Answer").toString());

            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject1 =new JSONObject(jsonArray.get(i).toString());

                Pasox pasox = new Pasox(jsonObject1.getString("PicAddress")
                        , jsonObject1.getString("UserName"), jsonObject1.getString("Text")
                        , jsonObject1.getString("LikeCount"), jsonObject1.getString("DisLikeCount")
                        , jsonObject1.getString("RegTime"));

                pasox.setQid(qid);
                templist.add(pasox);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListePasoxhayeksoalAdapter listePasoxhayeksoalAdapter = new ListePasoxhayeksoalAdapter(PasoxePorsesh.this,templist);
        listView.setAdapter(listePasoxhayeksoalAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(josadsin){
                    final LinearLayout layeyelikesoal =(LinearLayout)findViewById(R.id.layeyelikesoal);
                    layeyelikesoal.setVisibility(View.VISIBLE);
                    TextView matnesoal=(TextView)findViewById(R.id.matnesoal);
                    ImageView likeimmmmsoal =(ImageView)findViewById(R.id.likeimmmmsoal);
                    ImageView dislikeimmmmsoal =(ImageView)findViewById(R.id.dislikeimmmmsoal);
                    ImageView closeinvisibleimagsoal =(ImageView)findViewById(R.id.closeinvisibleimagsoal);

                    matnesoal.setText(templist.get(position).getMatnepasox());

                    likeimmmmsoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            likedislike(true,"question",qid);
                        }
                    });
                    dislikeimmmmsoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            likedislike(false,"question",qid);

                        }
                    });
                    closeinvisibleimagsoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layeyelikesoal.setVisibility(View.GONE);
                        }
                    });
                }else{

                    /*LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    dialogBuilder.setView(dialogView);

                    TextView textView =(TextView)dialogView.findViewById(R.id.aT);
                    TextView textView2 =(TextView)dialogView.findViewById(R.id.aaT);
                    textView.setText("");
                    textView2.setText("ابتدا باید وارد سیستم شوید");

                    Button button = (Button)dialogView.findViewById(R.id.buttombastan);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();

                        }
                    });*/

                    Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();

                }

            }
        });



    }

    void setAlage(final String s) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        String url = "http://telyar.dmedia.ir/webservice/Set_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("aladfffgree", response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                responsesetfavorisfavor(response);

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
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("contentid", qid); //Add the data you'd like to send to the server.
                MyData.put("status", s); //Add the data you'd like to send to the server.
                MyData.put("contenttype", "question"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void likedislike(final boolean like , final String contenttypee , final String contentid) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        String url = "http://telyar.dmedia.ir/webservice/Set_like_dislike/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("dfvflgnkjdfddfvflgnkjdfd",response);
                responsesetfavor(response);

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("userid", GlobalVar.getUserID());
                String aaa="-1";
                if(like)
                    aaa="1";
                MyData.put("status", aaa); //Add the data you'd like to send to the server.
                MyData.put("contenttype", contenttypee); //Add the data you'd like to send to the server.
                MyData.put("contentid",contentid); //Add the data you'd like to send to the server.
                MyData.put("deviceid",GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                Log.i("dfvflgnkjdfddfvflgnkjdfd",MyData.toString());
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }

    private void responsesetfavor(String response) {

        Log.i("asadsfbghh nrjh nsdvsdc",response);
        String m="-1" ,  mmessage="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            m=jsonObject.getString("Status");
            ImageView startalagemandiImageView=(ImageView)findViewById(R.id.startalagemandiImageView);

            if(m.equals("1")){
                if(IsFavourite){
                    startalagemandiImageView.setImageResource(R.drawable.alage1);
                }else {
                    startalagemandiImageView.setImageResource(R.drawable.alage0);
                }
            }else {
                IsFavourite=!IsFavourite;
            }

            mmessage=jsonObject.getString("Message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //LayoutInflater inflater = this.getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        Toast.makeText(getApplicationContext(), mmessage, Toast.LENGTH_LONG).show();

        /*if(m.equals("1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(mmessage);

            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            *//*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*//*
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    Intent intent = new Intent(PasoxePorsesh.this,PagePorseshha.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);


                }
            });
        }else if(m.equals("-1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(mmessage);
            TextView te =(TextView)dialogView.findViewById(R.id.aT);
            te.setText("خطا");
            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            *//*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*//*
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });

        }*/
    }

    private void responsesetfavorisfavor(String response) {

        Log.i("asadsfbghh nrjh nsdvsdc",response);
        String m="-1" ,  mmessage="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            m=jsonObject.getString("Status");
            ImageView startalagemandiImageView=(ImageView)findViewById(R.id.startalagemandiImageView);

            if(m.equals("1")){
                if(IsFavourite){
                    startalagemandiImageView.setImageResource(R.drawable.alage1);
                }else {
                    startalagemandiImageView.setImageResource(R.drawable.alage0);
                }
            }else {
                IsFavourite=!IsFavourite;
            }

            mmessage=jsonObject.getString("Message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), mmessage, Toast.LENGTH_LONG).show();

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
        Intent intent = new Intent(PasoxePorsesh.this , PagePorseshha.class);
        finish();
        startActivity(intent);
    }

}





