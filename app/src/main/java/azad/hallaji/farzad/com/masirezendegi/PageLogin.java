package azad.hallaji.farzad.com.masirezendegi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PageLogin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static AsyncHttpClient client = new AsyncHttpClient();
    TextView textView ;
    Button button ;
    EditText editText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiran_yermain);

        textView = (TextView)findViewById(R.id.lutfanimiyiText);
        button = (Button) findViewById(R.id.loginBut);
        editText = (EditText) findViewById(R.id.ahbarinshumarasiEdit);

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
                Intent intent = new Intent(PageLogin.this , Pagemenu.class);
                startActivity(intent);
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
            //Log.i("elabella1",aLagemandis.size()+ " ");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestDataa();
                }
            });

            //requestDatsssa();

            //Log.i("tutal",aLagemandis.size()+ " ");

            //requestData();

        }else {
            Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
        }


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

    private void requestDataa() {
        RequestParams params = new RequestParams();

        params.put("phone_number",editText.getText().toString()); //Add the data you'd like to send to the server.
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/register_user/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));

                    String Message = jsonObject.getString("Message");
                    String s= jsonObject.getString("Status");

                    updategraf(Message,s,editText.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("aassdfghasaddasasasasasasasjuytrew","1");
            }
        });
    }

    private void updategraf(String message, String m, final String phone) {

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_login, null);

        if(m.equals("1")){

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            dialogBuilder.setView(dialogView);

            TextView textView =(TextView)dialogView.findViewById(R.id.aaT);
            textView.setText(message);

            Button button = (Button)dialogView.findViewById(R.id.buttombastan);

            /*EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
            editText.setText("test label");*/
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    changeui(phone);

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

    private void changeui(final String phone) {

        editText.setText("");
        textView.setText("لطفا کد تایید را وارد نمایید");
        editText.setHint("کد تایید");
        button.setText("ارسال کد");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataaa(phone);
            }
        });

    }

    private void requestDataaa(String phone) {
        RequestParams params = new RequestParams();

        params.put("phone_number",phone); //Add the data you'd like to send to the server.
        params.put("regcode",editText.getText().toString()); //Add the data you'd like to send to the server.
        params.put("deviceid", GlobalVar.getDeviceID());
        client.post("http://telyar.dmedia.ir/webservice/check_registration_code/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));

                    Log.i("974861329+46",new String(responseBody));


                    try {

                        String Message = jsonObject.getString("Message");
                        String s= jsonObject.getString("Status");

                        if(s.equals("-1")){

                            updategraf(Message,"-1","");
                        }else{
                            try {
                                String UserType = jsonObject.getString("UserType");
                                String UID = jsonObject.getString("UID");
                                String PicAddress = jsonObject.getString("PicAddress");

                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserType", UserType).apply();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UID", UID).apply();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PicAddress", PicAddress).apply();

                                Log.i("asdfghhgbv","sefgtbrvfvdfvdfvlgklkgfgb1");
                                GlobalVar.setPicAddress(PicAddress);
                                Log.i("asdfghhgbv","sefgtbrvfvdfvdfvlgklkgfgb2");
                                GlobalVar.setUserID(UID);
                                Log.i("asdfghhgbv","sefgtbrvfvdfvdfvlgklkgfgb3");
                                GlobalVar.setUserType(UserType);
                                Log.i("asdfghhgbv","sefgtbrvfvdfvdfvlgklkgfgb4");


                                Intent intent = new Intent(PageLogin.this,Pagemenu.class);
                                startActivity(intent);



                            }catch (Exception ignored){}
                        }

                    }catch (Exception e){
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("aassdfghjuytrew","1");
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
