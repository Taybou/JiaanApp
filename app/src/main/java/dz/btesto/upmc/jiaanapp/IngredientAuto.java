package dz.btesto.upmc.jiaanapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
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

import dz.btesto.upmc.jiaanapp.custom.IngredientCompletionView;
import dz.btesto.upmc.jiaanapp.entity.Ingredients;

import static dz.btesto.upmc.jiaanapp.fragments.TabFragment.viewPager;

public class IngredientAuto extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Ingredients> {

    public static String searchIngredients;

    IngredientCompletionView completionView;
    List<Ingredients> ingredients;
    ArrayAdapter<Ingredients> adapter;
    ImageView search;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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


        adapter = new FilteredArrayAdapter<Ingredients>(this, R.layout.ingredient_item, ingredients) {
            @Override
            protected boolean keepObject(Ingredients ingredients, String mask) {
                mask = mask.toLowerCase();
                return ingredients.getName().toLowerCase().startsWith(mask) || ingredients.getImageUrl().toLowerCase().startsWith(mask);
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.ingredient_item, parent, false);
                }

                Ingredients p = getItem(position);
                ((TextView) convertView.findViewById(R.id.name)).setText(p.getName());
                //  ((TextView) convertView.findViewById(R.id.linkimage)).setText(p.getImageUrl());

//                Glide
//                        .with(getContext())
//                        .load("https://spoonacular.com/cdn/ingredients_100x100/"+p.getImageUrl())
//
//                        .into(inggredientImage);


                return convertView;
            }
        };

        completionView = (IngredientCompletionView) findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        //----------

        search = (ImageView) findViewById(R.id.idSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIngredients = updateTokenConfirmation();
                Log.i("DDDD", searchIngredients);
                viewPager.setCurrentItem(2);

                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String updateTokenConfirmation() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredients token : completionView.getObjects()) {
            stringBuilder.append(token.getName());
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    @Override
    public void onTokenAdded(Ingredients token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Ingredients token) {
        updateTokenConfirmation();
    }


    public List<Ingredients> getIngredient() throws IOException, JSONException {
        List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
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
            Ingredients ingredients = new Ingredients(i, image, name, true);
            ingredientsList.add(ingredients);
        }

        return ingredientsList;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("IngredientAuto Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
