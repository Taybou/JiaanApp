package dz.btesto.upmc.jiaanapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.custom.IngredientCompletionView;
import dz.btesto.upmc.jiaanapp.entity.Ingredient;

import static dz.btesto.upmc.jiaanapp.fragments.homeFragment.TabFragment.viewPager;

public class IngredientAuto extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Ingredient> {

    IngredientCompletionView completionView;
    List<Ingredient> ingredients;
    ArrayAdapter<Ingredient> adapter;
    public static String searchIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_auto);

        try {
            ingredients = getIngredient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new FilteredArrayAdapter<Ingredient>(this, R.layout.ingredient_item, ingredients) {
            @Override
            protected boolean keepObject(Ingredient ingredients, String mask) {
                mask = mask.toLowerCase();
                return ingredients.getName().toLowerCase().startsWith(mask) || ingredients.getImageUrl().toLowerCase().startsWith(mask);
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.ingredient_item, parent, false);
                }

                Ingredient p = getItem(position);
                ((TextView) convertView.findViewById(R.id.name)).setText(p.getName());
                return convertView;
            }
        };
        completionView = (IngredientCompletionView) findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


        ImageView search = (ImageView) findViewById(R.id.idSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIngredients = updateTokenConfirmation();
                viewPager.setCurrentItem(2);
                finish();
            }
        });
    }

    private String updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("");
        for (Ingredient token : completionView.getObjects()) {
            sb.append(token.getName());
            sb.append(",");


        }
        Log.d("TokenTExt", "" + sb.toString());

        return sb.toString();


    }

    @Override
    public void onTokenAdded(Ingredient token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Ingredient token) {
        updateTokenConfirmation();
    }


    public List<Ingredient> getIngredient() throws IOException, JSONException {
        List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
        InputStream is = getResources().openRawResource(R.raw.ingredient);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        JSONArray mainObject = new JSONArray(jsonString);
        for (int i = 0; i < mainObject.length(); i++) {
            String name = mainObject.getJSONObject(i).getString("name");
            String image = mainObject.getJSONObject(i).getString("image");
            Ingredient ingredients = new Ingredient(i, image, name, true);
            ingredientsList.add(ingredients);
        }

        return ingredientsList;
    }
}
