package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeTaxassoshaAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import azad.hallaji.farzad.com.masirezendegi.model.Taxassos;

public class ListeTaxassosHa extends AppCompatActivity {

    View ftView;
    ListView listView;
    ListeTaxassoshaAdapter adapter;
    List<Taxassos> totalList=new ArrayList<>();
    int tempcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_taxassosha);
        listView=(ListView)findViewById(R.id.ListeTxassoshaListView);

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);
        //mHandler = new MyHandler();

        if (isOnline()) {


            requestData(0,0);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();

                    //Todo


                }
            });
/*
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(totalList.size())+" : "+String.valueOf(view.getLastVisiblePosition()), Toast.LENGTH_LONG).show();


                    //Check when scroll to last item in listview, in this tut, init data in listview = 10 item
                    if(view.getLastVisiblePosition() == totalList.size()-1) {
                        //Toast.makeText(getApplicationContext(), view.getLastVisiblePosition(), Toast.LENGTH_LONG).show();

                        listView.addFooterView(ftView);
                        requestData(0,((totalList.size()%20+2)*20));
                        //  Thread thread = new ThreadGetMoreData();
                        //Start thread
                        //thread.start();
                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //Toast.makeText(getApplicationContext(), "onScroll", Toast.LENGTH_LONG).show();

                }
            });
*/

        } else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    private void requestData(int pid , int start) {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Get_all_subject");

        /*p.setParam("pid",  String.valueOf(pid));
        p.setParam("start", String.valueOf(start));
        p.setParam("deviceid", GlobalVar.getDeviceID());*/

        //Log.i("deviceid",GlobalVar.getDeviceID());
        p.setParam("pid", "0");
        p.setParam("start","0");
        p.setParam("deviceid", "3");


        /*p.setParam("subjectid",  String.valueOf(pid));
        p.setParam("start", String.valueOf(start));
        p.setParam("deviceid", GlobalVar.getDeviceID());*/

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

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


            List<Question> templist=new ArrayList<>();

            try {

                JSONArray jsonArray = new JSONArray(result);

                /*for(int i= 0 ; i<jsonArray.length() ; i++){
                    JSONObject s = (JSONObject) jsonArray.get(i);
                    Question temp = new Question(s.get("QID").toString(),s.get("SubjectID").toString(),
                            s.get("QuestionSubject").toString(),s.get("Text").toString()
                            ,s.get("RegTime").toString(),s.get("AnswerCount").toString());
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
*/


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
