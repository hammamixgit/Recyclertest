package recorder.appss.cool.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ViewModel {
    public static volatile ViewModel Current = null;

    public FileUtils fileUtils;
    public DeviceUtils device;
    public DataUtils dataUtils;
    public ApiUtils mApiUtils;

    public DataLoadingState mDataLoadingtate;

    List<Integer> mMatchStates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

    public ViewModel(DeviceUtils device, FileUtils fileUtils, DataUtils dataUtils, ApiUtils mApiUtils) {
        this.device = device;
        this.fileUtils = fileUtils;
        this.dataUtils = dataUtils;
        this.mApiUtils = mApiUtils;
        this.Current = this;
    }

    public ViewModel() {
        super();
    }

    public void setDataLoadingtate(DataLoadingState mDataLoadingtate) {
        this.mDataLoadingtate = mDataLoadingtate;
    }

    public List<Match> sortCompetition(List<Match> listMatch) {
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

    public void getLiveMatchs() {
        DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter mDateTimeFormatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        ViewModel.Current.mApiUtils.getSportService().getMatchList(mDateTimeFormatter.print(today) + "T00:00:00+" + today.getEra(), mDateTimeFormatter.print(tomorrow) + "T00:00:00+" + today.getEra(), RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                if (response.isSuccessful()) {
                    List<Match> listeMatch = response.body();
                    mDataLoadingtate.loadData(listeMatch); //TODO chaque classe en cour qui implemente DataLoadingtate on Call LoadData de cette classe
                } else {
                    mDataLoadingtate.showSnackMsg(""); //TODO on call la methode dans chaque classe qui implemente ShowSnackMsg
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                mDataLoadingtate.showSnackMsg("");
            }
        });
    }

    public int verifMatchForCompet(List<Match> mListMatchsLive2, int position) {
        int id_comp = mListMatchsLive2.get(position).getCompetition().getDbid();
        int result = 0;
        for (Match match : mListMatchsLive2) {
            if (match.getDbid() > -1 && match.getCompetition().getDbid().equals(id_comp)) {
                result++;
            }
        }
        return result;
    }
}
