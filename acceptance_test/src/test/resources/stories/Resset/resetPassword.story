Meta:Reset Password

Narrative:
As user I want to reset password

Scenario: As user I want to reset password
Given open reset password page
When enter email 'qathucydides@testdomain.com' in Resset page and send
Then show popup notification 'Some text'
