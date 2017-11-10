package recorder.appss.cool.recyclertest;

import android.support.multidex.MultiDexApplication;

import recorder.appss.cool.model.ViewModel;
import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ScoreApplication extends MultiDexApplication {  //TODO l'application supporte le multidex //ok

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO nouvelle instance de viewmodel crée au lancement de l'application
        // par exemple pour acceder au sharedpref depuis n'importe ou tu fais ViewModel.Current.dataUtils.GetSetting("", "");
        //tu fais  ViewModel.Current.device  pour acceder au methode sur le device par exemple affichage des message
        //ViewModel.Current.fileUtils  les methode qui traite les fichier ect

        ViewModel.Current = new ViewModel(new DeviceUtils(getApplicationContext()),
                new FileUtils(getApplicationContext()),
                new DataUtils(getApplicationContext()));
    }
}
