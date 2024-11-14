#include "org_example_NativeActions.h"
#include "Executor.h"

/*
 * Class:     org_example_NativeActions
 * Method:    createNativeExecutor
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_org_example_NativeActions_createNativeExecutor
  (JNIEnv *, jobject, jlong outputBufferAddress, jint size) {
    auto * outputBufferPtr = reinterpret_cast<uint8_t *>(outputBufferAddress);
    auto * executor = new Executor(outputBufferPtr, size);
    return reinterpret_cast<jlong>(executor);
  }

/*
 * Class:     org_example_NativeActions
 * Method:    setBuffer
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_org_example_NativeActions_setBuffer
  (JNIEnv *, jobject, jlong executorRef, jlong bufferAddress, jint size) {
    auto * executor = reinterpret_cast<Executor *>(executorRef);
    executor-> setBuffer(reinterpret_cast<uint8_t *> (bufferAddress), size);
  }

/*
 * Class:     org_example_NativeActions
 * Method:    processData
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_example_NativeActions_processData
  (JNIEnv *, jobject, jlong executorRef) {
    auto * executor = reinterpret_cast<Executor *> (executorRef);
    executor->processData();
  }