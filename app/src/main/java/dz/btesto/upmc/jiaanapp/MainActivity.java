package dz.btesto.upmc.jiaanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.volley.AppController;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Json-Respons";
    private static final String urlJsonObj= "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random";
    private String jsonResponse;
    private TextView jsonRes ;
    private Button fetchBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonRes = (TextView) findViewById(R.id.jsonTest);
        fetchBtn = (Button) findViewById(R.id.fetchBtn);

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonObjectRequest();
            }
        });
    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {



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
                             int recipeId = obj.getJSONObject(0).getInt("id") ;
                             String title = obj.getJSONObject(0).getString("title");
                             String instructions = obj.getJSONObject(0).getString("instructions");
                             int preparationMinutes =  obj.getJSONObject(0).getInt("preparationMinutes") ;
                             int cookingMinutes =  obj.getJSONObject(0).getInt("cookingMinutes") ;
                             String imageUrl = obj.getJSONObject(0).getString("image");;

                            Recipe recipe = new Recipe(recipeId,title,instructions,preparationMinutes,cookingMinutes,imageUrl,null);
                            jsonRes.setText(recipe.getRecipeId()+"---"+recipe.getTitle()+"---"+ recipe.getInstructions()
                            +"---"+recipe.getPreparationMinutes()+"---"+recipe.getCookingMinutes()+"---"+
                            recipe.getImageUrl());
                            Log.d(TAG+"Object", obj.getJSONObject(0).getString("sourceUrl"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

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
    }
}
