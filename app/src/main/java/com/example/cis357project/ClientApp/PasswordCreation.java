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

import com.example.cis357project.ClientApp.Password.PasswordContent;
import com.example.cis357project.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PasswordCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI(findViewById(R.id.passwordCreationLayout));

        TextView createPasswordTitle = findViewById(R.id.createPasswordTitle);
        TextView nameView = findViewById(R.id.nameView);
        TextView passwordView = findViewById(R.id.passwordView);
        final EditText nameEditText = findViewById(R.id.nameEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
                    String duo = "-" + nameEditText.getText().toString() + "-" + passwordEditText.getText().toString();
                    boolean duplicate = false;
                    String line = "";
                    try {
                        FileInputStream fis = openFileInput("Data");
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader bufferedReader = new BufferedReader(isr);
                        StringBuffer stringBuffer = new StringBuffer();
                        line = bufferedReader.readLine();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (line != null && !line.isEmpty()) {
                        line = line.substring(1);
                        String[] creds = line.split("-");
                        for (int i = 0; i < creds.length; i += 2) {
                            if (creds[i].equals(nameEditText.getText().toString())) {
                                duplicate = true;
                                Toast.makeText(getApplicationContext(), "You cannot have duplicate names!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    if (!duplicate) {
                        try {
                            FileOutputStream fos = openFileOutput("Data", MODE_APPEND);
                            fos.write(duo.getBytes());
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(PasswordCreation.this, AccountDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Input fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                Toast.makeText(getApplicationContext(), "Input fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }

        });
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
                    hideSoftKeyboard(PasswordCreation.this);
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
