package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import azad.hallaji.farzad.com.masirezendegi.helper.ListeAlagemandiHaAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.ALagemandi;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Markaz;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;

public class PageAlaghemandiha extends AppCompatActivity {

    RequestQueue MyRequestQueue ;
    List<ALagemandi> aLagemandis =new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaghemandiha_page);

        listView = (ListView)findViewById(R.id.ListeAlagemandiHaListView);

        //Get_favourite
        //input : userid , contenttype , deviceid

        if(isOnline()){
            postgetData1();
            //Log.i("elabella1",aLagemandis.size()+ " ");
            postgetData2();
            postgetData3();
            Log.i("tutal",aLagemandis.size()+ " ");

            //requestData();

        }else {
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
    private void postgetData1(){
        MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://telyar.dmedia.ir/webservice/Get_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahmadabiabiabi",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                updatelistview1(response);


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
                MyData.put("contenttype", "question"); //Add the data you'd like to send to the server.
                MyData.put("userid", "100"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview1(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            Log.i("mansansasasaa",jsonArray.length()+"s");
            for(int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String QID =jsonObject.getString("QID");
                String QuestionSubject = jsonObject.getString("QuestionSubject");
                /*String Text= jsonObject.getString("Text");
                String RegTime=jsonObject.getString("RegTime");
                String LikeCount=jsonObject.getString("LikeCount");
                String DisLikeCount=jsonObject.getString("DisLikeCount");
                String AnswerCount=jsonObject.getString("AnswerCount");*/
                ALagemandi aLagemandi = new ALagemandi(QID,QuestionSubject,"پرسش","");
                aLagemandis.add(aLagemandi);

            }

            Log.i("elabella",aLagemandis.size()+ " ");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void postgetData2(){

        String url = "http://telyar.dmedia.ir/webservice/Get_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahamdabi2",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                updatelistview2(response);

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
                MyData.put("contenttype", "adviser"); //Add the data you'd like to send to the server.
                MyData.put("userid", "100"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview2(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String AID =jsonObject.getString("AID");
                String UserName = jsonObject.getString("UserName");
                String PicAddress= jsonObject.getString("PicAddress");
                String Telephone=jsonObject.getString("Telephone");

                List<String>Tags=new ArrayList<>();
                JSONArray tags = new JSONArray(jsonObject.getJSONArray("Tag").toString());
                try {
                    for (int ii= 0 ; ii < tags.length() ; ii++){
                        Tags.add(tags.getString(ii));
                    }
                }catch (Exception ignored){}

                String Rating=jsonObject.getString("Rating");
                String AdviserMaxTime=jsonObject.getString("AdviserMaxTime");
                String RegTime=jsonObject.getString("RegTime");
                String CommentCount=jsonObject.getString("CommentCount");

                ALagemandi question = new ALagemandi(AID,UserName,"مشاور",PicAddress);
                aLagemandis.add(question);

            }
            Log.i("elabella2",aLagemandis.size()+ " ");

        } catch (JSONException e) {
            //e.printStackTrace();
        }

    }

    private void postgetData3(){

        String url = "http://telyar.dmedia.ir/webservice/Get_favourite/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahamdabi3",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                updatelistview3(response);

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
                MyData.put("contenttype", "mainplace"); //Add the data you'd like to send to the server.
                MyData.put("userid", "100"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    private void updatelistview3(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String AID =jsonObject.getString("MID");
                String UserName = jsonObject.getString("MainPlaceName");
                String PicAddress= jsonObject.getString("PicAddress");
                String Rating=jsonObject.getString("Rating");
                String AdviserMaxTime=jsonObject.getString("AdviserMaxTime");
                String RegTime=jsonObject.getString("RegTime");
                String CommentCount=jsonObject.getString("CommentCount");

                ALagemandi mush = new ALagemandi(AID,UserName,"مرکز",PicAddress);
                aLagemandis.add(mush);
            }
            Log.i("elabella3",aLagemandis.size()+ " ");

            ListeAlagemandiHaAdapter listeAlagemandiHaAdapter = new ListeAlagemandiHaAdapter(PageAlaghemandiha.this,aLagemandis);
            listView.setAdapter(listeAlagemandiHaAdapter);

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ALagemandi aLagemandi = aLagemandis.get(position);
                String typee = aLagemandi.getType();
                if(typee.equals("مشاور")){
                    Intent intent = new Intent(PageAlaghemandiha.this,ExplainMoshaver.class);
                    intent.putExtra("adviserid",aLagemandi.getID());
                    startActivity(intent);
                }else if (typee.equals("پرسش")){
                    Intent intent = new Intent(PageAlaghemandiha.this,PasoxePorsesh.class);
                    intent.putExtra("questionid",aLagemandi.getID());
                    startActivity(intent);
                }else if (typee.equals("مرکز")){
                    Intent intent = new Intent(PageAlaghemandiha.this,ExplainMarkaz.class);
                    intent.putExtra("placeid",aLagemandi.getID());
                    startActivity(intent);
                }

            }
        });

    }






    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        p.setUri("http://telyar.dmedia.ir/webservice/Get_favourite/");

        p.setParam("contenttype", "question");
        p.setParam("userid", "100");
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

            Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
            Log.i("elabella",result);


        }

    }


}
