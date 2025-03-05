FROM eclipse-temurin:21


LABEL mentainer="avishake"

WORKDIR /app

COPY target/common-library-0.0.1-SNAPSHOT.jar /app/common-library.jar

ENTRYPOINT [ "java" ,"-jar","common-library.jar"]

