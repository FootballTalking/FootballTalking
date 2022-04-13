package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button loginBtn;
    private Button buttonLoginTeam;
    private ProgressBar progressBar;
    private TextView forgetPassword;


    private FirebaseAuth mAuth;

    private Button SignUpbtn;
    private ImageView leftArrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        leftArrow = (ImageView) findViewById(R.id.leftarrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });


        TextView back = (TextView) findViewById(R.id.backText);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();

            }
        });

        buttonLoginTeam = (Button) findViewById(R.id.loginButtonTeams);
        buttonLoginTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeamPage();

            }
        });

        SignUpbtn = (Button) findViewById(R.id.signUpButton);
        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();

            }
        });

        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.emailfield);
        editPassword = (EditText) findViewById(R.id.passwordfiled);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();


        forgetPassword = (TextView) findViewById(R.id.forgetPassText);
        forgetPassword.setOnClickListener(this);



    }

    public void openMainPage() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void openSignUpPage() {
        Intent intent = new Intent(this , SignUp.class);
        startActivity(intent);
    }

    public void openTeamPage() {
        Intent intent = new Intent(this , TeamLogin.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                userLogin();
                break;
            case R.id.forgetPassText:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
        }



    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            editPassword.setError("Password is required!");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        // redirect to user profile
                        startActivity(new Intent(Login.this , FirstPage.class));


                    }

                    else {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this , "Check your email to verify your account!" , Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(Login.this , "Failed to login! please check your information" , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });



    }
}