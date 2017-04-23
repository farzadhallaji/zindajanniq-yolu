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

public class ListeAlagemandiHaAdapter extends ArrayAdapter<ALagemandi> {

    private Context mContext;
    private List<ALagemandi> reportItemList = new ArrayList<>();

    ALagemandi aLagemandi;

    public ListeAlagemandiHaAdapter(Context context, List<ALagemandi> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
        aLagemandi = reportItemList.get(0);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_alagemandiha,null);

        CircleImageView moshaverimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.AdviserName) ;
        TextView typeAlagemandiTextView  =(TextView)view.findViewById(R.id.typeAlagemandiTextView) ;


        new DownloadImageTask(moshaverimg).execute(aLagemandi.getMoshavers().get(position).getPicAddress());
        Name.setText(aLagemandi.getMoshavers().get(position).getAdviserName());
        typeAlagemandiTextView.setText("مشاور");

        /*new DownloadImageTaske().execute(Ittem);
        moshaverimg.setImageBitmap(Ittem.getBitmap());

        Name.setText(Ittem.getAdviserName());
        String ss="";
        for(String s:Ittem.getTag())
            ss+=","+s;

        Tag.setText(ss.substring(1));
        IDCOmment.setText(Ittem.getCommentCount() + "    " + Ittem.getAID()+ " کد کارشناس : ");
*/

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