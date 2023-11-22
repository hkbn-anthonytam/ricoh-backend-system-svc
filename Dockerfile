FROM openjdk:17 as builder
COPY .mvn .mvn                                               
COPY mvnw .                                                  
COPY pom.xml .  
COPY src src
RUN ./mvnw -f pom.xml clean package 

FROM openjdk:17
WORKDIR /app
COPY --from=builder /target/*.jar api.jar
RUN groupadd api-user
RUN useradd -u 1001 -g api-user api-user
USER api-user
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar api.jar"]