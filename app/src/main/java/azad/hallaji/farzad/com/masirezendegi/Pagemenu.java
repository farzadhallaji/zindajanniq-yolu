package azad.hallaji.farzad.com.masirezendegi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Pagemenu extends AppCompatActivity {

    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);


        linearLayout1= (LinearLayout) findViewById(R.id.alagemandihabutton);
        linearLayout2= (LinearLayout) findViewById(R.id.porseshhabutton);
        linearLayout3= (LinearLayout) findViewById(R.id.marakezbutton);
        linearLayout4= (LinearLayout) findViewById(R.id.moshaverinbutton);


        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagemenu.this, PageAlaghemandiha.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PagePorseshha.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagemenu.this, PageMarakez.class);
                startActivity(intent);
            }
        });
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagemenu.this, PageMoshaverin.class);
                startActivity(intent);
            }
        });



    }
}
