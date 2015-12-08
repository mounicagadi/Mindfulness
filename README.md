# JustBe-Android
The JustBe application, also known as the Mindfulness application, developed for Prof. Mariya Shiyko as semester long project in C4500. For a overview of the components within this Android App, see **__Overview of JustBe Exosystem ~ JustBe-Android__**.

# Development
While you are welcome to use other IDEs while continuing development on this, we highly recommend using Andorid Studio ver >= 2.0. The application is currently stored as a Android Studio App so if you want to use a different IDE there will be more work for you to do. That plus the fact that Android Studio is a fantastic IDE should be enough to deter you.

## Future Development ~ (12/8/2015)
Most of the andorid app has already been completed and the few things that need to be done requrire more specifications from Prof. Shiyko along with rework of the database and django API calls. This is a brief list of things that still need to be done before the App can be used for Prof. Shiyko's Research.

* Save Assesments and Responses: Requires modifications to the database and api calls as the responses have changed significantly since the database was created.
* Give Prof. Shiyko the ability to add questions, lessons, and mediations to the app remotely.

## Bugs 
Please see the Github Issues associated with this repository for all bugs that we know of at this time. NOTE: There are no feature tickets in GitHub Issues as the design specifications are likely to change. 

## Tests
When running the tests you need to run all the tests at once on a emulator without a user currently logged in in order to login and out. To best accomplish this uninstall the app from the emulator you wish to test on and then run the tests. Otherwise the app will save the state, like it would for a user, and you'll end up with failing tests that should pass.

# Overview of JustBe Ecosystem

## JustBe-Android
The andorid verison of the JustBe application consists of the following modules (in no particular order):
* **Activities** Control the user interactions with the UI
* **Layouts:** XML Files defining the UI of the Activities and other view we use
* **Models Dir:** Contains all the models that we use. The variable names on the objects we use to interact with the API have to be named as they are to ensure their data is correctly pulled from the responses from the database.
* **Rest Dir:** Contains all utilities needed to interact with the database.
* **ServerRequests**: Holds all API alls to the database, abstracted so all classes can use them as they please.
* **Session/SessonManager:** Holds the App's current sesson, i.e. User and cookeis, and manages the sharedPreferences. Could be refactored into a single class in the future.
* **PebbleComunicator:** Talks to the Pebble applciation.
* **AlarmReceiver:** Receives the alarms defined earlier in the day when they occuer and push the approprate notificaiton to the user, via either their phone or pebble.
* **AssessmentFlowManager:** Holds and displays all the questions for a assessment session. Saves the responses from each question as it goes through them. Then pushes the results to the database
* **AssessmentFlowManagerFactory:** Facotry used to help create the assessment questions and add them to the AssessmentFlowManager.

## JustBe-iOS
The iOS version of the JustBe applciation. The iOS app shares the same basic UI design as our android app with a few caveats due to the differences in Android and iOS development guidelines. The only major difference in implementations (as of 12/8/2015) is that the iOS app uses a cron job on the database to push notificatons down to the iOS devices using apns keys while on Andorid they are handled locally. 

## JustBe-Django
The database for both the iOS and Andorid apps can be found in this repo. The database itself is hosted on Heroku, please see logins doc from Prof. Shiyko for access. All calls to the database are done through a series of RESTFUL API calls. Be careful if you edit the Django app the indentation is very odd and can cause problems within python if you are not careful.

DO NOT TRUST [apiusage.txt]( https://github.com/justbeneu/JustBe-Django/blob/master/apiusage.txt#L46-L110)! When implementing API calls to the database use curl extensivly to test the calls and their output. As of this moment the apiusage.txt doc contains misinformation that has the potential to cause alot of headaches unless you run all the commands through curl before implementing them. 

For more information on using API calls within the Andorid Application please see the **__API Interactions** section below.

## JustBe-Pebble
The Pebble application to be used in tandem with the iOS/Android App.

## JustBe-Assets
Contains all images, design docs, and other miscellaneous files used during the development of the app. If you add any assest to the app please place them here so all teams can have access to them.

# API Interactions

Interacting with the API should be simple and beautiful. It should handle Errors gracefully, with clear and concise error messages.  It should be repeatable and reusable, and most of all, each API call should require a minimal amount of custom code.

## How to make an API Call

This is how you can make an API Call to the `create_user` endpoint. This endpoint takes a partial User Object and 
returns a partial User Object.  For more details check out the API usage info [here]( https://github.com/justbeneu/JustBe-Django/blob/master/apiusage.txt#L46-L110)

Implementing this in Java is fairly straitforward.

1. Create the User Model, you only need to do this once for each data type you are working with.  See [models/User.java](https://github.com/justbeneu/JustBe-Android/blob/master/app/src/main/java/justbe/mindfulnessapp/models/User.java) for an example.  Most of this is mindless work, adding fields and then auto-generating Getters and Setters.
    * Please remember to keep fields private and create Getters and Setters, they are really important here
    * Jackson2, the tool that takes JSON and converts it into an instance of a model (and vice versa) will map fields based on the getters and setter names. The getter `getFirst_name()` will map to a `first_name` field in the JSON.
    * The django server will ignore any fields it was not expecting so you can theoretically send it a bunch of extrainious info and it will just ignore it.  This is of course less than ideal... working on it, just be aware of this. (Example: you want to update a user's first name, but currently the client will send an entire User object instead of just a `{"first_name": "new name"}`)
2. Add some code like this to your activity's callback function... like `createAccountPressed`.
```java
// A function you write to create a new User Object using the fields available on the Activitiy View
User u = createUser();

// Create an HTTPRequestTask that sends a User Object and Returns a User Object
GenericHttpRequestTask<User, User> task = new GenericHttpRequestTask(User.class, User.class);

// Wrap the following in a try/catch in the event that the request fails for some reason.
try {
    // Execute the request on the given endpoint, Http Method, and with the User object u as the request body.
    task.execute("/api/v1/create_user/", HttpMethod.POST, u);

    // Wait for the request to finish
    ResponseEntity<User> result = task.waitForResponse()

    // Check the response to make sure it was successful (checks HTTP status and body for errors)
    // Will throw an exception with details to present to the user explaining the problem
    RestUtil.checkResponseHazardously(result);
     
    // All is good: Go to the getting stated activity
    Intent intent = new Intent(this, GettingStartedActivity.class);
    startActivity(intent);
} catch (Exception e) {
    // Something bad happened, generate an alert dialog to show to the user
    new UserPresentableException(e).alert(this);
}
```
