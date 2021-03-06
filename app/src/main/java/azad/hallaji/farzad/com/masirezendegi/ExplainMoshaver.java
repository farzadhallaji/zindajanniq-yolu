package azad.hallaji.farzad.com.masirezendegi;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class ExplainMoshaver extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private static AsyncHttpClient client = new AsyncHttpClient();
    RequestQueue MyRequestQueue;
    String Tagggggggs="";
    boolean IsFavorite=false;
    String adviseridm="100",placeid="",namemoshaver="";
    ImageView userimg;
    TextView name_moshaver_textview;
    TextView taxassose_moshaver_textview;
    TextView code_moshaver_textview;
    String License="";
    String comments="";
    String Mainplace="";
    ImageView alagestarmoshaver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_moshaver);
        Fabric.with(this, new Crashlytics());

        alagestarmoshaver = (ImageView)findViewById(R.id.alagestarmoshaver);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "";
            } else {
                placeid= extras.getString("placeid");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
        }


        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplainMoshaver.this , PageMoshaverin.class);
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

        userimg = (ImageView)findViewById(R.id.adviser_image);
        name_moshaver_textview =(TextView) findViewById(R.id.name_moshaver_textview);
        //taxassose_moshaver_textview =(TextView) findViewById(R.id.taxassose_moshaver_textview);
        code_moshaver_textview =(TextView) findViewById(R.id.code_moshaver_textview);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adviseridm= "0";
            } else {
                adviseridm= extras.getString("adviserid");
            }
        } else {
            adviseridm= (String) savedInstanceState.getSerializable("adviserid");
        }

        //Toast.makeText(getApplicationContext(), "+"+adviseridm+"+", Toast.LENGTH_LONG).show();

        if(isOnline()){
            //requestData();

            postgetData();
            //Log.i("Lieseesene",License);

            ImageView imageView2 = (ImageView)findViewById(R.id.adviser_about_img);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //requestDataa();

                    if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {

                        Intent intent3 = new Intent(ExplainMoshaver.this,PageRezerv.class);
                        intent3.putExtra("adviseridm",adviseridm);
                        intent3.putExtra("placeid",placeid);
                        intent3.putExtra("namemoshaver",namemoshaver);

                        startActivity(intent3);

                    }else{
                        Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();
                    }


                }
            });


            //
            // final ImageView alagestarmarkaz = (ImageView)findViewById(R.id.alagestarmarkaz);

            alagestarmoshaver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.i("sxascsdcpsdcpsdcpsdc","[sxascsdcpsdcpsdcpsdc"+alagestarmarkaz.getResources());

                    if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {

                            setAlage();
                            //Log.i("sxascsdcpsdcpsdcpsdc","[sxascsdcp2sdcpsdcpsdc"+hanyayo);

                    }else{
                        Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();
                    }
                }
            });



            ImageView adviser_reserve_img = (ImageView) findViewById(R.id.adviser_reserve_img);
            adviser_reserve_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LinearLayout darbareyehuseyin = (LinearLayout)findViewById(R.id.darbareyehuseyin);
                    darbareyehuseyin.setVisibility(View.VISIBLE);
                    TextView textTagsHusseyin = (TextView)findViewById(R.id.textTagsHusseyin);
                    try {
                        textTagsHusseyin.setText(Tagggggggs);
                        ImageView closeafzundanejavab = (ImageView)findViewById(R.id.closeinvisibleimag);
                        closeafzundanejavab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                darbareyehuseyin.setVisibility(View.GONE);
                            }
                        });

                    }catch (Exception e){
                        textTagsHusseyin.setText("");
                    }

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    void setAlage() {

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

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    if (jsonObject.getString("Status").equals("1")){
                        IsFavorite=!IsFavorite;
                        if(IsFavorite) {
                            alagestarmoshaver.setImageResource(R.drawable.alage1);
                        }
                        else{
                            alagestarmoshaver.setImageResource(R.drawable.alage0);
                        }
                    }
                    Toast.makeText(getApplicationContext(),jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();




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
                String s = IsFavorite ? "-1" : "1";
                Map<String, String> MyData = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("contentid", adviseridm); //Add the data you'd like to send to the server.
                MyData.put("status", s); //Add the data you'd like to send to the server.
                MyData.put("contenttype", "adviser"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                Log.i("aladfffgree",MyData.toString());
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

    void postgetData(){


        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Get_adviser_profile/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ExplainMoshaver",response);
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
                MyData.put("userid",GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                Log.i("ExplainMoshaver",MyData.toString());

                return MyData;
            }

            @Override
            protected void onFinish() {

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);

                TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

                TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("nazar");
                TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");
                TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("map");

                tabSpec3.setIndicator("نظرات");
                Intent intent1 =new Intent(ExplainMoshaver.this, ListeComments.class);
                intent1.putExtra("adviseridm",adviseridm);
                intent1.putExtra("comments",comments);
                /*intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                Log.i("aaaaaaaaaaaaaaa",comments+" ");
                tabSpec3.setContent(intent1);

                tabSpec2.setIndicator("مدارک");
                Intent intent2 =new Intent(ExplainMoshaver.this, ListeLiecence.class);
                intent2.putExtra("adviseridm",adviseridm);
                intent2.putExtra("License",License);
                /*intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                tabSpec2.setContent(intent2);

                tabSpec1.setIndicator("نقشه");
                final Intent intent = new Intent(ExplainMoshaver.this, MapsActivity.class);
                intent.putExtra("adviseridm",adviseridm);
                intent.putExtra("Mainplace",Mainplace);
                intent.putExtra("menuya","ya");
                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                tabSpec1.setContent(intent);

                tabHost.addTab(tabSpec1);
                tabHost.addTab(tabSpec2);
                tabHost.addTab(tabSpec3);

                tabHost.setCurrentTab(2);
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview(String response) {

        try {
            JSONObject  jsonObject= new JSONObject(response);


            List<String> strings=new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("Tag").toString());
            Log.i("responseresponse",response);
            String tuzihat ="";
            try {
                for(int i= 0 ; i<jsonArray.length() ; i++){
                    tuzihat+=jsonArray.get(i);
                    if(i!=jsonArray.length()-1)
                        tuzihat+="  , ";
                    Log.i("asasfdghnghjyujyuj",tuzihat);
                }
            }catch (Exception ignored){}

            //JSONArray Licenses = new JSONArray(jsonObject.getJSONArray("License").toString());

            License = jsonObject.getString("License");
            Log.i("Lieseesene",License);
            String AID = jsonObject.get("AID").toString();
            String Dialect = jsonObject.get("Dialect").toString();
            String Rating = jsonObject.getString("Rating");
            String PicAddress = jsonObject.getString("PicAddress");
            String AboutMe = jsonObject.get("AboutMe").toString();
            String AdviserName = jsonObject.get("AdviserName").toString();
            namemoshaver= AdviserName;
            String Telephone = jsonObject.getString("Telephone");
            String UniqueID = jsonObject.getString("UniqueID");
            String CostPerMin = jsonObject.get("AdviserName").toString();
            IsFavorite= jsonObject.getString("IsFavourite").equals("1");
            Mainplace= jsonObject.getString("mainplace");
            //Log.i("Explanfkdfdkfdfdfdfdfd",IsFavourite);
            if(IsFavorite){
                alagestarmoshaver.setImageResource(R.drawable.alage1);
            }else{
                alagestarmoshaver.setImageResource(R.drawable.alage0);
            }
            comments=jsonObject.getJSONArray("Comment").toString();

            JSONArray tags =new JSONArray(jsonObject.getString("Tag"));
            for(int ii=0 ; ii< tags.length() ; ii++){
                Tagggggggs+=tags.getString(ii)+"\n";
            }

            name_moshaver_textview.setText(AdviserName);
//            taxassose_moshaver_textview.setText(tuzihat);
            code_moshaver_textview.setText(UniqueID);

            Moshaver moshaver = new Moshaver(AID,AdviserName ,strings,PicAddress,"");

            moshaver.setCostPerMin(CostPerMin);
            moshaver.setTelephone(Telephone);
            moshaver.setIsFavourite(IsFavorite ? "1" : "-1");
            moshaver.setDialect(Dialect);
            moshaver.setRating(Rating);

            new DownloadImageTask(userimg).execute(PicAddress);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            Intent intent =new Intent(this , PageMarakez.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                //e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            try{
                bmImage.setImageBitmap(result);
                result.recycle();
            }catch (Exception e){}
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.adviser_image));
        System.gc();
    }
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        //super.onBackPressed();
        Intent intent =new Intent(this , PageMoshaverin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
