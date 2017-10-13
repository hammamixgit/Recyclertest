package recorder.appss.cool.remote;

/**
 * Created by work on 02/10/2017.
 */

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

import recorder.appss.cool.model.Match;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Sportservice {

    @GET("matches?")
    Call<List<Match>> getCompet(@Query("from") long from,@Query("to") long to, @Query("api_key") String key);


}

