package com.example.triviaapp.model;

public class Question {

    private String question;
    private boolean correct;

    public Question(String question, boolean correct) {
        this.question = question;
        this.correct = correct;
    }

    public Question() {}

    public String getQuestion() {
        return question;
    }

//    public void setQuestion(String question) {
//        this.question = question;
//    }

    public boolean isCorrect() {
        return correct;
    }

//    public void setCorrect(boolean correct) {
//        this.correct = correct;
//    }

}
