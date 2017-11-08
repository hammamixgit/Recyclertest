package recorder.appss.cool.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import recorder.appss.cool.ui.adapter.FragmentPagerAdap;


import recorder.appss.cool.recyclertest.R;
import recorder.appss.cool.ui.fragment.F3;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;
import recorder.appss.cool.ui.fragment.Tab_fragment_fav_match;
import recorder.appss.cool.ui.fragment.Tab_fragment_live_match;

//TODO Template T majuscule tjr
public class MainActivitytemplete extends AppCompatActivity implements Tab_fragment_live_match.OnFragmentInteractionListener, Tab_fragment_fav_match.OnFragmentInteractionListener, F3.OnFragmentInteractionListener, FragmentMatchsCompet.OnFragmentInteractionListener {

    private static final String KEY_DEMO = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_templete);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
     //   Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
       // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        //window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        /////////////////////////////////////////////////////


        ////////////////////////////////////////////////
   //     Configtemplete configtemplete = getDemo();
//
  //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    toolbar.setTitle(configtemplete.titleResId);
      //  setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        //tab.addView(LayoutInflater.from(this).inflate(configtemplete.layoutResId, tab, false));


        //SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        //configtemplete.setup(viewPagerTab);




        FragmentPagerAdap   adapter = new FragmentPagerAdap(
                getSupportFragmentManager());

        viewPager.setAdapter(adapter);
       // viewPagerTab.setViewPager(viewPager);
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



    //////////////////////////to see
    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*
     Intent a = new Intent(Intent.ACTION_MAIN);
a.addCategory(Intent.CATEGORY_HOME);
a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(a);
      */

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("adress freaag", uri.toString());
    }
}