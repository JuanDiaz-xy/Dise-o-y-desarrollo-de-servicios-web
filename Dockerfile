# Etapa 1: Maven compila el proyecto y empaqueta ROOT.war.
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q clean verify

# Etapa 2: Tomcat 10 publica el WAR en el contexto raíz.
FROM tomcat:10.1-jre17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
ENV PORT=10000
EXPOSE 10000
# Render establece PORT; Tomcat debe escuchar el mismo puerto en 0.0.0.0.
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-10000}\\\"/\" /usr/local/tomcat/conf/server.xml && exec catalina.sh run"]
