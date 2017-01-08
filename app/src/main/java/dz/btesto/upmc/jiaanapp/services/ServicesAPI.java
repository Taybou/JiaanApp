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

import dz.btesto.upmc.jiaanapp.entity.DisplayingRecipe;
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
    private String jsonResponse;
    public int successCount=0;
    private int errorCount=0;

    /**
     * Method to make json object request where json response starts wtih {
     */
    public List<Recipe> getRandomRecipes() {
        final List<Recipe> recipes = new ArrayList<Recipe>();
        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET,
                        randomUrl,
                        "",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());

                                try {
                                    JSONArray obj = response.getJSONArray("recipes");
                                    for (int i = 0; i < obj.length(); i++) {
                                        int recipeId = obj.getJSONObject(i).getInt("id");
                                        String title = obj.getJSONObject(i).optString("title", "N/A");
                                        String instructions = obj.getJSONObject(i).optString("instructions", "N/A");
                                        int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes", 0);
                                        int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
                                        String imageUrl = obj.getJSONObject(i).optString("image", null);
                                        Log.d("IMAGE-LINK----", imageUrl);
                                        for (int j = 0; j < obj.getJSONObject(i).getJSONArray("extendedIngredients").length(); j++) {

                                            int ingredientId = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).getInt("id");
                                            String inggredientImage = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("image", "");
                                            String name = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A");
                                            Log.d("INGREDIENT-NAME----", name);
                                            Ingredients ingredients = new Ingredients(ingredientId, inggredientImage, name,true);
                                            ingredientsList.add(ingredients);

                                        }

                                        ;

                                        Recipe recipe = new Recipe(recipeId, title, instructions, preparationMinutes, cookingMinutes, imageUrl, ingredientsList);
                                        recipes.add(recipe);
                                        Log.d("REcipe-SIZE----", String.valueOf(recipes.size()));
                                    }
                                    Log.d(TAG + "Object", obj.getJSONObject(0).getString("sourceUrl"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());


                    }
                }) {

            /**
             * Method to manipulate request header
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
        return recipes;
    }

    public List<DisplayingRecipe> getRecipesByIngredientss(String ingredients) {


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath("findByIngredients")
                .appendQueryParameter("fillIngredients", "false")
                .appendQueryParameter("ingredients", ingredients)
                .appendQueryParameter("limitLicense", "false")
                .appendQueryParameter("number","20")
                .appendQueryParameter("ranking","1");

        String recipeByIngredientsURl = builder.build().toString();


        final List<DisplayingRecipe> recipes = new ArrayList<DisplayingRecipe>();

        JsonArrayRequest jsonObjReq = new JsonArrayRequest
                (
                        recipeByIngredientsURl,

                        new Response.Listener<JSONArray>() {

                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());

                                try {
                                    Log.d("Object-size", "islam");
                                    JSONArray obj = new JSONArray(response.toString());
                                    Log.d("Object-size", String.valueOf(obj.length()));
                                    for (int i = 0; i < obj.length(); i++) {
                                        int recipeId = obj.getJSONObject(i).getInt("id");
                                        String title = obj.getJSONObject(i).optString("title", "N/A");
                                      //  String instructions = obj.getJSONObject(i).optString("instructions", "N/A");
                                      //  int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes", 0);
                                     //   int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
                                        String imageUrl = obj.getJSONObject(i).optString("image", null);
                                        String likes = obj.getJSONObject(i).optString("aggregateLikes", "0");
                                        Log.d("IMAGE-LINK----", imageUrl);


                                        ;

                                        DisplayingRecipe recipe = new DisplayingRecipe(recipeId, title, imageUrl, likes);
                                        recipes.add(recipe);
                                        Log.d("REcipeBYINGREDIENTSIZE", String.valueOf(recipes.size()));
                                    }
                                    Log.d(TAG + "Object", obj.getJSONObject(0).getString("sourceUrl"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());


                    }
                }) {

            /**
             * Method to manipulate request header
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
        return recipes;
    }

    public Recipe getRecipeDetails(int idRecipe,final DataCallback callback) {
        final Recipe recipe = new Recipe();

        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath(String.valueOf(idRecipe))
                .appendPath("information")
                .appendQueryParameter("includeNutrition", "false")
        ;

        String  recipeByIngredientsURl = builder.build().toString();



                JsonObjectRequest jsonObjReq = new JsonObjectRequest
                        (Request.Method.GET,
                                recipeByIngredientsURl,
                                "",
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());

                                        Log.d("Volley-succes", String.valueOf(successCount));

                                            Log.d(TAG, response.toString());

                                        try {
                                            callback.onSuccess(response);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

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
                                VolleyLog.d(TAG + "details", "Error: " + error.getMessage());
                                errorCount++;


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


    public List<DisplayingRecipe> getRecipesBynutrietions(String ingredients) {
      //  https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByNutrients

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .appendPath("recipes")
                .appendPath("findByNutrients")
                .appendQueryParameter("maxcalories", "250")
                .appendQueryParameter("maxcarbs", "100")
                .appendQueryParameter("maxfat", "20")
                .appendQueryParameter("maxprotein", "100")

                .appendQueryParameter("random", "true");

        String recipeByNutritionsURl = builder.build().toString();

        Log.d("Nutrition-URI", recipeByNutritionsURl);
        final List<DisplayingRecipe> recipes = new ArrayList<DisplayingRecipe>();

        JsonArrayRequest jsonObjReq = new JsonArrayRequest
                (
                        recipeByNutritionsURl,

                        new Response.Listener<JSONArray>() {

                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());

                                try {
                                    Log.d("Object-size", "islam");
                                    JSONArray obj = new JSONArray(response.toString());
                                    Log.d("Object-size", String.valueOf(obj.length()));
                                    for (int i = 0; i < obj.length(); i++) {
                                        int recipeId = obj.getJSONObject(i).getInt("id");
                                        String title = obj.getJSONObject(i).optString("title", "N/A");
                                        //  String instructions = obj.getJSONObject(i).optString("instructions", "N/A");
                                        //  int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes", 0);
                                        //   int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
                                        String imageUrl = obj.getJSONObject(i).optString("image", null);
                                        String likes = obj.getJSONObject(i).optString("aggregateLikes", "0");
                                        Log.d("IMAGE-LINK----", imageUrl);


                                        ;

                                        DisplayingRecipe recipe = new DisplayingRecipe(recipeId, title, imageUrl, likes);
                                        recipes.add(recipe);
                                        Log.d("REcipeBYINGREDIENTSIZE", String.valueOf(recipes.size()));
                                    }
                                    Log.d(TAG + "Object", obj.getJSONObject(0).getString("sourceUrl"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Log.d("ERROR-Volley",error.toString() );


                    }
                }) {

            /**
             * Method to manipulate request header
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
        return recipes;
    }



}
