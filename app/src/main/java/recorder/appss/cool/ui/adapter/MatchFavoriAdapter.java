package recorder.appss.cool.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import recorder.appss.cool.Holder.HeaderViewHolderMatchFavoriAdap;
import recorder.appss.cool.Holder.ViewHolderMatchFavoriAdap;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class MatchFavoriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> list_fav = new ArrayList<>();
    List<Match> list_of_matchs_live = new ArrayList<>();
    List<Match> list_of_matchs_live2 = new ArrayList<>();



    private int pos=0;
int current_compet=0;

    public MatchFavoriAdapter(List<Match> list_match ) {

        list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
        list_of_matchs_live.addAll(list_match);
     for(Match m : list_of_matchs_live)
         if(list_fav.contains(m.getDbid()+"")) {
             if (pos == 0) {
                 Match e = new Match();
                 e.setCompetition(m.getCompetition());
                 e.setDbid(-1);
                 list_of_matchs_live2.add(e);
                 list_of_matchs_live2.add(m);
                 current_compet = m.getCompetition().getDbid();
                 pos++;
             } else {
                 if (current_compet == m.getCompetition().getDbid()) {
                     list_of_matchs_live2.add(m);
                 } else {
                     Match e = null;
                     e.setCompetition(m.getCompetition());
                     e.setDbid(-1);
                     list_of_matchs_live2.add(e);
                     current_compet = m.getCompetition().getDbid();
                 }

             }
         }
    }

    @Override
    public int getItemViewType(int position) {

        if( list_of_matchs_live2.get(position).getDbid()<0)
        {return Constants.TYPE_HEADER;}

       else
        return  Constants.TYPE_ITEM;




    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false);
            ViewHolderMatchFavoriAdap mvh = new ViewHolderMatchFavoriAdap(v);
            return mvh;

        } else if (viewType == Constants.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_divider_matchs_live, parent, false);
            return new HeaderViewHolderMatchFavoriAdap(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

        if (holder instanceof ViewHolderMatchFavoriAdap) {
            if (list_fav.contains(list_of_matchs_live2.get(position).getDbid().toString()))((ViewHolderMatchFavoriAdap) holder).btn_like.setLiked(true);else ((ViewHolderMatchFavoriAdap) holder).btn_like.setLiked(false);

            ((ViewHolderMatchFavoriAdap) holder).btn_like.setOnLikeListener(new OnLikeListener() {

                @Override
                public void liked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.addfavPref( list_of_matchs_live2.get(position).getDbid().toString());
                    list_fav.clear();
                    list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
                    notifyItemChanged(position);
                     // Log.d("favori",list_of_matchs_live2.get(position).getAwayTeam().getName()+"-"+list_of_matchs_live2.get(position).getHomeTeam().getName()+"- liked!!");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.removefavPref(list_of_matchs_live2.get(position).getDbid().toString());
                    list_fav.clear();
                    list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
                    int resultat= verif_match_for_compet(list_of_matchs_live2.get(position).getCompetition().getDbid());
                    list_of_matchs_live2.remove(position);
                    if(resultat==1){list_of_matchs_live2.remove(position-1); notifyDataSetChanged();}else {
                         notifyItemRemoved(position);

                        // if((resultat==1))list_of_matchs_live2.remove(position);
                          notifyItemRangeChanged(position,list_of_matchs_live2.size());//delete from live 2

                    }

                   // Log.d("favori",list_of_matchs_live2.get(position).getAwayTeam().getName()+"-"+list_of_matchs_live2.get(position).getHomeTeam().getName()+"- unnliked!!");

                }
            });

            ((ViewHolderMatchFavoriAdap) holder).team1txt.setText(list_of_matchs_live2.get(position).getHomeTeam().getShortName());
            ((ViewHolderMatchFavoriAdap) holder).team2txt.setText(list_of_matchs_live2.get(position).getAwayTeam().getShortName());
            ((ViewHolderMatchFavoriAdap) holder).score.setText(list_of_matchs_live2.get(position).getHomeGoals() + "-" + list_of_matchs_live2.get(position).getAwayGoals());

            long timestamp_start = (long) list_of_matchs_live2.get(position).getStart();
            DateTime dt = new DateTime(timestamp_start);
            String hour, minutes;
            if (dt.getHourOfDay() > 9) {
                hour = dt.getHourOfDay() + "";
            } else {
                hour = "0" + dt.getHourOfDay();
            }
            if (dt.getMinuteOfHour() > 9) {
                minutes = dt.getMinuteOfHour() + "";
            } else {
                minutes = "0" + dt.getMinuteOfHour();
            }
            String time = hour + ":" + minutes;
            // String time = dt.getYear()+"."+dt.getMonthOfYear()+"."+dt.getDayOfMonth()+"\n"+"     "+hour+":"+minutes;
            ((ViewHolderMatchFavoriAdap) holder).timestart.setText(time);
            String Live_time;
            if (list_of_matchs_live2.get(position).getCurrentState() == 0)
                Live_time = instant_time(list_of_matchs_live2.get(position).getCurrentState(), 0);
            else
                Live_time = instant_time(list_of_matchs_live2.get(position).getCurrentState(), (long) list_of_matchs_live2.get(position).getCurrentStateStart());

            String[] live_data = Live_time.split(":");


            ((ViewHolderMatchFavoriAdap) holder).time.setText(live_data[1]);

        } else if (holder instanceof HeaderViewHolderMatchFavoriAdap) {
            ((HeaderViewHolderMatchFavoriAdap) holder).txt_header.setText(list_of_matchs_live2.get(position).getCompetition().getName());


            MultiTransformation multi = new MultiTransformation(
                    new BlurTransformation(1),
                    new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
            Glide
                    .with(((HeaderViewHolderMatchFavoriAdap) holder).im_header.getContext())
                    .load(list_of_matchs_live2.get(position).getCompetition().getFlagUrl())
                    .apply(bitmapTransform(multi))
                    .into(((HeaderViewHolderMatchFavoriAdap) holder).im_header);
        }

    }

private int verif_match_for_compet(Integer id_comp)

{
int result =0;
    for(Match m : list_of_matchs_live2)
    {
        if(m.getDbid()>-1 && m.getCompetition().getDbid().equals(id_comp )){result++;}
    }

    return  result;
}
    @Override
    public int getItemCount() {
        return list_of_matchs_live2.size() ;
    }

    public void updateAnswers(List<Match> items) {
        pos=0;
        list_of_matchs_live.clear(); list_of_matchs_live2.clear();
        list_of_matchs_live .addAll(items) ;
        for(Match m : list_of_matchs_live) {
            Log.d("matchdetails", m.getCompetition().getName() + "-" + m.getAwayTeam().getName());
            if(list_fav.contains(m.getDbid()+"")) {
            if (pos == 0) {
                Match e = new Match();
                e.setCompetition(m.getCompetition());
                e.setDbid(-1);
                list_of_matchs_live2.add(e);
                list_of_matchs_live2.add(m);
                current_compet = m.getCompetition().getDbid();
                pos++;
            } else {
                if (current_compet == m.getCompetition().getDbid()) {
                    list_of_matchs_live2.add(m);
                } else {
                    Match e = new Match();
                    e.setCompetition(m.getCompetition());
                    e.setDbid(-1);
                    list_of_matchs_live2.add(e);
                    list_of_matchs_live2.add(m);
                    current_compet = m.getCompetition().getDbid();
                }
            }
        }
            }
        notifyDataSetChanged();
        Log.d("matchssize", list_of_matchs_live2.size()+"");
    }

    public String instant_time(int state, long current_state_start) {
        String result = "";
        if (state == 0) {
            result = "1:FX";
        } else if (state == 1) {
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result = "2:" + duration.getStandardMinutes();
        }//2:0+now-start
        else if (state == 2) {
            result = "2:HT";
        } else if (state == 3) {
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result = "2:" + (45 + duration.getStandardMinutes());
        }//2:45+now-start
        else if (state == 4) {
            result = "2:ET";
        } else if (state == 5) {
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result = "2:" + (90 + duration.getStandardMinutes());
        }//2:90+now-start
        else if (state == 6) {
            result = "2:ET HT";
        } else if (state == 7) {
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result = "2:" + (105 + duration.getStandardMinutes());
        }//2:105+nowstart
        else if (state == 8) {
            result = "2:PEN";
        } else if (state == 9) {
            result = "3:FT";
        } else if (state == 101) {
            result = "3:ABD";
        } else if (state == 102) {
            result = "1:P-P";
        }

        return result;
    }


}
