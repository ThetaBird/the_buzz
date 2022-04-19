# Administrative Interface
TESTS - Review performed 3/28
BACKEND
-All Routes are now being tested in RoutesTest.java
-Requests are being tested, along with the tables in the database in RequestTest.java
-AppTest.java can be deleted, holds no purpose
WEB
-Jasmine Tests for Logic were to be implemented
-Jasmine Tests for the UI were to be implemented
MOBILE
-Flutter Tests for Logic were to be implemented
-Flutter Tests for the UI were to be implemented
The Mobile and Web tests were to be similar in what was being tested
ADMIN
-Unit Test for the database has been implemented in AppTest.java



heroku login

deploy locally:
https://www.12factor.net/dev-prod-parity
https://devcenter.heroku.com/articles/heroku-postgresql
instructions to run postgres locally:
    - postgres.app: https://postgresapp.com
    - also need postgres CLI tools installed
    1) createdb /*only need to do this once*/

    2) psql -h localhost
    3) export DATABASE_URL=postgres://$(laurenwasserman)
    4) Then add to the local database
    5) git push heroku master (cse216-group4-app?)

deploy heroku:
mvn clean heroku:deploy
heroku open --app cse216-group4-app