package dz.btesto.upmc.jiaanapp.activities.recipesDetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import dz.btesto.upmc.jiaanapp.entity.Ingredient;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.services.ServicesAPI;
import dz.btesto.upmc.jiaanapp.utils.Parser;
import dz.btesto.upmc.jiaanapp.utils.volley.DataCallback;

import static dz.btesto.upmc.jiaanapp.activities.MainActivity.MY_PREFS_NAME;

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
public class RecipesDetails extends AppCompatActivity implements CustomAdapter.SendCartData {
    List<Ingredient> ingredientses;
    HashMap<Integer, Ingredient> ingredientsCart;
    List<Ingredient> arrayIngredientsCart;
    ImageView recipeImage;
    TextView cookingTimeTv;
    TextView instructionTv;
    Toolbar toolbar;

    private static RecyclerView.Adapter adapterR;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;


    public static final String INGREDIENTS_COLUMN = "ingredients";
    public static final String RECIPES_COLUMN = "recipes";
    public static final String FAVORITE_RECIPES_COLUMN = "favorite_recipes";

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
        Intent intent = getIntent();
        int idRecipe = intent.getIntExtra("recipeID", -1);
        boolean isFavorite = intent.getBooleanExtra("isFavorite", false);

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
                    Toast.makeText(getApplicationContext(), "You should added at least one ingredient", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fabFavorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        if (!isFavorite) {
            fabFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogFavoriteConfirmation();
                }
            });
        } else {
            fabFavorite.setImageResource(R.drawable.ic_delete);
            fabFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogFavoriteDelete();
                }
            });
        }
    }

    Recipe recipe;

    public void useData(int idRecipe) {

        final ServicesAPI servicesAPI = new ServicesAPI();
        servicesAPI.getRecipeDetails(idRecipe, new DataCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                recipe = Parser.parsingRecipeDetails(response);
                cookingTimeTv.setText(Parser.formatTime(recipe.getCookingMinutes()));
                instructionTv.setText(recipe.getInstructions());
                Glide.with(getApplicationContext()).load(recipe.getImageUrl()).into(recipeImage);

                ingredientses = new ArrayList<>();
                ingredientses = recipe.getIngredientsList();

                recyclerView = (RecyclerView) findViewById(R.id.ingredientLv);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                adapterR = new CustomAdapter(ingredientses);
                recyclerView.setAdapter(adapterR);
                toolbar.setTitle(recipe.getTitle());
                setSupportActionBar(toolbar);

                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String userID = prefs.getString("userID", null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Ingredient");
        alertDialogBuilder.setMessage("You want to add ingredients to shopping cart ?");
        alertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myRef.child(INGREDIENTS_COLUMN)
                        .child(userID)
                        .child(String.valueOf(recipe.getRecipeId()))
                        .setValue(arrayIngredientsCart);

                recipe.setIngredientsList(arrayIngredientsCart);

                myRef.child(RECIPES_COLUMN)
                        .child(userID)
                        .child(String.valueOf(recipe.getRecipeId()))
                        .setValue(recipe);

                Toast.makeText(getApplicationContext(), "Added with success", Toast.LENGTH_LONG).show();
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

    private void dialogFavoriteConfirmation() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String userID = prefs.getString("userID", null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Recipe");
        alertDialogBuilder.setMessage("You want to add this recipe to favorite recipes ?");
        alertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myRef.child(FAVORITE_RECIPES_COLUMN)
                        .child(userID)
                        .child(String.valueOf(recipe.getRecipeId()))
                        .setValue(recipe);

                Toast.makeText(getApplicationContext(), "Added with success", Toast.LENGTH_LONG).show();
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

    private void dialogFavoriteDelete() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String userID = prefs.getString("userID", null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Recipe");
        alertDialogBuilder.setMessage("You want to delete this recipe from favorite recipes ?");
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myRef.child(FAVORITE_RECIPES_COLUMN)
                        .child(userID)
                        .child(String.valueOf(recipe.getRecipeId()))
                        .removeValue();

                Toast.makeText(getApplicationContext(), "deleted with success", Toast.LENGTH_LONG).show();
                finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
