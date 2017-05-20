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
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExplainMarkaz extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static AsyncHttpClient client2 = new AsyncHttpClient();



    String hanyayo="0";
    String placeid="0";
    ImageView userimg;
    String Adviser;
    TextView name_markaz_textview;
    TextView taxassose_markaz_textview;
    TextView code_markaz_textview;

    String PicAddress ,Address,AboutMainplace,MainPlaceName= " ";
    ImageView alagestarmarkaz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_markaz);

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExplainMarkaz.this , Pagemenu.class);
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

        if(GlobalVar.getUserID().equals("100")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }

        userimg = (ImageView)findViewById(R.id.markaz_image);
        name_markaz_textview =(TextView) findViewById(R.id.name_markaz_textview);
        taxassose_markaz_textview =(TextView) findViewById(R.id.taxassose_markaz_textview);
        code_markaz_textview =(TextView) findViewById(R.id.code_markaz_textview);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "0";
            } else {
                placeid= extras.getString("placeid");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
        }

        //Toast.makeText(getApplicationContext(), "+"+adviseridm+"+", Toast.LENGTH_LONG).show();

        if(isOnline()){
            requestData();

            //Set_favourite();

            //postgetData(placeid);
            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
            TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("nazar");
            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");



            tabSpec2.setIndicator("نقشه");
            Intent intent2 =new Intent(this, Liste_Moshaverine_Markaz.class);
            tabSpec2.setContent(intent2);

            tabSpec1.setIndicator("همکاران");
            Intent intent1 =new Intent(this, Liste_Moshaverine_Markaz.class);
            intent1.putExtra("placeid",placeid);
            tabSpec1.setContent(intent1);

            tabHost.addTab(tabSpec1);
            tabHost.addTab(tabSpec2);

            ImageView imageView2 = (ImageView)findViewById(R.id.alagestarmarkaz);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //requestData("asdfghjh",1);
                    //requestDataa();
                    Set_favourite();

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
    private void requestData(String subid , int start) {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Set_favourite/");

        p.setParam("deviceid", GlobalVar.getDeviceID());
        p.setParam("contenttype", "mainplace");
        p.setParam("status", hanyayo);
        p.setParam("contentid", placeid);
        p.setParam("userid",  GlobalVar.getUserID());


        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

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

    private void requestDataa() {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Set_favourite";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahmasdfghggfdad",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //updatelistview(response);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.i("ahmasdfghggfdad","asd");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                MyData.put("deviceid", GlobalVar.getDeviceID());
                MyData.put("contenttype", "mainplace");
                MyData.put("status", hanyayo);
                MyData.put("contentid", placeid);
                MyData.put("userid",  GlobalVar.getUserID());

                return MyData;
            }

            /*@Override
            *//**
             * Returns the raw POST or PUT body to be sent.
             *
             * @throws AuthFailureError in the event of auth failure
             *//*
            public byte[] getBody() throws AuthFailureError {
                //        Map<String, String> params = getParams();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id","1");
                params.put("name", "myname");
                if (params != null && params.size() > 0) {
                    return encodeParameters(params, getParamsEncoding());
                }
                return null;

            }*/
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void Set_favourite() {

        //userid=100 & status=1 & contenttype=adviser & contentid=2 & deviceid=3

        /*RequestParams params = new RequestParams();
        params.put("userid",  GlobalVar.getUserID());
        params.put("deviceid", GlobalVar.getDeviceID());
        params.put("contenttype", "mainplace");
        params.put("status", hanyayo);
        params.put("contentid", placeid);*/
        RequestParams params = new RequestParams();
        /*params.put("userid",  "100");
        params.put("deviceid", "3");
        params.put("contenttype", "mainplace");
        params.put("status", "1");
        params.put("contentid", "2");*/
        params.put("contenttype", "mainplace");

        client2.post("http://telyar.dmedia.ir/webservice/Set_favourite/", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Log.i(";asdfghgf","hfdsa " + responseBody.length);

                    Log.i("qwertyu",new String(responseBody));

                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    /*LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    dialogBuilder.setView(dialogView);

                    TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
                    textView.setText(jsonObject.getString("Message"));

                    if(jsonObject.getString("Status").equals("-1")){

                        TextView textViews =(TextView)dialogView.findViewById(R.id.aT);
                        textView.setText("خطا");

                    }else{
                        hanyayo=String.valueOf(-1*Integer.parseInt(hanyayo));
                        ImageView imageView = (ImageView)findViewById(R.id.alagestar);
                        if(hanyayo.equals("1")){
                            imageView.setImageResource(R.drawable.alage0);
                        }else {
                            imageView.setImageResource(R.drawable.alage1);
                        }
                    }
                    Button button = (Button)dialogView.findViewById(R.id.buttombastan);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.i(";asdfghgf",new String(responseBody));

            }
        });


    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("placeid",  String.valueOf(placeid));
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/Get_mainplaceID", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
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
                        AboutMainplace = jsonObject.getString("AboutMainplace");

                    }catch (Exception ignored){}
                    Log.i("asads",PicAddress + Address + AboutMainplace + MainPlaceName);

                    String IsFavourite= jsonObject.getString("IsFavourite");
                    if(IsFavourite.equals("-1")){
                        hanyayo="1";
                        ImageView imageView = (ImageView)findViewById(R.id.alagestarmarkaz);
                        imageView.setImageResource(R.drawable.alage0);
                    }else if(IsFavourite.equals("1")){
                        hanyayo="-1";
                        ImageView imageView = (ImageView)findViewById(R.id.alagestarmarkaz);
                        imageView.setImageResource(R.drawable.alage1);
                    }

                    Adviser=jsonObject.getString("Adviser");

                    new DownloadImageTask(userimg).execute(PicAddress);
                    name_markaz_textview.setText(MainPlaceName);
                    taxassose_markaz_textview.setText(Address);
                    code_markaz_textview.setText(AboutMainplace);
                    Log.i("uldu","miu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
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
            startActivity(new Intent(this , PageMarakez.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this , PageVirayesh.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(this , PageLogin.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(this , PagePorseshha.class));
        } else if (id == R.id.nav_logout){
            startActivity(new Intent(this , PageLogout.class));
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

}
