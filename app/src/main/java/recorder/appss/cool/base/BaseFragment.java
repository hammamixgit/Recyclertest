package recorder.appss.cool.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yassin baccour on 07/11/2017.
 */

public abstract class BaseFragment extends Fragment {

    public abstract int getFragmentId();  //TODO Methode abstraite tu va la redefinir dans chaque fragment
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
        rootView = inflater.inflate(getFragmentId(), null);  //TODO maintenant un seul onCreateView et tu supprime oncreate view dans chaque frahment qui etend le base
        return rootView;   //TODO tu redifini getFragmentId() dans chaque fragment qui donnera " return R.id.nom_du_view"
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
