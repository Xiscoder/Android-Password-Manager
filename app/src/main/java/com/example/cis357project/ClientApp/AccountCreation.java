package com.example.cis357project.ClientApp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cis357project.R;

import org.w3c.dom.Text;

public class AccountCreation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView createAccountTitle = findViewById(R.id.createAccountTitle);
        TextView usernameView = findViewById(R.id.usernameView);
        TextView passwordView = findViewById(R.id.passwordView);
        TextView confirmationView = findViewById(R.id.confirmationView);
        TextView securityQuestionOneView = findViewById(R.id.securityQuestionOneView);
        Spinner questionOne = findViewById(R.id.questionOne);
        EditText answerOne = findViewById(R.id.answerOne);
        TextView securityQuestionTwoView = findViewById(R.id.securityQuestionTwoView);
        Spinner questionTwo = findViewById(R.id.questionTwo);
        EditText answerTwo = findViewById(R.id.answerTwo);
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        EditText confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
