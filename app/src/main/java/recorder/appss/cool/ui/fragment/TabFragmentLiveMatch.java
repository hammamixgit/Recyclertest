package recorder.appss.cool.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import recorder.appss.cool.base.BaseFragment;
import recorder.appss.cool.model.BaseView;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.remote.Sportservice;
import recorder.appss.cool.ui.adapter.ItemOffsetDecoration;
import recorder.appss.cool.ui.adapter.MatchLiveAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragmentLiveMatch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragmentLiveMatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentLiveMatch extends BaseFragment implements BaseView {
    @BindView(R.id.rv_list_live)
    RecyclerView mRecyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Integer> mMatchStates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
    List<Match> mListMatchLive;
    private Sportservice mService;
    MatchLiveAdapter mMatchLiveAdap;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public int getFragmentId() {
        return R.layout.fragment_live_match;
    }

    public TabFragmentLiveMatch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentLiveMatch.
     */

    public static TabFragmentLiveMatch newInstance(String param1, String param2) {
        TabFragmentLiveMatch fragment = new TabFragmentLiveMatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   ButterKnife.bind(getActivity());
        mService = ApiUtils.getSOService();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mListMatchLive = new ArrayList<>();
        mMatchLiveAdap = new MatchLiveAdapter(mListMatchLive);
        mRecyclerView.setAdapter(mMatchLiveAdap);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getLiveMatchs() {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter mDateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        mService.getMatch(mDateTimeFormatter.print(today) + "T00:00:00+" + today.getEra(), mDateTimeFormatter.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    mListMatchLive = response.body();
                    mMatchLiveAdap.updateAnswers(sortCompetition(mListMatchLive));
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

    private List<Match> sortCompetition(List<Match> listMatch) {
        List<Match> matchArrayList = new ArrayList<>();
        DateTime date1 = new DateTime();

        for (Match match : listMatch) {
            DateTime date2 = new DateTime((long) match.getCurrentStateStart());
            Duration duration = new Duration(date2, date1);
            long minutes = duration.getStandardMinutes();
            if (mMatchStates.contains(match.getCurrentState()) && (minutes < 60)) {
                Competition competition = new Competition();
                competition = match.getCompetition();
                competition.setName(getCountryFromUrl(competition.getFlagUrl()) + " : " + competition.getName());
                match.setCompetition(competition);
                matchArrayList.add(match);
            }
        }
        Collections.sort(matchArrayList, new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                return o1.getCompetition().getName().compareTo(o2.getCompetition().getName());
            }
        });

        return matchArrayList;
    }

    private String getCountryFromUrl(String url) {
        String result = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        if (result.equals("fifa") || result.equals("uefa") || result.equals("afc"))
            result = "world";
        return result;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            getLiveMatchs();
        } else {
        }
    }


}
