package azad.hallaji.farzad.com.masirezendegi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import azad.hallaji.farzad.com.masirezendegi.helper.TypefaceUtil;
import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddQuestion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String subjectid="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        //Toast.makeText(getApplicationContext(), qid, Toast.LENGTH_LONG).show();


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

        ImageView imageView1 = (ImageView) findViewById(R.id.backButton);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestion.this , Pagemenu.class);
                startActivity(intent);
            }
        });


        if(GlobalVar.getUserID().equals("100")){
            GlobalVar.Jirmiyib(this);
        }else{
            GlobalVar.Jirib(this);
        }

        if(isOnline()){
            //requestData();

            Button button = (Button)findViewById(R.id.buttonersalsoal);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText =(EditText) findViewById(R.id.edittextsoal);
                    requestData(subjectid,editText.getText().toString(),""); //ToDO what is the questioncategory?



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

        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://telyar.dmedia.ir/webservice/Set_new_question");

        //subjectid , text , questioncategory, userid , deviceid

        //p.setParam("questionid",  qid);
        p.setParam("deviceid", GlobalVar.getDeviceID());
        p.setParam("subjectid", subjectid);
        p.setParam("text",text);
        p.setParam("questioncategory", questioncategory);
        p.setParam("userid", GlobalVar.getUserID());

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute(p);

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
                String m = jsonObject.getString("Status");
                String mess = jsonObject.getString("Message");
                updategraf(mess,m,"");

            } catch (JSONException e) {
                //e.printStackTrace();
            }


        }

    }

}
