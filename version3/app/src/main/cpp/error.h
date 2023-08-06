#ifndef SOCKETS_ANDROID_PDYTR2021_ERROR_H
#define SOCKETS_ANDROID_PDYTR2021_ERROR_H

#include <android/log.h>

void error (char * tag, char * msg){
    __android_log_print(ANDROID_LOG_INFO, tag, "%s", msg);
}

#endif //SOCKETS_ANDROID_PDYTR2021_ERROR_H