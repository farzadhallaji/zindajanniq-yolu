package azad.hallaji.farzad.com.masirezendegi.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fayzad on 3/24/17.
 */

public class ListePorseshhaAdapter extends ArrayAdapter<Question>  {

    private Context mContext;
    private List<Question> reportItemList = new ArrayList<>();



    public ListePorseshhaAdapter(Context context, List<Question> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.porseshha_item,null);

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.OnvanePorseshTextView) ;
        TextView text  =(TextView)view.findViewById(R.id.MatnePorseshTextView) ;
        TextView Count  =(TextView)view.findViewById(R.id.AnswerCount) ;
        TextView IDsub  =(TextView)view.findViewById(R.id.QID) ;

        Question Ittem = reportItemList.get(position);

        //new DownloadImageTask(userimg).execute(Ittem.getPicAddress()); //TODO ?*
        Name.setText(Ittem.getQuestionSubject());
        text.setText(Ittem.getText());  //TODO
        Count.setText(Ittem.getAnswerCount());
        IDsub.setText(Ittem.getQID());

        return view;
    }


}
