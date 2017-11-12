package recorder.appss.cool.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import recorder.appss.cool.recyclertest.R;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public abstract class BaseFragment extends Fragment {
    //TODO quand tu ajoutera butterknife library oublie pas la Base
    public abstract int getFragmentId();
    protected View rootView;
    protected Activity mActivity;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getFragmentId()== R.layout.fragment_matchs_compet_list && container != null) {
            container.removeAllViews();
        }
        rootView = inflater.inflate(getFragmentId(), null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
