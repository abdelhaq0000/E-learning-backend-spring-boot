
FROM maven:3.8.3-openjdk-17 as build

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn clean install -DskipTests

FROM openjdk:17-alpine

ARG JAR_FILE=AuthMongoDB-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=build /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","AuthMongoDB-0.0.1-SNAPSHOT.jar"]