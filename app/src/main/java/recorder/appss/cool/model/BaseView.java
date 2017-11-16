package recorder.appss.cool.model;

/**
 * Created by yassin baccour on 12/11/2017.
 */

public interface BaseView {
    void showSnackMsg(String msg);

    void showLoading();  //TODO pense a ajouter un loader au futur ou porgresse bar pour le chargement des données

    void hideLoading();

}
