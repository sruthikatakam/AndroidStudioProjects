package com.example.sruthikatakam.geoquiz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.app.Activity;

public class QuizActivity extends AppCompatActivity {
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;


    private Button mNextButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question_australia,
                            true),
                    new Question(R.string.question_oceans, true),
                    new Question(R.string.question_mideast,
                            false),
                    new Question(R.string.question_africa,
                            false),
                    new Question(R.string.question_americas,
                            true),
                    new Question(R.string.question_asia, true),
            };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) {
            mCurrentIndex =
                    savedInstanceState.getInt(KEY_INDEX, 0);
        }






        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        //int question = mQuestionBank[mCurrentIndex].getTextResId();
        //mQuestionTextView.setText(question);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,
                      //  R.string.incorrect_toast,
                       // Toast.LENGTH_SHORT).show();
                // Does nothing yet, but soon!
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,
                      //  R.string.correct_toast,
                      //  Toast.LENGTH_SHORT).show();
                // Does nothing yet, but soon!
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) %
                        mQuestionBank.length;
                mIsCheater = false;
                //int question =
                // mQuestionBank[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                updateQuestion();
            }
        });


        mCheatButton =
                (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(QuizActivity.this,
                      //  CheatActivity.class);

                boolean answerIsTrue =
                        mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent =
                        CheatActivity.newIntent(QuizActivity.this,
                                answerIsTrue);
               // startActivity(intent);
                startActivityForResult(intent,
                        REQUEST_CODE_CHEAT);

                // Start CheatActivity
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater =
                    CheatActivity.wasAnswerShown(data);
        }
    }


    private void updateQuestion() {
        int question =
                mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue =
                mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
            Toast.makeText(this, messageResId,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }


    @Override
    public void onSaveInstanceState(Bundle
                                            savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,
                mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
