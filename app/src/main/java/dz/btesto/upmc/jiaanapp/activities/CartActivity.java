package dz.btesto.upmc.jiaanapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Iterator;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.custom.CartCustomAdapter;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

public class CartActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String thumbnailURL = intent.getStringExtra("thumbnailURL");
        ingredients = (List<Ingredient>) intent.getSerializableExtra("cartList");

        Iterator<Ingredient> i = ingredients.iterator();
        while (i.hasNext()) {
            Ingredient s = i.next(); // must be called before you can call i.remove()
            if (s.isState()) {
                i.remove();
            }
        }

        setupRecyclerView();
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cartIngredientLv);
        CartCustomAdapter adapter = new CartCustomAdapter(ingredients);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
