package recorder.appss.cool.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import recorder.appss.cool.base.BaseActivity;
import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.adapter.FragmentPagerAdap;
import recorder.appss.cool.ui.fragment.F3;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;
import recorder.appss.cool.ui.fragment.Tab_fragment_fav_match;
import recorder.appss.cool.ui.fragment.Tab_fragment_live_match;

//TODO Template T majuscule tjr
public class MainActivityTemplete extends BaseActivity implements Tab_fragment_live_match.OnFragmentInteractionListener, Tab_fragment_fav_match.OnFragmentInteractionListener, F3.OnFragmentInteractionListener, FragmentMatchsCompet.OnFragmentInteractionListener {

    //TODO dans activity_main_templete.xml add CoordinatorLayout the root layout toujours pour afficher le snack message par exemple
    @Override
    protected int getLayout() {
        return R.layout.activity_main_templete;
    }

    @Override
    protected void initData() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarx); //TODO rename les variabel android commence toujours par mToolbar
        toolbar.setTitle("LiveScore");  //TODO mettre dans le string toujours getString(R.id.votreVariable)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        FragmentPagerAdap adapter = new FragmentPagerAdap(
                getSupportFragmentManager());
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
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}