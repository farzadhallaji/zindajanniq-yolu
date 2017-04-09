package azad.hallaji.farzad.com.masirezendegi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;

public class ListeComments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_comments);

        for (int i = 0 ; i < 10 ; i++){

            //Log.i("asasasadadssdsdsd",GlobalVar.getComments().get(i).getComment());

        }


    }
}
