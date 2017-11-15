package recorder.appss.cool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import recorder.appss.cool.Holder.HeaderViewHolderCompetitionAdap;
import recorder.appss.cool.Holder.ViewHolderCompetitionAdap;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;
import recorder.appss.cool.ui.fragment.TabFragmentCompetitionList;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class CompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Competition> mListCompetition = new ArrayList<>();
    private LinkedHashMap<Competition, String> mNewListCompetion = new LinkedHashMap<Competition, String>();
    private TabFragmentCompetitionList mFragmentTabList;
    private int mSumAllMatch;
    private int mSumLiveMatch;

    public CompetitionAdapter(LinkedHashMap<Competition, String> mCompetition, TabFragmentCompetitionList mTabFragmentCompetitionList) {  //TODO rename
        mNewListCompetion.putAll(mCompetition);
        this.mFragmentTabList = mTabFragmentCompetitionList;
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

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > mNewListCompetion.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.compet_list_item, parent, false);
            ViewHolderCompetitionAdap mViewHolderCompetitionAdap = new ViewHolderCompetitionAdap(mView);
            return mViewHolderCompetitionAdap;
        } else if (viewType == Constants.TYPE_HEADER) {
            View mViewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_competetion, parent, false);
            return new HeaderViewHolderCompetitionAdap(mViewHeader);
        } else if (viewType == Constants.TYPE_FOOTER) {
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderCompetitionAdap) {
            Set<Map.Entry<Competition, String>> mMapSet = mNewListCompetion.entrySet();
            Map.Entry<Competition, String> mElement = (Map.Entry<Competition, String>) mMapSet.toArray()[position - 1];
            Competition mCompetition = mElement.getKey();
            mListCompetition.add(mCompetition);
            String[] mListMatchSum = mElement.getValue().split(":");
            ((ViewHolderCompetitionAdap) holder).mSumMatch.setText(mListMatchSum[0]);
            ((ViewHolderCompetitionAdap) holder).mSumLive.setText(mListMatchSum[1]);
            String[] mListCompetitionInfo = mCompetition.getName().split(":");
            ((ViewHolderCompetitionAdap) holder).mCompetitionName.setText(mListCompetitionInfo[1]);
            ((ViewHolderCompetitionAdap) holder).mCountryName.setText(mListCompetitionInfo[0]);
            MultiTransformation mTranform = new MultiTransformation(
                    new BlurTransformation(1),
                    new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
            Glide
                    .with(((ViewHolderCompetitionAdap) holder).mFlag.getContext())
                    .load(mCompetition.getFlagUrl())
                    .apply(bitmapTransform(mTranform))
                    .into(((ViewHolderCompetitionAdap) holder).mFlag);

            ((ViewHolderCompetitionAdap) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = FragmentMatchsCompet.newInstance(mListCompetition.get(position - 1));

                    FragmentTransaction transaction = mFragmentTabList.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_main, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else if (holder instanceof HeaderViewHolderCompetitionAdap) {
            ((HeaderViewHolderCompetitionAdap) holder).mSumMatch.setText(mSumAllMatch + "");
            ((HeaderViewHolderCompetitionAdap) holder).mSumLive.setText(mSumLiveMatch + "");
        }
    }

    @Override
    public int getItemCount() {
        return mNewListCompetion.size() + 1;
    }

    public void updateAnswers(LinkedHashMap<Competition, String> items, int mAllMatch, int mLive) {
        mSumAllMatch = mAllMatch;
        mSumLiveMatch = mLive;
        mNewListCompetion = items;
        notifyDataSetChanged();
    }


}
