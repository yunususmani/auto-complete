# auto-complete
API that provides s auto completion suggestions for cities in India.

#Required pre installed Software to build and run are: \n
Java 8
Maven 3
Tomcat 8

#Build commnd
maven clean compile package

Once build is completed please copy generated war from target directory and paste it into tomcat's webapps directory

#endpoint
https://host:port/autocomplete/services/autocomplete/suggest_cities?start=che&atmost=5

query param: start (required)
             atmost (optional with default 10) should be valid integer value.


