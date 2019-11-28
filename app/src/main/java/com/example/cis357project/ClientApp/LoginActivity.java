package com.example.cis357project.ClientApp;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis357project.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView forgottenPassword = findViewById(R.id.forgottenPassword);
        final Button register = findViewById(R.id.CreateAccount);


        forgottenPassword.setPaintFlags(forgottenPassword.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);


        loginButton.setOnClickListener(new View.OnClickListener() {
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
                        if(creds[0].equals(usernameEditText.getText().toString()) && creds[1].equals(passwordEditText.getText().toString())){
                            Intent intent = new Intent(LoginActivity.this, AccountDashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect login credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "No account exists for this device", Toast.LENGTH_SHORT).show();
                }

                }
            });

        register.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                File file = new File(getApplicationContext().getFilesDir(), "AccountDetails");
                if (file.exists()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setMessage("Cannot create account \nAccount already exists");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, AccountCreation.class);
                    startActivity(intent);
                }
            }
            });
        }


    }
