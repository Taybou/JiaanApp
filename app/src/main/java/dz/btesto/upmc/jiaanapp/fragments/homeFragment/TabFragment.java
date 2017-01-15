package dz.btesto.upmc.jiaanapp.fragments.homeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.activities.autocomplete.IngredientAuto;

import static dz.btesto.upmc.jiaanapp.activities.autocomplete.IngredientAuto.searchIngredients;

/**
 * -------------------------
 * ### JI3AN APPLICATION ###
 * -------------------------
 * <p>
 * Created by :
 * ------------
 * ++ Nour Elislam SAIDI
 * ++ Mohamed Tayeb BENTERKI
 * <p>
 * ------ 2016-2017 --------
 */
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
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_restaurant_menu);
                tabLayout.getTabAt(1).setIcon(R.drawable.calories);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_room_service);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2 && searchIngredients == null) {
                    getContext().startActivity(new Intent(getContext(), IngredientAuto.class));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

}
