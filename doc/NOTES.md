NOTES
=====

# Feature backlog
* RESTful service invocation architecture using content providers
* Retrofit integration - done 12/29
* Android Annotations integration - done 12/29
* Navigation drawer - done 12/29
* Invalidate token on logout
* Screens
  * Splash screen - done 12/29
  * Login screen - done 12/29
  * Dashboard screen
  * Register screen


---

# Interesting Open Source Projects
```
Android Query for easy UI manipulation - http://code.google.com/p/android-query/
Android Menu Drawer - https://github.com/SimonVT/android-menudrawer

Square:
Retrofit for REST
Otto Event Bus
Dagger for DI (compile time)
Tape for Persistent Task Queueing

JSON Path for XPath-like queries on JSON - https://code.google.com/p/json-path/
ORMLite - http://ormlite.com/
RoboGuice for DI - https://github.com/roboguice/roboguice

Robotium - testing - http://www.vogella.com/articles/AndroidTesting/article.html
Robolectric in JVM unit testing - http://robolectric.org/

Crash reporting - https://www.bugsense.com/docs/android#acra
Google analytics for user engagement

AppBrain stats - http://www.appbrain.com/stats/libraries/dev
```


---

# Quick commands

```
mvn android:deploy
mvn android:apk

# Sonar commands
sonar.sh start
mvn sonar:sonar
http://localhost:9000

# Get an access token and display trace to console
curl -vv -X POST -H "Content-Type: application/x-www-form-urlencoded" -d grant_type=password -d username=aweeraman@gmail.com -d password=weeraman -d client_secret=ede2105b-049d-4d3b-878b-7a3a4ec0427f -d client_id=a26bdb49-a557-451a-9ebf-8965b94d9e66 -u a26bdb49-a557-451a-9ebf-8965b94d9e66:ede2105b-049d-4d3b-878b-7a3a4ec0427f http://anuradha.local:8080/api/oauth2/token --trace-ascii -
```

# Problems and resolutions

### Title
Description
```
code
```
