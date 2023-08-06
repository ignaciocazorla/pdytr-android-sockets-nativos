
#ifndef SOCKETS_ANDROID_PDYTR2021_RESPONSE_H
#define SOCKETS_ANDROID_PDYTR2021_RESPONSE_H

jobject newResponse(JNIEnv *env, int status, char * msg){
    // Crea el objeto Response que va a contener la respuesta:
    // Obtiene la referencia a la clase Response
    jclass cls = (*env)->FindClass(env, "com/example/sockets_android_pdytr2021/Response");

    // Obtiene el id del metodo constructor de la clase Response
    jmethodID init = (*env)->GetMethodID(env, cls, "<init>", "(ILjava/lang/String;)V");

    jstring payload = (*env)->NewStringUTF(env, msg);

    // Crear el objeto
    return (*env)->NewObject(env, cls, init, status, payload);
}

#endif //SOCKETS_ANDROID_PDYTR2021_RESPONSE_H
