package dz.btesto.upmc.jiaanapp.services;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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

/**
 * Created by besto on 15/12/16.
 */

public class  ServicesAPI {

    private static final String TAG = "Json-Respons";
    private static final String urlJsonObj= "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10";

    private String jsonResponse;

    /**
     * Method to make json object request where json response starts wtih {
     * */
    public List<Recipe> makeJsonObjectRequest() {
        final List<Recipe>  recipes= new ArrayList<Recipe>();
        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET,
                        urlJsonObj,
                        "",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());

                                try {
                                    JSONArray obj = response.getJSONArray("recipes");
                                    for(int i=0;i<obj.length();i++) {
                                        int recipeId = obj.getJSONObject(i).getInt("id");
                                        String title = obj.getJSONObject(i).optString("title","N/A");
                                        String instructions = obj.getJSONObject(i).optString("instructions","N/A");
                                        int preparationMinutes = obj.getJSONObject(i).optInt("preparationMinutes",0);
                                        int cookingMinutes = obj.getJSONObject(i).optInt("cookingMinutes", 0);
                                        String imageUrl = obj.getJSONObject(i).optString("image", null);
                                        Log.d("IMAGE-LINK----", imageUrl);
                                        for (int j=0;j<obj.getJSONObject(i).getJSONArray("extendedIngredients").length();j++){

                                            int ingredientId = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).getInt("id");
                                            String inggredientImage = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("image", "");
                                            String name = obj.getJSONObject(i).getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A");
                                            Log.d("INGREDIENT-NAME----", name);
                                            Ingredients ingredients = new Ingredients(ingredientId,inggredientImage,name);
                                            ingredientsList.add(ingredients);

                                        }

                                        ;

                                        Recipe recipe = new Recipe(recipeId, title, instructions, preparationMinutes, cookingMinutes, imageUrl, ingredientsList);
                                        recipes.add(recipe);
                                        Log.d("REcipe-SIZE----", String.valueOf(recipes.size()));
                                    }
                                    Log.d(TAG+"Object", obj.getJSONObject(0).getString("sourceUrl"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());


                    }
                }){

            /**
             * Method to manipulate request header
             * @return
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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
