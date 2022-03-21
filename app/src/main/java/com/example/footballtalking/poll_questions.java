package com.example.footballtalking;

public class poll_questions {

    private String question;

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


}
