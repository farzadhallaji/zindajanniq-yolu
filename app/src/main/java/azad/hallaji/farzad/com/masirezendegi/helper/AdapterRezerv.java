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
import azad.hallaji.farzad.com.masirezendegi.model.Rezervable;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fayzad on 5/3/17.
 */

public class AdapterRezerv  extends ArrayAdapter<Rezervable> {


    private Context mContext;
    private List<Rezervable> reportItemList = new ArrayList<>();


    public AdapterRezerv(Context context, List<Rezervable> objects) {

        super(context,0, objects);
        this.mContext=context;
        this.reportItemList=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.item_rezerv,null);

        CircleImageView moshaverimg = (CircleImageView)view.findViewById(R.id.hayayooooo);
        TextView Name =(TextView)view.findViewById(R.id.NameMarkazRezervTextView) ;
        TextView dataTextRezerv =(TextView)view.findViewById(R.id.dataTextRezerv) ;
        TextView timeTextRezerv =(TextView)view.findViewById(R.id.timeTextRezerv) ;

        Name.setText(reportItemList.get(position).getName());
        dataTextRezerv.setText(reportItemList.get(position).getAdviserDate());
        timeTextRezerv.setText(reportItemList.get(position).getAdviserTime());

        String b=reportItemList.get(position).getFree();

        if(b.equals("0"))
            moshaverimg.setImageResource(R.drawable.noentry);
        else if(b.equals("1"))
            moshaverimg.setImageResource(R.drawable.entry);

        return view;
    }

}
