package dz.btesto.upmc.jiaanapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.entity.DisplayingRecipe;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;
import dz.btesto.upmc.jiaanapp.entity.Recipe;

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
public class Parser {

    public static Recipe parsingRecipeDetails(JSONObject response) throws JSONException {
        final Recipe recipe = new Recipe();
        final List<Ingredient> ingredientsList = new ArrayList<Ingredient>();

        for (int j = 0; j < response.getJSONArray("extendedIngredients").length(); j++) {
            Ingredient ingredients =
                    new Ingredient(response.getJSONArray("extendedIngredients").getJSONObject(j).getInt("id"),
                            response.getJSONArray("extendedIngredients").getJSONObject(j).optString("image", ""),
                            response.getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A"),
                            true);
            ingredientsList.add(ingredients);
        }
        recipe.setRecipeId(response.optInt("id", 0));
        recipe.setCookingMinutes(response.optInt("readyInMinutes", 0));
        recipe.setImageUrl(response.optString("image", "http/null"));
        recipe.setIngredientsList(ingredientsList);
        recipe.setTitle(response.optString("title", "N/A"));
        recipe.setPreparationMinutes(response.optInt("preparationMinutes", 0));
        recipe.setInstructions(response.optString("instructions", "N/A"));

        return recipe;

    }

    public static List<Recipe> parsingRandomRecipes(JSONObject result) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        JSONArray obj = result.getJSONArray("recipes");
        for (int i = 0; i < obj.length(); i++) {
            int recipeId = obj.getJSONObject(i).getInt("id");
            String title = obj.getJSONObject(i).optString("title", "N/A");
            String instructions = obj.getJSONObject(i).optString("instructions", "N/A");
            int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes", 0);
            int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
            String imageUrl = obj.getJSONObject(i).optString("image", null);
            Recipe recipe = new Recipe(recipeId, title, instructions, preparationMinutes, cookingMinutes, imageUrl);
            recipes.add(recipe);
        }
        return recipes;
    }

    public static List<DisplayingRecipe> parsingRecipesByIngredients(JSONArray result) throws JSONException {
        List<DisplayingRecipe> recipes = new ArrayList<>();
        JSONArray obj = new JSONArray(result.toString());
        for (int i = 0; i < obj.length(); i++) {
            int recipeId = obj.getJSONObject(i).getInt("id");
            String title = obj.getJSONObject(i).optString("title", "N/A");
            String imageUrl = obj.getJSONObject(i).optString("image", null);
            String likes = obj.getJSONObject(i).optString("aggregateLikes", "0");
            DisplayingRecipe recipe = new DisplayingRecipe(recipeId, title, imageUrl, likes);
            recipes.add(recipe);
        }
        return recipes;
    }

    public static List<DisplayingRecipe> parsingRecipesByNutrition(JSONArray result) throws JSONException {
        List<DisplayingRecipe> recipes = new ArrayList<>();
        JSONArray obj = new JSONArray(result.toString());
        for (int i = 0; i < obj.length(); i++) {
            int recipeId = obj.getJSONObject(i).getInt("id");
            String title = obj.getJSONObject(i).optString("title", "N/A");
            String imageUrl = obj.getJSONObject(i).optString("image", null);
            DisplayingRecipe recipe = new DisplayingRecipe(recipeId, title, imageUrl);
            recipes.add(recipe);
        }
        return recipes;
    }

    public static String formatTime(int time) {
        final String minuteTag = "min";
        final String hourTag = "h";
        if (time < 60) {
            return String.valueOf(time) + minuteTag;
        } else {
            return String.valueOf(time / 60 + hourTag + " " + time % 60 + minuteTag);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
