FROM openjdk:11
ADD target/daily_operations-0.0.1-SNAPSHOT.jar daily_operations-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/daily_operations-0.0.1-SNAPSHOT.jar"]
