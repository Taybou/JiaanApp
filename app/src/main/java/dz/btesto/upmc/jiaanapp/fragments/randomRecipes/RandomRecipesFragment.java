package dz.btesto.upmc.jiaanapp.fragments.randomRecipes;

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
import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
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
        rootView = inflater.inflate(R.layout.fragment_random_recipes, container, false);
        initializeData();

        return rootView;
    }

    private void initializeData() {
        ServicesAPI servicesAPI = new ServicesAPI();
        recipes = new ArrayList<>();

        //recipes =
        servicesAPI.getRandomRecipes(new DataCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                try {
                    JSONArray obj = result.getJSONArray("recipes");
                    for (int i = 0; i < obj.length(); i++) {
                        int recipeId = obj.getJSONObject(i).getInt("id");
                        String title = obj.getJSONObject(i).optString("title", "N/A");
                        String instructions = obj.getJSONObject(i).optString("instructions", "N/A");
                        int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes", 0);
                        int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
                        String imageUrl = obj.getJSONObject(i).optString("image", null);
                        List<Ingredients> ingredientsList = new ArrayList<>();
                        for (int j = 0; j < obj.getJSONObject(i).getJSONArray("extendedIngredients").length(); j++) {
                            int ingredientId = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).getInt("id");
                            String inggredientImage = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("image", "");
                            String name = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A");
                            Ingredients ingredients = new Ingredients(ingredientId, inggredientImage, name);
                            ingredientsList.add(ingredients);
                        }

                        Recipe recipe = new Recipe(recipeId, title, instructions, preparationMinutes, cookingMinutes, imageUrl, ingredientsList);
                        recipes.add(recipe);
                        //  Log.d("REcipe-SIZE----", String.valueOf(recipes.size()));
                    }
                    setupRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.random_recipes_recycler_view);
        RandomRecipesAdapter mRandomRecipesAdapter = new RandomRecipesAdapter(recipes, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mRandomRecipesAdapter);
    }


}
