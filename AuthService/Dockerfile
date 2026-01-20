FROM amazoncorretto:21
LABEL authors="yashoda-adarsh"

WORKDIR /app

COPY build/libs/*.jar authservice.jar

EXPOSE 9898

ENTRYPOINT ["java", "-jar", "authservice.jar"]
