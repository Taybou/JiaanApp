package dz.btesto.upmc.jiaanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import dz.btesto.upmc.jiaanapp.custom.CartCustomAdapter;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;

public class CartActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapterR;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final List<Ingredients> ingredientses = (List<Ingredients>) intent.getSerializableExtra("cartList");
//        for(int i=0;i<ingredientses.size();i++){
//            Log.d("ISLAMBESTO-SIZE", String.valueOf(ingredientses.size()));
//            Log.d("ISLAMBESTO", String.valueOf(ingredientses.get(i).getName()+" -- "+ ingredientses.get(i).isState()));
//            if(ingredientses.get(i).isState()==true){
//                Log.d("ISLAMBESTO-AFTER", String.valueOf(ingredientses.get(i).getName()+" -- "+ ingredientses.get(i).isState()));
//
//                ingredientses.remove(i);
//            }
//        }

        Iterator<Ingredients> i = ingredientses.iterator();
        while (i.hasNext()) {
            Ingredients s = i.next(); // must be called before you can call i.remove()
            if (s.isState()) {
                i.remove();
            }
        }

        Log.d("AFTER-ISLAMBESTO-SIZE", String.valueOf(ingredientses.size()));

        recyclerView = (RecyclerView) findViewById(R.id.cartIngredientLv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterR = new CartCustomAdapter(ingredientses);
        recyclerView.setAdapter(adapterR);

    }
}
