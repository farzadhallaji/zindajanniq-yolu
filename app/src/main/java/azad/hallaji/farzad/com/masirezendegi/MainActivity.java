package azad.hallaji.farzad.com.masirezendegi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;

public class MainActivity extends AppCompatActivity {


    String imei,wifimac,uniqueid,androidid,simno,operator,brand,model
            ,android_sdk,android_version,height,width,device_size,version,os="";


    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();

        textView = (TextView)findViewById(R.id.idLoadingTextview);
        progressBar=(ProgressBar)findViewById(R.id.loadprogs);
        progressBar.setVisibility(View.GONE);




        if (isOnline()) {

            Toast.makeText(getApplicationContext(), asad(), Toast.LENGTH_LONG).show();


            progressBar.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }




    private String asad() {

        BufferedReader reader = null;

        try {
            URL url = new URL("http://telyar.dmedia.ir/webservice/check");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("magent",  imei+"/"+wifimac+"/"+uniqueid+"/"+androidid+"/"+simno+"/"+operator+"/"+brand+"/"+model
                    +"/"+android_sdk+"/"+android_version+"/"+height+"/"+width+"/"+device_size+"/"+version+"/"+os);

            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.flush();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Toast.makeText(getApplicationContext(), sb.toString() , Toast.LENGTH_LONG).show();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }


            return sb.toString();

        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return "catch1";

        } catch (IOException e) {
            //e.printStackTrace();
            return "catch3";

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    return "catch2";
                }
            }
        }

    }


    @SuppressLint("HardwareIds")
    private void initial() {

        device_size="";
        version="1.0";
        os= "android";

        try{
            TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imei =mngr.getDeviceId();
            operator=mngr.getNetworkOperatorName();
            simno= mngr.getLine1Number();
        }catch (Exception e){
            Log.i("errorMainActivity",e.toString());
        }

        try{
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            wifimac = info.getMacAddress();
        }catch (Exception e){
            Log.i("errorMainActivity",e.toString());
        }

        try{

            androidid= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        }catch (Exception e){
            Log.i("errorMainActivity",e.toString());
        }

        try{

            androidid= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        }catch (Exception e){
            Log.i("err",e.toString());
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height=String.valueOf(displayMetrics.heightPixels);
        width=String.valueOf(displayMetrics.widthPixels);

        android_version= Build.VERSION.RELEASE;
        android_sdk=String.valueOf(Build.VERSION.SDK_INT);

        model= android.os.Build.MODEL;
        brand = Build.MANUFACTURER;

        String source="magent"+":"+imei+"/"+wifimac+"/"+uniqueid+"/"+androidid+"/"+simno+"/"+operator+"/"+brand+"/"+model
                +"/"+android_sdk+"/"+android_version+"/"+height+"/"+width+"/"+device_size+"/"+version+"/"+os;


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

}