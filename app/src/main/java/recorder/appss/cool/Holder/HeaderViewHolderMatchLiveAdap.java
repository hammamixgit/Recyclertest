package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderMatchLiveAdap extends RecyclerView.ViewHolder{


    public  TextView txt_header;
    public   ImageView im_header;

    public HeaderViewHolderMatchLiveAdap(View itemView) {
        super(itemView);
        im_header = (ImageView) itemView.findViewById(R.id.compet_flag_div);
        txt_header = (TextView) itemView.findViewById(R.id.compet_name_div);
    }


}
