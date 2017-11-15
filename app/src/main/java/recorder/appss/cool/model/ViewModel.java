package recorder.appss.cool.model;

import recorder.appss.cool.utils.DataUtils;
import recorder.appss.cool.utils.DeviceUtils;
import recorder.appss.cool.utils.FileUtils;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class ViewModel {
    public static volatile ViewModel Current = null;

    public FileUtils fileUtils;
    public DeviceUtils device;
    public DataUtils dataUtils;

    public ViewModel(DeviceUtils device, FileUtils fileUtils, DataUtils dataUtils) {
        this.device = device;
        this.fileUtils = fileUtils;
        this.dataUtils = dataUtils;
        this.Current = this;
    }

    public ViewModel() {
        super();
    }


}
