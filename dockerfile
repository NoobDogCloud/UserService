FROM openjdk:19-jdk-alpine3.16
COPY ./target/user-service-3.0.0-jar-with-dependencies.jar /home/app/
WORKDIR /home/app
ENTRYPOINT ["java", "-Dfile.encoding=utf-8", "-jar", "user-service-3.0.0-jar-with-dependencies.jar"]
