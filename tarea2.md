## Introducción a Servicios en Quarkus

### ¿Qué es `@ApplicationScoped` en Quarkus?

Se utiliza para definir un bean (un componente de la aplicación) como un bean de ámbito de aplicación. Esto significa que una única instancia de ese bean será creada al inicio de la aplicación y permanecerá en memoria durante todo su ciclo de vida, siendo compartida por todos los clientes o componentes que la requieran.

### ¿Cómo funciona la inyección de dependencias en Quarkus?

Se basa en los principios de CDI (Contexts and Dependency Injection), que es una especificación estándar en Java para la gestión de componentes y su ciclo de vida. A través de CDI, Quarkus permite que las dependencias (objetos) sean inyectadas automáticamente en otros componentes (beans), facilitando la gestión del ciclo de vida, la reutilización de código y la separación de responsabilidades.

1. Conceptos Clave de la Inyección de Dependencias en Quarkus:
   - Beans:
     - Son componentes que se gestionan por el contenedor CDI de Quarkus.
     - Pueden ser POJOs (Plain Old Java Objects) anotados con anotaciones como `@ApplicationScoped`, `@RequestScoped`, `@Singleton`, entre otros.
2. Anotaciones Principales:
   - **@Inject:** Es la anotación más común para realizar la inyección de dependencias. Se utiliza para indicar que un bean debe ser inyectado en un campo, un constructor o un método de la clase.
   - **@Qualifier:** Permite diferenciar entre múltiples implementaciones de un mismo tipo. Puedes crear tus propios qualifiers si necesitas más granularidad en la inyección.
   - **@Produces:** Indica que un método o un campo produce un bean que puede ser inyectado en otras partes de la aplicación.
   - **@ApplicationScoped, @RequestScoped, @SessionScoped, @Dependent:** Estas anotaciones definen el ciclo de vida del bean.
3. Cómo Funciona:
   - **Inyección de Constructor:** Quarkus inyecta las dependencias en el constructor de un bean.
   - **Inyección de Campo:** Se inyectan las dependencias directamente en los campos de la clase.
   - **Inyección de Métodos:** Dependencias pueden ser inyectadas a través de un método setter o un método cualquiera que reciba parámetros.

### ¿Cuál es la diferencia entre `@ApplicationScoped`, `@RequestScoped`, y `@Singleton` en Quarkus?

En Quarkus, estas anotaciones definen el alcance de un bean, es decir, durante cuánto tiempo existirá una instancia de ese bean y cómo será compartida. Cada una tiene un comportamiento específico:

-   ***@ApplicationScoped***
    - **Alcance:** Toda la aplicación.
    - **Comportamiento:** Se crea una única instancia al inicio de la aplicación y permanece disponible durante todo su ciclo de vida. Es compartida por todos los componentes de la aplicación. 
    - **Uso:** Ideal para datos de configuración, conexiones a bases de datos, caches o cualquier otro recurso que necesite ser compartido por toda la aplicación.

-   ***@RequestScoped***
    - **Alcance:** Una única petición HTTP.
    - **Comportamiento:** Se crea una nueva instancia por cada petición HTTP y se destruye al finalizar la misma. El estado de un bean con este alcance no se comparte entre diferentes peticiones. 
    - **Uso:** Perfecto para datos que solo son relevantes para una petición específica, como los datos de un formulario.

- ***@Singleton***
  - **Alcance:** Similar a @ApplicationScoped, pero con algunas diferencias sutiles.
  - **Comportamiento:** También se crea una única instancia, pero su creación es un poco más temprana. No utiliza un proxy de cliente, por lo que su instanciación es más directa.
  - **Uso:** Generalmente se utiliza para beans que no necesitan ser inyectados en otros beans, como utilidades o componentes de infraestructura.

### ¿Cómo se define un servicio en Quarkus utilizando `@ApplicationScoped`?

Para definir un servicio en Quarkus utilizando @ApplicationScoped, debes seguir estos pasos básicos:

- **Crear la clase del servicio:**
  - Define una clase Java que represente el servicio.
  - Anótala con `@ApplicationScoped` para indicar que será un bean gestionado por CDI con un ciclo de vida a nivel de aplicación.
- **Implementar la lógica del servicio:**
  - Dentro de la clase, implementa los métodos y la lógica que el servicio proporcionará.
- **Inyectar el servicio en otras partes de la aplicación:**
  - Puedes inyectar este servicio en otros componentes utilizando la anotación `@Inject`.

### ¿Por qué es importante manejar correctamente los alcances (scopes) en Quarkus al crear servicios?

El manejo adecuado de los alcances (scopes) en Quarkus es fundamental para garantizar el correcto funcionamiento, rendimiento y mantenibilidad de una aplicación. Al comprender las diferencias entre los distintos alcances y cómo utilizarlos de manera efectiva, podemos evitar problemas comunes como:

- **Fugas de memoria:** Crear demasiadas instancias de un bean puede consumir recursos innecesarios y afectar el rendimiento de la aplicación.
- **Concurrencia:** Acceder a un mismo recurso desde múltiples hilos de ejecución puede generar problemas de sincronización y datos inconsistentes.
- **Tiempos de vida incorrectos:** Si un bean tiene un alcance demasiado largo o demasiado corto, puede causar errores inesperados en la aplicación.

***¿Por qué es importante en Quarkus?***

- **MicroServicios:** Quarkus está diseñado para crear aplicaciones de microservicios, donde la gestión eficiente de los recursos es crucial. Un manejo incorrecto de los alcances puede llevar a la creación de microservicios ineficientes.
- **Inyección de dependencias:** Quarkus se basa en la inyección de dependencias para gestionar la creación y el ciclo de vida de los beans. Comprender los alcances es esencial para utilizar este mecanismo de manera efectiva.
- **Optimización de recursos:** Al elegir el alcance adecuado para cada bean, podemos optimizar el uso de memoria y otros recursos de la aplicación.

## Creación de un ApiResponse Genérico

### ¿Qué es un `ApiResponse` genérico y cuál es su propósito en un servicio REST?

Un `ApiResponse` genérico es una estructura comúnmente utilizada en servicios REST para estandarizar la respuesta que se envía desde el servidor al cliente. El propósito de un `ApiResponse` genérico es encapsular la información de respuesta de manera consistente, proporcionando no solo los datos solicitados, sino también metadatos adicionales como códigos de estado, mensajes de error o éxito, y cualquier otra información relevante.

### ¿Cómo se implementa una clase ApiResponse genérica en Quarkus?

En Quarkus, puedes implementar una clase genérica ApiResponse que encapsule la respuesta de una API. Esta clase puede contener datos comunes como el estado de la operación, un mensaje, y el cuerpo de la respuesta, que podría ser de cualquier tipo. A continuación, un ejemplo básico de cómo implementar esta clase genérica.

- **Ejemplo de implementación de ApiResponse genérica:**
```
package com.example.api;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // Constructor vacío
    public ApiResponse() {}

    // Constructor completo
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // Método estático para crear respuestas exitosas
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operation successful", data);
    }

    // Método estático para crear respuestas fallidas
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
```

- **Uso de la clase `ApiResponse` en un recurso REST en Quarkus**

    A continuación, un ejemplo de cómo utilizar esta clase en un recurso REST.

```
package com.example.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/items")
public class ItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse<Item> getItem() {
        Item item = new Item(1, "Example Item");
        return ApiResponse.success(item);
    }
}
```

- **Clase `Item` de ejemplo:**
```
package com.example.api;

public class Item {
    private int id;
    private String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

***Explicación***

- **Genérico (<T>):** La clase `ApiResponse<T>` es genérica, lo que significa que puede manejar cualquier tipo de dato como parte del cuerpo de la respuesta.
- **Métodos estáticos:** Se incluyen métodos estáticos para facilitar la creación de respuestas exitosas y fallidas.
- **Uso en un recurso REST:** En el ejemplo del recurso `ItemResource`, la clase `ApiResponse` se utiliza para encapsular la respuesta que será devuelta al cliente.

### ¿Cómo se modifica un recurso REST en Quarkus para que utilice un `ApiResponse` genérico?


Modificar un recurso REST en Quarkus para que utilice un ApiResponse genérico es bastante sencillo. A continuación, un ejemplo paso a paso.

Supongamos que tienes un recurso REST básico como este:
```
package com.example.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

@Path("/items")
public class ItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Item 1"));
        items.add(new Item(2, "Item 2"));
        return items;
    }
}
```
Modificación para utilizar `ApiResponse` genérico:

Para modificar este recurso para que utilice la clase `ApiResponse`, simplemente necesitas cambiar el tipo de retorno y encapsular la lista de `Item` dentro de una instancia de `ApiResponse`.
```
package com.example.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

@Path("/items")
public class ItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse<List<Item>> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Item 1"));
        items.add(new Item(2, "Item 2"));
        
        // Devolviendo una respuesta exitosa con la lista de items
        return ApiResponse.success(items);
    }
}
```
Detalles de la modificación:

- Cambio en el tipo de retorno: El método `getItems()` ahora devuelve un `ApiResponse<List<Item>>` en lugar de `List<Item>`. Esto significa que estamos envolviendo la lista de items en un objeto `ApiResponse`.
- Creación de la respuesta: Se utiliza el método estático `success()` de la clase `ApiResponse` para crear una respuesta exitosa que contiene la lista de items.

Ejemplo de manejo de errores:

Si deseas manejar errores, puedes hacer algo como esto:
```
package com.example.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

@Path("/items")
public class ItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse<List<Item>> getItems() {
        try {
            List<Item> items = new ArrayList<>();
            items.add(new Item(1, "Item 1"));
            items.add(new Item(2, "Item 2"));
            
            // Devolviendo una respuesta exitosa con la lista de items
            return ApiResponse.success(items);
        } catch (Exception e) {
            // En caso de error, devolver una respuesta de fallo
            return ApiResponse.failure("Failed to retrieve items: " + e.getMessage());
        }
    }
}
```

Explicación del manejo de errores:

- Captura de excepciones: Si ocurre una excepción durante la obtención de los items, capturamos la excepción y devolvemos una respuesta fallida usando el método estático `failure()` de `ApiResponse`.
- Mensaje de error personalizado: El mensaje de error que se devuelve puede ser personalizado con la información relevante.

Resultado esperado:

Cuando se accede al endpoint `/items`, la API devolverá una respuesta JSON que incluirá el estado de la operación `(success)`, un mensaje `(message)`, y los datos `(data)`, que en este caso será una lista de Item.

Por ejemplo, una respuesta exitosa podría verse así:
```
{
  "success": true,
  "message": "Operation successful",
  "data": [
    {
      "id": 1,
      "name": "Item 1"
    },
    {
      "id": 2,
      "name": "Item 2"
    }
  ]
}
```
Y una respuesta en caso de error podría verse así:
```
{
  "success": false,
  "message": "Failed to retrieve items: error message here",
  "data": null
}
```
Esta estructura hace que las respuestas de tu API sean más consistentes y fáciles de manejar por los clientes.

### ¿Qué beneficios ofrece el uso de un `ApiResponse` genérico en términos de mantenimiento y consistencia de código?

El uso de una clase ApiResponse genérica en tus APIs REST ofrece varios beneficios significativos en términos de mantenimiento y consistencia de código. A continuación, se detallan algunos de estos beneficios:

1. **Consistencia en las Respuestas de la API**
   - **Estructura Unificada:** Todas las respuestas de la API siguen una estructura uniforme, lo que facilita el manejo de las respuestas por parte de los clientes (front-end, otros servicios, etc.). Los consumidores de la API siempre sabrán que recibirán un objeto con propiedades como success, message, y data.
   - **Simplificación del Consumo:** Los clientes de la API pueden manejar las respuestas de manera consistente, independientemente del endpoint que se llame. Esto reduce la necesidad de lógica personalizada para manejar diferentes tipos de respuestas.
2. **Mantenimiento Simplificado**
   - **Cambio Centralizado:** Si necesitas hacer un cambio en la estructura de la respuesta (por ejemplo, agregar un nuevo campo), solo necesitas modificar la clase ApiResponse. Este cambio se propagará automáticamente a todas las partes de la aplicación que utilicen esta clase, lo que reduce significativamente el esfuerzo de mantenimiento.
   - **Reducción de Código Duplicado:** Al utilizar una clase genérica, se evita la duplicación de código para manejar respuestas en cada endpoint. Esto hace que el código sea más limpio y fácil de mantener.
3. **Mejor Manejo de Errores**
   - **Gestión Estándar de Errores:** Con una estructura estándar, puedes implementar un manejo de errores más coherente a lo largo de toda la aplicación. Todos los errores pueden ser capturados y devueltos en el mismo formato, lo que simplifica la depuración y el seguimiento de problemas.
   - **Mensajes Claros y Consistentes:** Los mensajes de error pueden ser gestionados de manera uniforme, proporcionando una experiencia de usuario más clara y predecible.
4. **Facilita la Evolución de la API**
   - **Extensibilidad:** Si en el futuro necesitas añadir más campos a la respuesta (por ejemplo, errorCode, timestamp, etc.), puedes hacerlo fácilmente en la clase ApiResponse sin afectar a las funcionalidades existentes.
   - **Compatibilidad con Versiones Anteriores:** Manteniendo una estructura de respuesta consistente, es más fácil manejar la compatibilidad con versiones anteriores cuando la API evoluciona.
5. **Facilita la Documentación y Comunicación**
   - **Documentación Estándar:** Al tener una estructura uniforme para las respuestas, es más fácil documentar la API. Los desarrolladores que utilicen la API entenderán rápidamente cómo interpretar las respuestas, lo que reduce el riesgo de malentendidos.
   - **Mejor Comunicación entre Equipos:** Si diferentes equipos están trabajando en el back-end y el front-end, una estructura de respuesta estándar reduce la fricción y facilita la comunicación entre los equipos.
6. **Mejora la Seguridad y Control**
   - **Control Centralizado de la Información:** Con un punto central para definir la estructura de las respuestas, puedes controlar mejor qué información se expone a los consumidores de la API. Por ejemplo, puedes asegurarte de que no se filtren datos sensibles en caso de un error.
   - **Facilita la Implementación de Políticas de Seguridad:** Puedes integrar políticas de seguridad como el manejo de errores seguros, donde los detalles técnicos no se exponen al cliente, desde un solo lugar.

### ¿Cómo manejarías diferentes tipos de respuestas (éxito, error, etc.) utilizando la clase ApiResponse?

Estrategias para Manejar Diferentes Tipos de Respuestas

1. **Código de Estado HTTP:**

   - **Éxito:** Utiliza códigos como 200 (OK), 201 (Created), etc.
   - **Error:** Utiliza códigos como 400 (Bad Request), 404 (Not Found), 500 (Internal Server Error), etc.
   - **Advertencia:** Considera utilizar códigos como 202 (Accepted) para indicar que la solicitud ha sido aceptada pero el procesamiento aún está en curso.

2. **Campo "message":**

    - **Mensaje descriptivo:** Proporciona un mensaje claro y conciso que explique el resultado de la operación.
    - **Mensajes personalizados:** Para errores específicos, puedes incluir mensajes personalizados que ayuden a diagnosticar el problema.

3. **Campo "data":**

    - **Datos de la respuesta:** Si la operación fue exitosa, incluye los datos solicitados en este campo.
    - **Datos de error:** En caso de error, puedes incluir información adicional sobre el error, como un código de error interno.

4. **Campo "errors":**

    - **Lista de errores:** Para múltiples errores, puedes incluir una lista de objetos de error con detalles específicos.

## Integración y Buenas Prácticas

### ¿Qué consideraciones se deben tener al inyectar servicios en un recurso REST en Quarkus?

Al inyectar servicios en un recurso REST en Quarkus, estamos aprovechando la inyección de dependencias para desacoplar la lógica de negocio de la capa de presentación. Esto mejora la modularidad, la testabilidad y la mantenibilidad de nuestra aplicación. Sin embargo, es importante tener en cuenta algunas consideraciones clave:

1. **Alcance de los Beans:**

    - **@ApplicationScoped:** Para servicios que se utilizan a lo largo de toda la aplicación, como conexiones a bases de datos o caches.
    - **@RequestScoped:** Para servicios que se necesitan solo durante el procesamiento de una solicitud, como servicios de validación o de generación de tokens.
   - **@Singleton:** Similar a @ApplicationScoped, pero con algunas diferencias sutiles en la creación.

2. **Ciclo de Vida de los Beans:**

    - **Creación y destrucción:** Comprender cómo se crean y destruyen los beans es fundamental para evitar fugas de memoria y otros problemas.
    - **Inyección circular:** Evitar inyectar beans que tienen una dependencia circular, ya que esto puede causar errores de inicialización.

3. **Pruebas Unitarias:**

    - **Mocks y stubs:** Utilizar herramientas de mocking para aislar los recursos REST y probar la lógica de negocio de los servicios de forma independiente.
    - **Inyección de dependencias en las pruebas:** Configurar el entorno de prueba para inyectar mock objects en los recursos REST.

4. **Performance:**

    - **Optimización de consultas:** Si el servicio interactúa con una base de datos, asegurarse de que las consultas sean eficientes.
    - **Caching:** Utilizar mecanismos de caching para reducir la carga en la base de datos y mejorar el rendimiento.

5. **Seguridad:**

    - **Validación de entrada:** Validar todos los datos de entrada para prevenir ataques de inyección y otros tipos de vulnerabilidades.
    - **Autorización:** Implementar mecanismos de autorización para controlar el acceso a los recursos.

6. **Transacciones:**

    - **Gestión de transacciones:** Si el servicio realiza múltiples operaciones que deben ser atómicas, utilizar un gestor de transacciones para garantizar la consistencia de los datos.

7. **Asyncronía:**

    - **CompletableFuture:** Utilizar `CompletableFuture` para realizar operaciones asíncronas y no bloquear el hilo de ejecución.

### ¿Cómo se pueden manejar excepciones en un servicio REST utilizando ApiResponse?

Cuando trabajamos con servicios REST, es fundamental contar con un mecanismo robusto para manejar las excepciones que puedan ocurrir durante la ejecución de las operaciones. Una forma efectiva de hacerlo es utilizando una clase `ApiResponse` genérica, la cual nos permite estandarizar las respuestas, incluyendo aquellas que indican un error.

***Estrategias para Manejar Excepciones con ApiResponse***

1. **Captura de Excepciones en los Métodos de los Recursos:**

    - **Identificar la excepción:** En cada método de tu recurso REST, captura las excepciones que puedan ocurrir durante la ejecución.
    - **Crear una respuesta de error:** Crea una instancia de ApiResponse con un código de estado HTTP que indique el error (por ejemplo, 404, 500) y un mensaje descriptivo.
    - **Devolver la respuesta:** Devuelve la instancia de ApiResponse como respuesta HTTP.

2. **Jerarquía de Excepciones:**

    - **Crear una jerarquía de excepciones personalizadas:** Define diferentes tipos de excepciones para representar diferentes tipos de errores (por ejemplo, AuthenticationException, ValidationException).
    - **Mapear excepciones a códigos de estado:** Asigna un código de estado HTTP específico a cada tipo de excepción.
    - **Manejar excepciones de forma centralizada:** Puedes crear un interceptor o un filtro para manejar todas las excepciones no capturadas y generar una respuesta estándar.

3. **Utilizar un Manejador de Excepciones Global:**

    - **Crear un manejador de excepciones:** Implementa un ExceptionMapper en Quarkus para interceptar todas las excepciones no manejadas. 
    - **Mapear excepciones a respuestas:** En el manejador, mapea cada tipo de excepción a una respuesta ApiResponse adecuada.

4. **Detalles de la Excepción:**

    - **Incluir detalles en la respuesta:** Para depuración, puedes incluir información adicional sobre la excepción en el mensaje de error o en un campo separado. Sin embargo, evita incluir información sensible. 
    - **Personalizar mensajes de error:** Adapta los mensajes de error a diferentes tipos de usuarios (por ejemplo, mensajes técnicos para desarrolladores y mensajes más amigables para usuarios finales).