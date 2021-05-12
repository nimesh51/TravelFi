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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignInActivity extends AppCompatActivity {

    EditText mail,pass;
    Button btnSign;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        auth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.usermail);
        pass = findViewById(R.id.userpass);
        btnSign = findViewById(R.id.signin_btn);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = mail.getText().toString();
                String txt_pass = pass.getText().toString();

                if(txt_email.isEmpty() || txt_pass.isEmpty()){
                    Toast.makeText(SignInActivity.this,"One or more fields are empty !",Toast.LENGTH_SHORT);
                }

                else if(txt_pass.length()<6){
                    Toast.makeText(SignInActivity.this,"Password must contain atleast 7 characters !",Toast.LENGTH_SHORT);
                }

                else{
                    SigninUser(txt_email,txt_pass);
                }
            }
        });
    }

    private void SigninUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignInActivity.this, "Signed in successfully !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
                finishAffinity();
            }
        });
    }

}