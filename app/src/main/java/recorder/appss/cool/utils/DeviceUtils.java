package recorder.appss.cool.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import java.io.File;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public class DeviceUtils {

    private Context mContext;

    public DeviceUtils(Context mContext) {
        this.mContext = mContext;
    }

    //TODO exemple de methode dan deviceUtils
    //TODO Snack est l'equivalent de toast
    public void showSnackMessage(CoordinatorLayout mRootLayout, String message) {
        Snackbar snackbar = Snackbar
                .make(mRootLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void shareFileAll(Activity ac, File file) {
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.setDataAndType(Uri.fromFile(file), "image/jpg");
        intent.putExtra("mimeType", "image/jpg");
        ac.startActivityForResult(Intent.createChooser(intent, "set as"), 200);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager mPackageManager = mContext.getPackageManager();
        boolean app_installed;
        try {
            mPackageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
