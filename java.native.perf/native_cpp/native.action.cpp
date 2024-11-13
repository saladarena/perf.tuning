#include <jni.h>
#include <iostream>
#include "com_example_HelloJNI.h"

using namespace std;

// C++ class that holds a reference to the Java object
class JNIObject {
public:
    JNIObject(JNIEnv* env, jobject obj) {
        this->env = env;
        this->obj = obj;
    }

    void callPrintMessage() {
        jclass clazz = env->GetObjectClass(obj); // Get the class of the object
        jmethodID methodId = env->GetMethodID(clazz, "printMessage", "()V"); // Get the method ID
        if (methodId != NULL) {
            env->CallVoidMethod(obj, methodId); // Call the method
        } else {
            cerr << "Method not found!" << endl;
        }
    }

private:
    JNIEnv* env;
    jobject obj;
};

// The native method implementation
extern "C" JNIEXPORT void JNICALL Java_com_example_HelloJNI_sayHello
  (JNIEnv* env, jobject thisObj, jobject obj) {
    JNIObject* nativeObj = new JNIObject(env, obj);
    nativeObj->callPrintMessage();  // Call the Java method
    delete nativeObj;
}
