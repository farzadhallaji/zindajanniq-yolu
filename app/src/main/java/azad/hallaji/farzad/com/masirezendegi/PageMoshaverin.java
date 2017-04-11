package azad.hallaji.farzad.com.masirezendegi;

import android.app.TabActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

public class PageMoshaverin extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Map");
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Partners");

        tabSpec1.setIndicator("نقشه مراکز");
        tabSpec1.setContent(new Intent(this, MapsActivity.class));

        tabSpec2.setIndicator("لیست مشاورین");
        tabSpec2.setContent(new Intent(this, ListeMoshaverin.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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
            startActivity(new Intent(PageMoshaverin.this , MarakeZ.class));
        }else if (id == R.id.nav_profile) {
            //startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        } else if (id == R.id.nav_setting) {
            //startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(PageMoshaverin.this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(PageMoshaverin.this , PagePorseshha.class));
        } else if (id == R.id.nav_logout){
            //startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


}