#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("android");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("android")
//      }
//    }

#include <jni.h>
#include <string>

extern "C"

jstring

Java_com_crater_android_data_network_AuthInterceptor_getTokenFromJNI(
        JNIEnv *env,
        jclass clazz
) {
    std::string token = "d2g5UmJoWkVVY1FOYTZObVl2N3RJTGJVZFh4X2tmcXdab2RnbF92VGVYNA==";
    return env->NewStringUTF(token.c_str());
}