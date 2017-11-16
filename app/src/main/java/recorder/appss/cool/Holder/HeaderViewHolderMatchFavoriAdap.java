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

public class HeaderViewHolderMatchFavoriAdap extends RecyclerView.ViewHolder {
    public @BindView(R.id.compet_name_div) TextView mCompetitionTitle;
    public @BindView(R.id.compet_flag_div) ImageView mFlagCountry;
    //TODO add butterknife library


    public HeaderViewHolderMatchFavoriAdap(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

}
