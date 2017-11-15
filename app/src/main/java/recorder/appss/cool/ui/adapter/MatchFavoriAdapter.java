package recorder.appss.cool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<String> mlistFav = new ArrayList<>();
    private List<Match> mListMatchsLive = new ArrayList<>();
    private List<Match> mListMatchsLive2 = new ArrayList<>();
    private int mPosistion = 0;
    private int mCurrentCompetition = 0;

    public MatchFavoriAdapter(List<Match> list_match) {

        mlistFav.addAll(ViewModel.Current.dataUtils.getfavPref());
        mListMatchsLive.addAll(list_match);
        for (Match match : mListMatchsLive)
            if (mlistFav.contains(match.getDbid() + "")) {
                if (mPosistion == 0) {
                    Match matchcompetition = new Match();
                    matchcompetition.setCompetition(match.getCompetition());
                    matchcompetition.setDbid(-1);
                    mListMatchsLive2.add(matchcompetition);
                    mListMatchsLive2.add(match);
                    mCurrentCompetition = match.getCompetition().getDbid();
                    mPosistion++;
                } else {
                    if (mCurrentCompetition == match.getCompetition().getDbid()) {
                        mListMatchsLive2.add(match);
                    } else {
                        Match match1 = null;
                        match1.setCompetition(match.getCompetition());
                        match1.setDbid(-1);
                        mListMatchsLive2.add(match1);
                        mCurrentCompetition = match.getCompetition().getDbid();
                    }
                }
            }
    }

    @Override
    public int getItemViewType(int position) {
        if (mListMatchsLive2.get(position).getDbid() < 0) {
            return Constants.TYPE_HEADER;
        } else
            return Constants.TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false);
            ViewHolderMatchFavoriAdap viewHolderMatchFavoriAdap = new ViewHolderMatchFavoriAdap(view);
            return viewHolderMatchFavoriAdap;

        } else if (viewType == Constants.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_divider_matchs_live, parent, false);
            return new HeaderViewHolderMatchFavoriAdap(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderMatchFavoriAdap) {
            if (mlistFav.contains(mListMatchsLive2.get(position).getDbid().toString()))
                ((ViewHolderMatchFavoriAdap) holder).mBtnLike.setLiked(true);
            else ((ViewHolderMatchFavoriAdap) holder).mBtnLike.setLiked(false);

            ((ViewHolderMatchFavoriAdap) holder).mBtnLike.setOnLikeListener(new OnLikeListener() {

                @Override
                public void liked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.addfavPref(mListMatchsLive2.get(position).getDbid().toString());
                    mlistFav.clear();
                    mlistFav.addAll(ViewModel.Current.dataUtils.getfavPref());
                    notifyItemChanged(position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.removefavPref(mListMatchsLive2.get(position).getDbid().toString());
                    mlistFav.clear();
                    mlistFav.addAll(ViewModel.Current.dataUtils.getfavPref());
                    int resultat = verifMatchForCompet(mListMatchsLive2.get(position).getCompetition().getDbid());
                    mListMatchsLive2.remove(position);
                    if (resultat == 1) {
                        mListMatchsLive2.remove(position - 1);
                        notifyDataSetChanged();
                    } else {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mListMatchsLive2.size());
                    }
                }
            });

            ((ViewHolderMatchFavoriAdap) holder).mTeam1.setText(mListMatchsLive2.get(position).getHomeTeam().getShortName());
            ((ViewHolderMatchFavoriAdap) holder).mTeam2.setText(mListMatchsLive2.get(position).getAwayTeam().getShortName());
            ((ViewHolderMatchFavoriAdap) holder).mScore.setText(mListMatchsLive2.get(position).getHomeGoals() + "-" + mListMatchsLive2.get(position).getAwayGoals());
            long mTimeStartLongFormat = (long) mListMatchsLive2.get(position).getStart();
            ((ViewHolderMatchFavoriAdap) holder).mTimeStart.setText(getTimeStart(mTimeStartLongFormat));
            String mLiveTime;
            if (mListMatchsLive2.get(position).getCurrentState() == 0)
                mLiveTime = getInstantTime(mListMatchsLive2.get(position).getCurrentState(), 0);
            else
                mLiveTime = getInstantTime(mListMatchsLive2.get(position).getCurrentState(), (long) mListMatchsLive2.get(position).getCurrentStateStart());
            String[] live_data = mLiveTime.split(":");
            ((ViewHolderMatchFavoriAdap) holder).mTime.setText(live_data[1]);
        } else if (holder instanceof HeaderViewHolderMatchFavoriAdap) {
            ((HeaderViewHolderMatchFavoriAdap) holder).mCompetitionTitle.setText(mListMatchsLive2.get(position).getCompetition().getName());
            setImageFlag((HeaderViewHolderMatchFavoriAdap) holder, position);
        }
    }

    private void setImageFlag(HeaderViewHolderMatchFavoriAdap holder, int position) {
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(1),
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide
                .with(holder.mFlagCountry.getContext())
                .load(mListMatchsLive2.get(position).getCompetition().getFlagUrl())
                .apply(bitmapTransform(multi))
                .into(holder.mFlagCountry);
    }

    private int verifMatchForCompet(Integer id_comp) {
        int result = 0;
        for (Match match : mListMatchsLive2) {
            if (match.getDbid() > -1 && match.getCompetition().getDbid().equals(id_comp)) {
                result++;
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return mListMatchsLive2.size();
    }

    public void updateAnswers(List<Match> items) {
        mPosistion = 0;
        mListMatchsLive.clear();
        mListMatchsLive2.clear();
        mListMatchsLive.addAll(items);
        for (Match match : mListMatchsLive) {

            if (mlistFav.contains(match.getDbid() + "")) {
                if (mPosistion == 0) {
                    Match matchFirst = new Match();
                    matchFirst.setCompetition(match.getCompetition());
                    matchFirst.setDbid(-1);
                    mListMatchsLive2.add(matchFirst);
                    mListMatchsLive2.add(match);
                    mCurrentCompetition = match.getCompetition().getDbid();
                    mPosistion++;
                } else {
                    if (mCurrentCompetition == match.getCompetition().getDbid()) {
                        mListMatchsLive2.add(match);
                    } else {
                        Match match1 = new Match();
                        match1.setCompetition(match.getCompetition());
                        match1.setDbid(-1);
                        mListMatchsLive2.add(match1);
                        mListMatchsLive2.add(match);
                        mCurrentCompetition = match.getCompetition().getDbid();
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    private String getInstantTime(int state, long current_state_start) {
        String result = "";
        DateTime date1;
        DateTime date2;
        Duration duration;
        switch (state) {
            case 0:
                result = "1:FX";
                break;
            case 1:
                date1 = new DateTime();
                date2 = new DateTime(current_state_start);
                duration = new Duration(date2, date1);
                result = "2:" + duration.getStandardMinutes();
                break;
            case 2:
                result = "2:HT";
                break;
            case 3:
                date1 = new DateTime();
                date2 = new DateTime(current_state_start);
                duration = new Duration(date2, date1);
                result = "2:" + (45 + duration.getStandardMinutes());
                break;
            case 4:
                result = "2:ET";
                break;
            case 5:
                date1 = new DateTime();
                date2 = new DateTime(current_state_start);
                duration = new Duration(date2, date1);
                result = "2:" + (90 + duration.getStandardMinutes());
                break;
            case 6:
                result = "2:ET HT";
                break;
            case 7:
                date1 = new DateTime();
                date2 = new DateTime(current_state_start);
                duration = new Duration(date2, date1);
                result = "2:" + (105 + duration.getStandardMinutes());
                break;
            case 8:
                result = "2:PEN";
                break;
            case 9:
                result = "3:FT";
                break;
            case 101:
                result = "3:ABD";
                break;
            case 102:
                result = "1:P-P";
                break;
        }


        return result;
    }

    private String getTimeStart(long mTimeLong) {
        DateTime dateTimeStart = new DateTime(mTimeLong);
        String hour, minutes;
        if (dateTimeStart.getHourOfDay() > 9) {
            hour = dateTimeStart.getHourOfDay() + "";
        } else {
            hour = "0" + dateTimeStart.getHourOfDay();
        }
        if (dateTimeStart.getMinuteOfHour() > 9) {
            minutes = dateTimeStart.getMinuteOfHour() + "";
        } else {
            minutes = "0" + dateTimeStart.getMinuteOfHour();
        }
        String timeStartFinal = hour + ":" + minutes;
        return timeStartFinal;
    }

}
