package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.id.message;


public class PasoxePorsesh extends AppCompatActivity {

    TextView MozueSoalTextView ;
    TextView OnvaneSoalTextView;
    ListView listView;
    String qid="";
    ImageView sabizshey;
    LinearLayout layeyeasli;
    RelativeLayout likedisalage;
    boolean alage=false;
    RequestQueue MyRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasoxe_porsesh);

        layeyeasli=(LinearLayout)findViewById(R.id.layeyeasli);
        likedisalage=(RelativeLayout)findViewById(R.id.layeyelikalage);

        layeyeasli.setVisibility(View.VISIBLE);
        likedisalage.setVisibility(View.GONE);

        /*Bundle bundle = getIntent().getExtras();
        qid = bundle.getString("questionid");
*/
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) qid = "0";
            else {
                qid= extras.getString("questionid");
            }
        } else {
            qid= (String) savedInstanceState.getSerializable("questionid");
        }


        MozueSoalTextView = (TextView)findViewById(R.id.MozueSoalTextView);
        OnvaneSoalTextView = (TextView)findViewById(R.id.OnvaneSoalTextView);
        listView=(ListView)findViewById(R.id.ListepasoxhaListView);



        //Toast.makeText(getApplicationContext(), qid, Toast.LENGTH_LONG).show();
        sabizshey= (ImageView)findViewById(R.id.sabizshey);
        Log.i("aasasasasaaaaa",qid);

        if(isOnline()){
            //requestData();
            postgetData(qid,GlobalVar.getDeviceID());



        }else{
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        MyRequestQueue = Volley.newRequestQueue(this);
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
            @Override
            protected void onFinish() {

                sabizshey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layeyeasli.setVisibility(View.GONE);
                        likedisalage.setVisibility(View.VISIBLE);
                    }
                });

                ImageView startalagemandiImageView=(ImageView)findViewById(R.id.startalagemandiImageView);
                ImageView dislikeimmmm=(ImageView)findViewById(R.id.dislikeimmmm);
                ImageView likeimmmm=(ImageView)findViewById(R.id.likeimmmm);

                if(alage)
                    startalagemandiImageView.setImageResource(R.drawable.alage1);
                else
                    startalagemandiImageView.setImageResource(R.drawable.alage0);

                startalagemandiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(alage)
                            setAlage("-1");
                        else
                            setAlage("1");
                    }
                });
                dislikeimmmm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likedislike(false);
                    }
                });
                likeimmmm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likedislike(true);
                    }
                });
                ImageView closeinvisibleimag = (ImageView)findViewById(R.id.closeinvisibleimag);
                closeinvisibleimag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layeyeasli.setVisibility(View.VISIBLE);
                        likedisalage.setVisibility(View.GONE);
                    }
                });

            }

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
            MozueSoalTextView.setText(jsonObject.get("SName").toString());
            OnvaneSoalTextView.setText(jsonObject.get("QuestionSubject").toString());
            //OnvaneSoalTextView.setText(jsonObject.get("TextText").toString());
            Log.i("asastuikjhg",jsonObject.toString());
            alage = jsonObject.getString("IsFavourite").equals("1");

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

    void setAlage(final String s) {

        String url = "http://telyar.dmedia.ir/webservice/Set_like_dislike/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("aladfffgree", response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                responsesetfavor(response);


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
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                MyData.put("contentid", qid); //Add the data you'd like to send to the server.
                MyData.put("status", s); //Add the data you'd like to send to the server.
                MyData.put("contenttype", "question"); //Add the data you'd like to send to the server.
                MyData.put("deviceid", GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void likedislike(final boolean like) {
        String url = "http://telyar.dmedia.ir/webservice/Set_like_dislike/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ahmad",response);
                responsesetfavor(response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("userid", GlobalVar.getUserID());
                String aaa="-1";
                if(like)
                    aaa="1";
                MyData.put("status", aaa); //Add the data you'd like to send to the server.
                MyData.put("contenttype", "question"); //Add the data you'd like to send to the server.
                MyData.put("contentid",qid); //Add the data you'd like to send to the server.
                MyData.put("deviceid",GlobalVar.getDeviceID()); //Add the data you'd like to send to the server.
                return MyData;
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


}





