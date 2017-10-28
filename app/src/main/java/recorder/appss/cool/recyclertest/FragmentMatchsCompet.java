package recorder.appss.cool.recyclertest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import recorder.appss.cool.adapter.CompetitionAdapter;
import recorder.appss.cool.adapter.ItemOffsetDecoration;
import recorder.appss.cool.adapter.MatchCompetitionAdapter;
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
public class FragmentMatchsCompet extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    List<Match> list_match_compet ;
    private Sportservice mService;
    // TODO: Rename and change types of parameters
    private Competition mParam1;
RecyclerView rv;
    MatchCompetitionAdapter m_comp_adap;
     Toolbar toolbar;
    AppCompatActivity appCompatActivity;
    private OnFragmentInteractionListener mListener;

    public FragmentMatchsCompet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment FragmentMatchsCompet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMatchsCompet newInstance(Competition param1) {
        FragmentMatchsCompet fragment = new FragmentMatchsCompet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 =getArguments().getParcelable(ARG_PARAM1);
            Log.d("compid",mParam1+"");
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = ApiUtils.getSOService();
        rv= (RecyclerView)view.findViewById(R.id.rv_match_compr);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager l=  new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(l);
        list_match_compet= new ArrayList<>();
        m_comp_adap = new MatchCompetitionAdapter(list_match_compet,mParam1);
        rv.setAdapter(m_comp_adap);
        getmatch(mParam1.getDbid()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  toolbar.setNavigationIcon(R.mipmap.ic_flash_on_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        return inflater.inflate(R.layout.fragment_matchs_compet, container, false);
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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                  back();

                    return true;

                }

                return false;
            }
        });
    }

private  void back ()
{
   // appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    Fragment fragment = new FragmentCompetitionList();
    Bundle args = new Bundle();
    args.putString("data", "This data has sent to FragmentTwo");
    fragment.setArguments(args);
    FragmentTransaction transaction =     getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.activity_main, fragment);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.addToBackStack(null);
    transaction.commit();

}

    private void getmatch(String id)

    {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        mService.getMatchCompet(id,fmt.print(today)+"T00:00:00+"+today.getEra(), fmt.print(tomorrow)+"T00:00:00+"+today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    list_match_compet = response.body();
                    m_comp_adap.updateAnswers(list_match_compet,0,0);


                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }


            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                // showErrorMessage();
                Log.d("MainActivity", "error loading from API" + t.toString());

            }
        });

    }


}

