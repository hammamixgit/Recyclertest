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


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCESNAME, 0);
    }

    public static List<String> getfavPref(Context context) {
        Set<String> tasksSet =getPrefs(context).getStringSet(Constants.FAV_PREF, new HashSet<String>());
        List<String> tasksList = new ArrayList<String>(tasksSet);
        return tasksList;
    }

    public static void addfavPref(Context context, String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref(context);
        if(!tasksList.contains(id))tasksList.add(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        getPrefs(context).edit().putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }

    public static void removefavPref(Context context, String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref(context);
        if(tasksList.contains(id))tasksList.remove(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        getPrefs(context).edit().putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }







}