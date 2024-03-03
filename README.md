# Sistema de Reserva de Ferry

## Descripción General

El Sistema de Reserva de Ferry es una aplicación Java simple diseñada para gestionar reservas de ferry. Incluye clases para manejar información de ferry, cruces disponibles y reservas. 
El sistema lee datos de ferry desde un archivo JSON y permite a los usuarios ver cruces disponibles y realizar reservas.

## Code Smells y Sugerencias de Refactorización

Este proyecto fue analizado para identificar code smells y oportunidades de refactorización. Las siguientes secciones detallan los problemas identificados y las mejoras sugeridas.
Antes de verificar clase por clase, modificaremos la estructura de paquetes para que el código sea más legible y fácil de mantener.

### Estructura de Paquetes

La estructura de paquetes actual es plana y no proporciona una separación clara de responsabilidades.

![Estructura de Paquetes Actual](/java/src/main/resources/img/paq.png)

Se sugiere reorganizar el código en paquetes más pequeños y enfocados, como se muestra a continuación (esto lo hacemos al inicio para identificar cada módulo,
sin embargo, si en el proceso vemos necesario volver a reorganizar los paquetes, por ejemplo, para señalar las clases controladoras y de servicios, lo haremos después):

![Estructura de Paquetes Sugerida](/java/src/main/resources/img/paqn.png)

---
## Ferry

### Clase Ferry

#### Code Smells

1. **Uso de Variables Públicas:** Las variables dentro de la clase `Ferry` son públicas, lo que puede llevar a problemas de seguridad y encapsulación.

![Variables Públicas](/java/src/main/resources/img/vpublic.png)

#### Sugerencias de Refactorización

1. **Encapsulación:** Cambiar las variables a privadas y proporcionar métodos getter y setter para acceder y modificar los valores. Para ello, usaremos la
anotación @Data de Lombok, que nos permite generar automáticamente los métodos getter y setter, además de los métodos equals, hashCode y toString.
Como este proyecto es de años anteriores, debemos actualizar el archivo build.gradle para que sea funcional y se importen las dependencias necesarias.

![Encapsulación](/java/src/main/resources/img/vprivate.png)

Una vez que se ha actualizado el archivo build.gradle, se ejecuta el comando `gradle build` para que se descarguen las dependencias y se construya el proyecto.

- Debemos cambiar el llamado de las variables en otras clases, ya que antes eran públicas y ahora son privadas.


### Clase Ferries

#### Code Smells

1. **Manejo de Excepciones para Control de Flujo:** El constructor de la clase `Ferries` utiliza excepciones para controlar el flujo de la aplicación. Esta no es una práctica recomendada.

![Manejo de Excepciones](/java/src/main/resources/img/exc.png)

2. **Múltiples Responsabilidades en el Constructor:** El constructor de `Ferries` es responsable de leer un archivo, parsear JSON y crear objetos `Ferry`. 
Esto viola el Principio de Responsabilidad Única de SOLID.

#### Sugerencias de Refactorización

1. **Extraer Método:** Extraer el código responsable de leer el archivo y parsear JSON en un método separado. Esto hará que el constructor sea más limpio y enfocado en su responsabilidad principal.

2. **Violación de Single Responsibility:** Dividir el constructor en métodos más pequeños, cada uno con una responsabilidad clara.

![Extraer Método](/java/src/main/resources/img/excC.png)

3. **Inyección de dependencias:** Para manejar la lectura del archivo, haciendo la clase más flexible y fácil de probar.

### Clase FerryAvailabilityService

#### Code Smells

1. **Uso de `Collections.sort` con un comparador anónimo**

2. **Método `nextFerryAvailableFrom` es demasiado largo y realiza múltiples responsabilidades**

3. **Uso de variables no inicializadas (`ferry` en el bucle `for`)**

#### Sugerencias de Refactorización

1. **Expresiones Lambda:** Utilizar expresiones lambda para simplificar el código y mejorar la legibilidad.
2. **Métodos más Pequeños:** Extraer partes del método `nextFerryAvailableFrom` en métodos más pequeños, cada uno con una responsabilidad clara.
3. **Inicialización de Variables:** Inicializar la variable `ferry` antes del bucle `for` para evitar confusiones y errores.

### Clase FerryManager

#### Code Smells

1. **Métodos estáticos que operan en objetos de instancia:** Los métodos en `FerryManager` son estáticos, pero operan en objetos de instancia. 
Esto puede llevar a problemas de encapsulación y mantenimiento.

2. **Método `createFerryJourney` realiza múltiples responsabilidades**

3. **Método `getFerryTurnaroundTime` utiliza condicionales múltiples**


#### Sugerencias de Refactorización
1. 
2. **Métodos de Instancia:** Considerar convertir los métodos estáticos en métodos de instancia o refactorizar la clase para que sea más orientada a objetos.
2. **Métodos más Pequeños:** Extraer partes del método `createFerryJourney` en métodos más pequeños, cada uno con una responsabilidad clara.
3. **Uso de Mapas o Switch:** Utilizar un mapa o un switch para manejar los diferentes casos de manera más limpia.


### Clase `FerryModule`

#### Code Smells

1. **Método `timeReady` realiza múltiples responsabilidades**

2. **Uso de `NullPointerException` para control de flujo**

#### Sugerencias de Refactorización
1. **Métodos más Pequeños:** Extraer partes del método `timeReady` en métodos más pequeños, cada uno con una responsabilidad clara.
2. **Manejo de Excepciones:** Utilizar excepciones personalizadas o validaciones previas para manejar casos de nulidad de manera más elegante.

---
## Booking

### Clase AvailableCrossing

#### Code Smells

1. **Uso de Variables Públicas:** Similar a `Ferry`, las variables dentro de la clase `AvailableCrossing` son públicas.

#### Sugerencias de Refactorización

1. **Encapsulación:** Cambiar las variables a privadas y proporcionar métodos getter y setter.

### Clase Booking

#### Code Smells

1. **Falta de Encapsulación:** Las variables dentro de la clase `Booking` son públicas.

#### Sugerencias de Refactorización

1. **Encapsulación:** Hacer las variables privadas y proporcionar métodos getter y setter.

### Clase Bookings

#### Code Smells

1. **Uso de Lista Mutable:** La lista de reservas en `Bookings` es mutable, lo que puede llevar a modificaciones no deseadas.

#### Sugerencias de Refactorización

1. **Inmutabilidad:** Considerar el uso de una lista inmutable o proporcionar métodos que devuelvan una copia de la lista.

---
## Journey

---
## Port


---
## TimeTable

---

