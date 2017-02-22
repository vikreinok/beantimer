<h3>BeanTimer<h3>

Agent instruments a target application targeting bean initialization time in Configuration annotated classes


The app will print directly into app log. 
Copy paste the data from log. 

TODO file output 

**Build agent:**

package assembly:single -DskipTests


**Add to VM options:**

-javaagent: abolute path \beantimer\target\beantimer.jar
