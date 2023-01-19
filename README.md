# PwC Backend Developer Test Java

### Application start

For launching the application use command *mvn spring-boot:run* (maven should be installed on your local machine before)

### Application usage

Application exposes REST GET endpoint *localhost:8080/routing/{origin}/{destination}* which returns as a result array of countries from *origin* to *destination*.
If journey between two countries is impossible or provided inputs are invalid, the endpoint returns HTTP 400.
