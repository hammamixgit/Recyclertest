package recorder.appss.cool.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
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
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.ui.adapter.ItemOffsetDecoration;
import recorder.appss.cool.ui.adapter.MatchFavoriAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabFragmentFavMatch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragmentFavMatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragmentFavMatch extends BaseFragment implements BaseView {

    @BindView(R.id.rv_list_fav)
    RecyclerView mRecyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<String> mListFavPrefsId = new ArrayList<>();
    List<Match> mListMatchFav;

    MatchFavoriAdapter mMatchFavAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public int getFragmentId() {
        return R.layout.fragment_fav_match;
    }

    public TabFragmentFavMatch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragmentFavMatch.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragmentFavMatch newInstance(String param1, String param2) {
        TabFragmentFavMatch fragment = new TabFragmentFavMatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   ButterKnife.bind(getActivity());
        mListFavPrefsId.addAll(ViewModel.Current.dataUtils.getfavPref());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mListMatchFav = new ArrayList<>();
        mMatchFavAdapter = new MatchFavoriAdapter(mListMatchFav);
        mRecyclerView.setAdapter(mMatchFavAdapter);
        getLiveMatchs();
    }


    // TODO: Rename method, update argument and hook method into UI event
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
        ViewModel.Current.mApiUtils.getSportService().getMatchList(mDateTimeFormatter.print(today) + "T00:00:00+" + today.getEra(), mDateTimeFormatter.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    mListMatchFav = response.body();
                    mMatchFavAdapter.updateAnswers(sortCompetition(mListMatchFav));
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }


            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                ViewModel.Current.device.showSnackMessage((CoordinatorLayout) getView(), getResources().getString(R.string.Error));


            }
        });

    }

    private List<Match> sortCompetition(List<Match> listmatch) {
        List<Match> matchArrayList = new ArrayList<>();


        for (Match match : listmatch) {

            if (mListFavPrefsId.contains(match.getDbid() + "")) {
                Competition mCompetition = new Competition();
                mCompetition = match.getCompetition();
                mCompetition.setName(getCountryFromUrl(mCompetition.getFlagUrl()) + " : " + mCompetition.getName());
                match.setCompetition(mCompetition);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            getLiveMatchs();
        } else {
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
