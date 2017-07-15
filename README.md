# mini-twitter
Implementation of the backend of a mini-messaging service inspired by Twitter. The service has a RESTful API and uses Spring Boot.

**MINI TWITTER BACKEND**  
*by Rishabh Ananthan*

This project is the backend of a mini-messaging service inspired by Twitter. It is a RESTful Web Application developed from a Spring Boot project and consists of the following endpoints:

1. GET: View the tweets of the current user and his/her followers  
Usage: http://localhost:8080/message

2. GET: Support of a search parameter that allows to filter tweets in 1  
Usage: http://localhost:8080/message/search=xyz (xyz is to be replaced by the desired string) 

3. POST: Write a new tweet  
Usage: http://localhost:8080/message/content=xyz (xyz is to be replaced by the desired string)

4. GET: List of all users being followed by the current user  
Usage: http://localhost:8080/following 

5. GET: List of all users following the current user  
Usage: http://localhost:8080/followers 

6. GET: List of all users  
Usage: http://localhost:8080/people 

7. POST: Follow a user  
Usage: http://localhost:8080/people/follow/id (id is the integer value ranging from 1 – 11) 

8. POST: Unfollow a user  
Usage: http://localhost:8080/people/unfollow/id (id is the integer value ranging from 1 – 11) 

9. GET: List of all users and most popular follower  
Usage: http://localhost:8080/option2 

***INSTALLATION AND EXECUTION***

1. Download the project. Open a terminal window from the root directory of the project. 

2. Run the following commands IN ORDER:  
    * gradle build  
    * gradle wrapper –gradle-version 4.0  
    * ./gradlew build
    * java -jar build/libs/mini-twitter-v1-0.0.1-SNAPSHOT.jar

*EASY WAY TO RUN*: Just run ./go

3. Once the Spring boot application launches, open Postman. Select Basic authentication. Username is ‘user’. For password, use the  default password that will show up in the terminal window (NOTE: This will change on every execution). 

4. Once username and password have been entered, each endpoint can be run and the desired output will be obtained.

***WORK IN PROGRESS (IN ORDER OF PRIORITY)***

1. Improved login with custom passwords.  

2. Adding an endpoint that returns the current user's "shortest distance" to some other user. The shortest distance is defined as the number of hops needed to reach a user through the users you are following (not through your followers; direction matters). For example, if you follow user B, your shortest distance to B is 1. If you do not follow user B, but you do follow user C who follows user B, your shortest distance to B is 2.  

3. Implementing a front-end for the service (currently on the back burner).
