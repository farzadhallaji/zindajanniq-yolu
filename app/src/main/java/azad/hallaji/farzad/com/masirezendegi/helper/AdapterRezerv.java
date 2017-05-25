package azad.hallaji.farzad.com.masirezendegi.helper;


import android.content.Context;
import android.util.Log;
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


        String date="" ,dd=reportItemList.get(position).getAdviserDate();
        String [] asaddddddd = new String[3];

        try {
            if (!dd.equals("")){
                asaddddddd=dd.split("-");
                Log.i("asdfghjntikuikuuyh",asaddddddd[0]);
                Log.i("asdfghjntikuikuuyh",asaddddddd[1]);
                Log.i("asdfghjntikuikuuyh",asaddddddd[2]);
            }
        }catch (Exception ignored){}

        //int y = Integer.getInteger();

        //Log.i("asdfghjntikuikuuyh",date.substring(3)+"dsd");

        //String asad =PersianDate.Shamsi(Integer.valueOf(asaddddddd[0]) ,Integer.valueOf(asaddddddd[1]) ,Integer.valueOf(asaddddddd[2]) );

        int y, m,d;
        try {
            y=Integer.parseInt(asaddddddd[0]);
        }catch (Exception e){
            y=0;
        }
        try {
            m=Integer.parseInt(asaddddddd[1]);
        }catch (Exception e){
            m=0;
            //m=Integer.parseInt(String.valueOf(asaddddddd[1].charAt(1)));
        }
        try {
            d=Integer.parseInt(asaddddddd[2]);
        }catch (Exception e){
            d=0;
            //d=Integer.parseInt(String.valueOf(asaddddddd[2].charAt(1)));
        }
        Log.i("hjntiasdsdkuikuuyh",y+"dsd"+m);

        String asad =PersianDate.Shamsi(y,m,d);
        if (y==0){
            dataTextRezerv.setText("");
        }else{
            dataTextRezerv.setText(asad);
        }


        timeTextRezerv.setText(reportItemList.get(position).getAdviserTime());

        String b=reportItemList.get(position).getFree();

        if(b.equals("0"))
            moshaverimg.setImageResource(R.drawable.noentry);
        else if(b.equals("1"))
            moshaverimg.setImageResource(R.drawable.entry);

        return view;
    }

}
