# pdytr-android-sockets-nativos

Trabajo final para la materia Programación distribuida y tiempo real.

* **Cómo ver el código si no tengo instalado Android Studio?**
* **Cómo importar código en Android Studio?**
* **Compilar código C**


### Cómo ver el código si no tengo instalado Android Studio?

El código se encuentra en app/src/main.
Por ejemplo, si queremos ver alguna de las versiones necesitamos acceder a la ruta ./versionX/app/src/main .


### Cómo importar código en Android Studio?

Para importar el proyecto sólo es necesario abrir la carpeta que contiene el código con Android Studio. Por ejemplo, si decidimos utilizar el código de ‘version3’:

![imagen](https://github.com/ignaciocazorla/pdytr-android-sockets-nativos/assets/22487770/50c6e4c1-d2c0-4ec1-ae59-9546d2766fec)

Presionamos "Open".

![imagen](https://github.com/ignaciocazorla/pdytr-android-sockets-nativos/assets/22487770/468d23e5-2b69-4a28-a638-e0e05d853cc5)


Presionamos “OK”.

A continuación, Gradle configurará nuestro proyecto automáticamente. Luego, podremos ejecutar la aplicación.

### Compilar código C

En **CodigoBaseSocketsC** podemos encontrar, tanto el cliente como el servidor que se tomaron como base.
Para compilar estos archivos fuente en **GNU/Linux**:
> $ gcc -o servidor server.c

> $ gcc -o cliente client.c

#### Ejecución
Servidor  
> $ ./servidor num_puerto

Cliente
> $ ./cliente dir_ip num_puerto
