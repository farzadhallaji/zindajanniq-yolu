package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeMoshaverinAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.CustomRequest;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExplainMarkaz extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private static AsyncHttpClient client = new AsyncHttpClient();
    //private static AsyncHttpClient client2 = new AsyncHttpClient();

    boolean IsFavourite=false;
    RequestQueue MyRequestQueue;
    String placeid="0";
    ImageView userimg;
    String Adviser;
    TextView name_markaz_textview;
    TextView taxassose_markaz_textview;
    TextView code_markaz_textview;
    String PicAddress ,Address,AboutMainplace,MainPlaceName= " ";
    ImageView alagestarmarkaz;

    TextView textdarvabasasHusseyin ;

    String MainplaceMainplace="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_markaz);
        Fabric.with(this, new Crashlytics());

        alagestarmarkaz = (ImageView)findViewById(R.id.alagestarmarkazasdfgfd);

        textdarvabasasHusseyin=(TextView)findViewById(R.id.textdarvabasasHusseyin);

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplainMarkaz.this , PageMarakez.class);
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

        userimg = (ImageView)findViewById(R.id.markaz_image);
        name_markaz_textview =(TextView) findViewById(R.id.name_markaz_textview);
        taxassose_markaz_textview =(TextView) findViewById(R.id.taxassose_markaz_textview);
        code_markaz_textview =(TextView) findViewById(R.id.code_markaz_textview);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "0";
                //MainplaceMainplace= "";
            } else {
                placeid= extras.getString("placeid");
                //MainplaceMainplace= extras.getString("Mainplace");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
            //MainplaceMainplace= (String) savedInstanceState.getSerializable("Mainplace");
        }

        //Toast.makeText(getApplicationContext(), "+"+adviseridm+"+", Toast.LENGTH_LONG).show();

        if(isOnline()){
            requestData();

            //Set_favourite();

            //postgetData(placeid);


            //ImageView imageView2 = (ImageView)findViewById(R.id.alagestarmarkazasdfgfd);
            alagestarmarkaz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //Toast.makeText(getApplicationContext(),"Message", Toast.LENGTH_LONG).show();


                    if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {
                        setAlage();
                    }else{
                        Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();
                    }

                }
            });

            /*alagestarmarkaz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        setAlage();
                }
            });*/

            ImageView adviser_reserve_img = (ImageView) findViewById(R.id.adviser_reserve_img);
            adviser_reserve_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LinearLayout darbareyehuseyin = (LinearLayout)findViewById(R.id.darbareyehuseyin);
                    darbareyehuseyin.setVisibility(View.VISIBLE);
                    TextView textTagsHusseyin = (TextView)findViewById(R.id.textTagsHusseyin);
                    textTagsHusseyin.setMovementMethod(new ScrollingMovementMethod());
                    try {
                        textTagsHusseyin.setText(Address);
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


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    void setAlage() {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Set_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("aladfffgree", response);
                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    if (jsonObject.getString("Status").equals("1")){
                        IsFavourite=!IsFavourite;

                        if(IsFavourite) {

                            alagestarmarkaz.setImageResource(R.drawable.alage1);
                        }
                        else{
                            alagestarmarkaz.setImageResource(R.drawable.alage0);
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
            }
        }) {
            protected Map<String, String> getParams() {
                String isfav=  IsFavourite ? "-1" :"1";

                Log.i("asdgmh,mnbv",isfav);
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",  GlobalVar.getUserID());
                params.put("deviceid", GlobalVar.getDeviceID());
                params.put("contenttype", "mainplace");
                params.put("status", isfav);
                params.put("contentid", placeid);
                return params;
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
            mmessage=jsonObject.getString("Message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        if(m.equals("1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(mmessage);

            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

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

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });

        }
    }

    private void requestData() {

        RequestParams params = new RequestParams();
        params.put("placeid",  String.valueOf(placeid));
        params.put("deviceid", GlobalVar.getDeviceID());
        params.put("userid", GlobalVar.getUserID());

        Log.i("asadafasadaddsds",params.toString());
        client.post("http://telyar.dmedia.ir/webservice/Get_mainplaceID", params, new AsyncHttpResponseHandler() {
            @Override
            public void onFinish() {

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onStart() {

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res="";
                try {
                    res=new String(responseBody);
                    Log.i("asadafasadaddsds",res);
                    JSONObject jsonObject = new JSONObject(res);
                    Log.i("asdfghytrewiuytr",jsonObject.toString());
                    try {
                        MainPlaceName = jsonObject.getString("MainPlaceName");

                    }catch (Exception ignored){}
                    try {
                        PicAddress = jsonObject.getString("PicAddress");
                    }catch (Exception ignored){}
                    try {
                        Address = jsonObject.getString("Address");

                    }catch (Exception ignored){}
                    try {
                        AboutMainplace = jsonObject.getString("AboutMainPlace");
                        textdarvabasasHusseyin.setText(AboutMainplace);
                        textdarvabasasHusseyin.setMovementMethod(new ScrollingMovementMethod());

                        //Log.i("asaasasasasasasads",AboutMainplace );

                    }catch (Exception ignored){}
                    //Log.i("asaasasasasasasads",PicAddress + Address + AboutMainplace + MainPlaceName);

                    IsFavourite= jsonObject.getString("IsFavourite").equals("1");
                    if(IsFavourite){
                        alagestarmarkaz.setImageResource(R.drawable.alage1);
                    }else {
                        alagestarmarkaz.setImageResource(R.drawable.alage0);
                    }

                    Adviser=jsonObject.getString("Adviser");

                    JSONObject jsonObject1 =new JSONObject();
                    jsonObject1.put("Lat",jsonObject.getString("Lat"));
                    jsonObject1.put("Long",jsonObject.getString("Long"));
                    jsonObject1.put("Name",jsonObject.getString("MainPlaceName"));
                    try {
                        jsonObject1.put("MID",jsonObject.getString("MID"));

                    }catch (Exception e){
                        jsonObject1.put("MID","0");
                    }

                    //Log.i("asdfghytrewiuytrsasas",jsonObject1.toString());


                    new DownloadImageTask(userimg).execute(PicAddress);
                    name_markaz_textview.setText(MainPlaceName);
                    try {
                        taxassose_markaz_textview.setText("کد مرکز : " +placeid );

                    }catch (Exception e){}
                    //code_markaz_textview.setText();
                    Log.i("uldu","miu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
                TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("nazar");
                TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");



                tabSpec2.setIndicator("نقشه");
                Intent intent2 =new Intent(ExplainMarkaz.this, MapsActivity2.class);
                intent2.putExtra("Mainplace",res);
                intent2.putExtra("hardandi","marakez");
                /*intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                tabSpec2.setContent(intent2);

                tabSpec1.setIndicator("همکاران");
                Intent intent1 =new Intent(ExplainMarkaz.this, Liste_Moshaverine_Markaz.class);
                intent1.putExtra("placeid",placeid);
                intent1.putExtra("Adviser",Adviser);
                /*intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                tabSpec1.setContent(intent1);

                tabHost.addTab(tabSpec2);
                tabHost.addTab(tabSpec1);

                tabHost.setCurrentTab(1);





            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            }
        });

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        //super.onBackPressed();
        /*Intent intent = new Intent(ExplainMarkaz.this , PageMarakez.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/

        Intent intent =new Intent(this , PageMarakez.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

}
