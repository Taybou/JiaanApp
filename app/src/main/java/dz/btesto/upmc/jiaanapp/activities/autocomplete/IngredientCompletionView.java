package dz.btesto.upmc.jiaanapp.activities.autocomplete;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

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
public class IngredientCompletionView extends TokenCompleteTextView<Ingredient> {
    public IngredientCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected View getViewForObject(Ingredient ingredients) {

        LayoutInflater l = LayoutInflater.from(getContext());
        TextView view = (TextView) l.inflate(R.layout.ingredient, (ViewGroup) getParent(), false);
        view.setText(ingredients.getName());


        return view;
    }

    @Override
    protected Ingredient defaultObject(String completionText) {
        Ingredient ingredients = new Ingredient(1, "i", "o", true);
        return ingredients;
    }
}
