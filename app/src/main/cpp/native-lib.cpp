#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL
Java_com_example_newsapp_util_Constants_getStringBaseUrlDevelopment(JNIEnv *env, jclass clazz) {
 return env->NewStringUTF("https://newsapi.org/");
}
