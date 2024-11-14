cmake_minimum_required(VERSION 3.10)
project(navtive_demo)

set(CMAKE_VERBOSE_MAKEFILE ON)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3")
# add_definitions(-DDEBUG)

# Find the JNI package
find_package(JNI REQUIRED)

# Set the output directory for the shared library
set(NATIVE_METHOD_HEADER_DIR ${CMAKE_SOURCE_DIR}/../JavaNativePerf/target/headers/)

set(SOURCES
    ByteBuffer.cpp 
    DataHelper.cpp 
    Executor.cpp 
    jni.cpp 
)
# Add the JNI implementation
add_library(jni_demo SHARED ${SOURCES})

# Link the JNI library
target_include_directories(jni_demo PRIVATE ${JNI_INCLUDE_DIRS})
target_include_directories(jni_demo PRIVATE ${NATIVE_METHOD_HEADER_DIR})
target_link_libraries(jni_demo ${JNI_LIBRARIES})


add_executable(demo_executor main.cpp )
target_link_libraries(demo_executor jni_demo)