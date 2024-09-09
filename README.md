this is a demo project for automation QA engineer.

---------stack:  
java 8, maven, selenium, junit5, allure, bonigarcia wdm, docker

---------implemented:  
UI tests  
parametrized tests  
API tests

---------requirements:  
OS windows
installed java, maven, allure  

---------how to run:  
choose browser   
src/main/resources/test.properties > browser > uncomment required
launch tests
maven - mvn clean test
see results   
generate report: allure generate --single-file --clean target/allure-results

---------troubleshooting:  
often timeout error > increase timeouts.factor in test.properties;  
docker image not found > docker pull <lost image>