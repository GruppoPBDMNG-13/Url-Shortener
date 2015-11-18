FROM java:latest


RUN apt-get update && apt-get install -y apache2 && apt-get  install -y redis-server

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-3.3.3 /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn




COPY Client /var/www/html/
COPY Server /Server
COPY Script.sh Script.sh

RUN chmod -R 755 /var/www/html 
RUN chmod 777 Script.sh

WORKDIR /Server
RUN mvn package -DskipTests

WORKDIR /

EXPOSE 80 4567 6379
