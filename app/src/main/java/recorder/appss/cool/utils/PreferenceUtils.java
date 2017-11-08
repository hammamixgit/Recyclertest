package recorder.appss.cool.utils;

/**
 * Created by work on 31/10/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import recorder.appss.cool.model.Constants;

public class PreferenceUtils {

//TODO ne pas copier dans datautils y'a déja une fonction
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCESNAME, 0);
    }

    //TODO copie le contenu dans DataUtils et tu passe Par ViewModel.Current.DataUtil.getfavPref ou ta besoin
    public static List<String> getfavPref(Context context) {
        Set<String> tasksSet =getPrefs(context).getStringSet(Constants.FAV_PREF, new HashSet<String>());  //todo getPrefs(context) déja mawjouda fee Datautil qui est mPref
        List<String> tasksList = new ArrayList<String>(tasksSet);
        return tasksList;
    }

    //TODO copie le contenu dans DataUtils et tu passe Par ViewModel.Current.DataUtil.addfavPref ou ta besoin
    public static void addfavPref(Context context, String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref(context);
        if(!tasksList.contains(id))tasksList.add(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        getPrefs(context).edit().putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }

    //TODO copie le contenu dans DataUtils et tu passe Par ViewModel.Current.DataUtil.removefavPref ou ta besoin
    public static void removefavPref(Context context, String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref(context);
        if(tasksList.contains(id))tasksList.remove(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        getPrefs(context).edit().putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }







}