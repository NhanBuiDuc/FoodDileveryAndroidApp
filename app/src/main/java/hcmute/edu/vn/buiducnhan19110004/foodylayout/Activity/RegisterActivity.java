package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.UserDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullnameEditText, emailEditText, phoneEditText, passEditText, confirmPassEditText;
    private TextView loginDirectTxt;
    private Button registerBtn;
    private UserDB userDb;

    private String fullname;
    private String email;
    private String phone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameEditText = findViewById(R.id.fullnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passEditText = findViewById(R.id.passwordEditText);
        confirmPassEditText = findViewById(R.id.confirmPasswordEditText);

        registerBtn = findViewById(R.id.registerBtn);
        loginDirectTxt = findViewById(R.id.directLoginTextView);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

        loginDirectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public void RegisterUser() {
        if(!passEditText.getText().equals(confirmPassEditText.getText())) {
            //write code to return to register page and announce that password didn't match
        }

        fullname = fullnameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        password = passEditText.getText().toString().trim();
        UserDomain insertUser = new UserDomain(0, fullname, email, phone, password);
        if(userDb.CheckDuplicateEmail(insertUser)) {
            //write code to return to register mage and announce that the email has already existed
        }
        else {
            userDb.InsertUser(insertUser);
        }
    }
}