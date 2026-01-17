FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean test -B

FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

COPY --from=build /app/target ./target
COPY --from=build /app/reports ./reports
COPY --from=build /app/logs ./logs

COPY --from=build /app/pom.xml .
COPY --from=build /app/src ./src

RUN mkdir -p reports logs

CMD ["mvn", "clean", "test", "-B"]
