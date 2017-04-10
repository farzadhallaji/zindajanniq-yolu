package azad.hallaji.farzad.com.masirezendegi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PageLiecence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liecence);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("License");

        Log.i("asasasasafdffggjghgds",message+" 0");
        TextView textView =(TextView)findViewById(R.id.licenceTextView);
        textView.setText(message);

    }
}
