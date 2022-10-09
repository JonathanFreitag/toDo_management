FROM openjdk:11
COPY target/MitigantToDo.jar MitigantToDo.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","MitigantToDo.jar"]