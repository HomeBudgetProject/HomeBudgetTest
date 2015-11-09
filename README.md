# HomeBudgetTest
Repository for automatic tests!

### To successfully create [Allure](http://allure.qatools.ru/) report, use the latest version of [Maven](https://maven.apache.org/download.cgi)

![alt text](https://wiki.jenkins-ci.org/download/attachments/327683/JENKINS?version=1&modificationDate=1302750804000 "Jenkins")
## Api - test
Testing API with [REST-assured](https://github.com/jayway/rest-assured) framework.

![alt text](https://github.com/jayway/rest-assured/blob/master/rest-assured-logo-green.png "REST-assured")

![alt text](http://allure.qatools.ru/img/allure-logo.png "Allure report") [API report](http://52.19.25.73/ci/job/home-budget-api-test/Allure_Report/)

## Functional tests

![alt text](http://www.seleniumhq.org/images/selenium-logo.png "Selenium")
![alt text](https://docs.saucelabs.com/images/sauce-labs.bbed5298.png "sauce-labs")

For local run execute

` --projects functional_test clean test site`


The default browser is Firefox. To change the browser, use the command

` -DinstanceBrowser=`

Available browsers:

+ Firefox
+ Chrome

Additionally set the path of the drive (eg Firefox)

` -Dwebdriver.chrome.driver=`


Additionally, see [here](https://code.google.com/p/selenium/wiki/UsingWebDriver)


Local reports can be viewed using Jetty

` jetty:run`

![alt text](http://allure.qatools.ru/img/allure-logo.png "Allure report") [Functional tests report](http://52.19.25.73/ci/job/homebudget-functional-test/Allure_Report/)


## Acceptance tests

![alt text](http://www.thucydides.info/img/serenity-logo.jpg "Serenity BDD")

[Acceptance tests report](http://52.19.25.73/ci/job/home-budget-acceptance-test/Serenity_Report/)

