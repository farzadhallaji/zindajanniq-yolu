package azad.hallaji.farzad.com.masirezendegi.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fayzad on 4/10/17.
 */

public class ListeCommentAdapter extends ArrayAdapter<Comment> {

private Context mContext;
private List<Comment> reportItemList = new ArrayList<>();



public ListeCommentAdapter(Context context, List<Comment> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.comment_item,null);

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.NameNazardahandeTExtView) ;
        TextView text  =(TextView)view.findViewById(R.id.MatneCommentTextView) ;
        TextView Tarix  =(TextView)view.findViewById(R.id.TarixeCommentTextview) ;

        Comment Ittem = reportItemList.get(position);

        //new DownloadImageTask(userimg).execute(Ittem.getPicAddress());
        Name.setText(Ittem.getUserName() + " " +Ittem.getUserFamilyName());
        text.setText(Ittem.getComment());
        Tarix.setText(Ittem.getRegTime());
        return view;
        }

}
