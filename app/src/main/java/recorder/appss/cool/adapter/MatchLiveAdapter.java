package recorder.appss.cool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.utils.PreferenceUtils;

/**
 * Created by work on 29/09/2017.
 */

public class MatchLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Match> list_of_matchs_live = new ArrayList<>();
    List<Match> list_of_matchs_live2 = new ArrayList<>();
    int size_compet=0;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private int pos=0;
int current_compet=0;
    public MatchLiveAdapter(List<Match> list_match ) {

        list_of_matchs_live.addAll(list_match);
     for(Match m : list_of_matchs_live)
         if(pos==0)
         {Match e = new Match();e.setCompetition(m.getCompetition());e.setDbid(-1);list_of_matchs_live2.add(e);list_of_matchs_live2.add(m);current_compet=m.getCompetition().getDbid();pos++;}
         else
         {
         if(current_compet== m.getCompetition().getDbid()){list_of_matchs_live2.add(m);}
         else{ Match e = null;e.setCompetition(m.getCompetition());e.setDbid(-1);list_of_matchs_live2.add(e);current_compet=m.getCompetition().getDbid();}

         }
    }

    @Override
    public int getItemViewType(int position) {

        if( list_of_matchs_live2.get(position).getDbid()<0)
        {return TYPE_HEADER;}

       else
        return  TYPE_ITEM;




    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false);
            Myviewholder mvh = new Myviewholder(v);
            return mvh;

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_divider_matchs_live, parent, false);
            return new DividerViewHolder(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        final Context ctx=((MatchCompetitionAdapter.Myviewholder) holder).btn_like.getContext();
        final int pos=position;
        if (holder instanceof MatchCompetitionAdapter.Myviewholder) {
            ((MatchCompetitionAdapter.Myviewholder) holder).btn_like.setOnLikeListener(new OnLikeListener() {

                @Override
                public void liked(LikeButton likeButton) {
                    PreferenceUtils.addfavPref( ctx,list_of_matchs_live2.get(pos).getDbid().toString());
                    //   Log.d("favori",list_of_matchs.get(position-1).getAwayTeam().getName()+"-"+list_of_matchs.get(position-1).getHomeTeam().getName()+"- liked!!");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    PreferenceUtils.removefavPref( ctx,list_of_matchs_live2.get(pos).getDbid().toString());
                    //   Log.d("unfavori",list_of_matchs.get(position-1).getAwayTeam().getName()+"-"+list_of_matchs.get(position-1).getHomeTeam().getName()+"- unliked!!");

                }
            });

            ((Myviewholder) holder).team1txt.setText(list_of_matchs_live2.get(position).getHomeTeam().getShortName());
            ((Myviewholder) holder).team2txt.setText(list_of_matchs_live2.get(position).getAwayTeam().getShortName());
            ((Myviewholder) holder).score.setText(list_of_matchs_live2.get(position).getHomeGoals() + "-" + list_of_matchs_live2.get(position).getAwayGoals());

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
            ((Myviewholder) holder).timestart.setText(time);
            String Live_time;
            if (list_of_matchs_live2.get(position).getCurrentState() == 0)
                Live_time = instant_time(list_of_matchs_live2.get(position).getCurrentState(), 0);
            else
                Live_time = instant_time(list_of_matchs_live2.get(position).getCurrentState(), (long) list_of_matchs_live2.get(position).getCurrentStateStart());

            String[] live_data = Live_time.split(":");


            ((Myviewholder) holder).time.setText(live_data[1]);

        } else if (holder instanceof DividerViewHolder) {
            ((DividerViewHolder) holder).txt_header.setText(list_of_matchs_live2.get(position).getCompetition().getName());
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(40)
                    .oval(true)
                    .build();

            Picasso.with(((DividerViewHolder) holder).im_header.getContext())
                    .load(list_of_matchs_live2.get(position).getCompetition().getFlagUrl())
                    .fit()
                    .transform(transformation)
                    .into(((DividerViewHolder) holder).im_header);

        }

    }


    @Override
    public int getItemCount() {
        return list_of_matchs_live2.size() ;
    }

    public void updateAnswers(List<Match> items) {
        pos=0;
        list_of_matchs_live.clear(); list_of_matchs_live2.clear();
        list_of_matchs_live .addAll(items) ;
        for(Match m : list_of_matchs_live){
            Log.d("matchdetails", m.getCompetition().getName()+"-"+m.getAwayTeam().getName());
            if(pos==0)
            {Match e = new Match();e.setCompetition(m.getCompetition());e.setDbid(-1);list_of_matchs_live2.add(e);list_of_matchs_live2.add(m);current_compet=m.getCompetition().getDbid();pos++;}
            else {
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

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView team1txt, team2txt, score, time, timestart;
        ImageView im1, im2;
        LinearLayout layout_live;

        public Myviewholder(View itemView) {
            super(itemView);
            layout_live = (LinearLayout) itemView.findViewById(R.id.lllive);
            team1txt = (TextView) itemView.findViewById(R.id.team1);
            team2txt = (TextView) itemView.findViewById(R.id.team2);
            score = (TextView) itemView.findViewById(R.id.score);
            time = (TextView) itemView.findViewById(R.id.time);
            im1 = (ImageView) itemView.findViewById(R.id.imgteam1);
            im2 = (ImageView) itemView.findViewById(R.id.imgteam2);
            timestart = (TextView) itemView.findViewById(R.id.match_start);

        }
    }

    public class DividerViewHolder extends RecyclerView.ViewHolder {
        public View View;
        TextView txt_header;
        ImageView im_header;

        public DividerViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            im_header = (ImageView) itemView.findViewById(R.id.compet_flag_div);
            txt_header = (TextView) itemView.findViewById(R.id.compet_name_div);

            // add your ui components here like this below
            //txtName = (TextView) View.findViewById(R.id.txt_name);

        }
    }
}
