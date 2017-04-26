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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeMoshaverinAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.ALagemandi;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import cz.msebera.android.httpclient.Header;

public class Liste_Moshaverine_Markaz extends AppCompatActivity {

    String placeid="";
    ListView ListeMoshaverinMarakezListView;
    private static AsyncHttpClient client = new AsyncHttpClient();
    List<Moshaver>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__liste__moshaverine__markaz);

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

        ListeMoshaverinMarakezListView =(ListView)findViewById(R.id.ListeMoshaverinMarakezListView);

        if(isOnline()){
            requestData();

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

                    JSONArray advisers =new JSONArray(jsonObject.getString("Adviser"));

                    for(int i= 0 ; i < advisers.length() ; i++){

                        Moshaver aLagemandi = new Moshaver(((JSONObject)advisers.get(i)).getString("AID"),
                                ((JSONObject)advisers.get(i)).getString("AdviserName"),
                                new ArrayList<String>(),((JSONObject)advisers.get(i)).getString("PicAddress"),
                                ((JSONObject)advisers.get(i)).getString("CommentCount"));

                        list.add(aLagemandi);
                    }

                    Log.i("1234y",list.size() + " ");

                    ListeMoshaverinAdapter adapter = new ListeMoshaverinAdapter(Liste_Moshaverine_Markaz.this,list);
                    ListeMoshaverinMarakezListView.setAdapter(adapter);

                    ListeMoshaverinMarakezListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(Liste_Moshaverine_Markaz.this , ExplainMoshaver.class);
                            intent.putExtra("adviserid", list.get(position).getAID());
                            startActivity(intent);

                        }
                    });


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
}
