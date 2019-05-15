# Solar System Application
API REST Application made with Java 8 & Spring Boot

### Entorno productivo
Tanto la API REST como el modelo de datos se encuentran hosteados en Google Cloud.
Al deployar la aplicación en la nube se ejecutó el JOB solicitado. El mismo consiste en correr un proceso para realizar la predicción del clima del sistema solar compuesto por los planetas indicados en el enunciado, de manera que sea posible consultar los resultados obtenidos a través de pedidos http. Los mismos son indicados a continuación:

- Para obtener el clima de un dia especifico:
	- `https://sistema-solar-240720.appspot.com/clima?dia=<nroDia>`
- Para obtener el reporte de los próximos diez años:
	- `https://sistema-solar-240720.appspot.com/reporteProximosDiezAnios`



#### Correr el project de manera local:
Levantar la aplicacion con el comando:
`mvn appengine:deploy`

Se realizará la predicción del clima para los proximos diez anios, almacenando los resultados en una base de datos que luego podrá ser consultada a través de distintos request expuestos por la API.

#### End Points
Una vez deployado localmente, es posible realizar las siguientes consultas:
- Obtener el clima para un dia especifico
    - `localhost:8080/clima?dia=<diaConsultado>`
- Obtener la cantidad de dias de sequia, de dias optimos, de dias de lluvia y el numero del dia que mas llovio.
    - `localhost:8080/reporteProximosDiezAnios`

#### Decisiones de diseño
- Las coordenadas del sol son (0,0) para facilitar las cuentas al obtener las posiciones de los planetas que giran a su alrededor.
- Inicialmente los planetas se encuentran a 90 grados con respecto al sol.
- Consideré realizar la iteración de los 10 años manejando un local date de la siguiente forma:

`for(LocalDate date = LocalDate.now(); date.isBefore(date.plusYears(10); date.plusDays(1)))`

   La finalidad era poder contemplar los años bisiestos. La desventaja era que demoraba mucho tiempo en recorrer el ciclo. Por lo tanto, a fin de cumplir con los fines prácticos de la evaluación contemplé a todos los años con 365 días.  De esta manera el for simplemente itera con un contador (int).

- Por otro lado considere crear tres clases que hereden de Planet, una para cada tipo de civilización. La ventaja era que permitía asignar los valores a los atributos simplemente instanciando la clase necesaria. Sin embargo no considero que este argumento alcance para justificar la creación de tres clases más ya que es utilizar una herramienta tan fuerte como la herencia y no hay comportamiento diferenciado entre las distintas civilizaciones más allá del valor de sus atributos. Por ejemplo, así hubiese quedado la clase Ferengi:
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


- La base de datos utilizada es Cloud Firestore. Es una base de datos NOSQL documental que almacena los datos en la nube de Google. Dado que el modelo es sencillo y pequeño no se justifica usar una bd referencial. Esta base de datos me permite almacenar los datos  de mi projecto en un espacio seguro y gratuito.  
Además no es necesario hacer un borrado de los datos en la inicialización de la aplicación ya que la operación insert - update es la misma para este motor. Es decir que al insertar un documento, si ya existe lo actuliza y si no lo crea.

- Utilice el patron Template Method para actualizar el reporte dia a dia. La super clase es Weather, que contiene el metodo `updateReport(WeatherReport report, Integer dayNumber)`

    De ella heredan varias clases, una por cada clima, y todas tienen su propia implementacion del metodo. De modo que es posible tratar a todos los climas de manera polimorfica para que cada uno complete el reporte como le corresponda.
