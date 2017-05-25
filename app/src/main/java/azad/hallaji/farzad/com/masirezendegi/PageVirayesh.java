package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.internet.HttpManager;
import azad.hallaji.farzad.com.masirezendegi.internet.RequestPackage;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageVirayesh extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    Uri selectedImage;
    EditText namexanivadeEdit , shomareteleEdit , emailEdit;
    EditText barchasbEdit  , costperminEdit;
    EditText maxtimeEdit , sexEdit , dialtecEdit , aboutmeEdit;

    TextView sihhhhh1,sihhhhh2;

    CircleImageView imageviewuserVirayesh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_virayesh);

        TextView virayeshTextinToolbar=(TextView) findViewById(R.id.virayeshTextinToolbar);
        final TextView zaxireTextinToolbar=(TextView) findViewById(R.id.zaxireTextinToolbar);
        init();
        ableall(false);



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


        final ImageView imageView1 =(ImageView) findViewById(R.id.onclickeasadinchimastanxanim);
        imageView1.setVisibility(View.GONE);

        if(isOnline()){
            setAlage();

            if(GlobalVar.getUserType().equals("adviser")) {

                virayeshTextinToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ableall(true);
                        sihhhhh1.setVisibility(View.VISIBLE);
                        sihhhhh2.setVisibility(View.VISIBLE);
                        imageView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                            }
                        });
                        zaxireTextinToolbar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestData();
                            }
                        });
                    }
                });

                imageView1.setVisibility(View.VISIBLE);

            }else if(GlobalVar.getUserType().equals("user")) {

                virayeshTextinToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextView  textView =(TextView)findViewById(R.id.sihhhhh1);
                        TextView  textView2 =(TextView)findViewById(R.id.sihhhhh2);
                        textView.setVisibility(View.GONE);
                        textView2.setVisibility(View.GONE);
                        imageView1.setVisibility(View.VISIBLE);
                        boolean b=false;
                        sihhhhh1.setVisibility(View.GONE);
                        sihhhhh2.setVisibility(View.GONE);
                        namexanivadeEdit.setEnabled(!b);
                        shomareteleEdit.setEnabled(!b);
                        emailEdit.setEnabled(!b);
                        barchasbEdit.setVisibility(View.GONE);
                        costperminEdit.setVisibility(View.GONE);
                        maxtimeEdit.setVisibility(View.GONE);
                        sexEdit.setVisibility(View.GONE);
                        dialtecEdit.setVisibility(View.GONE);
                        aboutmeEdit.setVisibility(View.GONE);

                        imageView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                            }
                        });
                        zaxireTextinToolbar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestData();
                            }
                        });
                    }
                });



            }


        }else{
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }



    }

    private void ableall(boolean b) {

        namexanivadeEdit.setEnabled(b);
        shomareteleEdit.setEnabled(b);
        emailEdit.setEnabled(b);
        barchasbEdit.setEnabled(b);
        costperminEdit.setEnabled(b);
        maxtimeEdit.setEnabled(b);
        sexEdit.setEnabled(b);
        dialtecEdit.setEnabled(b);
        aboutmeEdit.setEnabled(b);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    selectedImage = imageReturnedIntent.getData();
                    imageviewuserVirayesh.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    selectedImage = imageReturnedIntent.getData();
                    imageviewuserVirayesh.setImageURI(selectedImage);
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    private void init() {

        sihhhhh1 = (TextView)findViewById(R.id.sihhhhh1);
        sihhhhh2 = (TextView)findViewById(R.id.sihhhhh2);
        shomareteleEdit=(EditText)findViewById(R.id.shomareteleEdit);
        namexanivadeEdit=(EditText)findViewById(R.id.namexanivadeEdit);
        shomareteleEdit=(EditText)findViewById(R.id.shomareteleEdit);
        emailEdit=(EditText)findViewById(R.id.emailEdit);
        barchasbEdit=(EditText)findViewById(R.id.barchasbEdit);
        costperminEdit=(EditText)findViewById(R.id.costperminEdit);
        maxtimeEdit=(EditText)findViewById(R.id.maxtimeEdit);
        sexEdit=(EditText)findViewById(R.id.sexEdit);
        dialtecEdit=(EditText)findViewById(R.id.dialtecEdit);
        aboutmeEdit=(EditText)findViewById(R.id.aboutmeEdit);
        imageviewuserVirayesh=(CircleImageView)findViewById(R.id.imageviewuserVirayesh);

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

        try {
            p.setParam("userid", GlobalVar.getUserID());
        }catch (Exception ignored){}
        try {
            p.setParam("familyname",namexanivadeEdit.getText().toString());

        }catch (Exception ignored){}
        try {
            p.setParam("email",emailEdit.getText().toString());

        }catch (Exception ignored){}
        try {

            p.setParam("gender", sexEdit.getText().toString());
        }catch (Exception ignored){}
        try {
            p.setParam("telephone",shomareteleEdit.getText().toString());

        }catch (Exception ignored){}
        try {
            p.setParam("picaddress",selectedImage.toString());

        }catch (Exception ignored){}
        try {
            p.setParam("aboutme",aboutmeEdit.getText().toString());

        }catch (Exception ignored){}
        try {
            p.setParam("costpermin",costperminEdit.getText().toString());

        }catch (Exception ignored){}
        try {
            p.setParam("dialect",dialtecEdit.getText().toString());

        }catch (Exception ignored){}
        try {

            p.setParam("advisermaxtim",maxtimeEdit.getText().toString());
        }catch (Exception ignored){}


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
                jostaruzunueymahziba(jsonObject.getString("Message"));


                //Toast.makeText(getApplicationContext(),jsonObject.getString("Message"), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private void jostaruzunueymahziba(String message) {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
        TextView textVie =(TextView)dialogView.findViewById(R.id.aT);
        textView.setText(message);
        textVie.setVisibility(View.INVISIBLE);
        Button button = (Button)dialogView.findViewById(R.id.buttombastan);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });

    }

    void setAlage() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://telyar.dmedia.ir/webservice/Get_profile/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("aladfffgree", response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject= new JSONObject(response);
                    GlobalVar.setUserID(jsonObject.getString("UID"));
                    try {
                        namexanivadeEdit.setText(jsonObject.getString("Name")+ " "+ jsonObject.getString("FamilyName"));
                    }catch (Exception ignored){}

                    try {
                        emailEdit.setText(jsonObject.getString("Email"));
                    }catch (Exception ignored){}
                    try {
                        sexEdit.setText(jsonObject.getString("Gender"));
                    }catch (Exception ignored){}
                    try {
                        shomareteleEdit.setText(jsonObject.getString("Telephone"));
                    }catch (Exception ignored){}
                    try {
                        selectedImage=Uri.parse(jsonObject.getString("PicAddress"));
                        imageviewuserVirayesh.setImageURI(selectedImage);
                    }catch (Exception ignored){}
                    try {
                        aboutmeEdit.setText(jsonObject.getString("AboutMe"));
                    }catch (Exception ignored){}
                    try {
                        costperminEdit.setText(jsonObject.getString("CostPerMin"));
                    }catch (Exception ignored){}
                    try {
                        dialtecEdit.setText(jsonObject.getString("Dialect"));
                    }catch (Exception ignored){}
                    try {
                        maxtimeEdit.setText(jsonObject.getString("AdviserMaxTime"));
                    }catch (Exception ignored){}


                } catch (JSONException e) {
                    //e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //Log.i("asasasasasasa",adviseridm+"/"+GlobalVar.getDeviceID());
                MyData.put("userid", GlobalVar.getUserID()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
