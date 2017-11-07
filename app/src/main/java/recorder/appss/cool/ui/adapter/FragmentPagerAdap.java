package recorder.appss.cool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import recorder.appss.cool.ui.fragment.F3;
import recorder.appss.cool.ui.fragment.TabFragmentCompetitionList;
import recorder.appss.cool.ui.fragment.Tab_fragment_live_match;

/**
 * Created by work on 07/11/2017.
 */

public class FragmentPagerAdap extends FragmentPagerAdapter {
    public FragmentPagerAdap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new TabFragmentCompetitionList();
                break;
            case 1:
                fragment = new Tab_fragment_live_match();
                //fragment = new RaggaeMusicFragment();
                break;
            case 2:
                fragment = new Tab_fragment_live_match.Tab_fragment_fav_match();
                //fragment = new RapMusicFragment();
                break;
            case 3:
                fragment = new F3();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
