FROM openjdk:17-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/workflow-control-center-0.0.1-SNAPSHOT-plain.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
