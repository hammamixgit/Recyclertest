package recorder.appss.cool.remote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static String key = "8924361210ed4d069faa3692aaec130c";
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
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