package dz.btesto.upmc.jiaanapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dz.btesto.upmc.jiaanapp.fragments.byIngredientsRecipes.ByIngredientsRecipesFragment;
import dz.btesto.upmc.jiaanapp.fragments.byNutritionRecipes.ByNutritionRecipesFragment;
import dz.btesto.upmc.jiaanapp.fragments.randomRecipes.RandomRecipesFragment;

import static dz.btesto.upmc.jiaanapp.fragments.TabFragment.int_items;

/**
 * Created by Xo on 08/01/2017.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RandomRecipesFragment();
            case 1:
                return new ByNutritionRecipesFragment();
            case 2:
                return new ByIngredientsRecipesFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "First Fragment";
            case 1:
                return "Second Fragment";
            case 2:
                return "Third Fragment";

        }
        return null;
    }

}