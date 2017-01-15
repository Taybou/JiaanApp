package dz.btesto.upmc.jiaanapp.fragments.homeFragment.byIngredientsRecipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.DisplayingRecipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.utils.Parser;
import dz.btesto.upmc.jiaanapp.utils.volley.DataCallback;

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
public class ByIngredientsRecipesFragment extends Fragment {

    private static View rootView = null;
    static List<DisplayingRecipe> recipes;

    public ByIngredientsRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        if (searchIngredients != null)
            initializeData();

        return rootView;
    }

    private void initializeData() {
        if (Parser.isOnline(getContext())) {
            ServicesAPI servicesAPI = new ServicesAPI();
            recipes = new ArrayList<>();

            servicesAPI.getRecipesByIngredients(new DataCallback() {
                @Override
                public void onSuccess(JSONArray result) throws JSONException {
                    recipes = Parser.parsingRecipesByIngredients(result);
                    setupRecyclerView();
                }

                @Override
                public void onSuccess(JSONObject result) throws JSONException {

                }
            }, searchIngredients);
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.random_recipes_recycler_view);
        ByIngredientsRecipesAdapter mRandomRecipesAdapter = new ByIngredientsRecipesAdapter(recipes, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRandomRecipesAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeData();
    }

}
