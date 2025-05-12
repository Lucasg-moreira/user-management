FROM maven:3.9.9-amazoncorretto-24 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


FROM amazoncorretto:24

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE prod

ENTRYPOINT ["java", "-jar", "app.jar"]