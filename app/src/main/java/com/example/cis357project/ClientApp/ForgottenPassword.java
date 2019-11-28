package com.example.cis357project.ClientApp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
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

        TextView forgottenPassword = findViewById(R.id.forgottenPasswordTitle);
        TextView usernameViewFPW = findViewById(R.id.usernameViewFPW);
        TextView securityQuestionOneFPW = findViewById(R.id.securityQuestionOneFPW);
        EditText securityAnswerOneFPW = findViewById(R.id.securityAnswerOneFPW);
        TextView securityQuestionTwoFPW = findViewById(R.id.securityQuestionTwoFPW);
        EditText securityAnswerTwoFPW = findViewById(R.id.securityAnswerTwoFPW);
        EditText usernameInputFPW = findViewById(R.id.usernameInputFPW);
        Button submitButtonFPW = findViewById(R.id.submitButtonFPW);
    }

}
