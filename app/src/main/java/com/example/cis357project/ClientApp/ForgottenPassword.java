package com.example.cis357project.ClientApp;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cis357project.R;

public class ForgottenPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI(findViewById(R.id.forgottenPasswordLayout));

        TextView forgottenPassword = findViewById(R.id.forgottenPasswordTitle);
        TextView usernameViewFPW = findViewById(R.id.usernameViewFPW);
        TextView securityQuestionOneFPW = findViewById(R.id.securityQuestionOneFPW);
        EditText securityAnswerOneFPW = findViewById(R.id.securityAnswerOneFPW);
        TextView securityQuestionTwoFPW = findViewById(R.id.securityQuestionTwoFPW);
        EditText securityAnswerTwoFPW = findViewById(R.id.securityAnswerTwoFPW);
        EditText usernameInputFPW = findViewById(R.id.usernameInputFPW);
        Button submitButtonFPW = findViewById(R.id.submitButtonFPW);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ForgottenPassword.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
