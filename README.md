<h3>Bean timer</h3>

Have You ever wondered what takes up all the time during application startup? Well, a peace of the puzzle could be couple of beans which take too much time to initialize. 

... is a small tool which measures bean initialization time and order during application startup. 

There is a small spring boot app which collects and presents all data. 


How to use bean timer?


**Build agent:**

* mvn clean package -DskipTests
* cd agent 
* mvn clean package assembly:single -DskipTests


**Add to VM options:**
 * -DpackageToMeasure=com.corp.project -javaagent: abolute path \beantimer\target\beantimer.jar

**Start collector app**

* cd ..
* cd collector
* mvn spring-boot:run (uses in memory H2 DB)

-----------------------

Now, just run your integration tests or do some manual testing with app redeploys.
