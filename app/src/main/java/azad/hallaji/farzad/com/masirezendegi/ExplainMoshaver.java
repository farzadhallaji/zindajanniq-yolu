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
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;

public class ExplainMoshaver extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    String adviseridm="100";
    ImageView userimg;
    TextView name_moshaver_textview;
    TextView taxassose_moshaver_textview;
    TextView code_moshaver_textview;
    String License="";
    List<Comment> comments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_moshaver);


        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplainMoshaver.this , Pagemenu.class);
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


        userimg = (ImageView)findViewById(R.id.adviser_image);
        name_moshaver_textview =(TextView) findViewById(R.id.name_moshaver_textview);
        taxassose_moshaver_textview =(TextView) findViewById(R.id.taxassose_moshaver_textview);
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
            Log.i("Lieseesene",License);

            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

            TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("nazar");
            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");
            TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("map");

            tabSpec1.setIndicator("نظرات");
            Intent intent1 =new Intent(this, ListeComments.class);
            intent1.putExtra("adviseridm",adviseridm);
            tabSpec1.setContent(intent1);

            tabSpec2.setIndicator("مدارک");
            Intent intent2 =new Intent(this, PageLiecence.class);
            intent2.putExtra("adviseridm",adviseridm);
            tabSpec2.setContent(intent2);

            tabSpec3.setIndicator("تخصص ها");
            Intent intent = new Intent(this, Taxassosexusus.class);
            intent.putExtra("adviseridm",adviseridm);
            tabSpec3.setContent(intent);

            tabHost.addTab(tabSpec1);
            tabHost.addTab(tabSpec2);
            tabHost.addTab(tabSpec3);


        } else {
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
            JSONObject  jsonObject= new JSONObject(response);


            List<String> strings=new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("Tag").toString());
            Log.i("asasfdghnghjyujyuj",response);

            try {
                for(int i= 0 ; i<jsonArray.length() ; i++){
                    strings.add((String) jsonArray.get(i));
                    Log.i("asasfdghnghjyujyuj",strings.get(0));
                }
            }catch (Exception ignored){}

            License = jsonObject.getString("License");
            Log.i("Lieseesene",License);
            String AID = jsonObject.get("AID").toString();
            String Dialect = jsonObject.get("Dialect").toString();
            String Rating = jsonObject.getString("Rating");
            String PicAddress = jsonObject.getString("PicAddress");
            String AboutMe = jsonObject.get("AboutMe").toString();
            String AdviserName = jsonObject.get("AdviserName").toString();
            String Telephone = jsonObject.getString("Telephone");
            String UniqueID = jsonObject.getString("UniqueID");
            String CostPerMin = jsonObject.get("AdviserName").toString();
            String IsFavourite = jsonObject.getString("Telephone");

            comments=new ArrayList<>();
            JSONArray jsonArray2 =new JSONArray(jsonObject.getJSONArray("Comment").toString());
            try{
                for(int i= 0 ; i<jsonArray2.length() ; i++){
                    JSONObject object = (JSONObject) jsonArray2.get(i);
                    Comment comment = new Comment(object.getString("comment"),object.getString("RegTime"),
                                                    object.getString("UserName"),object.getString("UserFamilyName"));
                    comments.add((comment));
                }
                Log.i("aasasasassddfgfgyjyua",comments.size()+" ");
            }catch (Exception e){

            }


            name_moshaver_textview.setText(AdviserName);
            taxassose_moshaver_textview.setText(AboutMe);
            code_moshaver_textview.setText(UniqueID);

            Moshaver moshaver = new Moshaver(AID,AdviserName ,strings,PicAddress,"");

            moshaver.setCostPerMin(CostPerMin);
            moshaver.setTelephone(Telephone);
            moshaver.setIsFavourite(IsFavourite);
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
            startActivity(new Intent(ExplainMoshaver.this , PageMarakez.class));
        }/*else if (id == R.id.nav_profile) {
            startActivity(new Intent(ExplainMoshaver.this , PageVirayesh.class));
        } else if (id == R.id.nav_setting) {
            //startActivity(new Intent(ExplainMoshaver.this , MainActivity.class));
        }*/ else if (id == R.id.nav_login) {
            startActivity(new Intent(ExplainMoshaver.this , PageLogin.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(ExplainMoshaver.this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(ExplainMoshaver.this , PagePorseshha.class));
        } /*else if (id == R.id.nav_logout){
            //startActivity(new Intent(Pagemenu.this , Test1.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

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
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
