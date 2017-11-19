package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderMatchCompetHolder extends RecyclerView.ViewHolder {
    public @BindView(R.id.compet_name_h)
    TextView mCompetitionTitle;
    public @BindView(R.id.compet_flag_h)
    ImageView mFlagCountry;

    //TODO add butterknife library
    public HeaderViewHolderMatchCompetHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
