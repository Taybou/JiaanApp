package dz.btesto.upmc.jiaanapp.fragments.byNutritionRecipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import dz.btesto.upmc.jiaanapp.utils.Parssers;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

public class ByNutritionRecipesFragment extends Fragment {

    private View rootView = null;
    List<DisplayingRecipe> recipes;

    public ByNutritionRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_random_recipes, container, false);
        initializeData();
        return rootView;
    }

    private void initializeData() {
        ServicesAPI servicesAPI = new ServicesAPI();
        recipes = new ArrayList<>();
        servicesAPI.getRecipesByNutrition(new DataCallback() {
            @Override
            public void onSuccess(JSONArray result) throws JSONException {
                recipes = Parssers.parssingRecipesByNutrition(result);
                setupRecyclerView();
            }

            @Override
            public void onSuccess(JSONObject result) throws JSONException {
            }
        }, "");
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.random_recipes_recycler_view);
        ByNutritionRecipesAdapter mRandomRecipesAdapter = new ByNutritionRecipesAdapter(recipes, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRandomRecipesAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("FRAGMENT-NUT", "onSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        Log.d("FRAGMENT-NUT", "onResume");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("FRAGMENT-NUT", "onStop");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FRAGMENT-NUT", "onPause");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("FRAGMENT-NUT", "onAttach");

    }
}
