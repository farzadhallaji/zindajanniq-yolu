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
import azad.hallaji.farzad.com.masirezendegi.model.Moshaver;
import de.hdodenhof.circleimageview.CircleImageView;


public class ListeMoshaverinAdapter extends ArrayAdapter<Moshaver> {

    private Context mContext;
    private List<Moshaver> reportItemList = new ArrayList<>();



    public ListeMoshaverinAdapter(Context context, List<Moshaver> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_moshaverin,null);

        CircleImageView moshaverimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.AdviserName) ;
        TextView IDCOmment  =(TextView)view.findViewById(R.id.IDCommentCount) ;
        TextView commentinnazrioml  =(TextView)view.findViewById(R.id.commentinnazrioml) ;
        TextView Tag  =(TextView)view.findViewById(R.id.Tag) ;

        Moshaver Ittem = reportItemList.get(position);

        new DownloadImageTask(moshaverimg).execute(Ittem.getPicAddress());

        /*new DownloadImageTaske().execute(Ittem);
        moshaverimg.setImageBitmap(Ittem.getBitmap());*/

        Name.setText(Ittem.getAdviserName());
        IDCOmment.setText(Ittem.getUniqueID());
        commentinnazrioml.setText(Ittem.getCommentCount());
        String ss="";

        String date="";
        try {
            Long aLong ;
            String dd=Ittem.getRegTime().trim();
            if (!dd.equals("")){
                aLong=Long.parseLong(dd);
                date=new java.text.SimpleDateFormat("yyyy/MM/dd")
                        .format(new java.util.Date (aLong*1000));
            }
        }catch (Exception ignored){}
        String s="";
        for (int i = 0 ; i < Ittem.getTag().size() ; i++){
            s+=Ittem.getTag().get(i);
            if(i!=Ittem.getTag().size()-1){
                s+=" , ";
            }
        }
        Tag.setText(s);


        /*try {
            for(String s:Ittem.getTag())
                ss+=","+s;
            Tag.setText(ss.substring(1));

        }catch (Exception ignored){}*/




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

    private class DownloadImageTaske extends AsyncTask<Moshaver, Void, Bitmap> {
        Bitmap bmImage;
        Moshaver moshaver;

        @Override
        protected Bitmap doInBackground(Moshaver... params) {

            moshaver=params[0];
            String urldisplay = params[0].getPicAddress();
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

            moshaver.setBitmap(result);
        }
    }

}
