package com.example.jowisz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jowisz.Data.Api;
import com.example.jowisz.Data.RequestHandler;
import com.example.jowisz.Model.Basket;
import com.example.jowisz.Model.Category;
import com.example.jowisz.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class OrderForm extends AppCompatActivity {

    private EditText orderSurname;
    private EditText orderName;
    private EditText orderTel;
    private EditText orderAddress;
    private Button orderButton;

    private int orderID = 0;

    Basket basket;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        orderSurname = findViewById(R.id.orderSurname);
        orderAddress = findViewById(R.id.orderAdres);
        orderButton = findViewById(R.id.orderButton);
        orderName = findViewById(R.id.orderName);
        orderTel = findViewById(R.id.orderTel);

        readLastId();
        basket = (Basket) getIntent().getSerializableExtra("basket");

        System.out.println("Rozmiar koszyka w OrderForm: "+basket.size());

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
            orderTel.setError("Proszę wprowadzić nr telefonu");
            orderTel.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[0-9]+", telText)) {
            orderTel.setError("Numer telefonu musi zawierać tylko cyfry bez znaków specjalnych");
            orderTel.requestFocus();
            errors += 1;
        }

        if (errors == 0) {
            HashMap<String, String> params = new HashMap<>();
            params.put("Nazw", surnameText);
            params.put("Imie", nameText);
            params.put("Adres", addressText);
            params.put("Tel", telText);
            Date date = new Date();
            String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
            params.put("DataZam", modifiedDate);
            params.put("CzyOplacono", "NIE");
            params.put("IdPlat", "1");
            OrderForm.PerformNetworkRequest request = new OrderForm.PerformNetworkRequest(Api.URL_CREATE_ZAMOWIENIE, params, CODE_POST_REQUEST);
            request.execute();

            System.out.println("Przed createProductsOrders");
            createProductsOrders();

            System.out.println("Po createProductsOrders");

            Toast.makeText(this, "Zamowienie zostalo zlozone", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        //EXIT
        } else {
            return;
        }
    }

    private void createProductsOrders(){
        for(Product p: basket.getProducts()){
            HashMap<String, String> params = new HashMap<>();
            params.put("IdZam", Integer.toString(orderID));
            params.put("IdSpr", Integer.toString(p.getId()));
            params.put("Liczba", Integer.toString(p.getHowMany()));
            params.put("Tel", Double.toString(p.getTotalPriceDouble()));
            OrderForm.PerformNetworkRequest request = new OrderForm.PerformNetworkRequest(Api.URL_CREATE_ZAMOWIENIE_SPRZETU, params, CODE_POST_REQUEST);
            request.execute();
        }
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Log.d("aaa", "objectJSON jest nie jest errorem");
                    //refreshing the product list after every operation
                    //so we get an updated list
                    if(orderID == 0) {
                        orderID = object.getInt("lastID") + 1;
                        System.out.println("Order ID: " + orderID);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void readLastId() {
        OrderForm.PerformNetworkRequest request = new OrderForm.
                PerformNetworkRequest(Api.URL_READ_LAST_ID, null, CODE_GET_REQUEST);
        request.execute();
    }
}



