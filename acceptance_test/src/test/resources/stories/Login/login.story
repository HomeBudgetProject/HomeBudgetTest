Login

As user I want to login with my credentials

Scenario: As user I want to login with my credentials
Given open login page
When enter email 'user@test.com' password 'qwerty1' in Login page and send
Then redirect to main page
And: in menu displays the caption 'user@test.com' in page