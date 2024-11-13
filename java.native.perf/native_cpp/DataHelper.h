#ifndef DATAHELPER_H
#define DATAHELPER_H

#include <cstdint>
#include "ByteBuffer.h"  // Include the ByteBuffer class

class DataHelper {
public:
    // Default constructor
    DataHelper();

    // Destructor (if we need to manage any dynamic buffers)
    ~DataHelper();

    // Static functions to create and fill buffers
    static ByteBuffer*  createBuffer(size_t size);

 // data   1 byte vaule 127,  total 128,  so one buffer has 256 data, data value are all same , from 1, to 255
    static void fillBuffer(ByteBuffer * buffer);

    // Static functions to print buffer contents
    static void printBuffer(const ByteBuffer& buffer);
    static void printDataBuffer(const uint8_t * data, const size_t size);

private:
    // Helper function to delete a buffer (not needed here, but may be useful in other contexts)
    static void deleteBuffer(ByteBuffer& buffer);
};

#endif // DATAHELPER_H
