# CMakeLists.txt

# Set the minimum required version of CMake
cmake_minimum_required(VERSION 3.22)

# Set the project name and version
project(MemcpyExample VERSION 1.0 LANGUAGES CXX)
set(CMAKE_VERBOSE_MAKEFILE ON)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3")

set(SOURCE_FILES
    memcpy.cpp
)
set_source_files_properties(${SOURCE_FILES}
    LANGUAGE CXX
)

# Add the executable target
add_executable(memcpy ${SOURCE_FILES})
