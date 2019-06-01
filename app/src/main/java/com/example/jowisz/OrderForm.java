package com.example.jowisz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class OrderForm extends AppCompatActivity {

    private EditText orderSurname;
    private EditText orderName;
    private EditText orderTel;
    private EditText orderAddress;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        orderSurname = findViewById(R.id.orderSurname);
        orderAddress = findViewById(R.id.orderAdres);
        orderButton = findViewById(R.id.orderButton);
        orderName = findViewById(R.id.orderName);
        orderTel = findViewById(R.id.orderTel);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrder();
            }
        });


    }

    public void setOrder(){
        String surnameText = orderSurname.getText().toString().trim();
        String nameText = orderName.getText().toString().trim();
        String addressText = orderAddress.getText().toString().trim();
        String telText = orderTel.getText().toString().trim();

        int errors = 0;

        if (TextUtils.isEmpty(surnameText)) {
            orderSurname.setError("Proszę wprowadzić nazwisko");
            orderSurname.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[a-zA-Z]+", nameText)) {
            orderSurname.setError("Nazwisko musi zawierać tylko litery");
            orderSurname.requestFocus();
            errors += 1;
        }

        if (TextUtils.isEmpty(nameText)) {
            orderName.setError("Proszę wprowadzić imię");
            orderName.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[a-zA-Z]+", nameText)) {
            orderName.setError("Imię musi zawierać tylko litery");
            orderName.requestFocus();
            errors += 1;
        }

        if (TextUtils.isEmpty(telText)) {
            orderTel.setError("Proszę wprowadzić nr. telefonu");
            orderTel.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[0-9]+", nameText)) {
            orderTel.setError("Numer telefonu musi zawierać tylko cyfry bez znaków specjalnych");
            orderTel.requestFocus();
            errors += 1;
        }

        if (errors == 0) {
            // SET TO DATABASE
        //EXIT
        } else {
            return;
        }
    }
}



