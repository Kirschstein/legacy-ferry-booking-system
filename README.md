# Legacy Ferry Booking System , Description

This is a simple console application that pretends to be a rudimentary ferry booking application.
It was originally written for a series of legacy code workshops and should provide plenty of material for refactoring exercises.

### Analizar el código existente y documentar code smells o malas prácticas que identifiquen entorno a la deuda técnica en el código, identificar algunas técnicas de refactoring, candidatas a aplicar y documentarlas en el archivo .md. Si lo desean pueden implementar algunas de estas prácticas
- Este código legacy contiene muchos code smells en todas sus clases que indican malas practicas como por ejemplo la falta de documentación , campos mutables, ausencia de pipelines y pruebas unitarias como se especifica en los criterios de aceptación para este proyecto. Observemos las clases más robustas para tener un paronama general del estado del codigo.
#### Clase AvailableCrossing :
1. Public Mutable Fields: Los campos de la clase AvailableCrossing son públicos y mutables. Esto puede llevar a problemas de seguridad y mantenibilidad ya que cualquier clase puede modificar estos campos. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados. Esto se conoce como el principio de encapsulación.
2. Missing Class Responsibility Description: La clase AvailableCrossing no tiene una descripción de su responsabilidad, lo que puede dificultar la comprensión de su propósito. Refactorización: Agrega comentarios de documentación (JavaDoc) para describir la responsabilidad de la clase.
3. Primitive Obsession: La clase AvailableCrossing utiliza tipos primitivos para representar conceptos complejos como setOff y arrive. Refactorización: Introduce clases de objeto de valor para representar estos conceptos.

#### Clase Ferry :
1. Excepción Genérica: El bloque catch está capturando Exception, que es la excepción más genérica. Esto puede ocultar problemas subyacentes y hacer que el código sea más difícil de depurar. Refactorización: Captura excepciones más específicas que podrían ocurrir. Por ejemplo, si estás leyendo un archivo, podrías capturar
2. Clase de Datos Pública: La clase Ferry parece ser una clase de datos pública ya que sus campos son públicos. Esto puede llevar a problemas de seguridad y mantenibilidad. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados.
3. Carga de Datos en el Constructor: El constructor de Ferries está cargando datos de un archivo. Esto puede hacer que la clase sea difícil de probar y usar en diferentes contextos. Refactorización: Considera mover la carga de datos a un método separado o a una clase de servicio.
4. Missing Class Responsibility Description: La clase AvailableCrossing no tiene una descripción de su responsabilidad, lo que puede dificultar la comprensión de su propósito. Refactorización: Agrega comentarios de documentación (JavaDoc) para describir la responsabilidad de la clase.

#### Clase JourneyBookingService :
1. Long Method: El método canBook es bastante largo y hace varias cosas. Esto puede hacer que el método sea difícil de entender y mantener. Refactorización: Considera dividir el método en varios métodos más pequeños, cada uno con una responsabilidad única.
2. Feature Envy: El método canBook parece estar más interesado en los datos de otras clases (TimeTable, Booking) que en los de su propia clase. Refactorización: Considera mover este método a una clase que encapsule estos datos.
3. Carga de Datos en el Constructor: El constructor de Ferries está cargando datos de un archivo. Esto puede hacer que la clase sea difícil de probar y usar en diferentes contextos. Refactorización: Considera mover la carga de datos a un método separado o a una clase de servicio.
4. Data Clumps: Los datos de journeyId y passengers siempre se pasan juntos. Refactorización: Considera introducir un objeto de parámetro para agrupar estos datos.

#### Clase TimeTableService :
1. Duplicación de Código: El código para recopilar todas las entradas de los horarios y ordenarlas se repite en los métodos getTimeTable y getAvailableCrossings. Refactorización: Considera extraer este código a un método privado que pueda ser reutilizado.
2. Duplicación de Código: El código para calcular el número total de pasajeros (pax y pax2) se repite dos veces. Refactorización: Considera extraer este código a un método privado que pueda ser reutilizado.
3. Long Method: Los métodos getTimeTable y getAvailableCrossings son bastante largos y hacen varias cosas. Esto puede hacer que los métodos sean difíciles de entender y mantener. Refactorización: Considera dividir los métodos en varios métodos más pequeños, cada uno con una responsabilidad única.
4. Clase de Datos Pública: La clase TimeTableViewModelRow parece ser una clase de datos pública ya que sus campos son públicos. Esto puede llevar a problemas de seguridad y mantenibilidad. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados.
5. Long Method: El método getAvailableCrossings es bastante largo y hace varias cosas. Esto puede hacer que el método sea difícil de entender y mantener. Refactorización: Considera dividir el método en varios métodos más pequeños, cada uno con una responsabilidad única.
6. Uso de Literales de Número: El código utiliza literales de número para verificar si hay asientos disponibles (seatsLeft > 0). Refactorización: Considera usar una constante para representar el número mínimo de asientos disponibles.

#### CodeSmells generales:
1. El proyecto no posee ningun estilo de arquitectura como por ejemplo MVC que permita el entendimiento y organización de estas clases.
2. No hay pruebas unitarias.
3. Todas las clases contienen codeSmells, algunos de ellos especificados en la sección de arriba.
4. No hay pipeline/
#### Prácticas XP para mejorar la calidad del código:

1. Test-Driven Development (Desarrollo Guiado por Pruebas): Es una práctica en la que se escriben las pruebas antes de escribir el código de producción. Esto ayuda a garantizar que el código esté bien probado y cumpla con los requisitos establecidos.

2. Continuous Integration (Integración Continua): La integración continua implica integrar y probar el código de manera frecuente y automática. Esto ayuda a identificar y corregir errores de manera temprana, mejorando así la calidad del código.

3. Refactoring (Refactorización): La refactorización es el proceso de reestructurar el código sin cambiar su comportamiento externo. Esto ayuda a mejorar la calidad del código al eliminar duplicaciones, simplificar la lógica y mejorar la legibilidad.

4. Simple Design (Diseño Simple): Seguir el principio de diseño simple ayuda a mantener el código limpio y fácil de entender. Se deben evitar soluciones complejas y buscar la simplicidad en el diseño del código.

#### Identificar en su proyecto cuales prácticas de Testing Debt se presentan y documentar con ejemplos si aplica:
- En el proyecto no hay ninguna practica de testing debt , se identifican las siguientes fallas:
  1. Falta de Pruebas Unitarias: El proyecto no tiene pruebas unitarias implementadas, lo que puede llevar a problemas de calidad, mantenibilidad y escalabilidad en el código. Es importante implementar pruebas unitarias para validar el comportamiento de las distintas partes del sistema de forma individual.
  2. Falta de Cobertura de Pruebas: Aunque no se especifica directamente en el contexto proporcionado, la falta de pruebas unitarias implica una falta de cobertura de pruebas en el proyecto. La cobertura de pruebas es importante para garantizar que se están probando adecuadamente todas las funcionalidades del sistema.
  3. Ausencia de Integración Continua (CI): No se menciona la presencia de un pipeline de integración continua en el proyecto. La integración continua es una práctica importante que ayuda a identificar errores de manera temprana al integrar y probar el código de forma automática y frecuente.
  4. Ausencia de Documentación de Casos de Uso: No se menciona la presencia de documentación de casos de uso en el proyecto. La documentación de casos de uso es esencial para comprender y comunicar claramente los requisitos y funcionalidades del sistema.


#### Si no existen pruebas unitarias en su proyecto proponer algunas pruebas y si ya existen proponer algunos escenarios complementarios para garantizar un mayor cubrimiento (Coverage) (Documentar ejemplos)
- No existen pruebas unitarias , crearemos pruebas unitarias con el objetivo de cubrir la mayor cantidad de codigo por clase.
  1. Clase bookings: Creamos el directorio para los test y la clase BookingsTesT , probaremos los metodos Add y All de esta clase
     ![BookingTest](\java\src\main\resources\bookingtest.png)
  2. Clase Ferries: Estas pruebas cubren el constructor y el método all de la clase
     ![FerriesTest](\java\src\main\resources\ferriestest.png)
  3. Clase AvailableCrossing: utilizamos la librería Mockito para crear mocks de las clases TimeTables y PortManager. Luego, creamos dos pruebas unitarias para el método nextFerryAvailableFrom.
     ![FerriesTest](\java\src\main\resources\ferryavailabilityservicetest.png)

#### Proponer algunas mejoras o ideas para reducir la deuda técnica:
- Se propone las siguientes ideas para reducir la deuda:
    1. Implementación de Pruebas Unitarias: Se podrían crear pruebas unitarias para las clases y métodos clave del proyecto, como las clases AvailableCrossing, JourneyBookingService, y Ferry. Estas pruebas ayudarían a validar el comportamiento de las funciones de manera aislada y a detectar posibles errores.
    2. Cobertura de Pruebas: Se debería asegurar una cobertura adecuada de pruebas para todas las funcionalidades del sistema. Esto implica escribir pruebas para cubrir diferentes casos de uso y escenarios, con el objetivo de garantizar que el código funciona correctamente en diversas situaciones.
    3. Integración Continua: Se podría implementar un pipeline de integración continua que compile el código, ejecute las pruebas automáticamente y genere informes sobre la calidad del código. Esto ayudaría a identificar problemas de integración y errores de manera temprana en el ciclo de desarrollo.
    4. Documentación de Casos de Uso: Se podría documentar los casos de uso principales del sistema, como la búsqueda de viajes disponibles, la reserva de asientos en un viaje, y la finalización de la compra del viaje. Esto ayudaría a comprender y comunicar claramente los requisitos y funcionalidades del sistema.