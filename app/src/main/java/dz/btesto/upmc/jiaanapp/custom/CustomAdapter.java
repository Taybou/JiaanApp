package dz.btesto.upmc.jiaanapp.custom;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by besto on 06/01/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<Ingredients> ingredientsArrayList ;
    private SendCartData data ;


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

    public CustomAdapter(List<Ingredients> data) {
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
        for(int i=0;i<ingredientsArrayList.size();i++){
            Log.d("State-Ingr",String.valueOf(ingredientsArrayList.get(i).getName()+" -- "+ ingredientsArrayList.get(i).isState()) );
        }

        if(ingredientsArrayList.get(position).isState()){
            addImageview.setImageResource(R.drawable.ic_action_add);
        }else{
            addImageview.setImageResource(R.drawable.ic_action_minus);
        }

        addImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientsArrayList.get(position).isState()){
                    ingredientsArrayList.get(position).setState(false);
                    addImageview.setImageResource(R.drawable.ic_action_minus);
                }else{
                    ingredientsArrayList.get(position).setState(true);
                    addImageview.setImageResource(R.drawable.ic_action_add);
                }
            }
        });



        data.getCartData(ingredientsArrayList);

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
        void getCartData(List<Ingredients> ingredientses);
    }
}
