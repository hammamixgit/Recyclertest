package recorder.appss.cool.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import recorder.appss.cool.Holder.HeaderViewHolderMatchFavoriAdap;
import recorder.appss.cool.Holder.ViewHolderMatchFavoriAdap;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by yassin baccour on 19/11/2017.
 */

public abstract class BaseMatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<String> mListFav = new ArrayList<>();
    protected List<Match> mListMatchsLive = new ArrayList<>();
    protected List<Match> mListMatchsLive2 = new ArrayList<>();
    protected int mPosition = 0;
    protected int mCurrentCompetition = 0;

    public BaseMatchAdapter(List<Match> list_match) {
        mListFav.addAll(ViewModel.Current.dataUtils.getfavPref());
        mListMatchsLive.addAll(list_match);
        for (Match match : mListMatchsLive)
            if (mListFav.contains(match.getDbid() + "")) {
                if (mPosition == 0) {
                    Match matchcompetition = new Match();
                    matchcompetition.setCompetition(match.getCompetition());
                    matchcompetition.setDbid(-1);
                    mListMatchsLive2.add(matchcompetition);
                    mListMatchsLive2.add(match);
                    mCurrentCompetition = match.getCompetition().getDbid();
                    mPosition++;
                } else {
                    if (mCurrentCompetition == match.getCompetition().getDbid()) {
                        mListMatchsLive2.add(match);
                    } else {
                        Match match1 = null;
                        match1.setCompetition(match.getCompetition()); //TODO null puis tu set NullPointerException
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
            return new ViewHolderMatchFavoriAdap(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchs_compet_item, parent, false));
        } else if (viewType == Constants.TYPE_HEADER) {
            return new HeaderViewHolderMatchFavoriAdap(LayoutInflater.from(parent.getContext()).inflate(R.layout.section_divider_matchs_live, parent, false));
        }
        throw new RuntimeException();
    }
}
