package recorder.appss.cool.remote;

/**
 * Created by work on 02/10/2017.
 */
public class ApiUtils {

    public static final String BASE_URL = "https://api.crowdscores.com/v1/";

    public static Sportservice getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(Sportservice.class);
    }
}