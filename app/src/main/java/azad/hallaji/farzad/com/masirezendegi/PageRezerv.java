package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.AdapterRezerv;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Rezervable;
import cz.msebera.android.httpclient.Header;

public class PageRezerv extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();

    String adviseridm="100",placeid="",namemoshaver="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_rezerv);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                placeid= "";
            } else {
                placeid= extras.getString("placeid");
                adviseridm= extras.getString("adviseridm");
                namemoshaver= extras.getString("namemoshaver");
            }
        } else {
            placeid= (String) savedInstanceState.getSerializable("placeid");
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
            namemoshaver= (String) savedInstanceState.getSerializable("namemoshaver");
        }

        TextView textView = ( TextView) findViewById(R.id.Titlasdfghgfds);
        textView.setText(textView.getText()+ " "+ namemoshaver);


        if(isOnline()){
            requestDataa();
        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }



    }


    private void requestDataa() {
        RequestParams params = new RequestParams();
        //‫‪Input:‬‬ ‫‪adviserid‬‬ ‫‪،‬‬ ‫‪deviceid‬‬ ‫‪،placeid‬‬
        params.put("adviserid",adviseridm); //Add the data you'd like to send to the server.
        params.put("placeid", placeid);
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/Show_adviser_time/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    List<Rezervable> rezervableList=new ArrayList<>();
                    Log.i("wertyuio",new String(responseBody));
                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for(int i = 0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
//set

//
                        /*List<String> RID =new ArrayList<>();
                        List<String> AdviserDate =new ArrayList<>();
                        List<String> AdviserTime =new ArrayList<>();
                        List<String> Free =new ArrayList<>();*/

                        JSONArray times = jsonObject.getJSONArray("Time");
                        for(int j = 0 ; j< times.length() ; j++){

                            Rezervable rezervable = new Rezervable();
                            rezervable.setAdviserID(jsonObject.getString("AdviserID"));
                            rezervable.setName(jsonObject.getString("Name"));
                            rezervable.setPlaceID(jsonObject.getString("PlaceID"));
                            JSONObject time = times.getJSONObject(j);
                            rezervable.setRID(time.getString("RID"));
                            rezervable.setAdviserDate(time.getString("AdviserDate"));
                            rezervable.setAdviserTime(time.getString("AdviserTime"));
                            rezervable.setFree(time.getString("Free"));
                            rezervableList.add(rezervable);

                        }
                    }

                    AdapterRezerv adapterRezerv = new AdapterRezerv(PageRezerv.this,rezervableList);
                    ListView listView = (ListView)findViewById(R.id.Listerezerva);
                    listView.setAdapter(adapterRezerv);
                    //updategraf(Message,s,editText.getText().toString());
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

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
