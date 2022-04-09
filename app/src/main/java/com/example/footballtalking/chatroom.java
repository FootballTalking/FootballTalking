package com.example.footballtalking;

import static com.example.footballtalking.FirebaseCords.MAIN_CHAT_DATABASE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class chatroom extends AppCompatActivity {

    private RecyclerView chat_list;
    private RecyclerView.Adapter mAdapter;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    FirebaseAuth mAuth;
    Toolbar toolbar;
    EditText chat_box;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        firebaseFirestore = FirebaseFirestore.getInstance();
       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chat_box = (EditText) findViewById(R.id.chat_box);
        chat_list = (RecyclerView) findViewById(R.id.chat_list);

        // Query
        //Query query = firebaseFirestore.collection("Chat");
        Query query = MAIN_CHAT_DATABASE.orderBy("timestamp" , Query.Direction.ASCENDING);

        // RecyclerOptions
        FirestoreRecyclerOptions<ChatModel> options = new FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query, ChatModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ChatModel, ChatViewHolder>(options) {
            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.their_message, parent, false);
                ChatViewHolder holder = new ChatViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatModel model) {
                holder.message.setText(model.getMessage());


            }
        };

        chat_list.setHasFixedSize(true);
        chat_list.setLayoutManager(new LinearLayoutManager(this));
        //mAdapter = new RecyclerViewAdapter();
        chat_list.setAdapter(adapter);


    }

    private void initChatList() {


    }

    public void addMessage(View view) {
        String message = chat_box.getText().toString();
        //FirebaseUser user = mAuth.getCurrentUser();
        if (!TextUtils.isEmpty(message)) {

            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd: HH:mm:ss");
            String messageID = format.format(today);


            HashMap<String, Object> messageObj = new HashMap<>();
            messageObj.put("message", message);
            //messageObj.put("user_name" , user.getDisplayName());
            messageObj.put("timestamp", FieldValue.serverTimestamp());
            messageObj.put("messageID", messageID);

            MAIN_CHAT_DATABASE.document(messageID).set(messageObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(chatroom.this, "Message sent!", Toast.LENGTH_LONG).show();
                        chat_box.setText("");

                    } else {
                        Toast.makeText(chatroom.this, "error: message not sent", Toast.LENGTH_LONG).show();

                    }

                }
            });

        }

    }


    private class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView message;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_body);

            //itemView.setOnClickListener(this);


        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}