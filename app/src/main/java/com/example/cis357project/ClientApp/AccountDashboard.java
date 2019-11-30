package com.example.cis357project.ClientApp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
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

import com.example.cis357project.ClientApp.Password.PasswordContent;
import com.example.cis357project.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class AccountDashboard extends AppCompatActivity implements PasswordFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI(findViewById(R.id.accountDashboardLayout));

        PasswordContent.setContext(this.getApplicationContext());

        TextView passwordTitle = findViewById(R.id.passwordTitle);
        TextView updateAccountText = findViewById(R.id.updateAccountText);
        Button addPasswordButton = findViewById(R.id.addPasswordButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        updateAccountText.setPaintFlags(updateAccountText.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);

        addPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(AccountDashboard.this, PasswordCreation.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(AccountDashboard.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        updateAccountText.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent (AccountDashboard.this, AccountCreation.class);
                File file = new File(getApplicationContext().getFilesDir(), "AccountDetails");
                if (file.exists()) {
                    try {
                        FileInputStream fis = openFileInput("AccountDetails");
                        InputStreamReader isr = new InputStreamReader(fis);

                        BufferedReader bufferedReader = new BufferedReader(isr);
                        StringBuffer stringBuffer = new StringBuffer();
                        String line = bufferedReader.readLine();

                        String[] creds = line.split("-");
                        intent.putExtra("USERNAME", creds[0]);
                        intent.putExtra("PASSWORD", creds[1]);
                        intent.putExtra("SECURITYQ1", creds[2]);
                        intent.putExtra("SECURITYQ2", creds[3]);
                        intent.putExtra("SECURITYA1", creds[4]);
                        intent.putExtra("SECURITYA2", creds[5]);
                        intent.putExtra("RENAMEBUTTON", "Save");
                        intent.putExtra("TITLEFIX", "Update Account");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(intent);
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
                    hideSoftKeyboard(AccountDashboard.this);
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

    @Override
    public void onListFragmentInteraction(PasswordContent.Password item) {
        Intent toDetails = new Intent(this, PasswordEditViewActivity.class);
        toDetails.putExtra("USERNAME", item.name);
        toDetails.putExtra("PASSWORD", item.password);
        toDetails.putExtra("DATECREATED", item.dateCreated);
        toDetails.putExtra("DATELASTSEEN", item.dateLastSeen);
        startActivity(toDetails);
    }
}
