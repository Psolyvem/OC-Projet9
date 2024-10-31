FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=medilabo
ENV MYSQL_USER=Admin
ENV MYSQL_PASSWORD=Admin

COPY database.sql /docker-entrypoint-initdb.d/

EXPOSE 3306