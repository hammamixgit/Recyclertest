package recorder.appss.cool.model;

import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ViewModel {
    public static volatile ViewModel Current=null;  //TODO singleton (ViewModel Global)

    static  public FileUtils fileUtils;
    static public DeviceUtils device;
    static   public DataUtils dataUtils;

    public ViewModel(DeviceUtils device, FileUtils fileUtils, DataUtils dataUtils) {
        this.device = device;
        this.fileUtils = fileUtils;
        this.dataUtils = dataUtils;
        ViewModel.Current = getInstance() ;
    }
    public ViewModel(){
        super();
    }
    public final static ViewModel getInstance() {
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
        //d'éviter un appel coûteux à synchronized,
        //une fois que l'instanciation est faite.
        if (ViewModel.Current == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized(ViewModel.class) {
                if (ViewModel.Current == null) {
                    ViewModel.Current = new ViewModel();
                }
            }
        }
        return ViewModel.Current;
    }
}
