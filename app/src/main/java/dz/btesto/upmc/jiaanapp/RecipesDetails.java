package dz.btesto.upmc.jiaanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.custom.CustomAdapter;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.utils.Parssers;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

public class RecipesDetails extends AppCompatActivity implements CustomAdapter.SendCartData {
    List<Ingredients> ingredientses;
    ImageView recipeImage;
    TextView cookingTimeTv;
    TextView instructionTv;
    Toolbar toolbar;

     Intent cartIntent= null ;


    private static RecyclerView.Adapter adapterR;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

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
        cartIntent = new Intent(RecipesDetails.this, CartActivity.class);
        useData(idRecipe);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(cartIntent);
            }
        });
    }

    public void useData(int idRecipe) {


        final ServicesAPI servicesAPI = new ServicesAPI();
        servicesAPI.getRecipeDetails(idRecipe, new DataCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                final Recipe recipe = Parssers.parssingRecipeDetails(response);
                Log.d("recipetest", recipe.getInstructions());
                cookingTimeTv.setText(Parssers.formatTime(recipe.getCookingMinutes()));
                instructionTv.setText(recipe.getInstructions());
                Glide.with(getApplicationContext()).load(recipe.getImageUrl()).into(recipeImage);
                ingredientses = new ArrayList<Ingredients>();
                for (int i = 0; i < recipe.getIngredientsList().size(); i++) {
                    ingredientses.add(recipe.getIngredientsList().get(i));
                    Log.d("NameIngredient", recipe.getIngredientsList().get(i).getName());
                }
                Log.d("Name", recipe.getIngredientsList().get(1).getName());
                recyclerView = (RecyclerView) findViewById(R.id.ingredientLv);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapterR = new CustomAdapter(ingredientses);
                recyclerView.setAdapter(adapterR);
                toolbar.setTitle(recipe.getTitle());
                setSupportActionBar(toolbar);

            }
        });

    }

    private void initView() {
        cookingTimeTv = (TextView) findViewById(R.id.CookingTimeTv);
        instructionTv = (TextView) findViewById(R.id.InstructionTv);
        recipeImage = (ImageView) findViewById(R.id.recipeImage);
    }

    @Override
    public void getCartData(List<Ingredients> ingredientses) {
        cartIntent.putExtra("cartList", (Serializable) ingredientses);
    }
}
