package dz.btesto.upmc.jiaanapp.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;

/**
 * Created by besto on 17/12/16.
 */

public class IngredientAdapter extends ArrayAdapter<Ingredients> {

    private final int resource ;
    List<Ingredients> objects;
    private Context context;
    boolean state;

    public IngredientAdapter(Context context, int resource, List<Ingredients> objects) {
        super(context, resource, objects);
        this.resource = resource ;
        this.context = context;
        this.objects = objects;
        this.state = true ;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);

        }

        Ingredients ingredients = getItem(position);


        TextView tv = (TextView)convertView.findViewById(R.id.ingredientName);
        tv.setText(ingredients.getName());

//
        ImageView imgv = (ImageView) convertView.findViewById(R.id.listview_image);

        Glide
                .with(context)
                .load(objects.get(position).getImageUrl())
                .override(400,300)
                .centerCrop()
                .into((ImageView) imgv);

       final ImageView ingredientImage = (ImageView) convertView.findViewById(R.id.idIngredientIv);
        ingredientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state) {
                    ingredientImage.setImageResource(R.drawable.ic_action_minus);
                    //TODO
                    state = false;
                }else{
                    ingredientImage.setImageResource(R.drawable.ic_action_add);
                    state = true ;
                }
            }
        });

        return convertView;
    }
}
