package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.UserDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class ProfileActivity extends AppCompatActivity {
    private ConstraintLayout deleteProfileBtn;
    private EditText profilePhone, profileName, profileEmail, profilePassword;
    private TextView updateProfileBtn;
    private Button logOutBtn;

    //Database classes
    private FoodyDBHelper foodyDBHelper;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        foodyDBHelper = new FoodyDBHelper(this);
        userDB = new UserDB(foodyDBHelper);

        deleteProfileBtn = findViewById(R.id.deleteProfileConstraintLayout);
        updateProfileBtn = findViewById(R.id.profileUpdateTxt);
        profilePhone = findViewById(R.id.profilePhoneEditText);
        profileName = findViewById(R.id.profileNameEditText);
        profileEmail = findViewById(R.id.profileEmailEditText);
        profilePassword = findViewById(R.id.profilePasswordEditText);
        logOutBtn = findViewById(R.id.logOutButton);

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = profilePhone.getText().toString();
                String name = profileName.getText().toString();
                String email = profileEmail.getText().toString();
                String password = profilePassword.getText().toString();

                UserDomain updatedUser = new UserDomain(CurrentUser.getUser_id(), name, email, phone, password);
                userDB.UpdateCurrentUser(updatedUser);
            }
        });

        deleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Are you sure want to delete your account?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDB.DeleteUserById(CurrentUser.getUser_id());
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.show();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }
}