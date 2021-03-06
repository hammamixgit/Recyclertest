package recorder.appss.cool.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import recorder.appss.cool.base.BaseActivity;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.adapter.LiveScoreFragmentPagerAdapter;
import recorder.appss.cool.ui.fragment.F3;
import recorder.appss.cool.ui.fragment.FragmentMatchCompetition;
import recorder.appss.cool.ui.fragment.TabFragmentFavMatch;
import recorder.appss.cool.ui.fragment.TabFragmentLiveMatch;


public class MainActivityTemplete extends BaseActivity implements TabFragmentLiveMatch.OnFragmentInteractionListener, TabFragmentFavMatch.OnFragmentInteractionListener, F3.OnFragmentInteractionListener, FragmentMatchCompetition.OnFragmentInteractionListener {



    @BindView(R.id.vpPager) ViewPager viewPager;
    @Override
    protected int getLayout() {
        return R.layout.activity_main_templete;
    }

    @Override
    protected void initData() {

    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.toolbartitle);
        LiveScoreFragmentPagerAdapter adapter = new LiveScoreFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}