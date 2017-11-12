package recorder.appss.cool.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity { // tu dois etendre tout tes activiter de BaseActivity

    //TODO quand tu ajoutera butterknife library oublie pas la Base
    protected Activity mContext;
    protected abstract int getLayout();
    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        initData();
    }
}
