#include "DataHelper.h"
#include <iostream>
#include <cstring>


// Default constructor implementation
DataHelper::DataHelper() {
    // Nothing to initialize for now
}

// Destructor implementation
DataHelper::~DataHelper() {
    // Nothing to clean up, all buffers are managed in the ByteBuffer class
}

// Static function to create a ByteBuffer
ByteBuffer *  DataHelper::createBuffer(size_t size) {
    return new ByteBuffer(size);  // Return a ByteBuffer instance
}

// Static function to fill a ByteBuffer with a specific value
 // data   1 byte vaule 127,  total 128,  so one buffer has 256 data, data value are all same , from 1, to 255
void DataHelper::fillBuffer(ByteBuffer*  bufferPtr) {

    int dataLength = 127;
    for (int index =0; index < 256; index ++ ) {

        uint8_t * currentPtr = bufferPtr->data + (index * 128);

        *currentPtr = dataLength;

        for (int value = 0; value < dataLength; value ++ ) {
            currentPtr ++;
            *currentPtr = (value + index ) % 128;
        }
    }
}



// Static function to print the contents of a ByteBuffer
void DataHelper::printBuffer(const ByteBuffer& buffer) {
    std::cout << "Buffer: ";
    for (size_t i = 0; i < buffer.size; ++i) {
        std::cout << static_cast<int>(buffer.data[i]) << " ";  // Print as integer values
    }
    std::cout << std::endl;
}

// Static function to print the contents of a ByteBuffer
void DataHelper::printDataBuffer(const uint8_t * data, const size_t size) {
    std::cout << "Data Buffer: size " << size  << ":";
    for (size_t i = 0; i < size; ++i) {
        std::cout << static_cast<int>(data[i]) << " ";  // Print as integer values
    }
    std::cout << std::endl;
}


// Static function to delete a buffer (optional for cleanup)
void DataHelper::deleteBuffer(ByteBuffer& buffer) {
    // ByteBuffer cleans up automatically when it goes out of scope
    // This function is just a placeholder in this context.
}
