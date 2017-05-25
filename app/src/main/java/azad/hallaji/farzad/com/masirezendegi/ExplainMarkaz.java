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
    //private static AsyncHttpClient client2 = new AsyncHttpClient();

    RequestQueue MyRequestQueue;
    String hanyayo="0";
    String placeid="0";
    ImageView userimg;
    String Adviser;
    TextView name_markaz_textview;
    TextView taxassose_markaz_textview;
    TextView code_markaz_textview;
    String PicAddress ,Address,AboutMainplace,MainPlaceName= " ";
    ImageView alagestarmarkaz;

    String MainplaceMainplace="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_markaz);
        alagestarmarkaz = (ImageView)findViewById(R.id.alagestarmarkazasdfgfd);

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
            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
            TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("nazar");
            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("madarek");



            tabSpec2.setIndicator("نقشه");
            Intent intent2 =new Intent(this, MapsActivity.class);
            intent2.putExtra("Mainplace",MainplaceMainplace);
            tabSpec2.setContent(intent2);

            tabSpec1.setIndicator("همکاران");
            Intent intent1 =new Intent(this, Liste_Moshaverine_Markaz.class);
            intent1.putExtra("placeid",placeid);
            tabSpec1.setContent(intent1);

            tabHost.addTab(tabSpec2);
            tabHost.addTab(tabSpec1);

            tabHost.setCurrentTab(1);

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

    void setAlage() {

        MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/Set_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("aladfffgree", response);

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    if (jsonObject.getString("Status").equals("1")){
                        if(hanyayo.equals("1")) {
                            alagestarmarkaz.setImageResource(R.drawable.alage1);
                            hanyayo="-1";
                        }
                        else{
                            alagestarmarkaz.setImageResource(R.drawable.alage0);
                            hanyayo="1";
                        }
                    }
                    /*else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    }*/
                    Toast.makeText(getApplicationContext(),jsonObject.getString("Message"), Toast.LENGTH_LONG).show();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",  GlobalVar.getUserID());
                params.put("deviceid", GlobalVar.getDeviceID());
                params.put("contenttype", "mainplace");
                params.put("status", hanyayo);
                params.put("contentid", placeid);
                params.put("contenttype", "mainplace");
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
                        alagestarmarkaz.setImageResource(R.drawable.alage0);
                    }else if(IsFavourite.equals("1")){
                        hanyayo="-1";
                        alagestarmarkaz.setImageResource(R.drawable.alage1);
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
