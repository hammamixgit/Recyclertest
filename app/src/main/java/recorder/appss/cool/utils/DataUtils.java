package recorder.appss.cool.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
