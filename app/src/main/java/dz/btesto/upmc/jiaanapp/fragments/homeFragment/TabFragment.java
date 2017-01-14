package dz.btesto.upmc.jiaanapp.fragments.homeFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dz.btesto.upmc.jiaanapp.R;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_tab, null);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabFragmentAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                //for (int i = 0; i < tabLayout.getTabCount(); i++) {
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_restaurant_menu);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_room_service);

                //}
            }
        });

//        actionBar.addTab(actionBar.newTab().setText("ddd1")
//                .setIcon(R.drawable.ic_restaurant_menu));
//        actionBar.addTab(actionBar.newTab().setText("ddd2")
//                .setIcon(R.drawable.ic_restaurant_menu));
//        actionBar.addTab(actionBar.newTab().setText("ddd3")
//                .setIcon(R.drawable.ic_restaurant_menu));

//        toolbar.addTab(actionBar.newTab().setText(tabs[i])
//                .setIcon(NewsFeedActivity.this.getResources().getDrawable(ICONS[i]))
//                .setTabListener(this));


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
        return view;
    }

}
