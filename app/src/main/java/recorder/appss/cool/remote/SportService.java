package recorder.appss.cool.remote;

/**
 * Created by work on 02/10/2017.
 */


import java.util.List;

import recorder.appss.cool.model.Match;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportService {

    @GET("matches?")
    Call<List<Match>> getMatchList(@Query("from") String from, @Query("to") String to, @Query("api_key") String key);

    @GET("matches?")
    Call<List<Match>> getMatchCompetitionList(@Query("competition_id") String competition, @Query("from") String from, @Query("to") String to, @Query("api_key") String key);
}

