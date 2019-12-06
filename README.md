# Lock & Key Tutorial
A Password Manager for CIS357 using on Device Storage
<h2>Overview of the Lock & Key Application and On-Device Storage</h2>
The Lock & Key application allows the user to store a list of the passwords they use directly on their phone. No need for a cloud database, the application is secured directly within the applications data on the device. The primary reason for using the on device storage is because of security. This is mentioned in Androids data and file storage <a href="https://developer.android.com/training/data-storage" target="_blank">(found here)</a>:
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
First, we create an activity for the login. We then lay it out as shown above in figure 1 and then we wire up (shown below) the various fields to the login activity.
<br>
<br>
```
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
<br>
<br>
Another thing we can do here do make our activities or screens more user-friendly is to hide the keyboard when we touch outside one of the fields. We can do this simply by using these two methods, setupUI and hideSoftKeyboard, to listen for a touch from the user outside of the field and then to hide the keyboard. These methods are shown below in Figure 13
<br>
<br>
![this screenshot](/tutorialImages/HideKeyboardLogic.JPG)
<br>
<strong>Figure 13:</strong> Methods to hide keyboard
<br>
<br>
Now we can move onto what we are centered on showcasing in this app and that is the on-device storage. When we hit the Login button on the on the LoginActivity we want to search our app’s storage to see if there is even an account on this device and if there is, if our username and password match. Before we begin to mess with the file system of your app it would be very beneficial to read the Android documentation about File Storage on Android. There they talk about best practices for reading and writing to the internal file system<a href="https://developer.android.com/training/data-storage/app-specific" target="_blank">(found here)</a>:
<br>
<br>
In our application we store the master account info inside a file called “AccountDetails”. This file holds all information about the user in one place which is a good design pattern we believe as the data surrounding the object (the user) should be kept together. 
<br>
<br>
So, when we hit login, we first need to check if the file “AccountDetails” exist. We believe it to be good practice whenever you are reading from a file to check it exists first for the safety of your app. If our file exists, then we want to read from it. We will do so using a File Input Stream, an Input Stream Reader and a buffered reader. This is way that the Android Documentation went about doing this and so we believe it to be the optimal method. Each of these three tools needs an import:
```
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
```





