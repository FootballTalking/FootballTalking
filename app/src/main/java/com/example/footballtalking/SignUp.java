package com.example.footballtalking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    private ImageView leftArrows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        leftArrows = (ImageView) findViewById(R.id.leftarrow2);
        leftArrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });

        TextView backk = (TextView) findViewById(R.id.backText2);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();

            }
        });

        TextView LoginHere = (TextView) findViewById(R.id.LoginHereBtn);
        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();

            }
        });


    }

    public void openMainPage() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this , Login.class);
        startActivity(intent);
    }



}