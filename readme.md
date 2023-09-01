## Library application
You can use my application for working with books and users in a library. You can:
* add a new user
* look all users
* update the user
* delete the user
* look which books the user has taken
* add a new book
* look all books in a library
* update the book
* delete the book
* look who has taken the book
* release the book to a library
* appoint the book to a user 
## Installing
You have to install:
1. JDK 20 or higher - https://bell-sw.com/pages/downloads/
2. Apache Tomcat version 10 or higher. - https://tomcat.apache.org/download-10.cgi
3. For starting tomcat server I use plugin Smart Tomcat version 4.6.1 or higher in Intellij Idea.\
You should add a new configuration for running from this plugin with next settings:
```
Content path - /
Server port - 8080
Admin port - 8005
Tomcat Server - usin our folder with Tomcat server
```
4. PostgreSQL version 14 or higher. - https://www.postgresql.org/download/
5. I use plugin Database Navigator for working with DB in Intellij Idea. \
Settings:
```
DB Browser -> Green Plus
Host - postgres
Port - 5432
Database - name of your DB. My is library_db.
User - your username in postgres.
Password - your password for DB.
```
After it you can work with consoles. Don't forget to choose public schema in header of console and do Auto-Commit ON on connection by clicking right button.
##Settings for DB.
For DB we use file database.properties in folder "resources". You can use the sample file database.properties.sample.
Sample for values of keys.
```
username=postgres
password=0000
url=jdbc:postgresql://localhost:5432/library_db
driver=org.postgresql.Driver
```
Before working you have to create database with tables. All sql-code you find in folder /src/main/sql
## Application logic
The application has some main pages. 
###The first main page for library users. 
Her path is http://localhost:8080/people - On this page you can add a new user and watch all library users.\
http://localhost:8080/people/new - path for adding a new user.\
http://localhost:8080/people/{id}/edit - path for updating the user.\
http://localhost:8080/people/{id} - GET method, show the user.\
http://localhost:8080/people/{id} - DELETE method, delete the user from DB.

### The main page for work with books.
http://localhost:8080/books - the main path.
http://localhost:8080/books/new - path for adding a new book.\
http://localhost:8080/books/{id}/edit - path for updating the book.\
http://localhost:8080/books/{id} - GET method, show the book.\
http://localhost:8080/books/{id} - DELETE method, delete the book from DB.\
http://localhost:8080/{id}/appoint - appoint the book to a user.\
http://localhost:8080/{id}/release - release the book from user.

**For all actions we use buttons and links.**




