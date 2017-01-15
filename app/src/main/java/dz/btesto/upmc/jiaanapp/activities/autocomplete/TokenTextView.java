package dz.btesto.upmc.jiaanapp.activities.autocomplete;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import dz.btesto.upmc.jiaanapp.R;

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
public class TokenTextView extends TextView {

    public TokenTextView(Context context) {
        super(context);
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.close_x : 0, 0);
    }
}
