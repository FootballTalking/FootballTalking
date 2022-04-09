package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TeamLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText teamEmail;
    private EditText teamPassword;
    private Button loginBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_login);

        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        teamEmail = (EditText) findViewById(R.id.emailfield);
        teamPassword = (EditText) findViewById(R.id.passwordfiled);

        mAuth = FirebaseAuth.getInstance();






    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                teamLogin();
        }

    }

    private void teamLogin() {
        String email = teamEmail.getText().toString().trim();
        String pass = teamPassword.getText().toString().trim();

        if (email.isEmpty()) {
            teamEmail.setError("Email is required!");
            teamEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            teamEmail.setError("Please provide valid email!");
            teamEmail.requestFocus();
            return;
        }

        if (!email.matches("^\\S+@footballtalking\\.com$")) {
            teamEmail.setError("Email is not correct! please try again!");
            teamEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            teamPassword.setError("Password is required!");
            teamPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(TeamLogin.this , TeamPage.class));
                } else {
                    Toast.makeText(TeamLogin.this , "Failed to login! please check your information" , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

    }
}