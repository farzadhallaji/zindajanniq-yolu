package azad.hallaji.farzad.com.masirezendegi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import de.hdodenhof.circleimageview.CircleImageView;

public class PageVirayesh extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    EditText nameTextViewVirayesh,nameXanevadeTextViewVirayesh,marefiyejmaliTextViewVirayesh;
    CircleImageView imageviewuserVirayesh;
    EditText emailTextViewVirayesh , telefonTextViewVirayesh , gushuTextViewVirayesh;
    EditText introTextViewVirayesh  , maxtimeTextViewVirayesh;
    EditText costimeTextViewVirayesh , marefiTextViewVirayesh;
    RadioGroup radioSexGroup;

    String userid="100" , name , familyname , telephone , email , gender , picaddress=" "
            , aboutme , costpermin , license , tag , dialect , advisermaxtim="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_virayesh);

        TextView virayeshTextinToolbar=(TextView) findViewById(R.id.virayeshTextinToolbar);
        init();

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageVirayesh.this , Pagemenu.class);
                startActivity(intent);
            }
        });

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

        if(isOnline()){

            virayeshTextinToolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name=nameTextViewVirayesh.getText().toString();
                    familyname=nameXanevadeTextViewVirayesh.getText().toString();
                    telephone=telefonTextViewVirayesh.getText().toString();
                    email=emailTextViewVirayesh.getText().toString();
                    aboutme=marefiyejmaliTextViewVirayesh.getText().toString();
                    costpermin=costimeTextViewVirayesh.getText().toString();
                    license=introTextViewVirayesh.getText().toString();
                    tag=marefiTextViewVirayesh.getText().toString();
                    dialect=gushuTextViewVirayesh.getText().toString();
                    advisermaxtim=maxtimeTextViewVirayesh.getText().toString();
//TODO parametr lari bir chech elamali

                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id

                    if(selectedId==(R.id.radioM)){
                        gender="مرد";
                    }else {
                        gender="زن";
                    }
                    Log.i("gender",gender);

                    requestData();

                }
            });

        }else{
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }




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
            startActivity(new Intent(this , PageMarakez.class));
        }/*else if (id == R.id.nav_profile) {
            startActivity(new Intent(this , PageVirayesh.class));
        } else if (id == R.id.nav_setting) {
            //startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        }*/ else if (id == R.id.nav_login) {
            startActivity(new Intent(this , MainActivity.class));
        } else if (id == R.id.nav_moshaverin) {
            startActivity(new Intent(this , PageMoshaverin.class));
        } else if (id == R.id.nav_porseshha) {
            startActivity(new Intent(this , PagePorseshha.class));
        } /*else if (id == R.id.nav_logout){
            //startActivity(new Intent(PageMoshaverin.this , MainActivity.class));
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void init() {

        nameTextViewVirayesh=(EditText)findViewById(R.id.nameTextViewVirayesh);
        nameXanevadeTextViewVirayesh=(EditText)findViewById(R.id.nameXanevadeTextViewVirayesh);
        marefiyejmaliTextViewVirayesh=(EditText)findViewById(R.id.marefiyejmaliTextViewVirayesh);
        emailTextViewVirayesh=(EditText)findViewById(R.id.emailTextViewVirayesh);
        telefonTextViewVirayesh=(EditText)findViewById(R.id.telefonTextViewVirayesh);
        gushuTextViewVirayesh=(EditText)findViewById(R.id.gushuTextViewVirayesh);
        introTextViewVirayesh=(EditText)findViewById(R.id.introTextViewVirayesh);
        //=(EditText)findViewById(R.id.genderTextViewVirayesh);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGrp);

        maxtimeTextViewVirayesh=(EditText)findViewById(R.id.maxtimeTextViewVirayesh);
        costimeTextViewVirayesh=(EditText)findViewById(R.id.costimeTextViewVirayesh);
        marefiTextViewVirayesh=(EditText)findViewById(R.id.marefiTextViewVirayesh);
        imageviewuserVirayesh=(CircleImageView )findViewById(R.id.imageviewuserVirayesh);

    }


    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void requestData() {

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Edit_profile");

        p.setParam("userid", userid);
        p.setParam("name", name);
        p.setParam("familyname",familyname);
        p.setParam("email",email);
        p.setParam("gender",gender);
        p.setParam("telephone",telephone);
        p.setParam("picaddress",picaddress);
        p.setParam("aboutme",aboutme);
        p.setParam("costpermin",costpermin);
        p.setParam("license",license);
        p.setParam("tag",tag);
        p.setParam("dialect",dialect);
        p.setParam("advisermaxtim",advisermaxtim);



        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

    }


    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "0"+result+"0", Toast.LENGTH_LONG).show();

            //Log.i("resuuuuuul",result);

            try {
                JSONObject jsonObject =new JSONObject(result);

                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
