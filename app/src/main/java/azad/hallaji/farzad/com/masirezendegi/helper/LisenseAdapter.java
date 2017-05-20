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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.ALagemandi;
import de.hdodenhof.circleimageview.CircleImageView;

public class LisenseAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> reportItemList = new ArrayList<>();
    private List<ImageView> moshaverimgs = new ArrayList<>();
    private List<Bitmap> BitmapBitmap = new ArrayList<>();

    public LisenseAdapter(Context context, List<String> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.itemelisense,null);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.itemelisense, null);
            holder = new ViewHolder();
            holder.moshaverimg = (ImageView) convertView.findViewById(R.id.asdfghjkidghjm);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        new DownloadImageTask(holder.moshaverimg).execute(reportItemList.get(position));



        //holder.personImageView.setImageBitmap(person.getImage());

        return convertView;
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
            BitmapBitmap.add(mIcon11);
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private static class ViewHolder {
        private ImageView moshaverimg;

    }
}