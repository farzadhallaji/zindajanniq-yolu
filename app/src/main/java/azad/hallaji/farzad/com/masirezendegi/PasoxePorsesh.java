package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.helper.ListePasoxhayeksoalAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Pasox;


public class PasoxePorsesh extends AppCompatActivity {

    TextView MozueSoalTextView ;
    TextView OnvaneSoalTextView;
    TextView TarixePorsesh;
    ListView listView;
    String qid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasoxe_porsesh);

        Bundle bundle = getIntent().getExtras();
        qid = bundle.getString("questionid");

        MozueSoalTextView = (TextView)findViewById(R.id.MozueSoalTextView);
        OnvaneSoalTextView = (TextView)findViewById(R.id.OnvaneSoalTextView);
        TarixePorsesh = (TextView)findViewById(R.id.TarixePorsesh);
        listView=(ListView)findViewById(R.id.ListepasoxhaListView);


        //Toast.makeText(getApplicationContext(), qid, Toast.LENGTH_LONG).show();

        Log.i("aasasasasaaaaa",qid);

        if(isOnline()){
            //requestData();
            postgetData(qid,GlobalVar.getDeviceID());


        }else{
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


    void postgetData(final String qid , final String deviceid){


        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/get_question_answer";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahmad",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                updateview(response);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("questionid", qid); //Add the data you'd like to send to the server.
                MyData.put("deviceid",deviceid); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }

    private void updateview(String response) {

        List<Pasox> templist=new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            MozueSoalTextView.setText(jsonObject.get("QuestionSubject").toString());
            OnvaneSoalTextView.setText(jsonObject.get("Text").toString());
            Log.i("asasaasasasa",jsonObject.get("RegTime").toString());
            TarixePorsesh.setText(String.valueOf(jsonObject.get("RegTime").toString()));

            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("Answer").toString());
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject1 =new JSONObject(jsonArray.get(i).toString());
                Pasox pasox = new Pasox(jsonObject1.getString("PicAddress")
                        , jsonObject1.getString("UserName"), jsonObject1.getString("Text")
                        , jsonObject1.getString("LikeCount"), jsonObject1.getString("DisLikeCount")
                        , jsonObject1.getString("RegTime"));

                pasox.setQid(qid);
                templist.add(pasox);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListePasoxhayeksoalAdapter listePasoxhayeksoalAdapter = new ListePasoxhayeksoalAdapter(PasoxePorsesh.this,templist);
        listView.setAdapter(listePasoxhayeksoalAdapter);



    }

    private void requestData(String userid , String contentid , String status) {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Set_like_dislike");

        p.setParam("userid",  userid);
        p.setParam("contentid", contentid);
        p.setParam("contenttype", "question");
        p.setParam("status", status);
        p.setParam("deviceid", GlobalVar.getDeviceID());


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


            //List<Moshaver> templist=new ArrayList<>();
            Log.i("listeasadsddf",result) ;



        }

    }


}
