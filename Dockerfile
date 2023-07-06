FROM openjdk:11
EXPOSE 8080
ADD target/pccw-tech-exam-0.0.1-SNAPSHOT.jar pccw-tech-exam.jar
CMD ["java", "-jar", "/pccw-tech-exam.jar"]