package recorder.appss.cool.recyclertest;

import android.support.multidex.MultiDexApplication;

import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.remote.ApiUtils;
import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ScoreApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();


        ViewModel.Current = new ViewModel(new DeviceUtils(getApplicationContext()),
                new FileUtils(getApplicationContext()),
                new DataUtils(getApplicationContext()),
                new ApiUtils());
    }
}
