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

import org.w3c.dom.Text;

public class PasswordCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView createPasswordTitle = findViewById(R.id.createPasswordTitle);
        TextView nameView = findViewById(R.id.nameView);
        TextView passwordView = findViewById(R.id.passwordView);
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);
    }

}
