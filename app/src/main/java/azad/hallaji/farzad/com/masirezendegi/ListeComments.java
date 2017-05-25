package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import azad.hallaji.farzad.com.masirezendegi.helper.ListeCommentAdapter;
import azad.hallaji.farzad.com.masirezendegi.helper.ListePasoxhayeksoalAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListeComments extends AppCompatActivity {

    String adviseridm;
    String commentscomments;
    List<Comment> comments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_comments);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adviseridm= "0";
                commentscomments= "";
            } else {
                adviseridm= extras.getString("adviseridm");
                commentscomments= extras.getString("comments");
            }
        } else {
            adviseridm= (String) savedInstanceState.getSerializable("adviseridm");
            commentscomments= (String) savedInstanceState.getSerializable("comments");
        }

        try {
            updatelistview(commentscomments);
        }catch (Exception e){}

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void updatelistview(String response) {

        comments=new ArrayList<>();
        JSONArray jsonArray2 = null;
        try {
            jsonArray2 = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            assert jsonArray2 != null;
            for(int i = 0; i<jsonArray2.length() ; i++){
                JSONObject object = (JSONObject) jsonArray2.get(i);
                Comment comment = new Comment(object.getString("comment"),object.getString("RegTime"),
                        object.getString("UserName"),object.getString("UserFamilyName") , object.getString("UserPicAddress"));
                comments.add((comment));
            }
            ListView listView = (ListView)findViewById(R.id.commentListView);
            ListeCommentAdapter listePasoxhayeksoalAdapter =new ListeCommentAdapter(ListeComments.this,comments);
            listView.setAdapter(listePasoxhayeksoalAdapter);
            Log.i("aasasasassddfgfgyjyua",comments.size()+" ");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
