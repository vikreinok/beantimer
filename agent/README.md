<h3>Bean timer</h3>

Agent instruments an application targeting all methods annotated with @Bean in class annotated with @Configuration

The agent will sent collected data to collector application (small spring boot app) periodically. 

**Build agent:**

agent package assembly:single -DskipTests


**Add to VM options:**

-DpackageToMeasure=com.corp.project -javaagent: absolute path \beantimer\target\beantimer.jar -DbeantimerUser=your_name

-----------------------

Upcoming features:
* Output to file (.csv) instead of collector