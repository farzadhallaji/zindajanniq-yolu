package azad.hallaji.farzad.com.masirezendegi.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azad.hallaji.farzad.com.masirezendegi.R;
import azad.hallaji.farzad.com.masirezendegi.model.Question;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Question> reportItemList = new ArrayList<>();

    //Constructor

    public ProductListAdapter(Context context, List<Question> objects) {
        this.mContext=context;
        this.reportItemList=objects;
    }

    public void addListItemToAdapter(List<Question> list) {
        //Add list to current array list of data
        reportItemList.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return reportItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
//        Tag.setText(Ittem.getTag().toString());  //TODO

        return view;
    }
}
