package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderCompetitionAdap extends RecyclerView.ViewHolder {

    //TODO add butterknife library
    public TextView mSumMatch;
    public TextView mSumLive;

    public HeaderViewHolderCompetitionAdap(View itemView) {
        super(itemView);
        mSumMatch = (TextView) itemView.findViewById(R.id.allnb);
        mSumLive = (TextView) itemView.findViewById(R.id.allnblive);
    }
}
