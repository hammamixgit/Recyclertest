package recorder.appss.cool.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

import recorder.appss.cool.Holder.HeaderViewHolderMatchCompetHolder;
import recorder.appss.cool.Holder.ViewHolderMatchCompetAdap;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class MatchCompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Match> mListMatchs = new ArrayList<>();
    private Competition mCompetition;
    private List<String> mListFavori = new ArrayList<>();

    public MatchCompetitionAdapter(List<Match> list_match, Competition competition) {
        mListMatchs.addAll(list_match);
        mCompetition = competition;
        mListFavori.addAll(ViewModel.Current.dataUtils.getfavPref());
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return Constants.TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return Constants.TYPE_FOOTER;
        }
        return Constants.TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > mListMatchs.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            return new ViewHolderMatchCompetAdap(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false));
        } else if (viewType == Constants.TYPE_HEADER) {
            return new HeaderViewHolderMatchCompetHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_matchs_competetion, parent, false));
        } else if (viewType == Constants.TYPE_FOOTER) {
        }
        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderMatchCompetAdap) {
            if (mListFavori.contains(mListMatchs.get(position - 1).getDbid().toString()))
                ((ViewHolderMatchCompetAdap) holder).mBtnLike.setLiked(true);
            else {
                ((ViewHolderMatchCompetAdap) holder).mBtnLike.setLiked(false);
            }
            ((ViewHolderMatchCompetAdap) holder).mBtnLike.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.addfavPref(mListMatchs.get(position - 1).getDbid().toString());
                    mListFavori.clear();
                    mListFavori.addAll(ViewModel.Current.dataUtils.getfavPref());
                    notifyItemChanged(position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    ViewModel.Current.dataUtils.removefavPref(mListMatchs.get(position - 1).getDbid().toString());
                    mListFavori.clear();
                    mListFavori.addAll(ViewModel.Current.dataUtils.getfavPref());
                    notifyItemChanged(position);
                }
            });
            ((ViewHolderMatchCompetAdap) holder).mTeam1.setText(mListMatchs.get(position - 1).getHomeTeam().getShortName());
            ((ViewHolderMatchCompetAdap) holder).mTeam2.setText(mListMatchs.get(position - 1).getAwayTeam().getShortName());
            ((ViewHolderMatchCompetAdap) holder).mScore.setText(mListMatchs.get(position - 1).getHomeGoals() + "-" + mListMatchs.get(position - 1).getAwayGoals());
            long mTimeStartLongFormat = (long) mListMatchs.get(position - 1).getStart();
            ((ViewHolderMatchCompetAdap) holder).mTimeStart.setText(getTimeStart(mTimeStartLongFormat));
            String mLiveTime;
            if (mListMatchs.get(position - 1).getCurrentState() == 0)
                mLiveTime = getInstantTime(mListMatchs.get(position - 1).getCurrentState(), 0);
            else
                mLiveTime = getInstantTime(mListMatchs.get(position - 1).getCurrentState(), (long) mListMatchs.get(position - 1).getCurrentStateStart());
            String[] live_data = mLiveTime.split(":");
            ((ViewHolderMatchCompetAdap) holder).mTime.setText(live_data[1]);
        } else if (holder instanceof HeaderViewHolderMatchCompetHolder) {
            ((HeaderViewHolderMatchCompetHolder) holder).mCompetitionTitle.setText(mCompetition.getName());
            setImage((HeaderViewHolderMatchCompetHolder) holder);
        }
    }

    private void setImage(HeaderViewHolderMatchCompetHolder holder) {
        Glide
                .with(holder.mFlagCountry.getContext())
                .load(mCompetition.getFlagUrl())
                .apply(bitmapTransform(ViewModel.Current.fileUtils.getMultiTransformation()))
                .into(holder.mFlagCountry);
    }


    @Override
    public int getItemCount() {
        return mListMatchs.size() + 1;
    }

    public void updateAnswers(List<Match> items) {
        mListMatchs = items;
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

    private String getTimeStart(long mTimeLong) {  //TODO ver le viewmodel ou une classe timeUtil
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
