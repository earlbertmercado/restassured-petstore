# =========================
# Build stage – dependency caching only
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml first for layer caching
COPY pom.xml .

# Download dependencies only
RUN mvn dependency:go-offline -B

# Copy source code (no test execution)
COPY src ./src


# =========================
# Runtime stage – test execution
# =========================
FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

# Copy everything needed to run tests
COPY --from=build /app /app

# Create directories that will be generated at runtime
RUN mkdir -p target reports logs

# Execute tests when container runs
CMD ["mvn", "clean", "test", "-B"]
