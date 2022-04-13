package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordAgain;
    private EditText editDoB;
    final Calendar myCalendar= Calendar.getInstance();


    private Button registerUser;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

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


        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.SignupButton);
        registerUser.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.UserName);
        editEmail = (EditText) findViewById(R.id.UserEmail);
        editPassword = (EditText) findViewById(R.id.UserPassword);
        editPasswordAgain = (EditText) findViewById(R.id.UserPasswordAgain);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editDoB = (EditText) findViewById(R.id.dateOfBirth);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        editDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUp.this, date ,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editDoB.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void openMainPage() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this , Login.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignupButton:
                registerUser();
        }

    }

    private void registerUser() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();
        String passAgain = editPasswordAgain.getText().toString().trim();
        String dob = editDoB.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Name is required!");
            editName.requestFocus();
            return;
        }

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

        if (pass.length() < 8) {
            editPassword.setError("Minimum password length should be 8 characters!");
            editPassword.requestFocus();
            return;
        }

        if(!pass.matches("(.*[A-Z].*)")) {
            editPassword.setError("Password must contain at least one uppercase!");
            editPassword.requestFocus();
            return;


        }

        if(!pass.matches("(.*[a-z].*)")) {
            editPassword.setError("Password must contain at least one lowercase!");
            editPassword.requestFocus();
            return;


        }

        if (passAgain.isEmpty()) {
            editPasswordAgain.setError("Password is required!");
            editPasswordAgain.requestFocus();
            return;
        }

        if (!passAgain.matches(pass)) {
            editPasswordAgain.setError("Password doesn't match!");
            editPasswordAgain.requestFocus();
            return;
        }

        if (dob.isEmpty()) {
            editDoB.setError("Date of Birth is required!");
            editDoB.requestFocus();
            return;
        }

        if (getAge(dob) < 18) {
            editDoB.setError("Sorry! Your age does not comply with the terms of use of the application!");
            editDoB.requestFocus();
            return;

        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    User user = new User(name , email , pass , dob);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                            }
                            else {
                                Toast.makeText(SignUp.this, "Failed to register! try again!" , Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(SignUp.this, "Failed to register! try again!" , Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }

            }
        });

    }

    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age;
    }
}