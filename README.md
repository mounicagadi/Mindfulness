# JustBe-Android
Android version of the JustBe app, also known as the Mindfulness app. 
Probably advisable to use Android Studios while editing the code.


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
