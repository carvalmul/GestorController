# GestorController

Este proyecto es una API REST en Spring Boot para gestionar proyectos y tareas. La idea principal es que el usuario hace una petición HTTP, el controlador la recibe, la valida, la transforma a un modelo del dominio, la lógica de negocio la procesa y finalmente se guarda en memoria dentro de repositorios en Java.

> Importante: no hay base de datos real aquí. Los repositorios usan listas en memoria (`ArrayList`), así que los datos desaparecen al reiniciar la aplicación.

---

## 1. Visión general de la arquitectura

La aplicación sigue una estructura por capas muy típica:

1. `controller`: recibe las peticiones HTTP y responde con JSON.
2. `dto`: define lo que entra y sale por la API.
3. `mapper`: convierte entre DTO y modelo de negocio.
4. `service`: aplica la lógica de negocio.
5. `repository`: guarda y consulta los datos en memoria.
6. `model`: representa los objetos del dominio (`Proyecto`, `Tarea`).
7. `error`: gestiona errores y devuelve respuestas HTTP coherentes.
8. `validacion`: añade validaciones personalizadas.

El flujo general es:

```text
HTTP request
   ↓
Controller
   ↓
DTO / validación
   ↓
Mapper
   ↓
Service
   ↓
Repository
   ↓
Modelo / entidad
   ↓
Mapper de salida
   ↓
Response JSON
```

---

## 2. Estructura del proyecto

```text
src/
├── main/
│   ├── java/
│   │   └── com/ejemplo/gestor/
│   │       ├── GestorApplication.java
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── error/
│   │       ├── mapper/
│   │       ├── memoria/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── validacion/
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/ejemplo/gestor/
```

---

## 3. Qué hace cada carpeta

### `src/main/java/com/ejemplo/gestor`

Este es el paquete raíz de la aplicación. Aquí reside la clase principal que arranca el proyecto.

- `GestorApplication.java`: es la entrada de la aplicación Spring Boot. Solo se encarga de lanzar la app con `SpringApplication.run(...)`.

### `controller`

Aquí están los endpoints HTTP. Este paquete expone la API REST.

- `ProyectoController.java`: gestiona todo lo relacionado con proyectos.
  - `GET /proyectos`: lista proyectos.
  - `GET /proyectos/{id}`: obtiene un proyecto concreto.
  - `GET /proyectos/{id}/tareas`: devuelve las tareas que pertenecen a ese proyecto.
  - `POST /proyectos`: crea un proyecto.
  - `PUT /proyectos/{id}`: reemplaza un proyecto entero.
  - `PATCH /proyectos/{id}`: modifica parcialmente un proyecto.
  - `DELETE /proyectos/{id}`: borra un proyecto.

- `TareaController.java`: gestiona tareas.
  - `GET /tareas`: lista tareas, con filtro por completada o no.
  - `GET /tareas/{id}`: obtiene una tarea concreta.
  - `POST /tareas`: crea una tarea.
  - `PUT /tareas/{id}`: reemplaza una tarea.
  - `PATCH /tareas/{id}`: modifica parcialmente la tarea.
  - `DELETE /tareas/{id}`: elimina una tarea.

- `Incidencia.java`: no es un `@RestController` ni un endpoint real; parece ser una clase de ejemplo o modelo auxiliar aislado. No forma parte del flujo principal de la API.

### `dto`

Este paquete define los objetos que entran y salen por la API.

- `ProyectoRequest`: representa el JSON que llega para crear o actualizar un proyecto.
- `ProyectoPatchRequest`: representa cambios parciales de un proyecto.
- `ProyectoResponse`: representa el JSON que devuelve la API al cliente para proyectos.
- `TareaRequest`: representa el JSON que llega para crear o actualizar una tarea.
- `TareaPatchRequest`: representa cambios parciales de una tarea.
- `TareaResponse`: representa el JSON de salida para tareas.

Estos objetos son importantes porque separan la representación HTTP de los modelos internos. El cliente no trabaja directamente con `Proyecto` o `Tarea`, sino con DTOs más seguros y controlados.

### `mapper`

Los mappers convierten entre DTOs y modelos.

- `ProyectoMapper`: transforma un `ProyectoRequest` o `ProyectoPatchRequest` en un `Proyecto`, y un `Proyecto` en un `ProyectoResponse`.
- `TareaMapper`: hace lo mismo para tareas.

Es decir, el mapper se encarga de pasar un dato de un formato a otro sin mezclar responsabilidades. La capa de control no decide cómo se construye el modelo; simplemente delega en el mapper.

### `model`

Aquí están las entidades de negocio.

- `Proyecto`: representa un proyecto con id, nombre, descripción, estado activo y número de incidencias.
- `Tarea`: representa una tarea con id, título, prioridad, si está completada, y a qué proyecto pertenece.

Estos modelos son la representación del dominio de la aplicación; no son los JSON de entrada ni salida, sino la estructura interna con la que trabaja la lógica de negocio.

### `service`

La capa de servicio contiene la lógica de negocio.

- `ProyectoService`: valida reglas relacionadas con proyectos:
  - lista proyectos según filtro de activo/inactivo,
  - obtiene un proyecto por id,
  - evita duplicados por nombre ignorando mayúsculas/minúsculas,
  - marca proyectos como activos al crearlos,
  - actualiza o modifica propiedades del proyecto,
  - devuelve las tareas asociadas a un proyecto,
  - elimina un proyecto.

- `TareaService`: aplica la lógica de tareas:
  - lista tareas por estado de completada,
  - obtiene una tarea por id,
  - crea nuevas tareas con `completada = false`,
  - reemplaza o modifica campos de una tarea,
  - elimina una tarea.

La capa de servicio es el punto donde se decide qué está permitido, qué no y qué regla de negocio se debe aplicar antes de guardar o devolver algo.

### `repository`

Los repositorios simulan la persistencia, pero en este caso no usan base de datos: guardan todo en listas en memoria.

- `ProyectoRepository`: almacena proyectos y ofrece consultas como `findAll`, `findById`, `save`, `deleteById` y `existsByNombreIgnoreCase`.
- `TareaRepository`: almacena tareas y ofrece `findAll`, `findById`, `findByProyectoId`, `save` y `deleteById`.

Esto hace que la app funcione como un “backend en memoria”, ideal para practicar API REST sin depender de BD.

### `error`

Este paquete centraliza la gestión de errores.

- `RecursoNoEncontradoException`: se lanza cuando no existe un proyecto o una tarea con el id indicado.
- `ConflictoException`: se lanza cuando hay un conflicto de negocio, por ejemplo, un proyecto duplicado.
- `CampoError`: representa un error de validación asociado a un campo concreto.
- `ManejadorDeErrores`: es la clase con `@RestControllerAdvice`, y transforma excepciones en respuestas HTTP estandarizadas (`ProblemDetail`).

Es decir, si el servicio falla por un recurso inexistente, o si la validación falla, el cliente recibe un JSON de error estructurado en lugar de una excepción Java sin control.

### `validacion`

Aquí se define la validación custom.

- `PrioridadValida`: es una anotación para validar que la prioridad de una tarea sea una de `baja`, `media` o `alta`.
- `PrioridadValidaValidator`: es la lógica que comprueba si el valor recibido es válido o no.

Esto permite que una tarea no pueda llegar al servicio con una prioridad inválida, porque la validación ocurre antes de que el servicio procese la petición.

### `memoria`

- `MemoriaProyecto`: es una estructura auxiliar con dos listas: una para proyectos y otra para tareas. Aunque el proyecto parece haber empezado a usar esta clase como contenedor compartido, la lógica real de persistencia termina estando en los repositorios.

### `resources`

- `application.properties`: contiene la configuración de la aplicación. En este caso solo tiene `spring.application.name=gestor`.

### `test`

- `GestorApplicationTests.java`: prueba que el contexto de Spring arranca correctamente.

---

## 4. Flujo de un dato completo

### Caso A: crear un proyecto

1. El cliente hace una petición HTTP como:

```http
POST /proyectos
Content-Type: application/json
```

2. `ProyectoController.crear(...)` recibe el JSON en un `ProyectoRequest`.
3. La anotación `@Valid` activa las restricciones del DTO (nombre obligatorio, longitud máxima, etc.).
4. Si la validación pasa, el controlador llama a `ProyectoMapper.aModelo(peticion)`.
5. `ProyectoMapper` crea un `Proyecto` y rellena sus propiedades (`nombre`, `descripcion`).
6. El controlador llama a `ProyectoService.crear(proyecto)`.
7. `ProyectoService` aplica la regla de negocio:
   - comprueba si ya existe otro proyecto con el mismo nombre ignorando mayúsculas/minúsculas,
   - si existe, lanza `ConflictoException`,
   - si no, marca el proyecto como activo y lo envía al repositorio.
8. `ProyectoRepository.save(proyecto)` asigna un ID y lo guarda en la lista interna.
9. El `ProyectoService` devuelve el proyecto creado al controlador.
10. `ProyectoMapper.aRespuesta(proyecto)` convierte el modelo a `ProyectoResponse`.
11. El controlador devuelve una respuesta `201 Created` con `Location: /proyectos/{id}` y el JSON del proyecto creado.

### Caso B: crear una tarea

1. El cliente hace:

```http
POST /tareas
Content-Type: application/json
```

2. `TareaController.crear(...)` recibe un `TareaRequest`.
3. `@Valid` comprueba:
   - título obligatorio,
   - prioridad válida (`baja`, `media`, `alta`),
   - `proyectoId` obligatorio y positivo.
4. El DTO se transforma con `TareaMapper.aModelo(peticion)`.
5. El servicio `TareaService.crear(tarea)` le asigna el estado por defecto `completada = false`.
6. El repositorio `TareaRepository.save(tarea)` asigna id y guarda la tarea en memoria.
7. La respuesta sale convertida a `TareaResponse` con `TareaMapper.aRespuesta(...)`.
8. El cliente recibe la tarea creada con su `id`, título, prioridad, estado y proyecto asociado.

### Caso C: listar tareas de un proyecto

1. El cliente llama a:

```http
GET /proyectos/{id}/tareas
```

2. `ProyectoController.tareasDelProyecto(id)` recibe el id del proyecto.
3. El controlador delega en `ProyectoService.tareasDelProyecto(id)`.
4. El servicio primero valida que ese proyecto exista con `obtener(id)`, que a su vez usa `ProyectoRepository.findById(id)`.
5. Si existe, llama a `TareaRepository.findByProyectoId(id)`.
6. El repositorio filtra la lista en memoria por `proyectoId`.
7. El mapper `TareaMapper.aRespuestas(...)` convierte la lista de modelos a `TareaResponse`.
8. El JSON con las tareas asociadas se devuelve al cliente.

---

## 5. Cómo se conectan todas las capas

La relación entre capas es bastante directa:

- `controller` → pregunta al `service`.
- `service` → usa el `repository` para leer o guardar datos.
- `repository` → trabaja con listas en memoria.
- `model` → define las entidades.
- `dto` → define qué llega y sale por HTTP.
- `mapper` → hace la conversión entre DTO y model.
- `error` → convierte errores en respuestas HTTP coherentes.
- `validacion` → corta peticiones inválidas antes de llegar al servicio.

En pocas palabras, el flujo de datos no es más que una cadena de transformación:

```text
JSON de petición
→ DTO
→ modelo del dominio
→ validación y lógica de negocio
→ repositorio en memoria
→ modelo de salida
→ JSON de respuesta
```

---

## 6. Resumen rápido

Este proyecto es una API REST pequeña y didáctica para gestionar proyectos y tareas. Su fortaleza es la claridad:

- los controladores dicen qué rutas existen,
- los DTOs definen el contrato HTTP,
- los mappers convierten datos,
- los servicios aplican reglas,
- los repositorios almacenan datos en memoria,
- y el manejador de errores devuelve respuestas uniformes.

Si te pones a seguir un dato desde la petición hasta la respuesta, verás que siempre sigue el mismo patrón: la información entra por un controlador, pasa por validación y mapeo, es procesada por servicio, almacenada en repositorio y vuelve convertida a JSON.
