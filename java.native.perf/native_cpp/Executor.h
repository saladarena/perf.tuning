#ifndef EXECUTOR_H
#define EXECUTOR_H

#include <cstdint>  // for uint8_t
#include <cstddef>  // for size_t

class Executor {
private:
    uint8_t* buffer;   // Pointer to the buffer (dynamic array)
    size_t size_;       // Size of the buffer
    int position;

    uint8_t* outputBufferAddress_;
    size_t outputSize_;

    uint8_t * internalPre_;
    size_t internlPreSize;

    uint8_t * internalPost_;
    size_t internlPostSize;

public:
    // Constructor: Initializes the buffer with a given size
    explicit Executor(uint8_t* outputBufferAddress, size_t outputSize);

    // Destructor: Releases the dynamically allocated memory
    ~Executor();

    void setBuffer(uint8_t * bufferAddress, int size);

    //retStatus 0 more data , others buffer completed.
    void processData();

    int handleOutputInternal(uint8_t *output, size_t size);

    int handleOutput ();
};

#endif // EXECUTOR_H