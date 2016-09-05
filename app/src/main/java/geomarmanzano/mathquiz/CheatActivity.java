package geomarmanzano.mathquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String KEY_SHOWN = "shown";
    private static final String EXTRA_ANSWER = "geomarmanzano.mathquiz.answer";
    private static final String EXTRA_ANSWER_SHOWN =
            "geomarmanzano.mathquiz.answer_shown";
    private static final String EXTRA_CLEAR_SETTINGS =
            "geomarmanzano.mathquiz.clear_settings";
    private static final boolean DEFAULT_BOOL = false;
    private static final String CLASS_TAG = "CheatActivity";

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    private boolean mAnswer;
    private boolean mIsAnswerShown;
    private boolean mClearSettings;

    public static Intent newIntent(Context packageContext,
                                   boolean answer, boolean clearSettings) {
        Log.d(CLASS_TAG, "newIntent entered");
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER, answer);
        i.putExtra(EXTRA_CLEAR_SETTINGS, clearSettings);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        Log.d(CLASS_TAG, "wasAnswerShown entered");
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, DEFAULT_BOOL);
    }

    @Override
    public void onBackPressed() {
        Log.d(CLASS_TAG, "onBackPressed entered");
        setAnswerShownResult(mIsAnswerShown);
        SavePreferences();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Log.d(CLASS_TAG, "onCreate entered");

        LoadPreferences();

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_SHOWN,
                    DEFAULT_BOOL);
        }

        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, DEFAULT_BOOL);
        mClearSettings = getIntent().getBooleanExtra(EXTRA_CLEAR_SETTINGS,
                DEFAULT_BOOL);

        if (mClearSettings) {
            mIsAnswerShown = false;
        }

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mIsAnswerShown = true;
            }
        });
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
        savedInstanceState.putBoolean(KEY_SHOWN, mIsAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Log.d(CLASS_TAG, "setAnswerShownResult entered");
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    private void SavePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SHOWN, mIsAnswerShown);
        editor.commit();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        mIsAnswerShown = sharedPreferences.getBoolean(KEY_SHOWN, DEFAULT_BOOL);
    }
}