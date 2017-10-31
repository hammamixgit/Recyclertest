package recorder.appss.cool.recyclertest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import recorder.appss.cool.adapter.ItemOffsetDecoration;
import recorder.appss.cool.adapter.MatchCompetitionAdapter;
import recorder.appss.cool.adapter.MatchLiveAdapter;
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
 * {@link Tab_fragment_live_match.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab_fragment_live_match#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab_fragment_live_match extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Integer> match_states = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
    List<Match> list_match_live;
    private Sportservice mService;
    RecyclerView rv_live;
    MatchLiveAdapter match_live_adap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab_fragment_live_match() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_fragment_live_match.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_fragment_live_match newInstance(String param1, String param2) {
        Tab_fragment_live_match fragment = new Tab_fragment_live_match();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = ApiUtils.getSOService();
        rv_live = (RecyclerView) view.findViewById(R.id.rv_list_live);
        rv_live.setHasFixedSize(true);
        rv_live.addItemDecoration(new ItemOffsetDecoration(25));
        final LinearLayoutManager l = new LinearLayoutManager(view.getContext());
        rv_live.setLayoutManager(l);
        list_match_live = new ArrayList<>();
        match_live_adap = new MatchLiveAdapter(list_match_live);
        rv_live.setAdapter(match_live_adap);
        getlivematchs();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_match, container, false);
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
private void getlivematchs()
{
    DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
    DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
    DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
    mService.getMatch( fmt.print(today) + "T00:00:00+" + today.getEra(), fmt.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

        @Override
        public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

            if (response.isSuccessful()) {
                list_match_live = response.body();

                match_live_adap.updateAnswers(sort_compet(list_match_live));


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
    private List<Match>  sort_compet(List<Match> ll) {
        List<Match> cc = new ArrayList<>();
        DateTime d1 = new DateTime();

        for (Match m : ll) {
            DateTime d2 =new DateTime((long)m.getCurrentStateStart());
            Duration duration = new Duration(d2, d1);
            long mn= duration.getStandardMinutes();
            if(match_states.contains(m.getCurrentState())&& (mn<60))
            {
            Competition c1 = new Competition();
            c1 = m.getCompetition();
            c1.setName(get_country_fromurl(c1.getFlagUrl()) + " : " + c1.getName());
            m.setCompetition(c1);
            cc.add(m);}
        }
        Collections.sort(cc, new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                return o1.getCompetition().getName().compareTo(o2.getCompetition().getName());
            }
        });
return  cc;
    }
    private String get_country_fromurl(String s) {
        String result = s.substring(s.lastIndexOf("/") + 1, s.lastIndexOf("."));
        if (result.equals("fifa") || result.equals("uefa") || result.equals("afc"))
            result = "world";
        return result;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getlivematchs();
        }
        else {
        }
    }

}
