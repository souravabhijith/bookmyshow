# Getting Started


#### How to build
```text
1. Open the project in your IDE Eclipse / IntelliJ.
2. Run
mvn clean install
3. Start the Application
```
Once started go to http://localhost:8090/swagger-ui.html to explore the apis

#### Flow
1. First you need to login using login controller with any of the below username-pwd combinations
```dtd
admin - admin
user1 - user1
user2 - user2
```
2. In response you will get a JWT Token. You need to copy this and click on Authorize on top right
3. In the api key input box, Type `Bearer`` followed by space and paste the token you copied before.
```dtd
if token is abcd
api key would be 
Bearer abcd
```

4. You can now make other api calls.

#### Database Schema

Only listed important keys. Actual columns are present in entities package.

```dtd
Theatre 
- id
- username
- city
- address
- other props

Movie
- id
- name
- duration
- other props


Show
- id
- theatre_id
- movie_id
- show_number
- show_start_time
- date

ShowSeatBooking
- show_id
- seat_num
- status (RESERVED,BOOKED,FREE)
- booking_id

Booking
- id
- user_id
- number_of_seats
- amount

User
- id
- username
- password
- role
```



### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

