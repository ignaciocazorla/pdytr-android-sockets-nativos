#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_pruebacodigonativo_MainActivity_saludar(JNIEnv *env, jobject thiz) {
    char * hello = "Hola desde C!";
    return (*env)->NewStringUTF(env, hello);
}