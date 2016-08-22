package geomarmanzano.mathquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_number, true),
            new Question(R.string.question_trig, false),
            new Question(R.string.question_sum, true),
            new Question(R.string.question_algebra, false),
            new Question(R.string.question_mod, true),
            new Question(R.string.question_binary, true)
    };

    private int mCurrentIndex = 0;

    private void updateQuestion(int n) {
        if (n < 0) {
            n = Math.abs(n) % mQuestionBank.length;
            mCurrentIndex = (mCurrentIndex + mQuestionBank.length - n) %
                    mQuestionBank.length;
        } else mCurrentIndex = (mCurrentIndex + n) % mQuestionBank.length;

        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        int messageResId;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (userPressedTrue == answerIsTrue)
            messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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
                updateQuestion(-1);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(1);
            }
        });

        updateQuestion(0);
    }
}