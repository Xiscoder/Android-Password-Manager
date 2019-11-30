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

public class AccountCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


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
        setSupportActionBar(toolbar);
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


        ArrayAdapter<CharSequence> adapterQuestionSet1 = ArrayAdapter.createFromResource(this,
                R.array.questionSet1, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterQuestionSet2 = ArrayAdapter.createFromResource(this,
                R.array.questionSet2, android.R.layout.simple_spinner_item);

        adapterQuestionSet1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionOne.setAdapter(adapterQuestionSet1);
        questionOne.setOnItemSelectedListener(this);

        adapterQuestionSet2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionTwo.setAdapter(adapterQuestionSet2);
        questionTwo.setOnItemSelectedListener(this);

        Intent intent = this.getIntent();
        if(intent.hasExtra("USERNAME")) {
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


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidInfo()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(AccountCreation.this).create();
                    alertDialog.setMessage("Error in fields\nEither fields are empty or passwords do not match\nFields can only use upper and lower case characters " +
                            "and the '!' character");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
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
                }
            }

        });
    }

    public boolean isValidInfo() {
        boolean validUsername = !usernameInput.getText().toString().isEmpty() && usernameInput.getText().toString().matches("[a-zA-Z0-9!]*");
        boolean validPassword = passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())
                && passwordInput.getText().toString().length() >= 6 && passwordInput.getText().toString().matches("[a-zA-Z0-9!]*");
        boolean validSpinners = questionOne.getSelectedItem() != null && questionOne != null &&
                questionTwo.getSelectedItem() != null && questionTwo != null;
        boolean validAnswers = !answerOne.getText().toString().isEmpty() && !answerTwo.getText().toString().isEmpty() &&
                answerOne.getText().toString().matches("[a-zA-Z0-9]*") && answerTwo.getText().toString().matches("[a-zA-Z0-9!]*");
        return validUsername && validSpinners && validPassword && validAnswers;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, String text) {
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
