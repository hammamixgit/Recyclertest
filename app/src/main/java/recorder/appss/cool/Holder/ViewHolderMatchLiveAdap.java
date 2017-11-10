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

    public   TextView team1txt, team2txt, score, time, timestart;
    public   ImageView im1, im2;
    public   LinearLayout layout_live;
    public LikeButton btn_like;

    public ViewHolderMatchLiveAdap(View itemView) {
        super(itemView);
        layout_live = (LinearLayout) itemView.findViewById(R.id.lllive);
        team1txt = (TextView) itemView.findViewById(R.id.team1);
        team2txt = (TextView) itemView.findViewById(R.id.team2);
        score = (TextView) itemView.findViewById(R.id.score);
        time = (TextView) itemView.findViewById(R.id.time);
        im1 = (ImageView) itemView.findViewById(R.id.imgteam1);
        im2 = (ImageView) itemView.findViewById(R.id.imgteam2);
        timestart = (TextView) itemView.findViewById(R.id.match_start);
        btn_like = (LikeButton) itemView.findViewById(R.id.star_button);

    }

}