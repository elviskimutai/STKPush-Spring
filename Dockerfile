FROM openjdk:17-jdk-alpine

ENV TZ=Africa/Nairobi
RUN apk update && apk add -U tzdata
RUN cp /usr/share/zoneinfo/Africa/Nairobi /etc/localtime && \
        echo "Africa/Nairobi" > /etc/timezone
RUN mkdir /application
RUN mkdir /application/log
RUN chmod -R 777 /application
WORKDIR /application
COPY ./target /application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /application/app.jar
EXPOSE 7011
ENTRYPOINT ["java","-jar","/application/app.jar"]