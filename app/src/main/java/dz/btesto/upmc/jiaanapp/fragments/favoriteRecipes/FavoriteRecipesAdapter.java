package dz.btesto.upmc.jiaanapp.fragments.favoriteRecipes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.activities.recipesDetails.RecipesDetails;
import dz.btesto.upmc.jiaanapp.entity.Recipe;
import dz.btesto.upmc.jiaanapp.utils.FirebaseRecyclerAdapter;

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

public class FavoriteRecipesAdapter extends FirebaseRecyclerAdapter<FavoriteRecipesAdapter.MyViewHolder, Recipe> {

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, numberOfIngredients, idRecipe;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            idRecipe = (TextView) view.findViewById(R.id.idRecipe);
            numberOfIngredients = (TextView) view.findViewById(R.id.ingredients_number);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public FavoriteRecipesAdapter(Context mContext, Query query, Class<Recipe> itemClass, @Nullable ArrayList<Recipe> items,
                                  @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_shopping_cart, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Recipe recipe = getItem(position);
        holder.title.setText(recipe.getTitle());
        holder.idRecipe.setText(String.valueOf(recipe.getRecipeId()));
        holder.numberOfIngredients.setText("Ingredients : " + recipe.getIngredientsList().size());

        Glide.with(mContext)
                .load(recipe.getImageUrl())
                //.override(400, 300)
                .centerCrop()
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipesDetails.class);
                intent.putExtra("isFavorite", true);
                intent.putExtra("recipeID", recipe.getRecipeId());
//                intent.putExtra("title", recipe.getTitle());
//                intent.putExtra("thumbnailURL", recipe.getImageUrl());
//                intent.putExtra("cartList", (Serializable) recipe.getIngredientsList());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    protected void itemAdded(Recipe item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Recipe oldItem, Recipe newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Recipe item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Recipe item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }

}