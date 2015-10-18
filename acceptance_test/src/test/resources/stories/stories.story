Authorization
As user I want to register
As user I want to login with my credentials
As user I want to reset password


Scenario: As user I want to register
Given open registration page
When enter email 'qatestemail@testdomain.com' password 'Qwerty123456' and send
Then show popup notification 'You have successfully registered'


Scenario: As user I want to login with my credentials
Given open login page
When enter email 'user@test.com' password 'qwerty1' in Login page
Then redirect to main page
And: in menu displays the caption 'user@test.com' in page

Scenario: As user I want to reset password
Given open resset password page
When enter email 'user@test.com' in Resset page
Then show popup notification 'Some text'