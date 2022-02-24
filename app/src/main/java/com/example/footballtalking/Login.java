package com.example.footballtalking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
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

        SignUpbtn = (Button) findViewById(R.id.signUpButton);
        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();

            }
        });


    }

    public void openMainPage() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void openSignUpPage() {
        Intent intent = new Intent(this , SignUp.class);
        startActivity(intent);
    }
}