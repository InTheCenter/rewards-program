REST API - REWARDS PROGRAM
===============

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a trhee month period, calculate the rewards points earned for each customer per month and total.
---------------

Pre requirements 📋

- Java 1.8+
- Apache Maven 3.5+

How to run the project 🚀
---------------
$ mvn spring-boot:run

How to run tests
---------------
$ mvn test


Total rewards (last 3 months):
---------------
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/reward'

Rewards per month (last 3 months)
---------------
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/reward?month=true'

Swagger url
---------------
http://localhost:8080/swagger-ui.html#/

H2 Database url
---------------
http://localhost:8080/h2/
