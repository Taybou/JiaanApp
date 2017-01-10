package dz.btesto.upmc.jiaanapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.custom.CustomAdapter;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.utils.Parser;
import dz.btesto.upmc.jiaanapp.volley.DataCallback;

public class RecipesDetails extends AppCompatActivity implements CustomAdapter.SendCartData {
    List<Ingredient> ingredientses;
    HashMap<Integer, Ingredient> ingredientsCart;
    List<Ingredient> arrayIngredientsCart;
    ImageView recipeImage;
    TextView cookingTimeTv;
    TextView instructionTv;
    Toolbar toolbar;

    Intent cartIntent = null;


    private static RecyclerView.Adapter adapterR;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;


    public static final String INGREDIENTS_COLUMN = "ingredients";
    public static final String RECIPES_COLUMN = "recipes";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        myRef = database.getReference();
        ingredientsCart = new HashMap<>();
        arrayIngredientsCart = new ArrayList<>();
        initView();
        // final ServicesAPI servicesAPI = new ServicesAPI();
        Intent intent = getIntent();
        int idRecipe = intent.getIntExtra("recipeID", -1);
        // Log.d("idRecipe", String.valueOf(idRecipe));
        //cartIntent = new Intent(RecipesDetails.this, CartActivity.class);
        useData(idRecipe);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Integer key : ingredientsCart.keySet()) {
                    if (!ingredientsCart.get(key).isState())
                        arrayIngredientsCart.add(ingredientsCart.get(key));
                }

                if (arrayIngredientsCart.size() > 0) {
                    dialogConfirmation();
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry...., ...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    Recipe recipe;

    public void useData(int idRecipe) {


        final ServicesAPI servicesAPI = new ServicesAPI();
        servicesAPI.getRecipeDetails(idRecipe, new DataCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                recipe = Parser.parsingRecipeDetails(response);
                Log.d("recipetest", recipe.getInstructions());
                cookingTimeTv.setText(Parser.formatTime(recipe.getCookingMinutes()));
                instructionTv.setText(recipe.getInstructions());
                Glide.with(getApplicationContext()).load(recipe.getImageUrl()).into(recipeImage);

                ingredientses = new ArrayList<Ingredient>();
                // for (int i = 0; i < recipe.getIngredientsList().size(); i++) {
                ingredientses = recipe.getIngredientsList();// .add(recipe.getIngredientsList().get(i));
                //Log.d("NameIngredient", recipe.getIngredientsList().get(i).getName());
                //   }
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

            @Override
            public void onSuccess(JSONArray result) throws JSONException {

            }
        });

    }

    private void initView() {
        cookingTimeTv = (TextView) findViewById(R.id.CookingTimeTv);
        instructionTv = (TextView) findViewById(R.id.InstructionTv);
        recipeImage = (ImageView) findViewById(R.id.recipeImage);
    }

    private void dialogConfirmation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Ingredient");
        alertDialogBuilder.setMessage("You want to add ingredients to shopping cart ?");
        alertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myRef.child(INGREDIENTS_COLUMN)
                        .child("userID")
                        .child(String.valueOf(recipe.getRecipeId()))
                        .setValue(arrayIngredientsCart);

                myRef.child(RECIPES_COLUMN)
                        .child("userID")
                        .child(String.valueOf(recipe.getRecipeId()))
                        .setValue(recipe);

                Toast.makeText(getApplicationContext(), "Great, ...", Toast.LENGTH_LONG).show();
                arrayIngredientsCart = new ArrayList<>();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void getCartData(HashMap ingredientsCart) {
        this.ingredientsCart = ingredientsCart;
    }
}
