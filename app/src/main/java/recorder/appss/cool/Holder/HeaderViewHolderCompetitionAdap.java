package recorder.appss.cool.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 10/11/2017.
 */

public class HeaderViewHolderCompetitionAdap extends RecyclerView.ViewHolder{


     public    TextView all, alllive;

        public HeaderViewHolderCompetitionAdap(View itemView) {
            super(itemView);
            //  View = itemView;
            all = (TextView) itemView.findViewById(R.id.allnb);
            alllive = (TextView) itemView.findViewById(R.id.allnblive);


    }


}
