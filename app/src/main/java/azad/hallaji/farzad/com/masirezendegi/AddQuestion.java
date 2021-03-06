package azad.hallaji.farzad.com.masirezendegi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import azad.hallaji.farzad.com.masirezendegi.helper.TypefaceUtil;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddQuestion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String subjectid="0";
    String questioncategoryquestioncategory="";
    //ProgressBar progressbardahaya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Fabric.with(this, new Crashlytics());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) subjectid = "0";
            else {
                subjectid= extras.getString("subjectid");
                questioncategoryquestioncategory= extras.getString("questioncategory");
                //Toast.makeText(getApplicationContext(), subjectid, Toast.LENGTH_LONG).show();

            }
        } else {
            subjectid= (String) savedInstanceState.getSerializable("subjectid");
            questioncategoryquestioncategory= (String) savedInstanceState.getSerializable("questioncategory");
        }

        //Toast.makeText(getApplicationContext(), qid, Toast.LENGTH_LONG).show();
        //progressbardahaya=(ProgressBar)findViewById(R.id.progressbardahaya);
        //progressbardahaya.setVisibility(View.GONE);

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestion.this , PagePorseshha.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });



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

        if(isOnline()){
            //requestData();

            Button button = (Button)findViewById(R.id.buttonersalsoal);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText =(EditText) findViewById(R.id.edittextsoal);
                    EditText editText2 =(EditText) findViewById(R.id.edittextOnvanesoal);
                    if(editText.getText().toString().equals("") || editText2.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "لطفا فیلدهای مربوطه را پر کنید", Toast.LENGTH_LONG).show();
                    }else {
                        requestData(subjectid, editText.getText().toString(), editText2.getText().toString()); //ToDO what is the questioncategory?
                    }
                }


            });


        }else{
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    private void requestData(String subjectid , String text , String questioncategory ) {
        ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
        progressbarsandaha.setVisibility(View.VISIBLE);
        //progressbardahaya.setVisibility(View.VISIBLE);
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Set_new_question");

        //subjectid , text , questioncategory, userid , deviceid

        //p.setParam("questionid",  qid);
        p.setParam("deviceid", GlobalVar.getDeviceID());
        p.setParam("subjectid", subjectid);
        p.setParam("text",text);
        p.setParam("questioncategory",questioncategoryquestioncategory);
        p.setParam("userid", GlobalVar.getUserID());
        p.setParam("QuestionSubject",questioncategory );
        Log.i("ujyh",p.getEncodedParams());

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

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
    private void updategraf(String message, String m, final String phone) {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        if(m.equals("1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            TextView textView2 =(TextView)dialogView.findViewById(R.id.aT);
            textView.setText(message);
            textView2.setText("پرسش جدید");

            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                    Intent intent = new Intent(AddQuestion.this,PagePorseshha.class);
                    intent.putExtra("soallllll","1");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                }
            });
        }else if(m.equals("-1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(message);
            TextView te =(TextView)dialogView.findViewById(R.id.aT);
            te.setText("خطا");
            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });



        }

    }


    private class LoginAsyncTask extends AsyncTask<RequestPackage, String, String> {


        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject= new JSONObject(result);
                Log.i("ujyh",result);

                String m = jsonObject.getString("Status");
                String mess = jsonObject.getString("Message");
                updategraf(mess,m,"");
                //progressbardahaya.setVisibility(View.INVISIBLE);

                ProgressBar progressbarsandaha =(ProgressBar)findViewById(R.id.progressbarsandaha);
                progressbarsandaha.setVisibility(View.INVISIBLE);
            } catch (JSONException e) {
                //e.printStackTrace();
            }


        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        super.onBackPressed();
    }


}
