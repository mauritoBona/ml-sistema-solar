##Tecnologías Utilizadas
Java 8 -- Spring 5.0.0 -- Spring Boot 2.0.2 -- JPA -- Spring Batch -- Mysql 8.0 -- Liquibase


## Problema a resolver: 
Poder predecir en los próximos 10 años las diferentes condiciones climáticas que se pueden dar en los 3 plantes del sistema solar.

## Solución: 
Sabiendo que lo 3 planetas, siempre se van a encontrar alineados o formando un triángulo, si no están alineados forman un triángulo y viceversa, y teniendo la velocidad angular y sentido para el que se mueven. Se utiliza la clase abstracta CoordinatesUtil, para saber cuándo lo están.

	- Se sabe que los 3 planetas están alineados cuando Los puntos A(x1,y1), B(x2,y2) y C(x3,y3) se consideran alineados si los vectores AB y AC tienen la misma dirección, por lo que se debe dar ((x2-x1)(y3-y2))-((y2-y1)(x3-x2)) = 0.
	- Si los planetas forman un triángulo, se necesita saber si el Sol esta adentro del mismo, para eso basta con sumar las distintas áreas de las todas combinaciones de los puntos y la del sol para ver si la suma de las áreas obtenidas es igual al área de los 3 puntos originales. Si esta suma es mayor, significa que el sol se encuentra fuera del triángulo. 
	- Para saber la cantidad de lluvia que cae, se sabe que esta es igual al perímetro, para esto sumamos los lados del triángulo formado por los planetas.

Para poder atacar el problema y obtener el resultado de las condiciones climáticas para los próximos X años que nosotros queramos simular, se encuentra el servicio simulateWeatherConditionsInYears en el SolarSystemService, en donde se va a simular el movimiento de los planetas por cada día y así obtener un WeatherPredictionResult del servicio getPredictionResult brindado por WeatherService, que dependiendo de la ubicación de los planetas con respecto al sol, va a dar la condición climática correspondiente, luego va a ir sumando los distintos resultados, para obtener el resultado final.

Además para guardar el clima de cada día en los próximos 10 años, se decidió realizar un Job chunk, en donde el Reader tiene el sistema solar, este le retorna al Processor el sistema solar, y a través del SolarSystemService, se utiliza el servicio simulateWeatherConditionsForTheNextTenYears en donde se va simulando la ubicación de los planetas por los próximos 10 años, y obteniendo en cada día el clima que va a haber a través de WeatherService.getWeatherPrediction, estos los guarda en una lista de WeatherCondition, en donde cada uno tiene el día y la descripción del clima.
Una vez terminado el Processor, retorna la lista de WeatherCondition al Writer Writer, en donde toma la lista y ejecuta el WeahterConditionRepository.saveAll para guardar todas las condiciones climáticas que se dan en los próximos 10 años.


### ¿Cómo se pensó la lógica para simular los días y obtener los resultados de las condiciones climáticas?
Para obtener los resultados de las condiciones climáticas, al llamar a uno de los 2 servicios de SolarSystemService que simulan el movimiento de los planetas del sistema solar, lo primero que se hace es iniciar un for, en donde la variable day, se inicia en 1, y sería el número del día por el que va la simulación, en cada día, se va a usar el método simuletedOneDay del objeto SolarSystem en donde se va a llamar al método simuletedOneDay de cada planeta, para que hagan el movimiento correspondiente por su velocidad angular.

Una vez que se termina esta simulación del día, dependiendo de lo que quisiéramos obtener (WeatherCondition o WeatherPredictionResult) se va a llamar a uno de los 2 servicios que brinda WeatherService. 
 > Los 2 servicios tienen la misma lógica: Utilizando siempre la clase abstracta CoordinatesUtil, se pregunta primero, a través del método areAligned del CoordinatesUtil, si los planetas están alineados, si lo están se fija si están alineados con el sol, si es así, hay periodo de sequía, sino hay periodo de óptimas condiciones. En el caso de que no estén alineados los planetas, quiere decir que los planetas están formando un triángulo, y se válida si el Sol esta adentro de este triángulo a través del método IsCoordianteInsideTriangle de la clase CoordinatesUtil, si lo está, hay Periodo de lluvia.

Una vez que se termina de predecir el clima, si nosotros queríamos saber el clima de cada día en los próximos 10 años, se guarda el día en el objeto WeatherCondition y se lo agrega en la lista que tiene todos los WeatherCondition para una vez terminada la simulación, se retorna.
En el caso de que queremos saber la cantidad de periodos de lluvia, sequia y optimas condiciones que hay en 10 años, se va a utilizar el objeto WeatherPredictionResult, el cual contiene un contador inicializado en 0 para cada periodo y una lista de días en donde va a estar el máximo perímetro y una lista de días donde hubo ese máximo perímetro. Este se inicializa en el método del SolarSystemService para llevar la suma/control de los resultados de cada día.
El WeatherService, me va a retornar un WeatherPredictionResult en donde va a tener, dependiendo del clima/condiciones, el contador del que corresponda en 1 dejando los otros en 0, y en el caso de que sea un periodo de sequía, se va a guardar también el perímetro del triángulo formado. Una vez retornado, en el SolarSystemService, le agrego a la lista de días donde hubo el máximo perímetro, el día que se esta simulando, para luego utilizar el método addResult de la instancia WeatherPredictionResult que se inicializo en SolarSystemService, pasando como parámetro la instancia que me devolvió el WeatherService, en donde se va a sumar cada contador, y se va a fijar si el máximo perímetro es mayor, se guarda este y se guarda la lista de días, si es igual, se agrega el día a la lista de días, sin pisar ninguno.
Una vez terminada la simulación de todos los días, se retorna la instancia de WeatherPredictionResult que tiene todos los resultados.

###  ¿Por qué se decidió utilizar Spring Batch Chunk para el job? 
Se decidió la utilización de Spring Batch Chunk, para tener una mejor división de tareas, la clase responsable de obtener el o los sistemas solares, va a ser el WeatherPredictionReader, la clase que se encargué de procesar y obtener las condiciones climáticas, a través de los servicios, en los próximos 10 años, va a ser WeatherPredictionProcessor y la encargada de utilizar el WeatherPredictionRepository para persistir en la base la lista de WeatherPrediction va a ser WeahterPredictionWriter.
En el caso de que alguna de esas 3 tareas, se necesiten cambiar, solo tiene que irse a la clase de la tarea y no se verían afectadas las otras 2, a menos que se cambie el tipo a retornar en las clases. Es por eso que se decidió por Spring Batch Chunk. 


## Mejoras: 

	-Al iniciar SpringBoot, se corre el RunScheduler, que valida si la Tabla WeatherCondition no tiene ninguna entidad, para ver si ya se corrió alguna vez o es la primera. La mejora es validar desde la tabla BATCH_JOB_EXECUTION, cuando fue la ultima vez que se corrió, y si fue hace 10 año, correrlo.
	-Crear las entidades de sistema solar y planeta, y guardar cada uno en la base, para obtenerlas de ahí al levantar y tener siempre la posición de los planetas donde quedo la última simulación.
	-En el WeatherPredictionReader, ir a buscar el sistema solar a la base, y no tenerlas como Autowired, así una vez que se pase este al Processor, ponerlo como null en el Reader, así se termina la ejecución del Job, una vez que termine el Writer, ya que es necesario que el Reader retorne null. 