FROM 3.9.1-eclipse-temurin-11-alpine as BUILD
WORKDIR /usr/src/myapp/

# To reuse the build cache, here we split maven dependency
# download and package into two RUN commands to avoid cache invalidation.
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /usr/src/myapp/src/
RUN mvn package

FROM eclipse-temurin:17-jdk-alpine
COPY --from=BUILD /usr/src/myapp/target/env-jvm-jersey-0.0.2.jar /app.jar
ENTRYPOINT java ${JVM_OPTS} -Djava.security.egd=file:/dev/./urandom -jar app.jar 8888
EXPOSE 8888
