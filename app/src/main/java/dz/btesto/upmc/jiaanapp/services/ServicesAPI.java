package dz.btesto.upmc.jiaanapp.services;

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

import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.volley.AppController;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

/**
 * Created by besto on 15/12/16.
 */

public class ServicesAPI {

    private static final String TAG = "Json-Respons";
    private static final String randomUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10";
    public int successCount = 0;

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

    public void getRecipesByNutrition(final DataCallback callback, String ingredients) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath("findByNutrients")
                .appendQueryParameter("maxcalories", "250")
                .appendQueryParameter("maxcarbs", "100")
                .appendQueryParameter("maxfat", "20")
                .appendQueryParameter("maxprotein", "100")
                .appendQueryParameter("mincalories", "0")
                // .appendQueryParameter("minCarbs", "10")
                .appendQueryParameter("minfat", "5")
                .appendQueryParameter("minProtein", "10")
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", "B0SwO2cGLvmshUiLiIw441A4O4bzp1MTCEmjsnsHYxhEtyGNw8");
                params.put("Accept", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public Recipe getRecipeDetails(int idRecipe, final DataCallback callback) {
        final Recipe recipe = new Recipe();

        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();

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
//                                                Ingredients ingredients = new Ingredients(ingredientId, inggredientImage, name);
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
