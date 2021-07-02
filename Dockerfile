FROM adoptopenjdk/openjdk8:alpine
WORKDIR /opt
COPY  target/docker-spring-boot.jar /opt
ENTRYPOINT ["java","-jar","docker-spring-boot.jar"]