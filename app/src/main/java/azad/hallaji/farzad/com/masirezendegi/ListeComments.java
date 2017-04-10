package azad.hallaji.farzad.com.masirezendegi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeCommentAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;

public class ListeComments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_comments);

        ListView listView = (ListView)findViewById(R.id.commentListView);
        Log.i("sizeaaaaaa",(GlobalVar.getComments().size())+ "");

        if(GlobalVar.getComments().size()>0){

            ListeCommentAdapter adapter =new ListeCommentAdapter(ListeComments.this,GlobalVar.getComments());
            listView.setAdapter(adapter);

        }

    }
}
