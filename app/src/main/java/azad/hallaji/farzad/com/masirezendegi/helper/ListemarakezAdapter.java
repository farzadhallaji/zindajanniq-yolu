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
import azad.hallaji.farzad.com.masirezendegi.model.Markaz;
import de.hdodenhof.circleimageview.CircleImageView;



public class ListemarakezAdapter extends ArrayAdapter<Markaz> {

    private Context mContext;
    private List<Markaz> reportItemList = new ArrayList<>();



    public ListemarakezAdapter(Context context, List<Markaz> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_markaz,null);

        TextView Name =(TextView)view.findViewById(R.id.AdviserName) ;
        TextView IDCOmment  =(TextView)view.findViewById(R.id.IDCommentCount) ;
        TextView Tag  =(TextView)view.findViewById(R.id.Tag) ;

        Markaz Ittem = reportItemList.get(position);

        Name.setText(Ittem.getMainPlaceName());
        //Tag.setText(Ittem.getAboutMainPlace());
        IDCOmment.setText(Ittem.getTelephone());


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
