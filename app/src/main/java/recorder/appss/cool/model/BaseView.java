package recorder.appss.cool.model;

/**
 * Created by yassin baccour on 12/11/2017.
 */

public interface BaseView {  //TODO implemente cette interface dans chaque fragment et implemente les methodes et laisse les vide pour l'instant quand tu aura besoin tu ajoute du code dedans

    void showSnackMsg(String msg);

    void showLoading();  //TODO pense a ajouter un loader au futur ou porgresse bar pour le chargement des données

    void hideLoading();

}
