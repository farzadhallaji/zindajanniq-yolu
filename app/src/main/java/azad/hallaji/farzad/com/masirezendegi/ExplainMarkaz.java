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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import cz.msebera.android.httpclient.Header;

public class ExplainMarkaz extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private static AsyncHttpClient client = new AsyncHttpClient();

    String placeid="0";
    ImageView userimg;
    String Adviser;
    TextView name_markaz_textview;
    TextView taxassose_markaz_textview;
    TextView code_markaz_textview;

    String PicAddress ,Address,AboutMainplace,MainPlaceName= " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_markaz);

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


        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(ExplainMarkaz.this , PageMarakez.class));
        }/*else if (id == R.id.nav_profile) {
            startActivity(new Intent(ExplainMarkaz.this , PageVirayesh.class));
        } else if (id == R.id.nav_setting) {
            //startActivity(new Intent(ExplainMarakez.this , MainActivity.class));
        }*/ else if (id == R.id.nav_login) {
            startActivity(new Intent(ExplainMarkaz.this , MainActivity.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(this , PagePorseshha.class));
        } /*else if (id == R.id.nav_logout){
            //startActivity(new Intent(ExplainMarakez.this , Test1.class));
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
