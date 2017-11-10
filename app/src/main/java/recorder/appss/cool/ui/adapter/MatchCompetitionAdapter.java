package recorder.appss.cool.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import recorder.appss.cool.Holder.HeaderViewHolderMatchCompetAdap;
import recorder.appss.cool.Holder.ViewHolderMatchCompetAdap;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class MatchCompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Match> list_of_matchs = new ArrayList<>();
    Competition competition;
    List<String> list_fav = new ArrayList<>();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private int nbmatch_total, nbmatch_live;

    public MatchCompetitionAdapter(List<Match> list_match, Competition c) {

        list_of_matchs.addAll(list_match);
        competition = c;
        list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
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
    @Override
    public long getItemId(int position) {
        return position;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false);
            ViewHolderMatchCompetAdap mvh = new ViewHolderMatchCompetAdap(v);
            return mvh;

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_matchs_competetion, parent, false);
            return new HeaderViewHolderMatchCompetAdap(view);

        } else if (viewType == TYPE_FOOTER) {

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
   //  final    Context ctx=((Myviewholder) holder).btn_like.getContext();
        int x=position;
        if (holder instanceof ViewHolderMatchCompetAdap) {


            if (list_fav.contains(list_of_matchs.get(position-1).getDbid().toString()))
               ((ViewHolderMatchCompetAdap) holder).btn_like.setLiked(true);
            else{ ((ViewHolderMatchCompetAdap) holder).btn_like.setLiked(false);
                //Log.d("favori",position+"rebind"+list_of_matchs.get(position-1).getAwayTeam().getName()+"-"+list_of_matchs.get(position-1).getDbid());
                }
            ((ViewHolderMatchCompetAdap) holder).btn_like.setOnLikeListener(new OnLikeListener() {

                @Override
                public void liked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.addfavPref( list_of_matchs.get(position-1).getDbid().toString());
                    list_fav.clear();
                    list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
                    notifyItemChanged(position);
                  //  Log.d("favori",list_of_matchs.get(position-1).getAwayTeam().getName()+"-"+list_of_matchs.get(position-1).getDbid()+"- liked!!");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.removefavPref( list_of_matchs.get(position-1).getDbid().toString());
                    list_fav.clear();
                    list_fav.addAll( ViewModel.Current.dataUtils.getfavPref());
                  //  likeButton.setLiked(false);

                   notifyItemChanged(position);
                  //  Log.d("unfavori",list_of_matchs.get(position-1).getAwayTeam().getName()+"-"+list_of_matchs.get(position-1).getDbid()+"- unliked!!");

                }
            });
            ((ViewHolderMatchCompetAdap) holder).team1txt.setText(list_of_matchs.get(position - 1).getHomeTeam().getShortName());
            ((ViewHolderMatchCompetAdap) holder).team2txt.setText(list_of_matchs.get(position - 1).getAwayTeam().getShortName());
            ((ViewHolderMatchCompetAdap) holder).score.setText(list_of_matchs.get(position - 1).getHomeGoals() + "-" + list_of_matchs.get(position - 1).getAwayGoals());

            long timestamp_start = (long) list_of_matchs.get(position - 1).getStart();
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
            ((ViewHolderMatchCompetAdap) holder).timestart.setText(time);
            String Live_time;
            if (list_of_matchs.get(position - 1).getCurrentState() == 0)
                Live_time = instant_time(list_of_matchs.get(position - 1).getCurrentState(), 0);
            else
                Live_time = instant_time(list_of_matchs.get(position - 1).getCurrentState(), (long) list_of_matchs.get(position - 1).getCurrentStateStart());

            String[] live_data = Live_time.split(":");


            ((ViewHolderMatchCompetAdap) holder).time.setText(live_data[1]);
        } else if (holder instanceof HeaderViewHolderMatchCompetAdap) {
            ((HeaderViewHolderMatchCompetAdap) holder).txt_header.setText(competition.getName());


            MultiTransformation multi = new MultiTransformation(
                    new BlurTransformation(1),
                    new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
            Glide
                    .with(((HeaderViewHolderMatchCompetAdap) holder).im_header.getContext())
                    .load(competition.getFlagUrl())
                    .apply(bitmapTransform(multi))
                    .into(((HeaderViewHolderMatchCompetAdap) holder).im_header);
        }

    }


    @Override
    public int getItemCount() {
        return list_of_matchs.size() + 1;
    }

    public void updateAnswers(List<Match> items, int nbmatch, int nblive) {
        nbmatch_total = nbmatch;
        nbmatch_live = nblive;
        list_of_matchs = items;
        notifyDataSetChanged();
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
