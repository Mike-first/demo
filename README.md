### This is a demo project for automation QA engineer

#### stack
java 8, maven, selenium, junit5, allure, bonigarcia wdm, docker

#### implemented
UI tests  
parametrized tests  
API tests  

#### requirements
installed java, maven, docker, appium 

#### how to run
choose browser   
src/main/resources/test.properties > browser > uncomment required  
launch tests: `mvn clean test`

generate report: 
```bash 
allure generate --single-file --clean target/allure-results
```

#### troubleshooting
1. often timeout error > increase `timeouts.factor` in `webtest.properties`;  
2. docker image not found > `docker pull <lost image>`