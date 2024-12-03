FROM amazoncorretto:21.0.4-alpine
LABEL maintainer="p.marko09@yahoo.com"
COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar /app/medical-clinic-proxy.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/medical-clinic-proxy.jar"]