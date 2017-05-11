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
import azad.hallaji.farzad.com.masirezendegi.model.ALagemandi;
import de.hdodenhof.circleimageview.CircleImageView;

public class LisenseAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> reportItemList = new ArrayList<>();


    public LisenseAdapter(Context context, List<String> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.itemelisense,null);

        ImageView moshaverimg = (ImageView) view.findViewById(R.id.asdfghjkidghjm);


        new DownloadImageTask(moshaverimg).execute(reportItemList.get(position));

        /*new DownloadImageTaske().execute(Ittem);
        moshaverimg.setImageBitmap(Ittem.getBitmap());
        Name.setText(Ittem.getAdviserName());
        String ss="";
        for(String s:Ittem.getTag())
            ss+=","+s;
        Tag.setText(ss.substring(1));
        IDCOmment.setText(Ittem.getCommentCount() + "    " + Ittem.getAID()+ " کد کارشناس : ");*/

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