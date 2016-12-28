package dz.btesto.upmc.jiaanapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.btesto.upmc.jiaanapp.custom.IngredientAdapter;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.volley.AppController;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

public class RecipesDetails extends AppCompatActivity {
    List<Ingredients> ingredientses;
    IngredientAdapter adapter;
    ImageView recipeImage;
    TextView cookingTimeTv;
    TextView instructionTv;
    ListView lv;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initView();
        final ServicesAPI servicesAPI = new ServicesAPI();
        Intent intent = getIntent();
        int idRecipe = intent.getIntExtra("recipeID", -1);
        Log.d("idRecipe", String.valueOf(idRecipe));
        useData(idRecipe);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void useData(int idRecipe) {

        final List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        final Recipe recipe = new Recipe();
        final ServicesAPI servicesAPI = new ServicesAPI();
        servicesAPI.getRecipeDetails(idRecipe, new DataCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                int recipeId = response.getInt("id");
                String title = response.optString("title", "N/A");
                String instructions = response.optString("instructions", "N/A");
                int preparationMinutes = response.optInt("preparationMinutes", 0);
                int cookingMinutes = response.optInt("readyInMinutes", 0);
                String imageUrl = response.optString("image", "http/null");
                Log.d("IMAGE-LINK----", imageUrl);
                for (int j = 0; j < response.getJSONArray("extendedIngredients").length(); j++) {

                    int ingredientId = response.getJSONArray("extendedIngredients").getJSONObject(j).getInt("id");
                    String inggredientImage = response.getJSONArray("extendedIngredients").getJSONObject(j).optString("image", "");
                    String name = response.getJSONArray("extendedIngredients").getJSONObject(j).optString("name", "N/A");
                    Log.d("INGREDIENT-NAME----", name);
                    Ingredients ingredients = new Ingredients(ingredientId, inggredientImage, name);
                    ingredientsList.add(ingredients);

                };
                recipe.setCookingMinutes(cookingMinutes);
                recipe.setImageUrl(imageUrl);
                recipe.setIngredientsList(ingredientsList);
                recipe.setTitle(title);
                recipe.setPreparationMinutes(preparationMinutes);
                recipe.setInstructions(instructions);
                Log.d("recipetest", recipe.getInstructions());
                cookingTimeTv.setText(String.valueOf(recipe.getCookingMinutes() + "min"));
                instructionTv.setText(recipe.getInstructions());
                Glide.with(getApplicationContext()).load(recipe.getImageUrl()).into(recipeImage);
                ingredientses = new ArrayList<Ingredients>();
                for (int i = 0; i < recipe.getIngredientsList().size(); i++) {
                    ingredientses.add(recipe.getIngredientsList().get(i));
                    Log.d("NameIngredient", recipe.getIngredientsList().get(i).getName());
                }
                Log.d("Name", recipe.getIngredientsList().get(1).getName());
                adapter = new IngredientAdapter(getApplicationContext(), R.layout.ingredients_list, ingredientses);
                lv.setAdapter(adapter);
                toolbar.setTitle(recipe.getTitle());
                setSupportActionBar(toolbar);


            }
        });

    }

    private void initView() {
        cookingTimeTv = (TextView) findViewById(R.id.CookingTimeTv);
        instructionTv = (TextView) findViewById(R.id.InstructionTv);
        recipeImage = (ImageView) findViewById(R.id.recipeImage);
        lv = (ListView) findViewById(R.id.ingredientLv);

    }

}
