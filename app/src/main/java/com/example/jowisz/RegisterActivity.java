package com.example.jowisz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

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
    }

    public void register (View v){
        StringBuilder errors = new StringBuilder();
        String password = this.password.getText().toString();
        String repeatedPassword = this.repeatedPassword.getText().toString();
        String name = this.name.getText().toString();
        String surname = this.surname.getText().toString();


        if (name.matches(".*\\d.*")){
            errors.append("Imię musi zawierać tylko litery");
            this.name.setBackgroundColor(getResources().getColor(R.color.errorLight));
        }
        if (surname.matches(".*\\d.*")){
            errors.append("Nazwisko musi zawierać tylko litery");
            this.surname.setBackgroundColor(getResources().getColor(R.color.errorLight));
        }

        if (!password.equals(repeatedPassword)) {
            errors.append("Hasłą się nie zgadzają");
            this.password.setBackgroundColor(getResources().getColor(R.color.errorLight));
            this.repeatedPassword.setBackgroundColor(getResources().getColor(R.color.errorLight));
        }
        if(!errors.toString().equals("")) {
            Toast.makeText(this, errors.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
