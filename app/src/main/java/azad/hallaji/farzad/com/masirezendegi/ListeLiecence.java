package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.LisenseAdapter;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListeLiecence extends AppCompatActivity {
    String adviseridm,commentscomments="";
    ListView listView;

    List<String> comments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liecence);

        Fabric.with(this, new Crashlytics());



        listView= (ListView)findViewById(R.id.lisenseid);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adviseridm= "0";
                commentscomments= "";
            } else {
                adviseridm= extras.getString("adviseridm");
                commentscomments= extras.getString("License");
            }
        } else {
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
            commentscomments= (String) savedInstanceState.getSerializable("License");
        }
        Log.i("asadasasfasadfd","asdfghjjhyrwefasdsvttbf");
        Log.i("asadasasfasadfd",commentscomments);

        updatelistview(commentscomments);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void updatelistview(String response) {


        try {

            JSONArray jsonArray = new JSONArray(response);

            Log.i("asadasasfasadfd",jsonArray.toString());

            for(int i=0 ; i<jsonArray.length() ; i++){

                String License = jsonArray.get(i).toString();
                comments.add(License);
                Log.i("asadasasfasadfd",jsonArray.get(i).toString());


            }
            Log.i("asadasasfasadfd",jsonArray.length()+"");


            LisenseAdapter listeAlagemandiHaAdapter = new LisenseAdapter(ListeLiecence.this,comments);
            listView.setAdapter(listeAlagemandiHaAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this , PageMoshaverin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
