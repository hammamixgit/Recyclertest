package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderMatchFavoriAdap extends RecyclerView.ViewHolder{

    //TODO add butterknife library
    public TextView txt_header;
    public  ImageView im_header;

    public HeaderViewHolderMatchFavoriAdap(View itemView) {
        super(itemView);

        im_header = (ImageView) itemView.findViewById(R.id.compet_flag_div);
        txt_header = (TextView) itemView.findViewById(R.id.compet_name_div);

        // add your ui components here like this below
        //txtName = (TextView) View.findViewById(R.id.txt_name);

    }

}
