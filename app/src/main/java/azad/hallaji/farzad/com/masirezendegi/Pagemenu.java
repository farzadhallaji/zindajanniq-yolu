package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Pagemenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView linearLayout1,linearLayout2,linearLayout3,linearLayout4 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagemenu);

        Fabric.with(this, new Crashlytics());


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


        if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")){

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


        linearLayout1= (ImageView) findViewById(R.id.alagemandihabutton);
        linearLayout2= (ImageView) findViewById(R.id.porseshhabutton);
        linearLayout3= (ImageView) findViewById(R.id.marakezbutton);
        linearLayout4= (ImageView) findViewById(R.id.moshaverinbutton);


        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalVar.getUserType().equals("adviser") || GlobalVar.getUserType().equals("user")) {
                    Intent intent = new Intent(Pagemenu.this, PageAlaghemandiha.class);
                    /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                    startActivity(intent);
                }else{
                    /*LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    dialogBuilder.setView(dialogView);

                    TextView textView =(TextView)dialogView.findViewById(R.id.aT);
                    TextView textView2 =(TextView)dialogView.findViewById(R.id.aaT);
                    textView.setText("");
                    textView2.setText("ابتدا باید وارد سیستم شوید");

                    Button button = (Button)dialogView.findViewById(R.id.buttombastan);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();

                        }
                    });*/
                    Toast.makeText(getApplicationContext(), "ابتدا باید وارد سیستم شوید", Toast.LENGTH_LONG).show();

                }
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PagePorseshha.class);
                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagemenu.this, PageMarakez.class);
                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                startActivity(intent);
            }
        });
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagemenu.this, PageMoshaverin.class);
                /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                startActivity(intent);
            }
        });



    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            Intent intent =new Intent(this , PageMarakez.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent =new Intent(this , PageVirayesh.class);
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            Intent intent =new Intent(this , PageLogin.class);
            startActivity(intent);
        } else if (id == R.id.nav_moshaverin) {
            Intent intent =new Intent(this , PageMoshaverin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else if (id == R.id.nav_porseshha) {
            Intent intent =new Intent(this , PagePorseshha.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout){
            Intent intent =new Intent(this , PageLogout.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }
    private int  time_interval = 2000;
    private long oldCurrentTimeMillis;
    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getBaseContext(), "برای خروج دوباره کلیک کنید.", Toast.LENGTH_SHORT);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }

        if (oldCurrentTimeMillis + time_interval > System.currentTimeMillis())
        {
            finish();
            toast.cancel();

        }
        else {
            toast.show();
        }
        oldCurrentTimeMillis = System.currentTimeMillis();
    }

}
