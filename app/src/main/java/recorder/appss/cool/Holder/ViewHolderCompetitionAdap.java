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

public class ViewHolderCompetitionAdap extends RecyclerView.ViewHolder {
    public  @BindView(R.id.txtcardcompt) TextView mCompetitionName;
    public  @BindView(R.id.txtcardcountry) TextView mCountryName;
    public  @BindView(R.id.nbmatch) TextView mSumMatch;
    public  @BindView(R.id.nbmatchlive) TextView mSumLive;
    public @BindView(R.id.imgcard) ImageView mFlag;
    //TODO add butterknife library

    public ViewHolderCompetitionAdap(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }
}

