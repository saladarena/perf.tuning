#ifndef BYTEBUFFER_H
#define BYTEBUFFER_H

#include <cstdint>
#include <cstdlib>  // For size_t

class ByteBuffer {
public:
    // Constructor to allocate the buffer with a given size
    ByteBuffer(size_t bufferSize);
    
    // Destructor to free the allocated buffer
    ~ByteBuffer();
    
    // Member variables
    uint8_t* data;   // Pointer to the buffer
    size_t size;     // Size of the buffer

private:
    // Disable copying and assignment (no deep copy)
    ByteBuffer(const ByteBuffer&) = delete;
    ByteBuffer& operator=(const ByteBuffer&) = delete;
};

#endif // BYTEBUFFER_H
