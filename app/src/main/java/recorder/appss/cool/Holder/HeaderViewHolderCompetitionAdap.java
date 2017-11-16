package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderCompetitionAdap extends RecyclerView.ViewHolder {
   public  @BindView(R.id.allnb) TextView mSumMatch;
    public @BindView(R.id.allnblive) TextView mSumLive;


    public HeaderViewHolderCompetitionAdap(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
