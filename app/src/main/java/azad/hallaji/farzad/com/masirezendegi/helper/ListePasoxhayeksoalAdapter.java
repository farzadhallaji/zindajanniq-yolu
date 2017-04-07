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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.Pasox;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fayzad on 4/7/17.
 */

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

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.NameMoshaverTExtView) ;
        TextView Matnepasox  =(TextView)view.findViewById(R.id.MatnePasoxTextView) ;
        TextView CountLike  =(TextView)view.findViewById(R.id.LikeCountTextView) ;
        TextView CountdisLike  =(TextView)view.findViewById(R.id.disLikeCountTextView) ;
        TextView TarixeJavab  =(TextView)view.findViewById(R.id.TarixepasoxTextview) ;

        Pasox Ittem = reportItemList.get(position);

        new DownloadImageTask(userimg).execute(Ittem.getUserimg());
        Name.setText(Ittem.getName());
        Matnepasox.setText(Ittem.getMatnepasox());
        CountLike.setText(Ittem.getCountLike());
        CountdisLike.setText(Ittem.getCountdisLike());
        TarixeJavab.setText(Ittem.getTarixeJavab());

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



}
