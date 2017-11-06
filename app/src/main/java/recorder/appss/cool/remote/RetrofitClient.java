package recorder.appss.cool.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static String key = "1b3534673b9e4b3c84194bb5d097034b";

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
         /*   Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
*/

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }


    public static String getkey() {

        return key;
    }
}