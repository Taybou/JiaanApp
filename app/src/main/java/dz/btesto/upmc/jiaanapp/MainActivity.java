package dz.btesto.upmc.jiaanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




import dz.btesto.upmc.jiaanapp.services.ServicesAPI;


public class MainActivity extends AppCompatActivity {


    private TextView jsonRes ;
    private Button fetchBtn ;

    final ServicesAPI servicesAPI = new ServicesAPI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonRes = (TextView) findViewById(R.id.jsonTest);
        fetchBtn = (Button) findViewById(R.id.fetchBtn);

        final Intent intent = new Intent(MainActivity.this, IngredientAuto.class);
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });




    }


}
