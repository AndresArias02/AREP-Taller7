# Plataforma de Microblogging con Quarkus

Este proyecto implementa una plataforma de microblogging similar a Twitter, que permite a los usuarios crear y ver publicaciones de hasta 140 caracteres. El proyecto sigue una progresión desde una arquitectura monolítica hasta un despliegue basado en microservicios sin servidor en AWS. A continuación, se presenta una descripción general del proceso de implementación y despliegue.

## Funcionalidades

- **Autenticación de Usuarios:** Inicio de sesión seguro y gestión de usuarios utilizando JWT y AWS Cognito.
- **Gestión de Publicaciones:** Los usuarios pueden crear y ver publicaciones.

## Comenzando

Las siguientes instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para fines de desarrollo y pruebas.

### Construido con:

* [Git](https://git-scm.com) - Control de versiones
* [Maven](https://maven.apache.org/download.cgi) - Manejador de dependencias
* [Java](https://www.oracle.com/java/technologies/downloads/#java22) - Lenguaje de programación
* [Quarkus](https://quarkus.io/) - Framework para desarrollo de aplicaciones Java
* [MongoDB](https://www.mongodb.com/) - Base de datos NoSQL
* [AWS](https://aws.amazon.com/) - Plataforma de servicios de nube

### Requisitos:

#### ⚠️ Importante

Es necesario tener instalado Git, Maven 3.9.9 y Java 17 para ejecutar el proyecto.

# Arquitectura de la Aplicación

La arquitectura de la aplicación utiliza varios servicios en la nube de AWS integrados con tecnologías como **Quarkus**, **MongoDB**, y otros componentes de AWS. A continuación, se detalla la arquitectura y el flujo de la aplicación.

![Image0](https://github.com/user-attachments/assets/53c6a3c5-afc7-432e-a9e9-7ea37247d6e2)

## **Componentes principales**

1. **Usuarios**:
    - Son los clientes o usuarios finales que interactúan con la aplicación desde el navegador.
    - Utilizan un cliente web que puede estar desarrollado con tecnologías como HTML, CSS y JavaScript.

2. **Amazon S3**:
    - Almacena los archivos estáticos de la aplicación (HTML, CSS y JavaScript).
    - Funciona como un servidor web estático que entrega los recursos del frontend a los navegadores de los usuarios.

3. **Amazon Cognito**:
    - Gestiona la autenticación y autorización de los usuarios.
    - Permite implementar flujos de inicio de sesión seguros (login, registro, recuperación de contraseñas).

4. **Amazon API Gateway**:
    - Proporciona un punto de entrada centralizado para las solicitudes de la aplicación.
    - Gestiona el enrutamiento de las solicitudes entrantes hacia las funciones de AWS Lambda según el método y ruta solicitados.

5. **AWS Lambda**:
    - Ejecución de la lógica de negocio sin necesidad de administrar servidores.
    - La arquitectura define dos funciones principales:
        - **GET: `/api/v1/post`**: Recupera información almacenada en MongoDB.
        - **POST: `/api/v1/post`**: Procesa y almacena datos enviados por los usuarios hacia MongoDB.

6. **MongoDB**:
    - Base de datos utilizada para almacenar datos persistentes de la aplicación.
    - Se conecta con las funciones Lambda para realizar operaciones de lectura y escritura.

7. **Quarkus**:
    - Framework Java utilizado para construir servicios REST.
    - Sirve como base para desarrollar la lógica de negocio que se ejecuta dentro de las funciones Lambda.

---

## **Flujo de la aplicación**

1. **Interacción inicial del usuario**:
    - Los usuarios acceden a la aplicación desde un navegador web.
    - Los recursos estáticos (HTML, CSS, JavaScript) son entregados desde **Amazon S3**.

2. **Autenticación del usuario**:
    - Antes de acceder a los recursos protegidos, los usuarios pasan por un proceso de autenticación gestionado por **Amazon Cognito**.
    - Cognito genera un token de autenticación para identificar al usuario en las solicitudes posteriores.

3. **Realización de solicitudes API**:
    - Una vez autenticado, el frontend envía solicitudes al **Amazon API Gateway**.
    - Las solicitudes pueden ser:
        - **GET**: Para obtener datos almacenados.
        - **POST**: Para enviar y almacenar nueva información.

4. **Procesamiento en AWS Lambda**:
    - El **API Gateway** redirige las solicitudes hacia la función Lambda correspondiente según la ruta y el método HTTP.
    - Las funciones Lambda, desarrolladas con **Quarkus**, ejecutan la lógica de negocio, procesan la solicitud y se conectan con **MongoDB** para interactuar con los datos.

5. **Interacción con MongoDB**:
    - Las funciones Lambda realizan operaciones de lectura (para GET) o escritura (para POST) en la base de datos.
    - MongoDB almacena los datos de manera persistente y devuelve las respuestas necesarias a Lambda.

6. **Respuesta al usuario**:
    - La función Lambda devuelve la respuesta al **API Gateway**.
    - **API Gateway** envía la respuesta al cliente web del usuario.
    - El frontend muestra los resultados al usuario en la interfaz.
---

## Cómo comprender el funcionamiento de las funcionalidades requeridas

### TokenController

Esta clase es responsable de gestionar la autenticación y autorización de los usuarios. En este caso, se implementa el proceso de login seguro utilizando AWS Cognito. La clase TokenController se encarga de manejar las solicitudes POST `/api/v1/security/login` y devolver un token de autenticación.

```bash
       @Path("/security")
public class TokenController {

    @Inject
    TokenService tokenService;

    @Inject
    UserService userService;

    /**
     * login method
     * This method logs in a user
     * @param user the user to be logged in
     * @return Response with the token
     */
    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Login(User user) {
        if(userService.verifyPassword(user.getUserName(),user.getHashedPassword())){
            TokenDto token = tokenService.generateToken(user);
            return Response.status(200).entity(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
        .....
  ```
### UserController

Esta clase es responsable de gestionar las operaciones relacionadas con los usuarios. En este caso, se encarga de gestionar las operaciones de obtener, crear y eliminar usuarios. 

```bash
@Path("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    /**
     * getUsers method
     * This method returns a list of users
     * @return Response with the list of users
     */
    @GET
    @RolesAllowed({"Admin", "User"})
    public Response getUsers() {
        return Response.status(200).entity(userService.getUsers()).build();
    }


    /**
     * createUser method
     * This method creates a new user
     * @param user the user to be created
     * @return Response with the created user
     */
    @POST
    @PermitAll
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.status(201).entity(user).build();
    }
    .......
  ```
### PostController

Esta clase es responsable de gestionar las operaciones relacionadas con los posts. En este caso, se encarga de gestionar las operaciones de obtener, crear y eliminar posts.

```bash
@Path("/api/v1/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    /**
     * getPosts method
     * This method returns a list of posts
     * @return Response with the list of posts
     */
    @GET
    @RolesAllowed({"Admin", "User"})
    public Response getPosts() {
        List<Post> posts = postService.getPosts();
        return Response.status(200).entity(posts).build();
    }

    /**
     * createPost method
     * This method creates a new post
     * @param post the post to be created
     * @return Response with the created post
     */
    @POST
    @RolesAllowed({"Admin", "User"})
    public Response createPost(Post post) {
        postService.createPost(post);
        return Response.status(201).entity(post).build();
    }
    .....
  ```
--- 

## Demostracion de las funcionalidades

### Registrar un Usuario

En esta imagen se muestra el registro de un usuario con los siguientes datos: andres, 123456.

![image01](https://github.com/user-attachments/assets/fe82c4cc-5652-4420-8d69-7dd532ed860a)

En esta imagen se muestra el registro de un usuario con los siguientes datos: David, 123456.

![image02]((https://github.com/user-attachments/assets/9657def3-7336-4fca-8063-4fe6f9935fda)

### Logearse en el Sistema

En esta imagen se muestra el login de un usuario con los siguientes datos: andres, 123456.

![image03](https://github.com/user-attachments/assets/0b8f5f31-e076-49b4-9192-e70d8de951dd)

En esta imagen se muestra el login de un usuario con los siguientes datos: David, 123456.

![image04](https://github.com/user-attachments/assets/dca077f8-e742-47be-8d2e-15c412e3f882)

### crear una publicación

En esta imagen se muestra la creación de una publicación del usuario andres..

![image05](https://github.com/user-attachments/assets/2a6947db-9dff-4b3a-963e-3956b40b48a1)

En esta imagen se observa la publicacion creada por el usuario andres y la que se va a publicar por el usuario David.

![image06](https://github.com/user-attachments/assets/ff5e68cf-e27c-4824-b18b-3cd127b978db)

En esta imagen se muestra la publicación creada por el usuario David en la pantalla del usuario andres.

![image07](https://github.com/user-attachments/assets/9657def3-7336-4fca-8063-4fe6f9935fda)

--- 

## Instalación y ejecución

Para instalar y ejecutar esta aplicación, sigue los siguientes pasos:

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/AndresArias02/AREP-Taller7.git
   cd AREP-taller7
   ```

2. **Compilar y ejecutar:**

    ```bash 
   mvn clean package
   docker-compose up -d
   ```

3. **Abrir la aplicación en un navegador web:**

   Navega a http://localhost:8080 para interactuar con la aplicación.

## Ejecutando las pruebas

Para ejecutar las pruebas, ejecute el siguiente comando:

```bash
mvn quarkus:test
```
![image06](https://github.com/user-attachments/assets/4bdaea3e-075e-4817-880a-c76bbac53f68)
## versionamiento

![AREP LAB 07](https://img.shields.io/badge/AREP_LAB_07-v1.0.0-blue)

## Autores

- Andrés Arias - [Andres Felipe Arias Ajiaco](https://github.com/AndresArias02)
- David Piñeros - [David Leonardo Piñeros cortes](https://github.com/leoncico)
- Sebastian Blanco - [Sebastian David Blanco Rodriguez](https://github.com/sebastian2929)

## Licencia

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Este proyecto está bajo la Licencia (MIT) - ver el archivo [LICENSE](LICENSE.md) para ver más detalles.

## Agradecimientos

- Al profesor [Luis Daniel Benavides Navarro](https://ldbn.is.escuelaing.edu.co) por compartir sus conocimientos.
    
