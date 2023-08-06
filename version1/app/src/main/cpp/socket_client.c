#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <jni.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define tag "CLIENT"

JNIEXPORT jint

JNICALL
Java_com_example_sockets_1android_1pdytr2021_MainActivity_runClient(JNIEnv *env, jobject thiz, jint port){

    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;
    char buffer[256];

    // Toma el numero de puerto de los argumentos
    portno = port;

    // Crea el file descriptor del socket para la conexion
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    //AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
    //SOCK_STREAM - TIPO DE SOCKET

    if (sockfd < 0) {
        error(tag, "ERROR opening socket");
        return 1;
    }

    // Indica la direccion a la que quiere conectarse
    server = gethostbyname("localhost");

    if (server == NULL) {
        error(tag, "ERROR, no such host");
        return 2;
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;

    // Copia la direccion ip y el puerto del servidor a la estructura del socket
    bcopy((char *) server->h_addr,
          (char *) &serv_addr.sin_addr.s_addr,
          server->h_length);
    serv_addr.sin_port = htons(portno);

    //DESCRIPTOR - DIRECCION - TAMAÑO DIRECCION
    if (connect(sockfd, &serv_addr, sizeof(serv_addr)) < 0) {
        error(tag, "ERROR connecting");
        return 3;
    }

    bzero(buffer, 256);

    // El mensaje que va a enviar al servidor
    strcpy(buffer, "Hola");

    // Envia un mensaje al socket
    n = write(sockfd, buffer, strlen(buffer));
    if (n < 0) {
        error(tag, "ERROR writing to socket");
        return 4;
    }
    bzero(buffer, 256);

    // Espera recibir una respuesta
    n = read(sockfd, buffer, 255);
    if (n < 0) {
        error(tag, "ERROR reading from socket");
        return 5;
    }

    return 0;
}

