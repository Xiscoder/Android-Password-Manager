package com.example.cis357project.ClientApp.Password;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.cis357project.ClientApp.AccountDashboard;
import com.example.cis357project.ClientApp.LoginActivity;
import com.example.cis357project.ClientApp.PasswordCreation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PasswordContent {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<Password> ITEMS = new ArrayList<Password>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Password> ITEM_MAP = new HashMap<String, Password>();


    public static void setContext(Context c) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        File file = new File(c.getFilesDir(), "Data");
        if (file.exists()) {
            String line = "";
            try {
                ITEMS.clear();
                FileInputStream fis = c.openFileInput("Data");
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
                Password p;
                for (int i = 0; i < creds.length; i += 2) {
                    String dateCreated = dateFormat.format(new Date());
                    String dateLastSeen = dateFormat.format(new Date());
                    p = new Password(creds[i], creds[i + 1], dateCreated, dateLastSeen);
                    ITEMS.add(p);
                }
            }
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Password {
        public final String name;
        public final String password;
        public String dateCreated;
        public String dateLastSeen;

        public Password(String name, String password, String dateCreated, String dateLastSeen) {
            this.name = name;
            this.password = password;
            this.dateCreated = dateCreated;
            this.dateLastSeen = dateLastSeen;
        }

    }
}
