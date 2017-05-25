package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeMoshaverinAdapter;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import azad.hallaji.farzad.com.masirezendegi.model.Subject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.widget.AdapterView;

public class ListeMoshaverin extends AppCompatActivity {

    View ftView;
    ListView listView;
    ListeMoshaverinAdapter adapter;
    List<Moshaver> totalList=new ArrayList<>();
    int tempcount=0;
    String subjectid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_moshaverin);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) subjectid = "0";
            else {
                subjectid= extras.getString("subjectid");
            }
        } else {
            subjectid= (String) savedInstanceState.getSerializable("subjectid");
        }



        listView=(ListView)findViewById(R.id.ListeMoshaverinListView);

        LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null);


        if (isOnline()) {
            //Toast.makeText(getApplicationContext(), "Network is available", Toast.LENGTH_LONG).show();
            requestData(subjectid,0);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), totalList.get(position).getAID(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication() , ExplainMoshaver.class);
                    intent.putExtra("adviserid",totalList.get(position).getAID());
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
                            requestData(subjectid,((totalList.size()/20)*20));
                        }       //TODO TEST sine den yazma
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



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

    private void requestData(String subid , int start) {

        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/get_adviser");

        p.setParam("subjectid",  subid);
        p.setParam("start", String.valueOf(start));
        p.setParam("deviceid", GlobalVar.getDeviceID());


        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {


            List<Moshaver> templist=new ArrayList<>();
            Log.i("saasasa",result) ;
            try {

                JSONArray jsonArray = new JSONArray(result);

                for(int i= 0 ; i<jsonArray.length() ; i++){
                    JSONObject s = (JSONObject) jsonArray.get(i);
                    List<String>tag=new ArrayList<>();
                    JSONArray tags = (JSONArray) s.get("Tag");
                    //Toast.makeText(getApplicationContext(),String.valueOf(tags), Toast.LENGTH_LONG).show();

                    for(int j= 0 ;j < tags.length() ; j++){

                        tag.add(tags.get(j).toString());
                        //Toast.makeText(getApplicationContext(),tags.get(j).toString(), Toast.LENGTH_LONG).show();

                    }
                    Moshaver temp = new Moshaver(s.get("AID").toString(),s.get("AdviserName").toString(),
                            tag,s.get("PicAddress").toString(),s.get("CommentCount").toString());
                    temp.setUniqueID(s.getString("UniqueID"));
                    templist.add(temp);
                }
                totalList.addAll(templist);
                if(tempcount==0){
                    adapter =new ListeMoshaverinAdapter(ListeMoshaverin.this,totalList);
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
