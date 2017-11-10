package recorder.appss.cool.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import recorder.appss.cool.model.Constants;


public class DataUtils {
    private Context mContext;
    private SharedPreferences mPref;
    private SharedPreferences.Editor prefsEditor;

    public DataUtils(Context mContext) {
        this.mContext = mContext;
        mPref = mContext.getSharedPreferences(Constants.PREFERENCESNAME,
                Context.MODE_PRIVATE);
        prefsEditor = mPref.edit();
    }


    public  List<String> getfavPref() {
        Set<String> tasksSet =mPref.getStringSet(Constants.FAV_PREF, new HashSet<String>());
        List<String> tasksList = new ArrayList<String>(tasksSet);
        return tasksList;
    }


    public  void addfavPref(String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref();
        if(!tasksList.contains(id))tasksList.add(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        prefsEditor.putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }

    public  void removefavPref(String id) {
        List<String> tasksList = new ArrayList<String>();
        tasksList=getfavPref();
        if(tasksList.contains(id))tasksList.remove(id);
        Set<String> tasksSet = new HashSet<String>(tasksList);
        prefsEditor.putStringSet(Constants.FAV_PREF, tasksSet).commit();
    }




    public void SetSetting(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void SetSetting(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void SetSetting(String key, Boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public String GetSetting(String key, String value) {
        return mPref.getString(key, value);
    }

    public int GetSetting(String key, int value) {
        return mPref.getInt(key, value);
    }

    public boolean GetSetting(String key, boolean value) {
        return mPref.getBoolean(key, value);
    }

}
