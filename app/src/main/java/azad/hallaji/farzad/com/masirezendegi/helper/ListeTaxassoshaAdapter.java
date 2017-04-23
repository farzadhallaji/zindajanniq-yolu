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
import azad.hallaji.farzad.com.masirezendegi.model.Taxassos;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fayzad on 3/25/17.
 */

public class ListeTaxassoshaAdapter extends ArrayAdapter<Taxassos> {

    private Context mContext;
    private List<Taxassos> reportItemList = new ArrayList<>();



    public ListeTaxassoshaAdapter(Context context, List<Taxassos> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_taxassos,null);

        CircleImageView userimg = (CircleImageView)view.findViewById(R.id.user_img);
        TextView Name =(TextView)view.findViewById(R.id.OnvanePorseshTextView) ;
        TextView Count  =(TextView)view.findViewById(R.id.HasChildTextView) ;
        TextView IDsub  =(TextView)view.findViewById(R.id.SID) ;

        Taxassos Ittem = reportItemList.get(position);

        //new DownloadImageTask(userimg).execute(Ittem.getPicAddress()); //TODO ?*
        Name.setText(Ittem.getName());
        Count.setText(Ittem.getHasChild());
        IDsub.setText(Ittem.getSID());

        return view;
    }


}