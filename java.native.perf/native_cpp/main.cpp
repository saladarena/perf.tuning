#include <iostream>
#include "Executor.h"
#include "DataHelper.h"
#include "common.h"

int main() {

    typedef  ByteBuffer*  BufferPtr;

    const int NUMBER_BUFFER = 10;
    const int BUFFER_SIZE = 32768;

    BufferPtr * buffers = new  BufferPtr [NUMBER_BUFFER];

    for (int i =0; i < NUMBER_BUFFER; i++) {
        buffers[i] = DataHelper::createBuffer(BUFFER_SIZE);
        DataHelper::fillBuffer(buffers[i]);
    }

    LOG("after Buffer created");

    const int OUTPUT_BUFFER_SIZE = 256;
    uint8_t * outputData = new uint8_t[OUTPUT_BUFFER_SIZE];

    Executor executor(outputData, OUTPUT_BUFFER_SIZE);

    LOG("output data address " << reinterpret_cast<long> (outputData));

    for (int i =0; i < NUMBER_BUFFER; i++) {

        LOG( "Process buffer "  << i );

        executor.setBuffer(buffers[i]->data, buffers[i]->size);

        LOG( "After set Process buffer "  << i );

        int ret = 0;
        int dataIndex = 0;
        do {

            LOG( "buffer "  << i  << " , dataIndex << " << dataIndex);

            executor.processData();
            ret = executor.handleOutput();

            LOG( "buffer "  << i  << " , dataIndex << " << dataIndex  << " ret is " << ret) ;

            dataIndex++;

        } while (ret == 0);

    };

    
}



