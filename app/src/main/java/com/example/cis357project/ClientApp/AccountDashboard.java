package com.example.cis357project.ClientApp;

import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cis357project.R;

public class AccountDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView passwordTitle = findViewById(R.id.passwordTitle);
        TextView updateAccountText = findViewById(R.id.updateAccountText);
        Button addPasswordButton = findViewById(R.id.addPasswordButton);

        updateAccountText.setPaintFlags(updateAccountText.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);
    }

}
