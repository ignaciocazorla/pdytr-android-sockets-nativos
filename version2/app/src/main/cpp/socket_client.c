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
#include "response.h"

#define tag "CLIENT"


JNIEXPORT jobject JNICALL
Java_com_example_sockets_1android_1pdytr2021_Client_runClient(JNIEnv *env, jobject thiz, jint port,
                                                              jstring msg) {

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
        return newResponse(env, 1, "ERROR opening socket");
    }

    // Indica la direccion a la que quiere conectarse
    server = gethostbyname("localhost");

    if (server == NULL) {
        return newResponse(env, 2, "ERROR, no such host");
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
        return newResponse(env, 3, "ERROR connecting");
    }

    bzero(buffer, 256);

    __android_log_print(ANDROID_LOG_INFO, tag, "%s", "OK!");

    // Prepara el mensaje recibido como argumento
    const char *message = (*env)->GetStringUTFChars(env, msg, NULL);

    // El mensaje que va a enviar al servidor
    strcpy(buffer, message);

    __android_log_print(ANDROID_LOG_INFO, tag, "%s", "OK! 2");

    // Envia un mensaje al socket
    n = write(sockfd, buffer, strlen(buffer));
    if (n < 0) {
        return newResponse(env, 4, "ERROR writing to socket");
    }
    bzero(buffer, 256);

    __android_log_print(ANDROID_LOG_INFO, tag, "%s", "OK! 3");

    // Espera recibir una respuesta
    n = read(sockfd, buffer, 255);
    if (n < 0) {
        return newResponse(env, 5, "ERROR reading from socket");
    }

    __android_log_print(ANDROID_LOG_INFO, tag, "%s", buffer);

    return newResponse(env, 0, buffer);
}