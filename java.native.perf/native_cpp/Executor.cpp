#include "Executor.h"
#include <iostream>
#include <cstring>  // for memset
#include <stdexcept> // for exception handling
#include "common.h"
#include "DataHelper.h"

// Constructor: Initializes the buffer with a given size
Executor::Executor(uint8_t* outputBufferAddress, size_t outputSize): 
   outputBufferAddress_(outputBufferAddress), outputSize_(outputSize) {

    //create internal pre buffer
    internlPreSize = 1024;
    internalPre_ = new uint8_t[internlPreSize];


    internlPostSize = 1024;
    internalPost_ = new uint8_t[internlPostSize];

}


// Destructor: Releases the dynamically allocated memory
Executor::~Executor() {
    delete[] buffer;
    std::cout << "Buffer of size " << size_ << " destroyed.\n";
}

void Executor::setBuffer(uint8_t *bufferAddress, int size)
{
    LOG("set Buffer");
    buffer = bufferAddress;
    size_ = size;
    position = 0;
}


// data encoding  1 byte uint8_t length, then length of bytes
void Executor::processData()
{
    
    LOG("Beginnng of processData position " << position );
    // 1. data from buffer to internal pre , total length + 1
    int length = static_cast<int>(buffer[position]);
     LOG("Beginnng of processData length " << length );
    std::memcpy(internalPre_, buffer + position, length + 1);

    position += length + 1;

    LOG("position " << position);
    LOG("length " << length);

    //2. data from internal pre to output buffer
    //retStatus 0 more data , others buffer completed.

    LOG("output data address " << reinterpret_cast<long> (outputBufferAddress_));
    std::memcpy(outputBufferAddress_ + 1, internalPre_, length + 1);

    uint8_t retStatus =  position >= size_ ? 1 : 0;
    * outputBufferAddress_  =  retStatus;
     LOG("End of processData");
}

int Executor::handleOutputInternal(uint8_t *output, size_t size)
{
     LOG("output data address " << reinterpret_cast<long> (output));

     int ret = static_cast<int>(*output);
     int length = static_cast<int>(*(output +1));

     LOG("ret " << ret);
     LOG("length " << length);

     if (length != 127) {
        throw std::runtime_error("length is not 127!");
     }

#ifdef DEBUG

     DataHelper::printDataBuffer((output + 2), length);
#endif

     LOG ("After printDataBuffer");
     return ret;
}

int Executor::handleOutput()
{
    return handleOutputInternal(outputBufferAddress_, outputSize_);
}
