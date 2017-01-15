package dz.btesto.upmc.jiaanapp.activities.shoppingCart;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

import static dz.btesto.upmc.jiaanapp.activities.MainActivity.MY_PREFS_NAME;
import static dz.btesto.upmc.jiaanapp.activities.recipesDetails.RecipesDetails.INGREDIENTS_COLUMN;
import static dz.btesto.upmc.jiaanapp.activities.recipesDetails.RecipesDetails.RECIPES_COLUMN;

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
public class CartActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRef = database.getReference();

        Intent intent = getIntent();
        final String idRecipe = intent.getStringExtra("idRecipe");
        String title = intent.getStringExtra("title");
        String thumbnailURL = intent.getStringExtra("thumbnailURL");
        ingredients = (List<Ingredient>) intent.getSerializableExtra("cartList");

        ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
        Glide.with(getApplicationContext()).load(thumbnailURL).into(recipeImage);

        Iterator<Ingredient> i = ingredients.iterator();
        while (i.hasNext()) {
            Ingredient s = i.next();
            if (s.isState()) {
                i.remove();
            }
        }

        setupRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConfirmation(idRecipe);
            }
        });

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cartIngredientLv);
        CartCustomAdapter adapter = new CartCustomAdapter(ingredients, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void dialogConfirmation(final String idRecipe) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String userID = prefs.getString("userID", null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Ingredient");
        alertDialogBuilder.setMessage("You want to delete this shopping cart ?");
        alertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                myRef.child(INGREDIENTS_COLUMN)
                        .child(userID)
                        .child(idRecipe)
                        .removeValue();

                myRef.child(RECIPES_COLUMN)
                        .child(userID)
                        .child(idRecipe)
                        .removeValue();

                Toast.makeText(getApplicationContext(), "Great, ...", Toast.LENGTH_LONG).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
