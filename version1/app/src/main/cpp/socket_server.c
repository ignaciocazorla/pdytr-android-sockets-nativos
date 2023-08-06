#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <jni.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <strings.h>
#include <unistd.h>
#include "error.h"

#define tag "SERVER"

/*void error(char *msg)
{
    __android_log_print(ANDROID_LOG_INFO, "SERVER", "%s", msg);
}*/


JNIEXPORT jint

JNICALL
Java_com_example_sockets_1android_1pdytr2021_MainActivity_runServer(JNIEnv *env, jobject thiz, jint port){

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
        error(tag, "ERROR opening socket");
        return 1;
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));

    // Asigna el puerto pasado por argumento
    // Asigna la ip en donde escucha (su propia ip)
    portno = (int) port;
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    // Vincula el file descriptor con la direccion y el puerto
    if (bind(sockfd, (struct sockaddr *) &serv_addr,
             sizeof(serv_addr)) < 0) {
        error(tag, "ERROR on binding");
        return 2;
    }

    // Setea la cantidad que pueden esperar mientras se maneja una conexion
    listen(sockfd,5);

    // Se bloquea a esperar una conexion
    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd,
                       (struct sockaddr *) &cli_addr,
                       &clilen);

    // Devuelve un nuevo file descriptor por el cual se van a realizar las comunicaciones
    if (newsockfd < 0){
        error(tag, "ERROR on accept");
        return 3;
    }

    bzero(buffer,256);

    // Lee el mensaje del cliente
    n = read(newsockfd,buffer,255);
    if (n < 0) {
        error(tag, "ERROR reading from socket");
        return 4;
    }

    __android_log_print(ANDROID_LOG_INFO, tag, "%s", buffer);

    // Responde al cliente
    n = write(newsockfd,"I got your message",18);
    if (n < 0) {
        error(tag, "ERROR writing to socket");
        return 5;
    }

    return 0;

}
