package dz.btesto.upmc.jiaanapp.utils;

import android.util.Log;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;

/**
 * Created by besto on 06/01/17.
 */

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
        };
        recipe.setCookingMinutes(response.optInt("readyInMinutes", 0));
        recipe.setImageUrl(response.optString("image", "http/null"));
        recipe.setIngredientsList(ingredientsList);
        recipe.setTitle(response.optString("title", "N/A"));
        recipe.setPreparationMinutes(response.optInt("preparationMinutes", 0));
        recipe.setInstructions(response.optString("instructions", "N/A"));
        Log.d("recipetest", recipe.getInstructions());

        return recipe ;

    }

    public static String formatTime (int time){
        final String minuteTag = "min";
        final String houreTag = "h";
        if(time<60){
            return String.valueOf(time)+minuteTag;
        }else{

            return String.valueOf(time / 60 +houreTag +" "+ time % 60 + minuteTag);
        }
    }
}
