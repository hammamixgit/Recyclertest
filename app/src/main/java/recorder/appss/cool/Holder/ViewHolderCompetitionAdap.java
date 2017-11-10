package recorder.appss.cool.Holder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;

/**
 * Created by work on 10/11/2017.
 */

public class ViewHolderCompetitionAdap extends RecyclerView.ViewHolder  {



     public    TextView tx, tcountry, txtmatch, txtnblive;
     public    ImageView im;

        public ViewHolderCompetitionAdap(View itemView) {
            super(itemView);
            txtmatch = (TextView) itemView.findViewById(R.id.nbmatch);
            txtnblive = (TextView) itemView.findViewById(R.id.nbmatchlive);
            tcountry = (TextView) itemView.findViewById(R.id.txtcardcountry);
            tx = (TextView) itemView.findViewById(R.id.txtcardcompt);
            im = (ImageView) itemView.findViewById(R.id.imgcard);

        }
    }

