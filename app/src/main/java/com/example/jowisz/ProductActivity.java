package com.example.jowisz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jowisz.Model.Product;

public class ProductActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView price;
    private TextView amount;
    private TextView avaliability;
    private Button addToBusket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        final Product product = (Product) intent.getSerializableExtra("product");

        name = findViewById(R.id.nameProduct);
        description = findViewById(R.id.descriptionProduct);
        price = findViewById(R.id.priceProduct);
        amount = findViewById(R.id.amountProduct);
        avaliability = findViewById(R.id.avaliabilityProduct);
        addToBusket = findViewById(R.id.addProduct);

        name.setText(product.getName());
        description.setText(product.getDescription());
        //price.setText((int)product.getPriceUnit());
        amount.setText("Ilość towaru: " + product.getAvaibility());

        if(product.getAvaibility() > 0){
            avaliability.setText("Dostępny");
            avaliability.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            avaliability.setText("Niedostępny");
            avaliability.setTextColor(getResources().getColor(R.color.red));
        }


        addToBusket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product.getAvaibility()>0){

                    // DODANIE DO KOSZYKA

                    //ODEJMOWANIE ILOSCI

                } else {
                    Toast.makeText(getApplicationContext(), "Towaru nie ma na magazynie", Toast.LENGTH_LONG).show();
                    avaliability.setText("Niedostępny");
                    avaliability.setTextColor(getResources().getColor(R.color.red));
                }

            }
        });
    }


}
