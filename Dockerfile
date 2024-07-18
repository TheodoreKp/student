FROM openjdk:21-jdk
VOLUME /tmp
COPY target/*.jar school-info-api.jar
ENTRYPOINT ["java","-jar","/school-info-api.jar"]