# EjCallCenter

## Consigna
Existe un call center donde hay 3 tipos de empleados: operador, supervisor y director. El proceso de la atención de una llamada telefónica en primera instancia debe ser atendida por un operador, si no hay ninguno libre debe ser atendida por un supervisor, y de no haber tampoco supervisores libres debe ser atendida por un director.

### Requerimientos

- Debe existir una clase Dispatcher encargada de manejar las llamadas, y debe contener el método dispatchCall para que las asigne a los empleados disponibles.
- El método dispatchCall puede invocarse por varios hilos al mismo tiempo.
- La clase Dispatcher debe tener la capacidad de poder procesar 10 llamadas al mismo tiempo (de modo concurrente).
- Cada llamada puede durar un tiempo aleatorio entre 5 y 10 segundos.
- Debe tener un test unitario donde lleguen 10 llamadas.

### Extras/Plus

- Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
- Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
- Agregar los tests unitarios que se crean convenientes.
- Agregar documentación de código

### Tener en Cuenta

- El proyecto debe ser creado con Maven.
- De ser necesario, anexar un documento con la explicación del cómo y porqué resolvió los puntos extras, o comentarlo en las clases donde se encuentran sus tests unitarios respectivos.

## Solución puntos extras

Para resolver los puntos extras de :

- Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
- Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.

Se consideró utilizar un PriorityBlockingQueue el cual, saca a los empleados disponibles de una queue priorizada, para garantizar que los primeros en atender una llamada sean los Operadores, luego los supervisores y por ultimo los directores. Ademas, si no hay empleados en la queue, el metodo take bloquea el flujo hasta que algun empleado vuleva a la queue para continuar el flujo.

Por eso, en ambos casos, tanto si no hay un empleado libre o hay 10 llamadas concurrentes, siempre que haya una empleado disponible, se atiende la llamada, sino quedará la llamada a la espera de que algun empleado vuelva a estar disponible.