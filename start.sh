mvn clean install -DskipTests
java -jar backend/target/callcenter-0.0.1-SNAPSHOT.jar --callcenter.callRecordsPath=$CALL_RECORDS_PATH
