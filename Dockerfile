FROM openjdk:8

ADD /target/office-auth-service-0.0.1-SNAPSHOT.jar office-auth-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "office-auth-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8087