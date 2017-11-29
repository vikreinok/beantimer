<h3>Bean timer</h3>

Have You ever wondered what takes up all the time during application startup? Well, a peace of the puzzle could be couple of beans which take too much time to initialize. 

... is a small tool which measures bean initialization time and order during application startup. 

There is a small spring boot app which collects and presents all data. 


How to use the bean timer?


**Build agent:**

* mvn clean package -DskipTests
* cd agent 
* mvn clean package assembly:single -DskipTests

**Add to VM options:**
 * -DpackageToMeasure=com.corp.project -javaagent: absolute path \beantimer\target\beantimer.jar
 * -DbeantimerUser=your_name

**Start collector app or...**
* cd ..
* cd collector
* mvn spring-boot:run (uses in memory H2 DB)

**Use the remote collector at http://84.52.54.143:9999**

-----------------------

Now, just run your integration tests or do some manual testing with app redeploys.
Stats will be available at http://84.52.54.143:9999 or your local collector

                                                       +---------------------+
     +-------------------------------+         +-------+  Agent instruments  |
     |                               |         |       |  Spring benas       |
     |   Your Spring application     <---------+       +---------+-----------+
     |                               |                           |
     +-------------------------------+                           |
                                                 +---------------+
                                                 |
                                                 |
                               +-----------------v----------------+
                               |                                  |
                               |           Collector              |
                               |   http://84.52.54.143:9999       |
                               |                                  |
                               |                                  |
                               |         Has web view for         |
                               |           data analysis          |
                               |                                  |                                 |
                               |                                  |
                               |                                  |
                               +----------------------------------+




------------------------

Upcoming features:
* Gantt plot (beans x loading time)
* ~~Remote collector by username (-DbeantimerUser={username)~~
* ~~Bean scope indicators (singleton, prototype)~~ DONE
