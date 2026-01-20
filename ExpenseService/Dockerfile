FROM amazoncorretto:21
LABEL authors="yashoda-adarsh"

WORKDIR /app

COPY build/libs/*.jar expenseservice.jar

EXPOSE 9898

ENTRYPOINT ["java", "-jar", "expenseservice.jar"]
