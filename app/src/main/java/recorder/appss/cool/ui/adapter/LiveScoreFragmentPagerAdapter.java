package recorder.appss.cool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import recorder.appss.cool.ui.fragment.F3;
import recorder.appss.cool.ui.fragment.TabFragmentCompetitionList;
import recorder.appss.cool.ui.fragment.TabFragmentFavMatch;
import recorder.appss.cool.ui.fragment.TabFragmentLiveMatch;


/**
 * Created by work on 07/11/2017.
 */

public class LiveScoreFragmentPagerAdapter extends FragmentPagerAdapter {
    public LiveScoreFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private String[] titles = new String[]{"Competitions", "Live", "My Games", "Info Teams"};

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TabFragmentCompetitionList();
                break;
            case 1:
                fragment = new TabFragmentLiveMatch();
                //fragment = new RaggaeMusicFragment();
                break;
            case 2:
                fragment = new TabFragmentFavMatch();
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
