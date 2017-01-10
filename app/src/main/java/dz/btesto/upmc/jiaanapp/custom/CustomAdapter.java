package dz.btesto.upmc.jiaanapp.custom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

/**
 * Created by besto on 06/01/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final HashMap<Integer, Ingredient> ingredients;
    private List<Ingredient> ingredientsArrayList;
    private SendCartData data;


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView ingredientImageView;
        TextView ingeredientTitle;
        ImageView addImageview;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.ingredientImageView = (ImageView) itemView.findViewById(R.id.listview_image);
            this.ingeredientTitle = (TextView) itemView.findViewById(R.id.ingredientName);
            this.addImageview = (ImageView) itemView.findViewById(R.id.idIngredientIv);
        }
    }

    public CustomAdapter(List<Ingredient> data) {
        ingredients = new HashMap<>();
        this.ingredientsArrayList = data;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, final int position) {
        final Ingredient ingredient = ingredientsArrayList.get(position);

        Glide
                .with(holder.ingredientImageView.getContext())
                .load(ingredient.getImageUrl())
                .override(400, 300)
                .centerCrop()
                .into(holder.ingredientImageView);

        holder.ingeredientTitle.setText(ingredient.getName());
        if (ingredient.isState()) holder.addImageview.setImageResource(R.drawable.ic_action_add);
        else holder.addImageview.setImageResource(R.drawable.ic_action_minus);

        holder.addImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ingredient.isState()) {
                    ingredient.setState(false);
                    ingredients.put(position, ingredient);
                    holder.addImageview.setImageResource(R.drawable.ic_action_minus);
                } else {
                    ingredient.setState(true);
                    ingredients.remove(position);
                    holder.addImageview.setImageResource(R.drawable.ic_action_add);
                }
            }
        });

        data.getCartData(ingredients);
    }


    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (recyclerView.getContext() instanceof SendCartData) {
            data = (SendCartData) recyclerView.getContext();
        } else {
            throw new RuntimeException(recyclerView.getContext().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        data = null;
    }

    public interface SendCartData {
        void getCartData(HashMap ingredientses);
    }
}
