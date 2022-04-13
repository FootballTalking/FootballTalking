package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamPage extends AppCompatActivity {

    private EditText questionTextView;
    private Button btnAddOption;
    private Button btnSend;
    ProgressDialog progressDialog;
    private LinearLayout layout;

    FirebaseFirestore db;


    List<EditText> allEditTextList = new ArrayList<EditText>();
    EditText newEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        // assign ID's of QuestionTextView, layout, the Button for Add new Option in xml file to following variables:
        questionTextView = (EditText) findViewById(R.id.QuestionTextView);
        layout = (LinearLayout) findViewById(R.id.layout);
        btnAddOption = (Button) findViewById(R.id.btn_addOption);


        // every-time the user click on button (Add Option), a new EditTextView will generate dynamically to fill by the user.
        btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // generate New EditText ---> "New Option for the Question"
                newEditText = new EditText(TeamPage.this);
                // Add this new EditText to a List of type EditText
                allEditTextList.add(newEditText);
                // set Text Size for the EditText
                newEditText.setTextSize(80);
                // set Gravity of EditText to put it in the center of the layout
                newEditText.setGravity(Gravity.CENTER);
                // define the height and width
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newEditText.setLayoutParams(params);
                layout.addView(newEditText);
            }
        });


        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get Question
                String question = questionTextView.getText().toString().trim();

                String[] strings = new String[allEditTextList.size()];
                for (int i = 0; i < allEditTextList.size(); i++) {
                    strings[i] = allEditTextList.get(i).getText().toString();
                }
                ArrayList<String> allEditTextContentList = new ArrayList<>(Arrays.asList(strings));

                // pass Question & Options to uploadData Method to store it on the Database
                uploadData(question, allEditTextContentList);
            }
        });
    }


    ////////////////////// ------------------ uploadData Method ------------------ //////////////////////

    public void uploadData(String question, ArrayList<String> options) {
        progressDialog.setTitle("Adding Question to Vote Page!");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("question", question);

        ArrayList<Integer> voters = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            doc.put("voteOption" + i, 0);
           // voters.add(0);
        }

        doc.put("options", options);
       // doc.put("voters", voters);
        doc.put("id", id);


        db.collection("poll_questions").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // this will be called when Data is added successfully
                progressDialog.dismiss();
                Toast.makeText(TeamPage.this, "Added", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this will be called when there is an error
                progressDialog.dismiss();
                Toast.makeText(TeamPage.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

    }


}