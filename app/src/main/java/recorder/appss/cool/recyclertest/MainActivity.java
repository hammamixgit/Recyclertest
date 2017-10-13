package recorder.appss.cool.recyclertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;
import recorder.appss.cool.model.Match;
import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.remote.RetrofitClient;
import recorder.appss.cool.remote.Sportservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Interceptor {
    private Sportservice mService;
private RecyclerView rv;
    List<Match> l= new ArrayList<Match>();
    Adapterrv adap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        mService = ApiUtils.getSOService();
        rv= (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adap =new Adapterrv(l,ImageLoader.getInstance());
        rv.setAdapter(adap);
        loadAnswers();
    }
    public void loadAnswers() {
         DateTime today = new DateTime().withTimeAtStartOfDay().toDateTimeISO();
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay().toDateTimeISO();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss-z");

        Log.d("DDDate", "loadAnswers: "+today+"     "+tomorrow);


//param en second -> timestamp "temps unix"
         mService.getCompet("2017-10-12","2017-10-13",RetrofitClient.getkey()).enqueue(new Callback<List<Match>>() {

                @Override
                public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {

                    if(response.isSuccessful()) {
                        l=response.body();
                        adap.updateAnswers(l);
                        Log.d("MainActivity", "posts loaded from API"+l.size()+l.toString());
                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                    }



                }

                @Override
                public void onFailure(Call<List<Match>> call, Throwable t) {
                    // showErrorMessage();
                    Log.d("MainActivity", "error loading from API"+t.toString());

                }
            });





    }



    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("infooooooo",String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        return null;
    }
}
