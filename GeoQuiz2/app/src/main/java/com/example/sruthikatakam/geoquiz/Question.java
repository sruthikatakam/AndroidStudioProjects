package com.example.sruthikatakam.geoquiz;

/**
 * Created by sruthikatakam on 1/15/18.
 */

public class Question {


    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    private int mTextResId;
    private boolean mAnswerTrue;
    public Question(int textResId, boolean
            answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }



}
