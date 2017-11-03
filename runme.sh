mvn clean package
mvn -f pom_client.xml package
mv target/client-1.0.jar .
mv target/server-1.0.jar .
mvn clean
