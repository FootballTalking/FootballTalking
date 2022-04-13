package com.example.footballtalking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class VoteFragment extends Fragment {


    private RecyclerView recyclerViewQuestionList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vote, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerViewQuestionList = (RecyclerView) getView().findViewById(R.id.questionsList);


        // query to show all document inside "poll_questions" collection
        Query query = firebaseFirestore.collection("poll_questions");


        // FirebaseUI library offers a RecyclerView adapter for Cloud Firestore called "FirestoreRecyclerAdapter"
        // FirestoreRecyclerAdapter binds a Query to a RecyclerView. When documents are added, removed, or change these updates are automatically applied to the UI in real time.


        // 1. Configure recycler adapter options:
        //  * query is the Query object defined above.
        //  * poll_questions.class instructs the adapter to convert each Document inside "poll_questions" collection to a poll_questions object
        FirestoreRecyclerOptions<poll_questions> options = new FirestoreRecyclerOptions.Builder<poll_questions>()
                .setQuery(query, poll_questions.class)
                .build();


        // 2. Create the FirestoreRecyclerAdapter object.
        // *  a QuestionsViewHolder is subclass for displaying each item.
        adapter = new FirestoreRecyclerAdapter<poll_questions, QuestionsViewHolder>(options) {
            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the QuestionsViewHolder, in this case we are using a custom
                // layout called R.poll_item for each item in the RecyclerView.
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent, false);
                QuestionsViewHolder holder = new QuestionsViewHolder(v);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position, @NonNull poll_questions model) {
                // Bind the poll_questions object to the QuestionsViewHolder

                holder.id = model.getId();
                // set the Question to the TextView
                holder.question.setText(model.getQuestion());


                for (int i = 0; i < model.getNoOfOptions(); i++) {
                    // generate a radioButton dynamically for each option
                    RadioButton radioButton = new RadioButton(getActivity());
                    // set option text using getOption() to the radioButton
                    radioButton.setText(model.getOptions().get(i));
                    radioButton.setId(View.generateViewId());
                    // add the ID of radioButton to optionID List
                    holder.optionsID.add(String.valueOf(radioButton.getId()));
                    holder.radioGroup.addView(radioButton, holder.rprms);

                    // generate a TextView dynamically to show number of voters for each option
                    TextView textView = new TextView(getActivity());
                    // set No. of voters using getVoters() to the TextView
                    textView.setText(String.valueOf(model.getVoters().get(i)));
                    // set the height & width
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setId(View.generateViewId());
                    // add No. of voters of each option to a List.
                    holder.votes.add(model.getVoters().get(i));
                    holder.votersLayout.addView(textView);

                }


                // by click on vote Button, the No. of voters of the chosen option (radioButton) will increase
                holder.vote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // get selected radio button from radioGroup
                        int selectedId = holder.radioGroup.getCheckedRadioButtonId();

                        // increase No. of voters of the chosen radioButton
                        for (int i = 0; i < model.getNoOfOptions(); i++) {
                            if (holder.optionsID.get(i).equalsIgnoreCase(String.valueOf(selectedId))) {
                                firebaseFirestore.collection("poll_questions").document(holder.id).update("v" + i, holder.votes.set(i, holder.votes.get(i) + 1));
                            }
                        }
                    }
                });
            }
        };

        // 3. Attach the adapter to the RecyclerView (recyclerViewQuestionList) with the setAdapter() method
        recyclerViewQuestionList.setHasFixedSize(true);
        recyclerViewQuestionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewQuestionList.setAdapter(adapter);


    }


    private class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView question;
        private Button vote;
        private RadioGroup radioGroup;
        RadioGroup.LayoutParams rprms;
        private LinearLayout linearLayout;
        private LinearLayout votersLayout;
        List<String> optionsID;
        List<Long> votes;
        private String id;

        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.list_question);
            vote = itemView.findViewById(R.id.SendAnswer);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            optionsID = new ArrayList<>();
            votes = new ArrayList<>();
            votersLayout = itemView.findViewById(R.id.voteresLayout);
            radioGroup = itemView.findViewById(R.id.mRgAllButtons);
            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
        }
    }


    // The FirestoreRecyclerAdapter uses a snapshot listener to monitor changes to the Firestore query.

    // Call this method when the Fragment stop:
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // To begin listening for data.
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}
