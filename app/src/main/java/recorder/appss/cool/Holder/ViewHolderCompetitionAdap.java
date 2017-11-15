package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class ViewHolderCompetitionAdap extends RecyclerView.ViewHolder {

    //TODO add butterknife library
    public TextView mCompetitionName;
    public TextView mCountryName;
    public TextView mSumMatch;
    public TextView mSumLive;
    public ImageView mFlag;

    public ViewHolderCompetitionAdap(View itemView) {
        super(itemView);
        mSumMatch = (TextView) itemView.findViewById(R.id.nbmatch);
        mSumLive = (TextView) itemView.findViewById(R.id.nbmatchlive);
        mCountryName = (TextView) itemView.findViewById(R.id.txtcardcountry);
        mCompetitionName = (TextView) itemView.findViewById(R.id.txtcardcompt);
        mFlag = (ImageView) itemView.findViewById(R.id.imgcard);

    }
}

