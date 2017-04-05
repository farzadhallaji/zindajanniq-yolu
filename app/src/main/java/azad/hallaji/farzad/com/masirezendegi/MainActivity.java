package azad.hallaji.farzad.com.masirezendegi;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
public class MainActivity extends AppCompatActivity {


    String imei, wifimac, uniqueid, androidid, simno, operator, brand, model, android_sdk, android_version, height, width, device_size, version, os = "";
    String source;

    TextView textView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();

        textView = (TextView) findViewById(R.id.idLoadingTextview);
        progressBar = (ProgressBar) findViewById(R.id.loadprogs);
        progressBar.setVisibility(View.GONE);


        if (isOnline()) {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "jeddi", Toast.LENGTH_LONG).show();
            source =  imei + "/" + wifimac + "/" + uniqueid + "/" + androidid + "/" + simno + "/" + operator + "/" + brand + "/" + model
                    + "/" + android_sdk + "/" + android_version + "/" + height + "/" + width + "/" + device_size + "/" + version + "/" + os;

            requestData();


        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }

    }



    @SuppressLint("HardwareIds")
    private void initial() {

        device_size = "";
        version = "1.0";
        os = "android";

        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imei = mngr.getDeviceId();
            operator = mngr.getNetworkOperatorName();
            simno = mngr.getLine1Number();
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }

        try {
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            wifimac = info.getMacAddress();
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }

        try {

            androidid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        } catch (Exception e) {
            Log.i("errorMainActivity", e.toString());
        }

        try {

            androidid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            uniqueid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.NAME);
        } catch (Exception e) {
            Log.i("err", e.toString());
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = String.valueOf(displayMetrics.heightPixels);
        width = String.valueOf(displayMetrics.widthPixels);

        android_version = Build.VERSION.RELEASE;
        android_sdk = String.valueOf(Build.VERSION.SDK_INT);

        model = android.os.Build.MODEL;
        brand = Build.MANUFACTURER;


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


    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");

        p.setUri("http://telyar.dmedia.ir/webservice/check");

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }


    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData2(params[0],source);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }

    }
}