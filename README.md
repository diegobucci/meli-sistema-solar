**Solar System Application**

API REST Application made with Java 8 & Spring Boot

Para correr el project de manera local:
`mvn spring-boot:run`

Considere realizar la iteración de los 10 años manejando un local date de la siguiente forma:

`for(LocalDate date = LocalDate.now(); date.isBefore(date.plusYears(10); date.plusDays(1)))
`

La finalidad era poder contemplar los años bisiestos. Pero el tiempo que demoraba en iterar era demasiado. Por lo tanto, para poder cumplir con los fines prácticos de la evaluación se contemplan a todos los años con 365 días. 