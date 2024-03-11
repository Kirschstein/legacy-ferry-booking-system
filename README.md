# Legacy Ferry Booking System , Description

This is a simple console application that pretends to be a rudimentary ferry booking application.
It was originally written for a series of legacy code workshops and should provide plenty of material for refactoring exercises.

## Se recomienda que utilicen una rama hija de main para que desde esta realicen la actividad y me envíen un pull request y este sea el que yo pueda validar y aprobarlo o reversarlo.

## Suggested Exercise: The Saff Squeeze.

There's a bug within this code base, the time table that is printed is missing a ferry name in the first time table slot. The expected correct timetable can be found in the `java/src/main/resources/` folder and in the `C-Sharp/FerryLegacy/Data/` folder.

## Analizar el código existente y documentar code smells o malas prácticas que identifiquen entorno a la deuda técnica en el código, identificar algunas técnicas de refactoring, candidatas a aplicar y documentarlas en el archivo .md. Si lo desean pueden implementar algunas de estas prácticas
- Este código legacy contiene muchos code smells en todas sus clases que indican malas practicas como por ejemplo la falta de documentación , campos mutables, ausencia de pipelines y pruebas unitarias como se especifica en los criterios de aceptación para este proyecto. Observemos las clases más robustas para tener un paronama general del estado del codigo.
### Clase AvailableCrossing :
1. Public Mutable Fields: Los campos de la clase AvailableCrossing son públicos y mutables. Esto puede llevar a problemas de seguridad y mantenibilidad ya que cualquier clase puede modificar estos campos. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados. Esto se conoce como el principio de encapsulación.
2. Missing Class Responsibility Description: La clase AvailableCrossing no tiene una descripción de su responsabilidad, lo que puede dificultar la comprensión de su propósito. Refactorización: Agrega comentarios de documentación (JavaDoc) para describir la responsabilidad de la clase.
3. Primitive Obsession: La clase AvailableCrossing utiliza tipos primitivos para representar conceptos complejos como setOff y arrive. Refactorización: Introduce clases de objeto de valor para representar estos conceptos.

### Clase Ferry :
1. Excepción Genérica: El bloque catch está capturando Exception, que es la excepción más genérica. Esto puede ocultar problemas subyacentes y hacer que el código sea más difícil de depurar. Refactorización: Captura excepciones más específicas que podrían ocurrir. Por ejemplo, si estás leyendo un archivo, podrías capturar 
2. Clase de Datos Pública: La clase Ferry parece ser una clase de datos pública ya que sus campos son públicos. Esto puede llevar a problemas de seguridad y mantenibilidad. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados.
3. Carga de Datos en el Constructor: El constructor de Ferries está cargando datos de un archivo. Esto puede hacer que la clase sea difícil de probar y usar en diferentes contextos. Refactorización: Considera mover la carga de datos a un método separado o a una clase de servicio.
4. Missing Class Responsibility Description: La clase AvailableCrossing no tiene una descripción de su responsabilidad, lo que puede dificultar la comprensión de su propósito. Refactorización: Agrega comentarios de documentación (JavaDoc) para describir la responsabilidad de la clase.

### Clase JourneyBookingService :
1. Long Method: El método canBook es bastante largo y hace varias cosas. Esto puede hacer que el método sea difícil de entender y mantener. Refactorización: Considera dividir el método en varios métodos más pequeños, cada uno con una responsabilidad única.
2. Feature Envy: El método canBook parece estar más interesado en los datos de otras clases (TimeTable, Booking) que en los de su propia clase. Refactorización: Considera mover este método a una clase que encapsule estos datos.
3. Carga de Datos en el Constructor: El constructor de Ferries está cargando datos de un archivo. Esto puede hacer que la clase sea difícil de probar y usar en diferentes contextos. Refactorización: Considera mover la carga de datos a un método separado o a una clase de servicio.
4. Data Clumps: Los datos de journeyId y passengers siempre se pasan juntos. Refactorización: Considera introducir un objeto de parámetro para agrupar estos datos.

### Clase TimeTableService :
1. Duplicación de Código: El código para recopilar todas las entradas de los horarios y ordenarlas se repite en los métodos getTimeTable y getAvailableCrossings. Refactorización: Considera extraer este código a un método privado que pueda ser reutilizado.
2. Duplicación de Código: El código para calcular el número total de pasajeros (pax y pax2) se repite dos veces. Refactorización: Considera extraer este código a un método privado que pueda ser reutilizado.
3. Long Method: Los métodos getTimeTable y getAvailableCrossings son bastante largos y hacen varias cosas. Esto puede hacer que los métodos sean difíciles de entender y mantener. Refactorización: Considera dividir los métodos en varios métodos más pequeños, cada uno con una responsabilidad única.
4. Clase de Datos Pública: La clase TimeTableViewModelRow parece ser una clase de datos pública ya que sus campos son públicos. Esto puede llevar a problemas de seguridad y mantenibilidad. Refactorización: Encapsula los campos con métodos getter y setter y haz que los campos sean privados.
5. Long Method: El método getAvailableCrossings es bastante largo y hace varias cosas. Esto puede hacer que el método sea difícil de entender y mantener. Refactorización: Considera dividir el método en varios métodos más pequeños, cada uno con una responsabilidad única.
6. Uso de Literales de Número: El código utiliza literales de número para verificar si hay asientos disponibles (seatsLeft > 0). Refactorización: Considera usar una constante para representar el número mínimo de asientos disponibles.

### CodeSmells generales:
1. El proyecto no posee ningun estilo de arquitectura como por ejemplo MVC que permita el entendimiento y organización de estas clases.
2. No hay pruebas unitarias.
3. Todas las clases contienen codeSmells, algunos de ellos especificados en la sección de arriba.
4. No hay pipeline/
### Prácticas XP para mejorar la calidad del código:

1. Test-Driven Development (Desarrollo Guiado por Pruebas): Es una práctica en la que se escriben las pruebas antes de escribir el código de producción. Esto ayuda a garantizar que el código esté bien probado y cumpla con los requisitos establecidos.

2. Continuous Integration (Integración Continua): La integración continua implica integrar y probar el código de manera frecuente y automática. Esto ayuda a identificar y corregir errores de manera temprana, mejorando así la calidad del código.

3. Refactoring (Refactorización): La refactorización es el proceso de reestructurar el código sin cambiar su comportamiento externo. Esto ayuda a mejorar la calidad del código al eliminar duplicaciones, simplificar la lógica y mejorar la legibilidad.

4. Simple Design (Diseño Simple): Seguir el principio de diseño simple ayuda a mantener el código limpio y fácil de entender. Se deben evitar soluciones complejas y buscar la simplicidad en el diseño del código.
