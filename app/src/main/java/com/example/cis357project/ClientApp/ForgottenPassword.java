package com.example.cis357project.ClientApp;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.cis357project.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
        final EditText securityAnswerOneFPW = findViewById(R.id.securityAnswerOneFPW);
        TextView securityQuestionTwoFPW = findViewById(R.id.securityQuestionTwoFPW);
        final EditText securityAnswerTwoFPW = findViewById(R.id.securityAnswerTwoFPW);
        final EditText usernameInputFPW = findViewById(R.id.usernameInputFPW);
        Button submitButtonFPW = findViewById(R.id.submitButtonFPW);

        String questionOne = "";
        String questionTwo = "";

        File file = new File(getApplicationContext().getFilesDir(), "AccountDetails");
        if (file.exists()) {
            try {
                FileInputStream fis = openFileInput("AccountDetails");
                InputStreamReader isr = new InputStreamReader(fis);

                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuffer stringBuffer = new StringBuffer();
                String line = bufferedReader.readLine();

                String[] creds = line.split("-");

                if(creds.length > 0) {
                    questionOne = creds[2];
                    questionTwo = creds[3];
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(questionOne.equals("")){
            securityQuestionOneFPW.setText("What is your mothers maiden name?");
        } else {
            securityQuestionOneFPW.setText(questionOne);
        }

        if(questionTwo.equals("")) {
            securityQuestionTwoFPW.setText("What street did you live on growing up?");
        } else {
            securityQuestionTwoFPW.setText(questionTwo);
        }

        submitButtonFPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getApplicationContext().getFilesDir(), "AccountDetails");
                if (file.exists()) {
                    try {
                        FileInputStream fis = openFileInput("AccountDetails");
                        InputStreamReader isr = new InputStreamReader(fis);

                        BufferedReader bufferedReader = new BufferedReader(isr);
                        StringBuffer stringBuffer = new StringBuffer();
                        String line = bufferedReader.readLine();

                        String[] creds = line.split("-");
                        if(creds[0].equals(usernameInputFPW.getText().toString()) && creds[4].equals(securityAnswerOneFPW.getText().toString())
                            && creds[5].equals(securityAnswerTwoFPW.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Your password is: " + creds[1], Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect credentials or no known account", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect credentials or no known account", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
