package recorder.appss.cool.model;

import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ViewModel {
    public static volatile ViewModel Current = null;  //TODO singleton (ViewModel Global)

    public FileUtils fileUtils; //TODO ne doivent pas etre static
    public DeviceUtils device; //TODO ne doivent pas etre static
    public DataUtils dataUtils; //TODO ne doivent pas etre static

    public ViewModel(DeviceUtils device, FileUtils fileUtils, DataUtils dataUtils) {
        this.device = device;
        this.fileUtils = fileUtils;
        this.dataUtils = dataUtils;
        ViewModel.Current = getInstance();  //TODO l'intance est crée dans la classe application
    }

    public ViewModel() {
        super();
    }

    public final static ViewModel getInstance() { //TODO a supprimer l'instance ce crée toujours dans ScoreApplication
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
        //d'éviter un appel coûteux à synchronized,
        //une fois que l'instanciation est faite.
        if (ViewModel.Current == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized (ViewModel.class) {  //TODO synchronise n'est pas trés utilise car y'a une seule instance a l'ouverture de l'application, si y'a beauoup d"instance dans des thread elle sera utile
                if (ViewModel.Current == null) {
                    ViewModel.Current = new ViewModel();
                }
            }
        }
        return ViewModel.Current;
    }
}
