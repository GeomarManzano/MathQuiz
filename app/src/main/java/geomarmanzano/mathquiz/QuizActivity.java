package geomarmanzano.mathquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {
    private static final String KEY_INDEX = "index";
    private static final String KEY_BOOL = "isCheater";
    private static final int DEFAULT_INT = 0;
    private static final boolean DEFAULT_BOOL = false;
    private static final String CLASS_TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private boolean mClearCheatSettings;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_number, true),
            new Question(R.string.question_trig, false),
            new Question(R.string.question_sum, true),
            new Question(R.string.question_algebra, false),
            new Question(R.string.question_mod, true),
            new Question(R.string.question_binary, true)
    };

    private boolean[] mQuestionsCheated = new boolean[mQuestionBank.length];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(CLASS_TAG, "onCreate entered");

        mIsCheater = false;
        mClearCheatSettings = true;

        Arrays.fill(mQuestionsCheated, false);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, DEFAULT_INT);
            mIsCheater = savedInstanceState.getBoolean(KEY_BOOL, DEFAULT_BOOL);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(1);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheater = false;
                mClearCheatSettings = true;
                updateQuestion(-1);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheater = false;
                mClearCheatSettings = true;
                updateQuestion(1);
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();

                Intent i = CheatActivity.newIntent(QuizActivity.this,
                        answer, mClearCheatSettings);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
                mClearCheatSettings = false;
            }
        });
        updateQuestion(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(CLASS_TAG, "onStart entered");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(CLASS_TAG, "onPause entered");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(CLASS_TAG, "onResume entered");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(CLASS_TAG, "onStop entered");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(CLASS_TAG, "onDestroy entered");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(CLASS_TAG, "onSaveInstanceState entered");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_BOOL, mIsCheater);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        Log.d(CLASS_TAG, "onActivityResult entered");

        if (resCode != Activity.RESULT_OK) {
            return;
        }

        if (reqCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);

            // If mQuestionsCheated[mCurrentIndex] has not been set yet and
            // the user cheated i.e. mIsCheater is true, then keep track of
            // the user cheating on the current question
            if (!mQuestionsCheated[mCurrentIndex] && mIsCheater)
                mQuestionsCheated[mCurrentIndex] = mIsCheater;
        }
    }

    private void updateQuestion(int n) {
        Log.d(CLASS_TAG, "updateQuestion entered");

        if (n < 0) {
            n = Math.abs(n) % mQuestionBank.length;
            mCurrentIndex = (mCurrentIndex + mQuestionBank.length - n) %
                    mQuestionBank.length;
        } else mCurrentIndex = (mCurrentIndex + n) % mQuestionBank.length;

        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        Log.d(CLASS_TAG, "checkAnswer entered");

        int messageResId;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (mQuestionsCheated[mCurrentIndex]) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}