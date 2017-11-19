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
import recorder.appss.cool.model.DataLoadingState;
import recorder.appss.cool.model.GenericModel;
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
public class TabFragmentLiveMatch extends BaseFragment implements DataLoadingState<Match> {  //TODO le T devient Match
    @BindView(R.id.rv_list_live)
    RecyclerView mRecyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Match> mListMatchLive;
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
        ViewModel.Current.setDataLoadingtate(this); //On enregistre le lisnter
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mListMatchLive = new ArrayList<>();
        mMatchLiveAdap = new MatchLiveAdapter(mListMatchLive);
        mRecyclerView.setAdapter(mMatchLiveAdap);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewModel.Current.setDataLoadingtate(null); //On desabonne le listner
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
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showSnackMsg(String msg) {
        ViewModel.Current.device.showSnackMessage((CoordinatorLayout) getView(), getResources().getString(R.string.Error));
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void loadData(List<Match> listeMatch) {
        mMatchLiveAdap.updateAnswers(ViewModel.Current.sortCompetition(listeMatch));
    }

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
           ViewModel.Current.getLiveMatchs(); //TODO on loade depuis le viewModel toujours puis le view model Call mes methode qui sont implementer dans cette classe
        } else {
        }
    }


}
