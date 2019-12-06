package com.example.cis357project.ClientApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis357project.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AccountCreation extends AppCompatActivity {


    EditText answerOne;
    EditText answerTwo;
    EditText passwordInput;
    EditText usernameInput;
    EditText confirmPasswordInput;
    Spinner questionTwo;
    Spinner questionOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final Intent intent = this.getIntent();
        setSupportActionBar(toolbar);
        if (intent.hasExtra("USERNAME")) {
            getSupportActionBar().setTitle("Update Account");
        } else {
            getSupportActionBar().setTitle("Create Account");
        }
        setupUI(findViewById(R.id.accountCreationLayout));

        TextView createAccountTitle = findViewById(R.id.createAccountTitle);
        TextView usernameView = findViewById(R.id.usernameView);
        TextView passwordView = findViewById(R.id.passwordView);
        TextView confirmationView = findViewById(R.id.confirmationView);
        TextView securityQuestionOneView = findViewById(R.id.securityQuestionOneView);
        questionOne = findViewById(R.id.questionOne);
        answerOne = findViewById(R.id.answerOne);
        TextView securityQuestionTwoView = findViewById(R.id.securityQuestionTwoView);
        questionTwo = findViewById(R.id.questionTwo);
        answerTwo = findViewById(R.id.answerTwo);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        Button createAccountButton = findViewById(R.id.createAccountButton);
        Button cancelButton = findViewById(R.id.cancelButton);


        ArrayAdapter<CharSequence> adapterQuestionSet1 = ArrayAdapter.createFromResource(this,
                R.array.questionSet1, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterQuestionSet2 = ArrayAdapter.createFromResource(this,
                R.array.questionSet2, android.R.layout.simple_spinner_item);

        adapterQuestionSet1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionOne.setAdapter(adapterQuestionSet1);
        adapterQuestionSet2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionTwo.setAdapter(adapterQuestionSet2);

        if (intent.hasExtra("USERNAME")) {
            usernameInput.setText(intent.getStringExtra("USERNAME"));
            passwordInput.setText(intent.getStringExtra("PASSWORD"));
            confirmPasswordInput.setText(intent.getStringExtra("PASSWORD"));
            createAccountButton.setText(intent.getStringExtra("RENAMEBUTTON"));
            answerOne.setText(intent.getStringExtra("SECURITYA1"));
            answerTwo.setText(intent.getStringExtra("SECURITYA2"));
            questionOne.setSelection(adapterQuestionSet1.getPosition(intent.getStringExtra("SECURITYQ1")));
            questionTwo.setSelection(adapterQuestionSet2.getPosition(intent.getStringExtra("SECURITYQ2")));
            createAccountTitle.setText(intent.getStringExtra("TITLEFIX"));
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (isValidInfo()) {
                    case 0: {
                        String data = usernameInput.getText().toString() + "-" + passwordInput.getText().toString() + "-" + questionOne.getSelectedItem().toString() +
                                "-" + questionTwo.getSelectedItem().toString() + "-" + answerOne.getText().toString() + "-" +
                                answerTwo.getText().toString();
                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("AccountDetails", MODE_PRIVATE);
                            fos.write(data.getBytes());
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(AccountCreation.this, AccountDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 1:
                        Toast.makeText(getApplicationContext(), "Username field cannot be empty!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Invalid username. Only upper/lower case letters and '!' allowed", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Invalid password. Only upper/lower case letters and '!' allowed", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Passwords must match!", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getApplicationContext(), "Please select two questions!", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getApplicationContext(), "Answers cannot be empty!", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getApplicationContext(), "Invalid answers! Only upper/lower case letters and '!' allowed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

    }

    public int isValidInfo() {
        boolean validUsernameEmpty = !usernameInput.getText().toString().isEmpty();
        boolean validUsername = usernameInput.getText().toString().matches("[a-zA-Z0-9!]*");
        boolean validPassword = passwordInput.getText().toString().matches("[a-zA-Z0-9!]*");
        boolean validPasswordMatch = passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString());
        boolean validPasswordLength = passwordInput.getText().toString().length() >= 6;
        boolean validSpinners = questionOne.getSelectedItem() != null && questionOne != null &&
                questionTwo.getSelectedItem() != null && questionTwo != null;
        boolean validAnswers = !answerOne.getText().toString().isEmpty() && !answerTwo.getText().toString().isEmpty();
        boolean validAnswersChars = answerOne.getText().toString().matches("[a-zA-Z0-9]*") && answerTwo.getText().toString().matches("[a-zA-Z0-9!]*");

        if (!validUsernameEmpty) {
            return 1;
        } else if (!validUsername) {
            return 2;
        } else if (!validPassword) {
            return 3;
        } else if (!validPasswordMatch) {
            return 4;
        } else if (!validPasswordLength) {
            return 5;
        } else if (!validSpinners) {
            return 6;
        } else if (!validAnswers) {
            return 7;
        } else if (!validAnswersChars) {
            return 8;
        }else return 0;
    }


    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
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
                    hideSoftKeyboard(AccountCreation.this);
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
