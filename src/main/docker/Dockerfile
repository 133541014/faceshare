FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD faceshare-1.0.jar faceshare.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/faceshare.jar"]