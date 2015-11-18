#!bin/bash
redis-server &
service apache2 start
cd /Server
java -jar target/Url-Shortener-1.0.jar

