package com.example.footballtalking;

import android.widget.SeekBar;

public class poll_questions {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int votersOption1;
    private int votersOption2;
    private int votersOption3;
    private String id;

    private poll_questions() {

    }

    private poll_questions(String question) {
        this.question = question;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVotersOption1() {
        return votersOption1;
    }

    public void setVotersOption1(int votersOption1) {
        this.votersOption1 = votersOption1;
    }

    public int getVotersOption2() {
        return votersOption2;
    }

    public void setVotersOption2(int votersOption2) {
        this.votersOption2 = votersOption2;
    }

    public int getVotersOption3() {
        return votersOption3;
    }

    public void setVotersOption3(int votersOption3) {
        this.votersOption3 = votersOption3;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }
}
