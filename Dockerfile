FROM java:jre-alpine
COPY ./*.jar app.jar
ENTRYPOINT java -jar app.jar
