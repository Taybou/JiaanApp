package dz.btesto.upmc.jiaanapp.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;

/**
 * Created by besto on 13/12/16.
 */

public class IngredientCompletionView extends TokenCompleteTextView<Ingredients> {
    public IngredientCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected View getViewForObject(Ingredients ingredients) {

        LayoutInflater l =  LayoutInflater.from(getContext());
        TextView view = (TextView) l.inflate(R.layout.ingredient, (ViewGroup) getParent(), false);
        view.setText(ingredients.getName());


        return view;
    }

    @Override
    protected Ingredients defaultObject(String completionText) {
        Ingredients ingredients = new Ingredients(1,"i","o");
        return ingredients;
    }
}
