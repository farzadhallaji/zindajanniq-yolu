package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListePasoxhayeksoalAdapter;
import azad.hallaji.farzad.com.masirezendegi.helper.ListePorseshhaAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListePorseshha extends AppCompatActivity {

    View ftView;
    ListView listView;
    ListePorseshhaAdapter adapter;
    List<Question> totalList=new ArrayList<>();
    int tempcount=0;
    String subjectid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_porseshha);
        Fabric.with(this, new Crashlytics());


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) subjectid = "0";
            else {
                subjectid= extras.getString("subjectid");
                //Toast.makeText(getApplicationContext(), subjectid, Toast.LENGTH_LONG).show();

            }
        } else {
            subjectid= (String) savedInstanceState.getSerializable("subjectid");
        }
        //Log.i("qwertassasasasyuioiuytre",subjectid);


        listView=(ListView)findViewById(R.id.ListePorseshhaListView);

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);

        if (isOnline()) {

           requestData(subjectid,0);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ListePorseshha.this, PasoxePorsesh.class);
                    intent.putExtra("questionid",totalList.get(position).getQID());
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    finish();
                    ListePasoxhayeksoalAdapter.contentid=totalList.get(position).getQID();
                    startActivity(intent);

                }
            });

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(totalList.size())+" : "+String.valueOf(view.getLastVisiblePosition()), Toast.LENGTH_LONG).show();
                    //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                    if(view.getLastVisiblePosition() == totalList.size()-1 ) {
                        //Toast.makeText(getApplicationContext(), view.getLastVisiblePosition(), Toast.LENGTH_LONG).show();

                        if(totalList.size()<19){

                        }else{
                            listView.addFooterView(ftView);
                            requestData(subjectid,((totalList.size()/20+1)*20));
                        }
                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //Toast.makeText(getApplicationContext(), "onScroll", Toast.LENGTH_LONG).show();

                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {
                    Intent intent = new Intent(ListePorseshha.this,namhansiTaxassus.class);
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();
                }




            }
        });
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

    private void requestData(String pid , int start) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        //p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        p.setUri("http://telyar.dmedia.ir/webservice/get_all_question");

        p.setParam("subjectid", pid);
        p.setParam("start", String.valueOf(start));
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

            //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();
            Log.i("qwertyuiuhg",result);


            List<Question> templist=new ArrayList<>();

            try {

                JSONArray jsonArray = new JSONArray(result);

                for(int i= 0 ; i<jsonArray.length() ; i++){
                    JSONObject s = (JSONObject) jsonArray.get(i);
                    Question temp = new Question(s.get("QID").toString(),s.get("SubjectID").toString(),
                            s.get("QuestionSubject").toString(),s.get("Text").toString()
                            ,s.get("RegTime").toString(),s.get("AnswerCount").toString());
                    temp.setDisLikeCount(s.getString("DisLikeCount"));
                    temp.setLikeCount(s.getString("LikeCount"));
                    templist.add(temp);
                }
                totalList.addAll(templist);

                Log.i("lisrtt",templist.toString());

                if(tempcount==0){
                    adapter =new ListePorseshhaAdapter(ListePorseshha.this,totalList);
                    listView.setAdapter(adapter);
                    tempcount++;
                }
                adapter.notifyDataSetChanged();
                listView.removeFooterView(ftView);



            } catch (JSONException e) {
                e.printStackTrace();
            }

            ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
            progressbarsandaha.setVisibility(View.INVISIBLE);

        }

    }

}
