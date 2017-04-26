package azad.hallaji.farzad.com.masirezendegi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Pagemenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView linearLayout1,linearLayout2,linearLayout3,linearLayout4 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagemenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        ImageView imageView = (ImageView) findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.END);

            }
        });


        linearLayout1= (ImageView) findViewById(R.id.alagemandihabutton);
        linearLayout2= (ImageView) findViewById(R.id.porseshhabutton);
        linearLayout3= (ImageView) findViewById(R.id.marakezbutton);
        linearLayout4= (ImageView) findViewById(R.id.moshaverinbutton);


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            startActivity(new Intent(Pagemenu.this , PageMarakez.class));
        }/*else if (id == R.id.nav_profile) {
            startActivity(new Intent(Pagemenu.this , PageVirayesh.class));
        }*/ /*else if (id == R.id.nav_setting) {
            //startActivity(new Intent(Pagemenu.this , MainActivity.class));
        }*/ else if (id == R.id.nav_login) {
            startActivity(new Intent(Pagemenu.this , MainActivity.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(Pagemenu.this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(Pagemenu.this , PagePorseshha.class));
        } /*else if (id == R.id.nav_logout){
            //startActivity(new Intent(Pagemenu.this , Test1.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
