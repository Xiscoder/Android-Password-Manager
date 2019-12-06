# Lock & Key Tutorial
A password manager for android users using on-device storage options
<h2>Overview of the Lock & Key Application and On-Device Storage</h2>
The Lock & Key application allows the user to store a list of the passwords they use directly on their phone. No need for a cloud database, the application is secured directly within the applications data on the device. The primary reason for using the on device storage is because of security. This is mentioned in Androids API about file and data storage <a href="https://developer.android.com/training/data-storage" target="_blank">found here</a>:
<br>
<br>
<strong>App-specific storage: Store files that are meant for your app's use only, either in dedicated directories within an internal storage volume or different dedicated directories within external storage. Use the directories within internal storage to save sensitive information that other apps shouldn't access.</strong>
<br>
<br>
This means that storing data directly on the device (using App-specific storage) is the most secure way of holding information. The only thing that has access to this data is the application itself. This data cannot be access anywhere else outside of the local application on the device.
<h3>Pictures of Application Usage</h3>
First the user is greeted by a login screen. For the following examples we're going to assume the user already has an account on the phone. For this application <strong>ONLY ONE</strong> account can be on a single phone. We do not allow multiple accounts because we assume that a phone is a personal device that a single person will be using.
<br>
<br>
![this screenshot](/tutorialImages/loginScreen.JPG)
<br>
<strong>Figure 1:</strong> Login Screen
<br>
<br>
The user is then taken to the dashboard of the application where they can see their passwords. Currently the user does not have any passwords. So upon clicking add button (+ button) it will take the user to a password creation screen.
<br>
<br>
![this screenshot](/tutorialImages/dashboard.JPG)
<br>
<strong>Figure 2:</strong> Dashboard
<br>
<br>
![this screenshot](/tutorialImages/passwordCreatePage.JPG)
<br>
<strong>Figure 3:</strong> Password Creation Page
<br>
<br>
From here the user can create a password. Once this is done the dashboard is updated. In this example I've created two passwords. The passwords are identified by Password: Name of Password.
<br>
<br>
![this screenshot](/tutorialImages/showingPasswordsDashboard.JPG)
<br>
<strong>Figure 4:</strong> Showing Passwords On Dashboard
<br>
<br>
The user can then click on a password and for which they will be taken to a password edit screen where they can then toggle an editting mode and update their password.
<br>
<br>
![this screenshot](/tutorialImages/showingPasswordEdit.JPG)
<br>
<strong>Figure 5:</strong> Password Editing
<br>
<br>
The last thing a user can do from the dashboard (other than logging out) is updating their login creditentials for the app itself. This is the same page as the initial account creation page accessed from the Create Account button on the login screen. The user can edit their login credentials here.
<br>
<br>
![this screenshot](/tutorialImages/updatingAccountInfo.JPG)
<br>
<strong>Figure 6:</strong> Account Creation/Updating
<br>
<br>
If the user has forgotten their account, there is a forgot password button on the main login screen which takes them to the a forgotten password display. Once they fill out the information a notification pops up that shows the user what their password is.
<br>
<br>
![this screenshot](/tutorialImages/forgottenPasswordScreen.JPG)
<br>
<strong>Figure 7:</strong> Forgotten Password Screen
<h3>Pictures of Data Storage</h3>
When the application is launched. You can open up the device file explorer on Android Studio and it will show you all of the files on that device.
<br>
<br>
![this screenshot](/tutorialImages/deviceFileExplorer.JPG)
<br>
<strong>Figure 8:</strong> Device File Explorer
<br>
<br>
From here you can navigate down to the folder data/data/com.example.PROJECTNAME/files/ to the two data files if the application has created them. We have the AccountDetails file for the users account details, and a Data file for the passwords saved onto the application. The data is stored just as text files with strings.
<br>
<br>
![this screenshot](/tutorialImages/locationOfFiles.JPG)
<br>
<strong>Figure 9:</strong> Location of Files on File Explorer
<br>
<br>
![this screenshot](/tutorialImages/passwordStorage.JPG)
<br>
<strong>Figure 10:</strong> Password Storage
<br>
<br>
![this screenshot](/tutorialImages/accountDetailsStorage.JPG)
<br>
<strong>Figure 11:</strong> Account Details Storage
<h2>Gettting started on your app</h2>
To start developing your app, we need to have Android Studio installed on our workstation. If you don’t have Android Studio on your workstation then you can go to <a href="https://developer.android.com/studio" target="_blank">this</a> website and follow the instructions there on how to get it set up. One of the pleasant things about this application we will be able to build everything with just Android Studio. Once you have Android studio properly installed, we can start building the app!
<br>
<br>
<h2>Step by step instructions</h2>
Let’s start by creating a few activities and then layout these activities. Once we have done this we can then move onto the functionality of our app
<br>
<br>
First, we create an activity for the login. We then lay it out as shown above in Figure 1 and then we wire up (shown below) the various fields to the login activity.

```java
public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI(findViewById(R.id.loginLayout));


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView forgottenPassword = findViewById(R.id.forgottenPassword);
        final Button register = findViewById(R.id.CreateAccount);
```     

Another thing we can do here do make our activities or screens more user-friendly is to hide the keyboard when we touch outside one of the fields. We can do this simply by using these two methods, setupUI and hideSoftKeyboard, to listen for a touch from the user outside of the field and then to hide the keyboard. These methods are shown below.

```java
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
                    hideSoftKeyboard(LoginActivity.this);
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
```

Now we can move onto what we are centered on showcasing in this app and that is the on-device storage. When we hit the Login button on the on the LoginActivity we want to search our app’s storage to see if there is even an account on this device and if there is, if our username and password match. Before we begin to mess with the file system of your app it would be very beneficial to read the Android documentation about File Storage on Android. There they talk about best practices for reading and writing to the internal file system <a href="https://developer.android.com/training/data-storage/app-specific" target="_blank">found here</a>:
<br>
<br>
In our application we store the master account info inside a file called “AccountDetails”. This file holds all information about the user in one place which is a good design pattern we believe as the data surrounding the object (the user) should be kept together. 
<br>
<br>
So, when we hit login, we first need to check if the file “AccountDetails” exist. We believe it to be good practice whenever you are reading from a file to check it exists first for the safety of your app. If our file exists, then we want to read from it. We will do so using a File Input Stream, an Input Stream Reader and a buffered reader. This is way that the Android Documentation went about doing this and so we believe it to be the optimal method. Each of these three tools needs an import:

```java
    import java.io.BufferedReader;
    import java.io.FileInputStream;
    import java.io.InputStreamReader;
```
<br>
<br>
To read from the files from the device you will be following the standard Java IO. The code for checking if an account exists and information is valid for the user is shown below. We have this logic check when the Login button is clicked. 

```java
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
                        if (creds[0].equals(usernameEditText.getText().toString()) &&                       creds[1].equals(passwordEditText.getText().toString())) {
                            Intent intent = new Intent(LoginActivity.this, AccountDashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect login credentials", Toast.LENGTH_SHORT).show();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No account exists for this device", Toast.LENGTH_SHORT).show();
                }
            }
        });
```  
<br>
<br>
In summary, to read from files on the Android device you use the standard Java IO. First, check if the file exists. If it does exist, read the data from the file into a string array. We then check the credientials in the file to see if they match what the user has submitted. If it's valid transition to the dashboard. Otherwise show the error "Incorrect login credentials". Otherwise, if the file does not exist an error will appear saying "No account exists for this device".At this point, the only option the user would have would be to create an account.
<br>
<br>
Once this page is done, allowing the user to create an account comes next. You will want to use an intent to reach the account creation activity you have created (or will create). The account creation screen should look something like Figure 6. Create the fields to allow the user to enter a Username, Password, Password Confirmation and two Security Questions with two fields for an answer.
<br>
<br>
Everything is fairly standard on this activity other than two factors: The security question selection, and the save button. First we will cover the creation of the security question selectors. We used Android Spinners. The following video will explain how to use text spinners if you do not know how to do so. 
<br>
<br>
<iframe width="420" height="315"
src="https://www.youtube.com/embed/on_OrrX7Nw4">
</iframe>
<br>
<br>
The save button is our primary focus though because this requires us to save information to a file on the device. When the save button is hit, we have the following actionListener activated.

```java
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
 ```
<br>
<br>
First we check if the input provided by the user is valid. This is done with a helper method called isValidInfo. You can create your own validation or use ours. This code is shown below.

```java
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
```
<br>
<br>
Essentially when the save button is hit, we use a switch statement with a function call to check whether the information is valid. We have different cases for whether or not it's valid - however, if the information is valid the statement case returned is 0. If the data is valid we create the string to add to the file that shall be added onto the device holding the given account information. We use the dashes to split the information. This is done because when the data is retrieved we need a way to split the string of information. Once the string has been created you simply stream the data to the file called "AccountDetails" onto the device. Since this file does not exist, it creates the file. Then the program writes the data to the newly created file and closes the stream. Next we take the user to their new dashboard using an Intent.
<br>
<br>
The dashboard simply displays the password information that the user has stored on their phone. Right now it's empty. We will come back to this activity to add the ability to see passwords later. For now add an intent to the add button and link this to the add password functionality. 
<br>
<br>
The primary idea behind the code of the add password activity is the logic behind actually adding the code to another file called "Data" that stores the users password data. The way the information is saved works exactly as it did when creating a users account. The following code was used to save the information given by the user into the Data file.

```java
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
```
<br>
<br>
The only difference is that first we read the file and check for duplicate strings. If there are duplicate names a notification is shown saying "You cannot have duplicate names!". Otherwise, if the input is different it appends the data to the file. One thing to make sure you do is tell the file output stream to APPEND the data to the file and not overwrite it. When creating the file output stream make sure to use openFileOutput("Data", MODE_APPEND); functionality otherwise you will be overwritting the data in the file. This is different from the MODE_PRIVATE used in the account creation page. Now we have an application that can save the passwords that the user wants. We need a way of displaying this information. So let's go back to the dashboard and add some additional functionality.
