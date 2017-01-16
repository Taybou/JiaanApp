package dz.btesto.upmc.jiaanapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

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

import java.util.HashMap;
import java.util.Map;

import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.utils.volley.AppController;
import dz.btesto.upmc.jiaanapp.utils.volley.DataCallback;

import static android.content.Context.MODE_PRIVATE;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_CAL;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_FAT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MAX_PROT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_CAL;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_FAT;
import static dz.btesto.upmc.jiaanapp.activities.SettingsActivity.PREFS_MIN_PROT;

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
public class ServicesAPI {

    private static final String TAG = "Json-Respons";
    private static final String randomUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=20";
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
                .appendQueryParameter("maxfat", String.valueOf(sharedPreferences.getInt(PREFS_MAX_FAT, 20)))
                .appendQueryParameter("maxprotein", String.valueOf(sharedPreferences.getInt(PREFS_MAX_PROT, 100)))
                .appendQueryParameter("mincalories", String.valueOf(sharedPreferences.getInt(PREFS_MIN_CAL, 0)))
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
                                try {
                                    callback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                successCount++;
                            }

                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
