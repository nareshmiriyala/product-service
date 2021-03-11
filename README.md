# product-service

# Steps to run the application

./gradlew clean build

./gradlew bootRun

To run only tests
./gradlew clean test

# Curl command to Test

curl http://localhost:8080/api/v1.0/product/merge?input=<inputFolder>&output=<outputFolder>

ex:
curl http://localhost:8080/api/v1.0/product/merge?input=/Users/miriyaln/workspace/product-service/src/test/resources/input&output=/Users/miriyaln/workspace/product-service/src/test/resources/output