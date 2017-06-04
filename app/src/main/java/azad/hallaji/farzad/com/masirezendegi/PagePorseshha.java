package azad.hallaji.farzad.com.masirezendegi;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import azad.hallaji.farzad.com.masirezendegi.helper.ListeTaxassoshaAdapter;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Taxassos;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PagePorseshha extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    int soallllllll=1;
    String subjectid="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Fabric.with(this, new Crashlytics());



        TextView textView = (TextView)findViewById(R.id.dashshshaqx);
        textView.setText("پرسش ها");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                subjectid= "0";
                soallllllll= 1;
            } else {
                subjectid= extras.getString("subjectid");

                String s="10";
                try {
                    s=extras.getString("soallllllll");
                    assert s != null;
                    soallllllll= s.equals("1") ? 1 :0 ;
                }catch (Exception ignored){}


            }
        } /*else {
            subjectid= (String) savedInstanceState.getSerializable("subjectid");
            soallllllll= 0;

        }*/

        //Toast.makeText(getApplicationContext(), subjectid, Toast.LENGTH_LONG).show();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView imageView = (ImageView) findViewById(R.id.menuButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.END);

            }
        });

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!GlobalVar.porseshdataxassusvirmisham){

                    Intent intent = new Intent(PagePorseshha.this , Pagemenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                }else {

                    Intent intent = new Intent(PagePorseshha.this , PagePorseshha.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("subjectid","0");
                    GlobalVar.porseshdataxassusvirmisham=false;
                    //Toast.makeText(getApplicationContext(), "156156516", Toast.LENGTH_LONG).show();

                    startActivity(intent);
                }

            }
        });

        if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);

        }else{

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_marakez).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_moshaverin).setVisible(true);
            nav_Menu.findItem(R.id.nav_porseshha).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Map");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Partners");

        tabSpec1.setIndicator("تخصص ها");
        Intent intent =new Intent(this, ListeTaxassosHa.class);
        tabSpec1.setContent(intent);

        tabSpec2.setIndicator("سوالات");
        Intent intent2 = new Intent(this, ListePorseshha.class);
        intent2.putExtra("subjectid",subjectid);
        //Toast.makeText(getApplicationContext(), "Network isn't available"+subjectid, Toast.LENGTH_LONG).show();
        tabSpec2.setContent(intent2);

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);

        tabHost.setCurrentTab(soallllllll);

    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            Intent intent =new Intent(this , PageMarakez.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent =new Intent(this , PageVirayesh.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            Intent intent =new Intent(this , PageLogin.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_moshaverin) {
            Intent intent =new Intent(this , PageMoshaverin.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_porseshha) {
            Intent intent =new Intent(this , PagePorseshha.class);
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_logout){
            Intent intent =new Intent(this , PageLogout.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }

        if(!GlobalVar.porseshdataxassusvirmisham){
            Intent intent = new Intent(PagePorseshha.this , Pagemenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent);
        }else {
            Intent intent = new Intent(PagePorseshha.this , PagePorseshha.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("subjectid","0");
            GlobalVar.porseshdataxassusvirmisham=false;
            //Toast.makeText(getApplicationContext(), "156156516", Toast.LENGTH_LONG).show();

            startActivity(intent);
        }
        //Toast.makeText(getApplicationContext(), "pageporsesh", Toast.LENGTH_LONG).show();


    }

}
