FROM adoptopenjdk:8-jre-hotspot

COPY target/Task_Schedueler-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]