package recorder.appss.cool.model;

import java.util.List;

/**
 * Created by yassin baccour on 19/11/2017.
 */

public interface DataLoadingState<T extends GenericModel> extends BaseView { //TODO T est un Object qui etend GenericModel
    void loadData(List<T> mlistOject);  //TODO en fais une methode load Generique de Type T qui loade tout type d'object
}
