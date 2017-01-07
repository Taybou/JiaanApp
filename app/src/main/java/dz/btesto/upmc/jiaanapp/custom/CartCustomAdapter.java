package dz.btesto.upmc.jiaanapp.custom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;

/**
 * Created by besto on 07/01/17.
 */

public class CartCustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<Ingredients> ingredientsArrayList ;



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

    public CartCustomAdapter(List<Ingredients> data) {

        this.ingredientsArrayList = data;
    }
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list, parent, false);



        CustomAdapter.MyViewHolder myViewHolder = new CustomAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder,final int position) {
        ImageView ingredientImageView = holder.ingredientImageView;
        TextView ingeredientTitle = holder.ingeredientTitle;
        final ImageView addImageview = holder.addImageview;

        Glide
                .with(ingredientImageView.getContext())
                .load(ingredientsArrayList.get(position).getImageUrl())
                .override(400,300)
                .centerCrop()
                .into((ImageView) ingredientImageView);

        // ingredientImageView.setText(dataSet.get(listPosition).getName());
        ingeredientTitle.setText(ingredientsArrayList.get(position).getName());
        if(ingredientsArrayList.get(position).isState()==true){
            addImageview.setImageResource(R.drawable.ic_action_add);
        }else{
            addImageview.setImageResource(R.drawable.ic_action_minus);
        }

//        addImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ingredientsArrayList.get(position).isState()){
//                    ingredientsArrayList.get(position).setState(false);
//                    addImageview.setImageResource(R.drawable.ic_action_minus);
//                }else{
//                    ingredientsArrayList.get(position).setState(true);
//                    addImageview.setImageResource(R.drawable.ic_action_add);
//                }
//            }
//        });



    }



    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }



}
