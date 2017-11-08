package recorder.appss.cool.remote;

import static recorder.appss.cool.model.Constants.BASE_URL;

/**
 * Created by work on 02/10/2017.
 */
public class ApiUtils {



    public static Sportservice getSOService() {
        return RetrofitClient.getClient( BASE_URL).create(Sportservice.class);
    }
}