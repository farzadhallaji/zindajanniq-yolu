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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.GlobalVar;
import azad.hallaji.farzad.com.masirezendegi.model.Pasox;
import de.hdodenhof.circleimageview.CircleImageView;


public class ListePasoxhayeksoalAdapter extends ArrayAdapter<Pasox> {


    private Context mContext;
    private List<Pasox> reportItemList = new ArrayList<>();
    private static String userid="100";
    public static String contentid="";
    private List<ViewHolder> viewHolders=new ArrayList<>();

    public ListePasoxhayeksoalAdapter(Context context, List<Pasox> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_pasoxesola,null);

        ImageView like = (ImageView)view.findViewById(R.id.likeImageview);
        ImageView dislike = (ImageView)view.findViewById(R.id.dislikeImageview);

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);

        TextView Name =(TextView)view.findViewById(R.id.NameMoshaverTExtView) ;
        TextView Matnepasox  =(TextView)view.findViewById(R.id.MatnePasoxTextView) ;

        TextView CountLike  =(TextView)view.findViewById(R.id.LikeCountTextView) ;
        TextView CountdisLike  =(TextView)view.findViewById(R.id.disLikeCountTextView) ;

        viewHolders.add(new ViewHolder(CountLike,CountdisLike,like,dislike));

        TextView TarixeJavab  =(TextView)view.findViewById(R.id.TarixepasoxTextview) ;


        final Pasox Ittem = reportItemList.get(position);

        /*like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postgetData(Ittem.getQid(),userid , "1", contentid ,position);
                Log.i("1111111","1");
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postgetData(Ittem.getQid(),userid  , "-1", contentid, position);
            }
        });*/

        /*long time = Long.parseLong(Ittem.getTarixeJavab());
        Date d= new Date(time);

        TarixeJavab.setText(String.valueOf(d));*/

        new DownloadImageTask(userimg).execute(Ittem.getUserimg());
        Name.setText(Ittem.getName());
        Matnepasox.setText(Ittem.getMatnepasox());
        CountLike.setText(Ittem.getCountLike());
        CountdisLike.setText(Ittem.getCountdisLike());
        TarixeJavab.setText(Ittem.getTarixeJavab());
        String date="";
        String [] asaddddddd = new String[3];
        try {
            Long aLong ;
            String dd=Ittem.getTarixeJavab().trim();
            if (!dd.equals("")){
                aLong=Long.parseLong(dd);
                date=new java.text.SimpleDateFormat("yyyy/MM/dd")
                        .format(new java.util.Date (aLong*1000));
                asaddddddd=date.split("/");
                Log.i("asdfghjntikuikuuyh",asaddddddd[0]);
                Log.i("asdfghjntikuikuuyh",asaddddddd[1]);
                Log.i("asdfghjntikuikuuyh",asaddddddd[2]);


            }
        }catch (Exception ignored){}

        //int y = Integer.getInteger();

        //Log.i("asdfghjntikuikuuyh",date.substring(3)+"dsd");

        //String asad =PersianDate.Shamsi(Integer.valueOf(asaddddddd[0]) ,Integer.valueOf(asaddddddd[1]) ,Integer.valueOf(asaddddddd[2]) );

        int y, m,d;
        try {
            y=Integer.parseInt(asaddddddd[0]);
        }catch (Exception e){
            y=0;
        }
        try {
            m=Integer.parseInt(asaddddddd[1]);
        }catch (Exception e){
            m=0;
            //m=Integer.parseInt(String.valueOf(asaddddddd[1].charAt(1)));
        }
        try {
            d=Integer.parseInt(asaddddddd[2]);
        }catch (Exception e){
            d=0;
            //d=Integer.parseInt(String.valueOf(asaddddddd[2].charAt(1)));
        }
        Log.i("hjntiasdsdkuikuuyh",y+"dsd"+m);

        String asad =PersianDate.Shamsi(y,m,d);
        if (y==0){
            TarixeJavab.setText("");
        }else{
            TarixeJavab.setText(asad);
        }
        //Log.i("asdfghjntikuikuuyh",convertedDate.getYear() +"" +convertedDate.getMonth()+"" +convertedDate.getDay());


        return view;
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


    class ViewHolder {

        TextView CountLikee ;
        TextView CountdisLikee;
        ImageView like;
        ImageView dislike;

        public ViewHolder(TextView countLikee, TextView countdisLikee, ImageView like, ImageView dislike) {
            CountLikee = countLikee;
            CountdisLikee = countdisLikee;
            this.like = like;
            this.dislike = dislike;
        }

        public TextView getCountLikee() {
            return CountLikee;
        }

        public void setCountLikee(TextView countLikee) {
            CountLikee = countLikee;
        }

        public TextView getCountdisLikee() {
            return CountdisLikee;
        }

        public void setCountdisLikee(TextView countdisLikee) {
            CountdisLikee = countdisLikee;
        }

        public ImageView getLike() {
            return like;
        }

        public void setLike(ImageView like) {
            this.like = like;
        }

        public ImageView getDislike() {
            return dislike;
        }

        public void setDislike(ImageView dislike) {
            this.dislike = dislike;
        }
    }

}
