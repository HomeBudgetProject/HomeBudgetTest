# HomeBudgetTest
Repository for automatic tests!


### Api - test

#### Get data
##### Register section
	?-> data statuscode 200
		|-> +	register process
		|-> -	verify error message
##### Login section
	|-> try login and get statuscode 
	?-> statuscode 200
		|-> +	login process and get auth_key
				verify whoami
				logout process
##### Delete section
				login process again and get auth_key
				delete account
				verify that session is destroyed
		|-> -	verify error message