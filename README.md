# Solar System Application
API REST Application made with Java 8 & Spring Boot

#### Para correr el project de manera local:
`mvn spring-boot:run`

#### Decisiones de diseño
- Las coordenadas del sol son (0,0) para facilitar las cuentas al obtener las posiciones de los planetas que giran a su alrededor.
- Consideré realizar la iteración de los 10 años manejando un local date de la siguiente forma:
`for(LocalDate date = LocalDate.now(); date.isBefore(date.plusYears(10); date.plusDays(1)))`
La finalidad era poder contemplar los años bisiestos. La desventaja era que demoraba mucho tiempo en recorrer el ciclo. Por lo tanto, a fin de cumplir con los fines prácticos de la evaluación contemplé a todos los años con 365 días.  De esta manera el for simplemente itera con un contador (int).

- También considere crear tres clases que hereden de Planet, una para cada tipo de civilización. La ventaja era que permitía asignar los valores a los atributos simplemente instanciando la clase necesaria. Sin embargo no considero que este argumento alcance para justificar la creación de tres clases más ya que es utilizar una herramienta tan fuerte como la herencia y no hay comportamiento diferenciado entre las distintas civilizaciones más allá del valor de sus atributos. Por ejemplo, así hubiese quedado la clase Ferengi:
`    public Ferengi() { super("Ferengi", 1, 500 , true );    }`

- Finalmente la clase Planet quedó definida con los siguientes atributos:
	- angularVelocityInGradesPerDay: Velocidad angular [grados/dia]
	- sunDistanceInKm: Distancia al sol [Km]
	- movementClockwise: Movimiento horario [boolean]
	- civilizationName: Nombre de la civilización (Ferengi, Vulcano o Betasoide)
	- position: Posición [Coordenadas (X,Y)]
	- actualGrade: Grado actual [grados]

	En un principio no contaba con los atributos position y actualGrade, ya que obtenía la posición del planeta a partir del número del día de manera dinámica. Pero la ecuación era más compleja ya que debía sumar dentro del cos y el sin la fase inicial del planeta (90 grados) más el número del día multiplicado por la velocidadAngular.  Por esta razón decidí agregar estos dos últimos atributos:
		 
		 - position: El planeta calcula sus coordenadas una única vez por día, independientemente de cuantas veces se consulte su posición.
		 - actualGrade: Permite facilitar el cálculo de la posición y además se utiliza para verificar si los planetas estan alineados con el sol, verificando si todos los ángulos sin iguales o si son opuestos por el vértice (el sol).

- Cree la clase WeatherReport a fin de cumplir con el enunciado previo al BONUS. Este objeto cumple la función de ser un informe en el cual se escribe el resultado del pronóstico. Decidí almacenar este objeto en la base de datos de manera que se pueda consultar el reporte a través de un request:

        `GET -> http://..../reporteProximosDiezAnios`
