this is a demo project for automation QA engineer.

---------used:  
maven  
Selenium  
bonigarcia wdm  
allure reporter  
cucumber  
testNG  
slf4j logging   
docker

---------implemented:  
UI tests  
parametrized tests  
API tests

---------requirements:  
OS windows
installed java, maven, allure  

---------how to run:  
choose browser   
src/main/resources/test.properties > browser
following are available: chrome, chrome-headless, firefox, edge, and chrome in selenoid container   
launch tests  
cucumber - public class RunCucumberTest  
maven - mvn clean test  
TestNG suite - suite.xml > context menu > run  
see results   
terminal > generate report - allure generate --single-file target/allure-results   
allure-report > index > open in browser

---------troubleshooting:  
often timeout error > increase timeouts.factor in test.properties;  
docker image not found > docker pull <lost image>