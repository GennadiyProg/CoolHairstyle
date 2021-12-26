FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} coolhairstyle.jar
ENTRYPOINT ["java", "-jar", "/coolhairstyle.jar"]