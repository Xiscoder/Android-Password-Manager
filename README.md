# Lock & Key Tutorial
A Password Manager for CIS357 using on Device Storage
<br>
<br>
<strong>Overview of the Lock & Key Application and On-Device Storage</strong>
<br>
<br>
The Lock & Key application allows the user to store a list of the passwords they use directly on their phone. No need for a cloud database, the application is secured directly within the applications data on the device. The primary reason for using the on device storage is because of security. This is mentioned in Androids data and file storage <a href="https://developer.android.com/training/data-storage" target="_blank">(found here)</a>:
<br>
<br>
<strong>App-specific storage: Store files that are meant for your app's use only, either in dedicated directories within an internal storage volume or different dedicated directories within external storage. Use the directories within internal storage to save sensitive information that other apps shouldn't access.</strong>
<br>
<br>
This means that storing data directly on the device (using App-specific storage) is the most secure way of holding information. The only thing that has access to this data is the application itself. This data cannot be access anywhere else outside of the local application on the device.
<br>
<br>
<strong>Gettting started on your app</strong>
<br>
<br>
To start developing your app, we need to have Android Studio installed on our workstation. If you don’t have Android Studio on your workstation then you can go to <a href="https://developer.android.com/studio" target="_blank">this</a> website and follow the instructions there on how to get it set up. One of the pleasant things about this application we will be able to build everything with just Android Studio.
<br>
<br>

