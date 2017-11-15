package recorder.appss.cool.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import recorder.appss.cool.base.BaseFragment;
import recorder.appss.cool.model.BaseView;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.activity.MainActivityTemplete;
import recorder.appss.cool.ui.adapter.ItemOffsetDecoration;
import recorder.appss.cool.ui.adapter.MatchCompetitionAdapter;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.remote.Sportservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMatchsCompet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMatchsCompet#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentMatchsCompet extends BaseFragment  implements BaseView {


    List<Match> mListMatchCompetition;
    private Sportservice mService;
    private Competition mCompetition;
    RecyclerView mRecylcerView;
    MatchCompetitionAdapter mMatchCompetitionAdapter;
    private OnFragmentInteractionListener mListener;
    @Override
    public int getFragmentId() {
        return R.layout.fragment_matchs_compet_list;
    }
    public FragmentMatchsCompet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentMatchsCompet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMatchsCompet newInstance(Competition param1) {
        FragmentMatchsCompet fragment = new FragmentMatchsCompet();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCompetition = getArguments().getParcelable(Constants.ARG_PARAM1);

        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mService = ApiUtils.getSOService();
        mRecylcerView = (RecyclerView) view.findViewById(R.id.rv_match_compr);
        mRecylcerView.setHasFixedSize(true);
        mRecylcerView.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager lLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecylcerView.setLayoutManager(lLinearLayoutManager);
        mListMatchCompetition = new ArrayList<>();
        mMatchCompetitionAdapter = new MatchCompetitionAdapter(mListMatchCompetition, mCompetition);
        mRecylcerView.setAdapter(mMatchCompetitionAdapter);
        GetMatch(mCompetition.getDbid() + "");
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


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Back();

                    return true;

                }

                return false;
            }
        });
    }

    private void Back() {
        // appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Fragment fragment = new TabFragmentCompetitionList();
        Bundle args = new Bundle();
        args.putString("data", "This data has sent to FragmentTwo");
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void GetMatch(String id)

    {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter mDateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        mService.getMatchCompet(id, mDateTimeFormatter.print(today) + "T00:00:00+" + today.getEra(), mDateTimeFormatter.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    mListMatchCompetition = response.body();
                    mMatchCompetitionAdapter.updateAnswers(mListMatchCompetition);


                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }


            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                ViewModel.Current.device.showSnackMessage((CoordinatorLayout) getView(),getResources().getString(R.string.Error));

            }
        });

    }


}

