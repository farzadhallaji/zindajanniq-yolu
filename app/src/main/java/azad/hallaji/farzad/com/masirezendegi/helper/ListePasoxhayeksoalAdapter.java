package azad.hallaji.farzad.com.masirezendegi.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.Pasox;
import de.hdodenhof.circleimageview.CircleImageView;


public class ListePasoxhayeksoalAdapter extends ArrayAdapter<Pasox> {


    private Context mContext;
    private List<Pasox> reportItemList = new ArrayList<>();



    public ListePasoxhayeksoalAdapter(Context context, List<Pasox> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.pasoxesola_item,null);

        ImageView like = (ImageView)view.findViewById(R.id.likeImageview);
        final ImageView dislike = (ImageView)view.findViewById(R.id.dislikeImageview);

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.NameMoshaverTExtView) ;
        TextView Matnepasox  =(TextView)view.findViewById(R.id.MatnePasoxTextView) ;
        TextView CountLike  =(TextView)view.findViewById(R.id.LikeCountTextView) ;
        TextView CountdisLike  =(TextView)view.findViewById(R.id.disLikeCountTextView) ;
        TextView TarixeJavab  =(TextView)view.findViewById(R.id.TarixepasoxTextview) ;

        Pasox Ittem = reportItemList.get(position);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likepost();
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikepost();
            }
        });

        new DownloadImageTask(userimg).execute(Ittem.getUserimg());
        Name.setText(Ittem.getName());
        Matnepasox.setText(Ittem.getMatnepasox());
        CountLike.setText(Ittem.getCountLike());
        CountdisLike.setText(Ittem.getCountdisLike());
        TarixeJavab.setText(Ittem.getTarixeJavab());

        return view;
    }

    private void dislikepost() {

        //userid , contentid , contenttype , status , deviceid



    }

    /*void postgetData(final String qid , final String deviceid){


        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "http://telyar.dmedia.ir/webservice/get_question_answer";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ahmad",response);
                //Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
                updateview(response);


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("questionid", qid); //Add the data you'd like to send to the server.
                MyData.put("deviceid",deviceid); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }*/

    private void updateview(String response) {



    }

    private void likepost() {



    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}
