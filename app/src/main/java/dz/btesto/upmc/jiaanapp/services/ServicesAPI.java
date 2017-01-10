package dz.btesto.upmc.jiaanapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dz.btesto.upmc.jiaanapp.entity.Ingredient;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.volley.AppController;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

import static android.content.Context.MODE_PRIVATE;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_CAL;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_FAT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_PROT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_CAL;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_FAT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_PROT;

/**
 * Created by besto on 15/12/16.
 */

public class ServicesAPI {

    private static final String TAG = "Json-Respons";
    private static final String randomUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10";
    public int successCount = 0;
    private SharedPreferences sharedPreferences;

    public void getRandomRecipes(final DataCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        randomUrl,
                        "",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.d(TAG, response.toString());
                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                            }
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", "B0SwO2cGLvmshUiLiIw441A4O4bzp1MTCEmjsnsHYxhEtyGNw8");
                params.put("Accept", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void getRecipesByIngredients(final DataCallback callback, String ingredients) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath("findByIngredients")
                .appendQueryParameter("fillIngredients", "false")
                .appendQueryParameter("ingredients", ingredients)
                .appendQueryParameter("limitLicense", "false")
                .appendQueryParameter("number", "20")
                .appendQueryParameter("ranking", "1");

        String recipeByIngredientsURl = builder.build().toString();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (recipeByIngredientsURl,
                        new Response.Listener<JSONArray>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONArray response) {
                                //       Log.d(TAG, response.toString());
                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //    VolleyLog.d(TAG, "Error: " + error.getMessage());
                            }
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", "B0SwO2cGLvmshUiLiIw441A4O4bzp1MTCEmjsnsHYxhEtyGNw8");
                params.put("Accept", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void getRecipesByNutrition(final DataCallback callback, Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS, MODE_PRIVATE);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath("findByNutrients")
                .appendQueryParameter("maxcalories", String.valueOf(sharedPreferences.getInt(PREFS_MAX_CAL, 250)))
                //.appendQueryParameter("maxcarbs", String.valueOf(sharedPreferences.getInt(,100)))
                .appendQueryParameter("maxfat", String.valueOf(sharedPreferences.getInt(PREFS_MAX_FAT, 20)))
                .appendQueryParameter("maxprotein", String.valueOf(sharedPreferences.getInt(PREFS_MAX_PROT, 100)))
                .appendQueryParameter("mincalories", String.valueOf(sharedPreferences.getInt(PREFS_MIN_CAL, 0)))
                // .appendQueryParameter("minCarbs", "10")
                .appendQueryParameter("minfat", String.valueOf(sharedPreferences.getInt(PREFS_MIN_FAT, 5)))
                .appendQueryParameter("minProtein", String.valueOf(sharedPreferences.getInt(PREFS_MIN_PROT, 10)))
                .appendQueryParameter("number", "20")
                .appendQueryParameter("offset", "0")
                .appendQueryParameter("random", "true");

        String recipeByNutritionURL = builder.build().toString();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (recipeByNutritionURL,
                        new Response.Listener<JSONArray>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                            }
                        }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-Mashape-Key", "B0SwO2cGLvmshUiLiIw441A4O4bzp1MTCEmjsnsHYxhEtyGNw8");
                params.put("Accept", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public Recipe getRecipeDetails(int idRecipe, final DataCallback callback) {
        final Recipe recipe = new Recipe();

        final List<Ingredient> ingredientsList = new ArrayList<Ingredient>();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath(String.valueOf(idRecipe))
                .appendPath("information")
                .appendQueryParameter("includeNutrition", "false");

        String recipeByIngredientsURl = builder.build().toString();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET,
                        recipeByIngredientsURl,
                        "",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //Log.d(TAG, response.toString());

                                //  Log.d("Volley-succes", String.valueOf(successCount));


//                                        try {
//
                                //  Log.d(TAG, response.toString());

                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                                            int recipeId = response.getInt("id");
//                                            String title = response.optString("title", "N/A");
//                                            String instructions = response.optString("instructions", "N/A");
//                                            int preparationMinutes = response.optInt("preparationMinutes", 0);
//                                            int cookingMinutes = response.optInt("cookingMinutes", 0);
//                                            String imageUrl = response.optString("image", "http/null");
//                                            Log.d("IMAGE-LINK----", imageUrl);
//                                            for (int j = 0; j < response.getJSONArray("extendedIngredients").length(); j++) {
//
//                                                int ingredientId = response.getJSONArray("extendedIngredients").getJSONObject(j).getInt("id");
//                                                String inggredientImage = response.getJSONArray("extendedIngredients").getJSONObject(j).optString("image", "");
//                                                String name = response.getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A");
//                                                Log.d("INGREDIENT-NAME----", name);
//                                                Ingredient ingredients = new Ingredient(ingredientId, inggredientImage, name);
//                                                ingredientsList.add(ingredients);
//
//                                            }
//
//                                            ;
//                                            recipe.setCookingMinutes(cookingMinutes);
//                                            recipe.setImageUrl(imageUrl);
//                                            recipe.setIngredientsList(ingredientsList);
//                                            recipe.setTitle(title);
//                                            recipe.setPreparationMinutes(preparationMinutes);
//                                            recipe.setInstructions(instructions);
//                                            Log.d("Object-Recipe", recipe.getImageUrl());

//                                        } catch (JSONException e1) {
//                                            e1.printStackTrace();
//                                        }
                                try {
                                    Log.d(TAG + "Object", response.getString("sourceUrl"));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                                successCount++;
                            }


                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  VolleyLog.d(TAG + "details", "Error: " + error.getMessage());

                    }
                }) {

            /**
             * Method to manipulate request header
             *
             * @return
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", "B0SwO2cGLvmshUiLiIw441A4O4bzp1MTCEmjsnsHYxhEtyGNw8");
                params.put("Accept", "application/json");

                return params;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


        return recipe;
    }

}
