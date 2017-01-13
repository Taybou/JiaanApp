package dz.btesto.upmc.jiaanapp.custom;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

/**
 * Created by besto on 07/01/17.
 */

public class CartCustomAdapter extends RecyclerView.Adapter<CartCustomAdapter.MyViewHolder> {

    private final Context context;
    private List<Ingredient> ingredientsArrayList;


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

    public CartCustomAdapter(List<Ingredient> data, Context context) {

        this.ingredientsArrayList = data;
        this.context = context;
    }

    @Override
    public CartCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list, parent, false);


        CartCustomAdapter.MyViewHolder myViewHolder = new CartCustomAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartCustomAdapter.MyViewHolder holder, final int position) {
        final Ingredient ingredient = ingredientsArrayList.get(position);

        // ImageView ingredientImageView = holder.ingredientImageView;
        //  TextView ingeredientTitle = holder.ingeredientTitle;
        //final ImageView addImageview = holder.addImageview;

        Glide
                .with(context)
                .load(ingredient.getImageUrl())
                .override(400, 300)
                .centerCrop()
                .into(holder.ingredientImageView);

        // ingredientImageView.setText(dataSet.get(listPosition).getName());
        holder.ingeredientTitle.setText(ingredient.getName());
        if (ingredient.isState() == true) {
            holder.addImageview.setImageResource(R.drawable.ic_action_minus);
        } else {
            holder.addImageview.setImageResource(R.drawable.ic_action_add);
        }

        holder.addImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingredient.isState()) {
                    ingredient.setState(false);
                    holder.ingeredientTitle.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                    holder.addImageview.setImageResource(R.drawable.ic_action_add);
                } else {
                    ingredient.setState(true);
                    holder.ingeredientTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.addImageview.setImageResource(R.drawable.ic_action_minus);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }


}
