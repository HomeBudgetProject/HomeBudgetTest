Reset Password

As user I want to reset password

Scenario: As user I want to reset password
Given open reset password page
When enter email 'user@test.com' in Resset page
Then show popup notification 'Some text'