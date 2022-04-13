package com.example.footballtalking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteFragment extends Fragment {


    private RecyclerView mFirestoreList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    //RadioGroup mRgAllButtons;
    private int size;
    private List<String> options2 = new ArrayList<String>();

    private RadioButton radioButton;
    private TextView textView;
    private LinearLayout linearLayout;
    EditText t;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vote, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayout = getView().findViewById(R.id.linearLayout);
        // mRgAllButtons = getView().findViewById(R.id.mRgAllButtons);


        firebaseFirestore = FirebaseFirestore.getInstance();

        mFirestoreList = (RecyclerView) getView().findViewById(R.id.firestore_list);


        // query
        Query query = firebaseFirestore.collection("poll_questions");

        // Recycler options
        FirestoreRecyclerOptions<poll_questions> options = new FirestoreRecyclerOptions.Builder<poll_questions>()
                .setQuery(query, poll_questions.class).build();

        adapter = new FirestoreRecyclerAdapter<poll_questions, QuestionsViewHolder>(options) {
            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent, false);
                QuestionsViewHolder holder = new QuestionsViewHolder(v);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position, @NonNull poll_questions model) {


                holder.id = model.getId();
                holder.list_ques.setText(model.getQuestion());

                for (int i = 0; i < model.getNoOfOptions(); i++) {
                    Log.d("Option: ", model.getOptions().get(i));

                }


                for (int i = 0; i < model.getNoOfOptions(); i++) {
                    RadioButton radioButton = new RadioButton(getActivity());
                    radioButton.setText(model.getOptions().get(i));
                    radioButton.setId(View.generateViewId());
                    holder.optionsID.add(String.valueOf(radioButton.getId()));
                    holder.mRgAllButtons.addView(radioButton, holder.rprms);

                    TextView textView = new TextView(getActivity());
                    textView.setText(String.valueOf(model.getVoters().get(i)));
                    //textView.setText("Option");
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setId(View.generateViewId());
                    holder.votes.add(model.getVoters().get(i));
                    holder.votersLayout.addView(textView);

                }


                holder.sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // get selected radio button from radioGroup
                        int selectedId = holder.mRgAllButtons.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        for (int i = 0 ;  i < model.getNoOfOptions() ; i++ ) {
                            if (holder.optionsID.get(i).equalsIgnoreCase(String.valueOf(selectedId))) {
                                firebaseFirestore.collection("poll_questions").document(holder.id).update("v" + i, holder.votes.set(i , holder.votes.get(i) + 1));


                            }
                        }



                        //Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();


                    }


                });


            }
        };


        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerViewAdapter();
        mFirestoreList.setAdapter(adapter);


    }


    /*private void addRadioButtons(int number) {
        mRgAllButtons.setOrientation(LinearLayout.HORIZONTAL);
        //
        for (int i = 1; i <= number; i++) {
            RadioButton rdbtn = new RadioButton(getActivity());
            rdbtn.setId(View.generateViewId());
            rdbtn.setText("Radio " + rdbtn.getId());
            mRgAllButtons.addView(rdbtn);


        }


    }*/

    private void uploadData(int votersOption1, int votersOption2, int votersOption3, String id) {
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption1", votersOption1);
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption2", votersOption2);
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption3", votersOption3);

    }


    private class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int percent;
        private TextView list_ques;
        private RadioGroup radioGroup;
        private Button sendBtn;
        private RadioGroup mRgAllButtons;
        RadioGroup.LayoutParams rprms;


        private LinearLayout linearLayout;
        private LinearLayout votersLayout;
        private TextView numOfVoters;

        List<String> optionsID;
        List<Long> votes;


        private String id;
        private int numOfOptions;


        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);


            list_ques = itemView.findViewById(R.id.list_question);
            sendBtn = itemView.findViewById(R.id.SendAnswer);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            optionsID = new ArrayList<>();
            votes = new ArrayList<>();
            votersLayout = itemView.findViewById(R.id.voteresLayout);
            mRgAllButtons = itemView.findViewById(R.id.mRgAllButtons);
            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            numOfVoters = itemView.findViewById(R.id.numOfVoters);


            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(view.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}
