package recorder.appss.cool.recyclertest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivitytemplete extends AppCompatActivity implements Tab_fragment_live_match.OnFragmentInteractionListener, F2.OnFragmentInteractionListener, F3.OnFragmentInteractionListener, FragmentMatchsCompet.OnFragmentInteractionListener {

    private static final String KEY_DEMO = "main";

    public static void startActivity(Context context, Configtemplete configtemplete) {
        Intent intent = new Intent(context, MainActivitytemplete.class);
        intent.putExtra(KEY_DEMO, configtemplete.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_templete);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        Configtemplete configtemplete = getDemo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(configtemplete.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(configtemplete.layoutResId, tab, false));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        configtemplete.setup(viewPagerTab);

        FragmentPagerItems pages = new FragmentPagerItems(this);


        pages.add(FragmentPagerItem.of("1", TabFragmentCompetitionList.class));
        pages.add(FragmentPagerItem.of("2", Tab_fragment_live_match.class));
        pages.add(FragmentPagerItem.of("3", F2.class));
        pages.add(FragmentPagerItem.of("4", F3.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }

    private Configtemplete getDemo() {
        return Configtemplete.valueOf(getIntent().getStringExtra(KEY_DEMO));
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

    }
}