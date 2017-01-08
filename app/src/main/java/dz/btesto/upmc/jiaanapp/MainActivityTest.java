package dz.btesto.upmc.jiaanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import dz.btesto.upmc.jiaanapp.services.ServicesAPI;


public class MainActivityTest extends AppCompatActivity {


    private TextView jsonRes ;
    private Button fetchBtn ;
    private Button autoBtn;
    private Button nutritionsbtn;
    private ProgressBar spinner;

    final ServicesAPI servicesAPI = new ServicesAPI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        jsonRes = (TextView) findViewById(R.id.jsonTest);
        fetchBtn = (Button) findViewById(R.id.fetchBtn);
        autoBtn= (Button) findViewById(R.id.autoBtn);
        nutritionsbtn = (Button) findViewById(R.id.nutritions);



        final Intent intent = new Intent(MainActivityTest.this, IngredientAuto.class);

        final Intent intent2 = new Intent(MainActivityTest.this, RecipesDetails.class);

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                intent2.putExtra("recipeID",  582367);
                startActivity(intent2);


            }
        });

        autoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        nutritionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), servicesAPI.getRecipesBynutrietions("apple").toString(),
                        Toast.LENGTH_LONG).show();
            }
        });




    }


}
