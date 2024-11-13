#include "ByteBuffer.h"

// Constructor to allocate the buffer with a given size
ByteBuffer::ByteBuffer(size_t bufferSize)
    : size(bufferSize) {
    data = new uint8_t[size];  // Allocate memory for the buffer
}

// Destructor to free the allocated buffer
ByteBuffer::~ByteBuffer() {
    delete[] data;  // Clean up the allocated memory
}
