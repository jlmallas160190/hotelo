FROM openjdk:8-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/hotelo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} avalith_hotelo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev" ,"-jar","/avalith_hotelo.jar"]