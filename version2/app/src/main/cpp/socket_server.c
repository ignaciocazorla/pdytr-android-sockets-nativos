#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <errno.h>
#include <jni.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <strings.h>
#include <unistd.h>
#include "error.h"

#define tag "SERVER"

JNIEXPORT jobject JNICALL
Java_com_example_sockets_1android_1pdytr2021_Server_runServer(JNIEnv *env, jobject thiz, jint port) {

    /* Servidor TCP. Recibe el puerto como argumento */

    int sockfd, newsockfd, portno, clilen;
    char buffer[256];
    struct sockaddr_in serv_addr, cli_addr;
    int n;

    // Crea el file descriptor del socket para la conexion
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    //  AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
    //  SOCK_STREAM - TIPO DE SOCKET

    if (sockfd < 0) {
        return newResponse(env, 1, "ERROR opening socket");
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));

    // Asigna el puerto pasado por argumento
    // Asigna la ip en donde escucha (su propia ip)
    portno = (int) port;
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY; //When we don't know the IP address of our machine, we can use the special IP address INADDR_ANY. It allows our server to receive packets that have been targeted by any of the interfaces.
    serv_addr.sin_port = htons(portno);

    // Vincula el file descriptor con la direccion y el puerto
    if (bind(sockfd, (struct sockaddr *) &serv_addr,
             sizeof(serv_addr)) < 0) {
        strcpy(buffer, "ERROR on binding:\n");
        strcat(buffer, strerror(errno));
        return newResponse(env, 2, buffer);
    }

    // Setea la cantidad de clientes que pueden esperar mientras se maneja una conexion
    listen(sockfd,1);

    // Se bloquea a esperar una conexion
    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd,
                       (struct sockaddr *) &cli_addr,
                       &clilen);

    // Devuelve un nuevo file descriptor por el cual se van a realizar las comunicaciones
    if (newsockfd < 0){
        return newResponse(env, 3, "ERROR on accept");
    }

    bzero(buffer,256);

    // Lee el mensaje del cliente
    n = read(newsockfd,buffer,255);
    if (n < 0) {
        return newResponse(env, 4, "ERROR reading from socket");
    }

    //__android_log_print(ANDROID_LOG_INFO, tag, "%s", buffer);

    // Responde al cliente
    n = write(newsockfd,"Tengo tu mensaje!",18);
    if (n < 0) {
        return newResponse(env, 5, "ERROR writing to socket");
    }

    // Cerrar el socket
    shutdown(sockfd,SHUT_RDWR);

    return newResponse(env, 0, buffer);

}