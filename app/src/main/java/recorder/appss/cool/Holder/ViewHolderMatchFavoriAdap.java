package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.LikeButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class ViewHolderMatchFavoriAdap extends RecyclerView.ViewHolder {

    public @BindView(R.id.team1) TextView mTeam1;
    public @BindView(R.id.team2) TextView mTeam2;
    public  @BindView(R.id.score) TextView mScore;
    public   @BindView(R.id.time) TextView mTime;
    public  @BindView(R.id.match_start) TextView mTimeStart;
    public  @BindView(R.id.imgteam1) ImageView mTeam1Logo;
    public  @BindView(R.id.imgteam2) ImageView mTeam2Logo;
    public  @BindView(R.id.lllive) LinearLayout mLayoutLive;
    public   @BindView(R.id.star_button) LikeButton mBtnLike;

    public ViewHolderMatchFavoriAdap(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}