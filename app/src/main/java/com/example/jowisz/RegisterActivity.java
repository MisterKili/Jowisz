package com.example.jowisz;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText bornDay;
    private EditText password;
    private EditText repeatedPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.nameTextField);
        surname = findViewById(R.id.surnameTextField);
        email = findViewById(R.id.emailTextField);
        bornDay = findViewById(R.id.bornDateTextField);
        password = findViewById(R.id.passwordTextField);
        repeatedPassword = findViewById(R.id.passwordTextFieldRepeat);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createKlient();
            }
        });


    }

    public void createKlient() {
        String nameText = name.getText().toString().trim();
        String surnameText = surname.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String bornDayText = bornDay.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String repeatedPasswordText = repeatedPassword.getText().toString().trim();

        int errors = 0;



        if (TextUtils.isEmpty(nameText)) {
            name.setError("Proszę wprowadzić imię");
            name.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[a-zA-Z]+", nameText)) {
            name.setError("Imię musi zawierać tylko litery");
            name.requestFocus();
            errors += 1;
        }


        if (TextUtils.isEmpty(surnameText)) {
            surname.setError("Proszę wprowadzić nazwisko");
            surname.requestFocus();
            errors += 1;
        } else if (!Pattern.matches("[a-zA-Z]+", surnameText)) {
            surname.setError("Nazwisko musi zawierać tylko litery");
            surname.requestFocus();
            errors += 1;
        }

        if (TextUtils.isEmpty(emailText)) {
            email.setError("Proszę wprowadzić e-mail");
            email.requestFocus();
            errors += 1;
        } else if (!isValidEmailAddress(emailText)) {
            email.setError("Niepoprawna forma e-mailu");
            email.requestFocus();
            errors += 1;
        }

        if (TextUtils.isEmpty(bornDayText)) {
            bornDay.setError("Proszę wprowadzić datę urodzenia (yyyy-mm-dd)");
            bornDay.requestFocus();
            errors += 1;
        } else if (!isValidData(bornDayText)){
            bornDay.setError("Niepoprawna data, yyyy-mm-dd");
            bornDay.requestFocus();
        }

        if (TextUtils.isEmpty(passwordText)) {
            password.setError("Proszę wprowadzić hasło");
            password.requestFocus();
            errors += 1;
        }

        if (TextUtils.isEmpty(repeatedPasswordText)) {
            repeatedPassword.setError("Proszę powtórzyć hasło");
            repeatedPassword.requestFocus();
            errors += 1;
        }

        if (!passwordText.equals(repeatedPasswordText)) {
            repeatedPassword.setError("Hasła się nie powtarzają");
            repeatedPassword.setText("");
            return;
        }


        if (errors == 0) {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", emailText);
            params.put("haslo", passwordText);
            params.put("nazw", surnameText);
            params.put("imie", nameText);
            params.put("dataUr", bornDayText);
            System.out.println("ok");
            Log.d("aaa", "register");
            PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_KLIENT, params, CODE_POST_REQUEST);
            request.execute();
        } else {
            return;
        }



    }

    public boolean isValidEmailAddress(String text) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(text);
        return m.matches();
    }

    public static boolean isValidData(String text) {
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return true;
        } catch (ParseException ex) {
            return false;
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

        //when the task started displaying a progressbar
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
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
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
}






