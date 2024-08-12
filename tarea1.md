
# Conceptos Fundamentales

## ¿Qué es un servicio REST?
Un servicio REST (Representational State Transfer) es un tipo de servicio web que sigue los principios y restricciones de la arquitectura REST. Este tipo de servicio permite la comunicación entre sistemas a través de HTTP, utilizando métodos estándar y sin estado para interactuar con recursos de datos.

## ¿Cuáles son los principios del diseño RESTful?
- `Interfaz Uniforme:` Define una interfaz estándar para interactuar con los recursos, generalmente usando métodos HTTP.
- `Sin Estado (Stateless):` Cada solicitud del cliente al servidor debe contener toda la información necesaria para entender y procesar la solicitud. No se debe almacenar el estado de la sesión en el servidor.
- `Cacheabilidad:` Las respuestas deben ser etiquetadas como cacheables o no cacheables para que los clientes puedan almacenar en caché las respuestas y mejorar el rendimiento.
- `Cliente-Servidor:` La arquitectura debe separar las preocupaciones del cliente y del servidor. El cliente maneja la interfaz de usuario, mientras que el servidor maneja el almacenamiento de datos y la lógica de negocio.
- `Sistema en Capas:` Un sistema REST puede estar compuesto de capas jerárquicas que mejoran la escalabilidad y la seguridad.
- `Código Bajo Demanda (opcional):` Los servidores pueden proporcionar código ejecutable (por ejemplo, scripts JavaScript) a los clientes bajo demanda.

## ¿Qué es HTTP y cuáles son los métodos HTTP más comunes?
HTTP (HyperText Transfer Protocol) es un protocolo de comunicación utilizado para enviar y recibir información en la web. Los métodos HTTP más comunes son:

- `GET:` Solicita un recurso del servidor.
- `POST:` Envía datos al servidor para crear un nuevo recurso.
- `PUT:` Actualiza un recurso existente en el servidor.
- `DELETE:` Elimina un recurso del servidor.
- `PATCH:` Aplica modificaciones parciales a un recurso. 

## ¿Qué es un recurso en el contexto de un servicio REST?
Un recurso es cualquier información que puede ser nombrada y que se puede acceder a través de una URI (Uniform Resource Identifier). En un servicio REST, los recursos son representaciones de datos que pueden ser manipulados a través de métodos HTTP.

## ¿Qué es un endpoint? 
Un endpoint es una URL específica dentro de una API REST que permite acceder a un recurso particular o ejecutar una operación específica. Es el punto final de la comunicación entre el cliente y el servidor.

# Estructura de un Servicio REST

## ¿Qué es un URI y cómo se define?
Un URI (Uniform Resource Identifier) es una cadena de caracteres que identifica un recurso de forma única en la web. Se define utilizando un esquema (como HTTP), un nombre de dominio, un puerto (opcional), una ruta, y parámetros de consulta (opcionales). Ejemplo:
> http://www.example.com/resource/id

## ¿Qué es una API RESTful?
Una API RESTful es una interfaz de programación de aplicaciones que sigue los principios y restricciones de la arquitectura REST. Permite la interacción con los recursos a través de métodos HTTP estándar.

## ¿Qué son los códigos de estado HTTP y cómo se usan en REST?
Los códigos de estado HTTP son códigos de tres dígitos que indican el resultado de una solicitud HTTP. En REST, se usan para informar al cliente sobre el estado de su solicitud. Los códigos de estado se dividen en varias categorías:

- `1xx (Informativo):` La solicitud fue recibida y el proceso continúa.
- `2xx (Éxito):` La solicitud fue recibida, entendida y aceptada con éxito.
- `3xx (Redirección):` Se requiere una acción adicional para completar la solicitud.
- `4xx (Error del Cliente):` La solicitud contiene una sintaxis incorrecta o no puede ser procesada.
- `5xx (Error del Servidor):` El servidor falló al cumplir una solicitud válida.

### Tabla con los códigos HTTP de respuesta más comunes y su significado:
```
Código	Significado
200	OK (Solicitud exitosa)
201	Created (Recurso creado exitosamente)
204	No Content (Sin contenido)
400	Bad Request (Solicitud incorrecta)
401	Unauthorized (No autorizado)
403	Forbidden (Prohibido)
404	Not Found (No encontrado)
500	Internal Server Error (Error interno del servidor)
503	Service Unavailable (Servicio no disponible)
```

## ¿Qué es JSON y por qué se usa comúnmente en APIs REST?
JSON (JavaScript Object Notation) es un formato de texto ligero para el intercambio de datos. Es fácil de leer y escribir para los humanos y fácil de parsear y generar para las máquinas. Se usa comúnmente en APIs REST porque es independiente del lenguaje de programación, tiene una estructura clara y es eficiente para la transmisión de datos.
![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJrn9aASxzrCkFIIkPocMG2hKAo3Yq47XnEQ&s)