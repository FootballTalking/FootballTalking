package com.example.footballtalking;

import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class poll_questions {

    private String question;
    private List<String> options = new ArrayList<>();
    private List<Long> voters = new ArrayList<>();
    private String id;

    // poll_questions class has an empty constructor, which is required for Firestore's automatic data mapping.
    private poll_questions() {

    }

    public poll_questions(String question, List<String> options, String id , List<Long> voters) {
        this.question = question;
        this.options = options;
        this.voters = voters;

        this.id = id;
    }

    public List<Long> getVoters() {
        return voters;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoOfOptions() {
        return options.size();
    }
}
