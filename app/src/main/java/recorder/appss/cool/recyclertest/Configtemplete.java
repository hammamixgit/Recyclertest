package recorder.appss.cool.recyclertest;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

public enum Configtemplete {


    CUSTOM_TAB_ICONS2(R.string.demo_title_custom_tab_icons2, R.layout.demo_custom_tab_icons2) {
        @Override
        public int[] tabs() {
            return new int[]{
                    R.string.demo_tab_no_title,
                    R.string.demo_tab_no_title,
                    R.string.demo_tab_no_title,
                    R.string.demo_tab_no_title
            };
        }

        @Override
        public void setup(SmartTabLayout layout) {
            super.setup(layout);

            final LayoutInflater inflater = LayoutInflater.from(layout.getContext());
            final Resources res = layout.getContext().getResources();

            layout.setCustomTabView(new SmartTabLayout.TabProvider() {
                @Override
                public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                    ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon2, container,
                            false);
                    switch (position) {
                        case 0:
                            icon.setImageDrawable(res.getDrawable(R.mipmap.ic_home_white_24dp));
                            break;
                        case 1:
                            icon.setImageDrawable(res.getDrawable(R.mipmap.ic_search_white_24dp));
                            break;
                        case 2:
                            icon.setImageDrawable(res.getDrawable(R.mipmap.ic_person_white_24dp));
                            break;
                        case 3:
                            icon.setImageDrawable(res.getDrawable(R.mipmap.ic_flash_on_white_24dp));
                            break;
                        default:
                            throw new IllegalStateException("Invalid position: " + position);
                    }
                    return icon;
                }
            });
        }
    };


    public final int titleResId;
    public final int layoutResId;

    Configtemplete(int titleResId, int layoutResId) {
        this.titleResId = titleResId;
        this.layoutResId = layoutResId;
    }


    public void startActivity(Context context) {
        MainActivitytemplete.startActivity(context, this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setup(final SmartTabLayout layout) {
        //Do nothing.
    }

    public int[] tabs() {
        return tab3();
    }

    public static int[] tab3() {
        return new int[]{
                R.string.demo_tab_no_title,
                R.string.demo_tab_no_title,
                R.string.demo_tab_no_title,
                R.string.demo_tab_no_title
        };
    }

}