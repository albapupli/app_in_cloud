FROM eclipse-temurin:19-alpine

RUN mkdir -p /var/logs
VOLUME /var/logs
EXPOSE 8080
WORKDIR /backend
ARG JAR_FILE=./target/*.jar
ADD ${JAR_FILE} cloud.jar

ENTRYPOINT ["java","-jar","cloud.jar"]