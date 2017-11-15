package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderMatchCompetAdap extends RecyclerView.ViewHolder {

    //TODO add butterknife library
    public TextView mCompetitionTitle;
    public ImageView mFlagCountry;

    public HeaderViewHolderMatchCompetAdap(View itemView) {
        super(itemView);
        mFlagCountry = (ImageView) itemView.findViewById(R.id.compet_flag_h);
        mCompetitionTitle = (TextView) itemView.findViewById(R.id.compet_name_h);

    }


}
