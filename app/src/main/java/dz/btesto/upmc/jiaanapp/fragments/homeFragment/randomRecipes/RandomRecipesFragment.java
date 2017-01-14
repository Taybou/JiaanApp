package dz.btesto.upmc.jiaanapp.fragments.homeFragment.randomRecipes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.utils.Parser;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

public class RandomRecipesFragment extends Fragment {

    private View rootView = null;
    List<Recipe> recipes;

    public RandomRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        initializeData();
        return rootView;
    }

    private void initializeData() {


        if(Parser.isOnline(getContext())) {
            ServicesAPI servicesAPI = new ServicesAPI();
            recipes = new ArrayList<>();

            servicesAPI.getRandomRecipes(new DataCallback() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    recipes = Parser.parsingRandomRecipes(result);
                    setupRecyclerView();
                }

                @Override
                public void onSuccess(JSONArray result) throws JSONException {

                }
            });
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.random_recipes_recycler_view);
        RandomRecipesAdapter mRandomRecipesAdapter = new RandomRecipesAdapter(recipes, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRandomRecipesAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("FRAGMENT-RAND", "onSTART");
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        Log.d("FRAGMENT-RAND", "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("FRAGMENT-RAND", "onStop");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FRAGMENT-RAND", "onPause");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("FRAGMENT-RAND", "onAttach");

    }



}
