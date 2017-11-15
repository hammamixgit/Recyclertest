package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.LikeButton;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class ViewHolderMatchLiveAdap extends RecyclerView.ViewHolder {

    //TODO add butterknife library
    public TextView mTeam1;
    public TextView  mTeam2;
    public TextView mScore;
    public TextView mTime ;
    public TextView  mTimeStart;
    public ImageView mTeam1Logo;
    public ImageView mTeam2Logo;
    public LinearLayout mLayoutLive;
    public LikeButton mBtnLike;

    public ViewHolderMatchLiveAdap(View itemView) {
        super(itemView);
        mLayoutLive = (LinearLayout) itemView.findViewById(R.id.lllive);
        mTeam1 = (TextView) itemView.findViewById(R.id.team1);
        mTeam2 = (TextView) itemView.findViewById(R.id.team2);
        mScore = (TextView) itemView.findViewById(R.id.score);
        mTime = (TextView) itemView.findViewById(R.id.time);
        mTeam1Logo = (ImageView) itemView.findViewById(R.id.imgteam1);
        mTeam2Logo = (ImageView) itemView.findViewById(R.id.imgteam2);
        mTimeStart = (TextView) itemView.findViewById(R.id.match_start);
        mBtnLike=(LikeButton)itemView.findViewById(R.id.star_button);
    }
}