Meta:Register

Narrative:
As unregistered user I want to be able to create an account then take access to the site functionality
As unregistered user if I try to enter the existing data then I getting a warinig message 'This email is already taken'
As unregistered user if I try to enter email which consists of 5 symbols then I getting a warning message 'Email должен содержать от 6 до 60 символов'
As unregistered user if I try to enter a space in the email field then I getting a warning message 'Введите Email'
As unregistered user if I try to enter password which consists of 5 symbols then I getting a warning message 'Пароль должен содержать от 6 до 100 символов'
As unregistered user if I try to enter not valid email then I getting a warning message 'Email is not valid'

Scenario: As unregistered user I want to be able to create an account then take access to the site functionality
Given open registration page
When enter email 'qathucydides@testdomain.com' password 'Qwerty123456' and send
Then show warning notification 'Вы успешно зарегистрированы' on Register page
And redirect to main page

Scenario: As unregistered user if I try to enter the existing data then I getting a warinig message 'This email is already taken'
Given open registration page
When enter email 'qathucydides@testdomain.com' password 'Qwerty123456' and send
Then show warning notification 'This email is already taken' on Register page

Scenario:As unregistered user if I try to enter email which consists of 5 symbols then I getting a warning message 'Email должен содержать от 6 до 60 символов'
Given open registration page
When enter email 'q@a.c' password 'Qwerty123456'
Then show email warning notification 'Email должен содержать от 6 до 60 символов' on Register page

Scenario: As unregistered user if I try to enter a space in the email field then I getting a warning message 'Введите Email'
Given open registration page
When enter email ' ' password 'Qwerty123456'
Then show email warning notification 'Введите Email' on Register page

Scenario: As unregistered user if I try to enter password which consists of 5 symbols then I getting a warning message 'Пароль должен содержать от 6 до 100 символов'
Given open registration page
When enter email 'qatestemail@testdomain.com' password '12345'
Then show password warning notification 'Пароль должен содержать от 6 до 100 символов' on Register page

Scenario: As unregistered user if I try to enter not valid email then I getting a warning message 'Email is not valid'
Given open registration page
When enter email 'qate$temail@testdomain.com' password 'Qwerty123456' and send
Then show warning notification 'Email is not valid' on Register page

