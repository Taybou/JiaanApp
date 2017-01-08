package dz.btesto.upmc.jiaanapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.entity.DisplayingRecipe;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;

public class Parssers {

    public static Recipe parssingRecipeDetails(JSONObject response) throws JSONException {
        final Recipe recipe = new Recipe();
        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();

        for (int j = 0; j < response.getJSONArray("extendedIngredients").length(); j++) {
            Ingredients ingredients =
                    new Ingredients(response.getJSONArray("extendedIngredients").getJSONObject(j).getInt("id"),
                            response.getJSONArray("extendedIngredients").getJSONObject(j).optString("image", ""),
                            response.getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A"),
                            true);
            ingredientsList.add(ingredients);
        }
        recipe.setCookingMinutes(response.optInt("readyInMinutes", 0));
        recipe.setImageUrl(response.optString("image", "http/null"));
        recipe.setIngredientsList(ingredientsList);
        recipe.setTitle(response.optString("title", "N/A"));
        recipe.setPreparationMinutes(response.optInt("preparationMinutes", 0));
        recipe.setInstructions(response.optString("instructions", "N/A"));
        Log.d("recipetest", recipe.getInstructions());
        Log.i("TEBBBBBB", recipe.getInstructions());

        return recipe;

    }

    public static List<Recipe> parssingRandomRecipes(JSONObject result) throws JSONException {
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

    public static List<DisplayingRecipe> parssingRecipesByIngredients(JSONArray result) throws JSONException {
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

    public static List<DisplayingRecipe> parssingRecipesByNutrition(JSONArray result) throws JSONException {
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
        final String houreTag = "h";
        if (time < 60) {
            return String.valueOf(time) + minuteTag;
        } else {

            return String.valueOf(time / 60 + houreTag + " " + time % 60 + minuteTag);
        }
    }
}
