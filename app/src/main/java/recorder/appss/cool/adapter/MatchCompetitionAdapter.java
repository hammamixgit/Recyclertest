package recorder.appss.cool.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 29/09/2017.
 */

public class MatchCompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Match> list_of_matchs= new ArrayList<>();
Competition competition;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
private int nbmatch_total,nbmatch_live;
    public MatchCompetitionAdapter( List<Match> list_match,Competition c) {

        list_of_matchs.addAll(list_match);
competition=c;
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > list_of_matchs.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet, parent, false);
            Myviewholder mvh = new Myviewholder(v);
            return mvh;

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_matchs_competetion, parent, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        if (holder instanceof Myviewholder) {

            ((Myviewholder)  holder).team1txt.setText(list_of_matchs.get(position-1).getHomeTeam().getShortName());
            ((Myviewholder)  holder).team2txt.setText(list_of_matchs.get(position-1).getAwayTeam().getShortName());
            ((Myviewholder)  holder).score.setText(list_of_matchs.get(position-1).getHomeGoals()+"-"+list_of_matchs.get(position-1).getAwayGoals());

            long timestamp_start=(long)list_of_matchs.get(position-1).getStart();
            DateTime dt = new DateTime(timestamp_start);
            String hour,minutes;
           if(dt.getHourOfDay()>9)
           {  hour=dt.getHourOfDay()+"" ;} else{  hour ="0"+dt.getHourOfDay();}
            if(dt.getMinuteOfHour()>9)
            {  minutes=dt.getMinuteOfHour()+"" ;} else{  minutes ="0"+dt.getMinuteOfHour();}
            String time =     hour+":"+minutes;
           // String time = dt.getYear()+"."+dt.getMonthOfYear()+"."+dt.getDayOfMonth()+"\n"+"     "+hour+":"+minutes;
            ((Myviewholder)  holder).timestart.setText(time);
            String Live_time;
            if(list_of_matchs.get(position-1).getCurrentState()==0)
             Live_time= instant_time(list_of_matchs.get(position-1).getCurrentState(),0);
              else
                Live_time= instant_time(list_of_matchs.get(position-1).getCurrentState(),(long)list_of_matchs.get(position-1).getCurrentStateStart());

            String[] live_data = Live_time.split(":");


            ((Myviewholder)  holder).time.setText(live_data[1]);
        }
        else if(holder instanceof HeaderViewHolder){
           ((HeaderViewHolder)  holder).txt_header.setText(competition.getName());
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(40)
                    .oval(true)
                    .build();

            Picasso.with( ((HeaderViewHolder)  holder).im_header.getContext())
                    .load(competition.getFlagUrl())
                    .fit()
                    .transform(transformation)
                    .into( ((HeaderViewHolder)  holder).im_header);

        }

    }


    @Override
    public int getItemCount() {
        return list_of_matchs.size()+1;
    }

    public void updateAnswers(List<Match> items,int nbmatch,int nblive) {
        nbmatch_total=nbmatch;
        nbmatch_live=nblive;
        list_of_matchs = items;
        notifyDataSetChanged();
    }
    public  String   instant_time(int state, long current_state_start) {
String result="";
        if(state==0) {result="1:FX";}
        else if(state==1){
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result="2:"+duration.getStandardMinutes();
        }//2:0+now-start
        else if(state==2){result="2:HT";}
        else if(state==3){
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result="2:"+(45+duration.getStandardMinutes());
        }//2:45+now-start
        else if(state==4){result="2:ET";}
        else if(state==5){
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result="2:"+(90+duration.getStandardMinutes());
        }//2:90+now-start
        else if(state==6){result="2:ET HT";}
        else if(state==7){
            DateTime d1 = new DateTime();
            DateTime d2 = new DateTime(current_state_start);
            Duration duration = new Duration(d2, d1);
            result="2:"+(105+duration.getStandardMinutes());
        }//2:105+nowstart
        else if(state==8){result="2:PEN";}
        else if(state==9){result="3:FT";}
        else if(state==101){result="3:ABD";}
        else if(state==102){result="1:P-P";}

return result;
    }
    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView team1txt,team2txt,score,time,timestart;
        ImageView im1,im2;
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
   public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;
       TextView txt_header;
       ImageView im_header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            im_header = (ImageView) itemView.findViewById(R.id.compet_flag_h);
            txt_header = (TextView) itemView.findViewById(R.id.compet_name_h);

            // add your ui components here like this below
            //txtName = (TextView) View.findViewById(R.id.txt_name);

        }
    }
}
