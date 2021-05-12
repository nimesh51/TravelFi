package com.example.travelfi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText name,email,phone,password;
    Button register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        name = findViewById(R.id.full_Name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        register = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_pass = password.getText().toString();

                if(txt_name.isEmpty() || txt_email.isEmpty() || txt_pass.isEmpty() || txt_phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"One or more fields are empty !",Toast.LENGTH_SHORT).show();
                }

                else{
                    registerUser(txt_name,txt_email,txt_phone,txt_pass);
                }
            }
        });

    }
    private void registerUser(String user_name, String user_email, String user_phone, String user_password) {

            auth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Name", user_name);
                            map.put("Email", user_email);
                            map.put("Phone", user_phone);
                            FirebaseDatabase.getInstance().getReference().child("User").push().updateChildren(map);

                            startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                            finishAffinity();
                        }

                        else {

                            String errorcode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorcode){

                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(SignUpActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(SignUpActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(SignUpActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(SignUpActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    email.setError("The email address is badly formatted.");
                                    email.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(SignUpActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                    password.setError("password is incorrect ");
                                    password.requestFocus();
                                    password.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(SignUpActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(SignUpActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(SignUpActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(SignUpActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    email.setError("The email address is already in use by another account.");
                                    email.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(SignUpActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(SignUpActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(SignUpActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(SignUpActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(SignUpActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(SignUpActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    password.setError("The password is invalid it must 6 characters at least");
                                    password.requestFocus();
                                    break;

                            }
                        }
                }
            });

    }
}