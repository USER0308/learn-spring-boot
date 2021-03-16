FROM openjdk:8
VOLUME /web
ADD learn-spring-boot-1.0-SNAPSHOT.jar /web/app.jar
ADD application-docker.yml /web/config/application.yml
CMD ["java", "-jar", "/web/app.jar", "--spring.config.location=/web/config/application.yml"]

