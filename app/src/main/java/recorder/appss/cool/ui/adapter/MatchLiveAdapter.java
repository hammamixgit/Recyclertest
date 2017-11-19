package recorder.appss.cool.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;

import recorder.appss.cool.Holder.HeaderViewHolderMatchLiveAdap;
import recorder.appss.cool.Holder.ViewHolderMatchLiveAdap;
import recorder.appss.cool.base.BaseMatchAdapter;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class MatchLiveAdapter extends BaseMatchAdapter {


    public MatchLiveAdapter(List<Match> list_match) {
        super(list_match);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderMatchLiveAdap) {
            BindHolderData1(holder, position);
        } else if (holder instanceof HeaderViewHolderMatchLiveAdap) {
            BindHolderData2((HeaderViewHolderMatchLiveAdap) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mListMatchsLive2.size();
    }

    private void BindHolderData1(RecyclerView.ViewHolder holder, final int position) {
        if (mListFav.contains(mListMatchsLive2.get(position).getDbid().toString()))
            ((ViewHolderMatchLiveAdap) holder).mBtnLike.setLiked(true);
        else ((ViewHolderMatchLiveAdap) holder).mBtnLike.setLiked(false);

        ((ViewHolderMatchLiveAdap) holder).mBtnLike.setOnLikeListener(new OnLikeListener() {

            @Override
            public void liked(LikeButton likeButton) {
                ViewModel.Current.dataUtils.addfavPref(mListMatchsLive2.get(position).getDbid().toString());
                mListFav.clear();
                mListFav.addAll(ViewModel.Current.dataUtils.getfavPref());
                notifyItemChanged(position);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                ViewModel.Current.dataUtils.removefavPref(mListMatchsLive2.get(position).getDbid().toString());
                mListFav.clear();
                mListFav.addAll(ViewModel.Current.dataUtils.getfavPref());
                notifyItemChanged(position);
            }
        });

        ((ViewHolderMatchLiveAdap) holder).mTeam1.setText(mListMatchsLive2.get(position).getHomeTeam().getShortName());
        ((ViewHolderMatchLiveAdap) holder).mTeam2.setText(mListMatchsLive2.get(position).getAwayTeam().getShortName());
        ((ViewHolderMatchLiveAdap) holder).mScore.setText(mListMatchsLive2.get(position).getHomeGoals() + "-" + mListMatchsLive2.get(position).getAwayGoals());
        long mTimeStartLongFormat = (long) mListMatchsLive2.get(position).getStart();
        ((ViewHolderMatchLiveAdap) holder).mTimeStart.setText(getTimeStart(mTimeStartLongFormat));
        String mLiveTime;
        if (mListMatchsLive2.get(position).getCurrentState() == 0)
            mLiveTime = getInstantTime(mListMatchsLive2.get(position).getCurrentState(), 0);
        else
            mLiveTime = getInstantTime(mListMatchsLive2.get(position).getCurrentState(), (long) mListMatchsLive2.get(position).getCurrentStateStart());
        String[] live_data = mLiveTime.split(":");
        ((ViewHolderMatchLiveAdap) holder).mTime.setText(live_data[1]);
    }

    private void BindHolderData2(HeaderViewHolderMatchLiveAdap holder, int position) {
        holder.mCompetitionTitle.setText(mListMatchsLive2.get(position).getCompetition().getName());
        Glide
                .with(holder.mFlagCountry.getContext())
                .load(mListMatchsLive2.get(position).getCompetition().getFlagUrl())
                .apply(bitmapTransform(ViewModel.Current.fileUtils.getMultiTransformation()))
                .into(holder.mFlagCountry);
    }

    public void updateAnswers(List<Match> items) {
        mPosition = 0;
        mListMatchsLive.clear();
        mListMatchsLive2.clear();
        mListMatchsLive.addAll(items);
        for (Match match : mListMatchsLive) {
            if (mPosition == 0) {
                Match matchCompet = new Match();
                matchCompet.setCompetition(match.getCompetition());
                matchCompet.setDbid(-1);
                mListMatchsLive2.add(matchCompet);
                mListMatchsLive2.add(match);
                mCurrentCompetition = match.getCompetition().getDbid();
                mPosition++;
            } else {
                if (mCurrentCompetition == match.getCompetition().getDbid()) {
                    mListMatchsLive2.add(match);
                } else {
                    Match newMatch1 = new Match();
                    newMatch1.setCompetition(match.getCompetition());
                    newMatch1.setDbid(-1);
                    mListMatchsLive2.add(newMatch1);
                    mListMatchsLive2.add(match);
                    mCurrentCompetition = match.getCompetition().getDbid();
                }
            }
        }
        notifyDataSetChanged();
    }

    private String getInstantTime(int state, long current_state_start) {  //TODO ver le viewmodel ou une classe timeUtil
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

    private String getTimeStart(long mTimeLong) { //TODO ver le viewmodel ou une classe timeUtil
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
        return hour + ":" + minutes;
    }
}
