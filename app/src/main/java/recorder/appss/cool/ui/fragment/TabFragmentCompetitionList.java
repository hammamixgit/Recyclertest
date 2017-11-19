package recorder.appss.cool.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import recorder.appss.cool.base.BaseFragment;
import recorder.appss.cool.model.BaseView;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.ui.adapter.CompetitionAdapter;
import recorder.appss.cool.ui.adapter.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragmentCompetitionList extends BaseFragment implements BaseView {
    @BindView(R.id.rv)
    RecyclerView mRecycleView;
    //Declaration var
    private List<Integer> mMatchState = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
    private List<Match> mListMatch;
    private List<Competition> mListCompetition;
    private LinkedHashMap<Competition, String> mCompetitionOccur;
    private CompetitionAdapter mCompetitionAdapter;
    private int mMatchsLive;

    @Override
    public int getFragmentId() {
        return R.layout.compet_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  ButterKnife.bind(mActivity,view);
        mMatchsLive = 0;
        mListMatch = new ArrayList<>();
        mListCompetition = new ArrayList<>();
        mCompetitionOccur = new LinkedHashMap<>();
        JodaTimeAndroid.init(view.getContext());
        mRecycleView.setHasFixedSize(false);
        mRecycleView.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecycleView.setLayoutManager(linearLayoutManager);
        mCompetitionAdapter = new CompetitionAdapter(mCompetitionOccur, this);
        mRecycleView.setAdapter(mCompetitionAdapter);
        loadAnswers();
    }

    public void loadAnswers() {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        ViewModel.Current.mApiUtils.getSportService().getMatch(dateTimeFormatter.print(today) + "T00:00:00+" + today.getEra(), dateTimeFormatter.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    mListMatch = response.body();
                    sortCompetition(mListMatch, mListCompetition);
                    mCompetitionAdapter.updateAnswers(countCompetition(mListCompetition), mListMatch.size(), mMatchsLive);

                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                ViewModel.Current.device.showSnackMessage((CoordinatorLayout) getView(), getResources().getString(R.string.Error));
            }
        });

    }

    private void sortCompetition(List<Match> listMatch, List<Competition> listCompetition) {
        for (Match match : listMatch) {
            Competition competition = new Competition();
            competition = match.getCompetition();
            competition.setName(getCountryFromUrl(competition.getFlagUrl()) + " : " + competition.getName());
            listCompetition.add(competition);
        }
        Collections.sort(listCompetition, new Comparator<Competition>() {
            @Override
            public int compare(Competition o1, Competition o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

    }

    private String getCountryFromUrl(String url) {
        String result = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        if (result.equals("fifa") || result.equals("uefa") || result.equals("afc"))
            result = "world";
        return result;
    }


    private LinkedHashMap countCompetition(List<Competition> listCompetitions) {
        int live = 0;
        List<Integer> idCompetition = new ArrayList<>();
        for (Competition competition : listCompetitions)
            idCompetition.add(competition.getDbid());
        LinkedHashMap<Competition, String> livePerCompetiton = new LinkedHashMap<>();
        Set<Integer> listIdCompetition = new LinkedHashSet<>(idCompetition);
        for (Integer id : listIdCompetition) {
            int occurrences = Collections.frequency(idCompetition, id);
            live = getLiveByComp(mListMatch, id);
            livePerCompetiton.put(getCompetitionById(listCompetitions, id), occurrences + ":" + live);
            mMatchsLive = mMatchsLive + live;
        }
        return livePerCompetiton;
    }


    private Competition getCompetitionById(List<Competition> listCompetition, Integer id) {
        Competition competition = new Competition();
        for (int i = 0; i < listCompetition.size(); i++)
            if (listCompetition.get(i).getDbid() == id) {
                competition = new Competition();
                competition = listCompetition.get(i);
                break;
            }
        return competition;
    }

    private Integer getLiveByComp(List<Match> matchs, Integer idComp) {
        DateTime date1 = new DateTime();
        int countLive = 0;
        for (int count = 0; count < matchs.size(); count++) {
            DateTime date2 = new DateTime((long) matchs.get(count).getCurrentStateStart());
            Duration duration = new Duration(date2, date1);
            long minute = duration.getStandardMinutes();
            if (minute < 60 && (matchs.get(count).getCompetition().getDbid().equals(idComp)) && (mMatchState.contains(matchs.get(count).getCurrentState()))) {
                countLive++;
            }
        }

        return countLive;
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    return true;

                }

                return false;
            }
        });
    }

    @Override
    public void showSnackMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}