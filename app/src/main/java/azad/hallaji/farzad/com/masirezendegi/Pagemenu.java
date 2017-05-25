package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_marakez) {
            startActivity(new Intent(this , PageMarakez.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this , PageVirayesh.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(this , PageLogin.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(this , PagePorseshha.class));
        } else if (id == R.id.nav_logout){
            startActivity(new Intent(this , PageLogout.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;

    }
}
