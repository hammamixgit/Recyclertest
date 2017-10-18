package recorder.appss.cool.recyclertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
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

import recorder.appss.cool.adapter.CompetitionAdapter;
import recorder.appss.cool.adapter.ItemOffsetDecoration;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.remote.Sportservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCompetitionList extends Fragment {

    //Declaration var
    List<Integer> match_states = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8));
    private Sportservice mService;
    private RecyclerView rv;
    List<Match> list_match ;
    List<Competition> list_competition ;
    LinkedHashMap<Competition, String> comp_occur ;
    //  List <Integer> live_match_per_comp = new ArrayList<>();
    CompetitionAdapter adap;
private int matchs_live=0;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_match = new ArrayList<>();
        list_competition = new ArrayList<>();
        comp_occur = new LinkedHashMap<>();
        int position = FragmentPagerItem.getPosition(getArguments());
        JodaTimeAndroid.init(view.getContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(view.getContext()).build();
        ImageLoader.getInstance().init(config);
        mService = ApiUtils.getSOService();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        // set true if your RecyclerView is finite and has fixed size
        rv.setHasFixedSize(false);
        rv.addItemDecoration(new ItemOffsetDecoration(25));
      final LinearLayoutManager l=  new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(l);
        adap = new CompetitionAdapter(comp_occur, ImageLoader.getInstance());
        rv.setAdapter(adap);

        loadAnswers();
    }

    public void loadAnswers() {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd");
        // Log.d("DDDate", "loadAnswers: "+fmt.print(today)+"     "+tomorrow);
        mService.getCompet(fmt.print(today), fmt.print(today), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    list_match = response.body();

                    sort_compet(list_match, list_competition);

                    adap.updateAnswers(count_compet(list_competition),list_match.size(),matchs_live);

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

    private void sort_compet(List<Match> ll, List<Competition> cc) {
        for (Match m : ll) {
            Competition c1 = new Competition();
            c1 = m.getCompetition();
            c1.setName(get_country_fromurl(c1.getFlagUrl()) + " : " + c1.getName());
            cc.add(c1);
        }
        Collections.sort(cc, new Comparator<Competition>() {
            @Override
            public int compare(Competition o1, Competition o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

    }

    private String get_country_fromurl(String s) {
        String result = s.substring(s.lastIndexOf("/") + 1, s.lastIndexOf("."));
        if (result.equals("fifa")||result.equals("uefa")||result.equals("afc")) result = "world";
        return result;
    }


    private LinkedHashMap count_compet(List<Competition> comp) {
        int var=0;
        List<Integer> id = new ArrayList<>();
        for (Competition c : comp)
            id.add(c.getDbid());
        LinkedHashMap<Competition, String> hm = new LinkedHashMap<>();
        Set<Integer> lhs = new LinkedHashSet<>(id);
        for (Integer c : lhs) {
            int occurrences = Collections.frequency(id, c);
            var=get_live_by_comp(list_match, c);
            hm.put(get_comp_by_id(comp, c), occurrences + ":" + var);
            matchs_live=matchs_live+var;
        }
        return hm;
    }


    private Competition get_comp_by_id(List<Competition> comp, Integer c) {
        Competition cp = new Competition();
        ;
        for (int i = 0; i < comp.size(); i++)
            if (comp.get(i).getDbid() == c) {
                cp = new Competition();
                cp = comp.get(i);
                break;
            }
        return cp;
    }

    private Integer get_live_by_comp(List<Match> matchs, Integer id_comp) {
        int count_live = 0;
        for (int count = 0; count < matchs.size(); count++) {
            if ((matchs.get(count).getCompetition().getDbid() == id_comp) && (match_states.contains(matchs.get(count).getCurrentState())))
                count_live++;
        }

        return count_live;
    }

}