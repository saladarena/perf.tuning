package org.example;

import java.nio.ByteBuffer;

public class AbstractActions {

    public static final int OUTPUT_BUFFER_SIZE = 1024;
    protected ByteBuffer outputBuffer = ByteBuffer.allocateDirect(OUTPUT_BUFFER_SIZE);
    protected final long  outputAddress = UnSafeUtils.getByteBufferAddress(outputBuffer);

    public static final int NUMBER_BUFFER = 100;

    public static final int DATA_BLOCK_SIZE = 128;
    public static final int BUFFER_SIZE = DATA_BLOCK_SIZE * 256;


    //data mem layout
    // 1 byte status
    // 1 byte length of result
    protected long [] bufferAddresses;  // address of buffers of RS partition
    protected ByteBuffer[] dataBuffers; // buffers of RS partition

    public AbstractActions() {
        this.generateDataBuffer();
    }

    public void generateDataBuffer() {

        this.dataBuffers= new ByteBuffer[NUMBER_BUFFER];
        this.bufferAddresses = new long[NUMBER_BUFFER];

        for (int i =0; i < NUMBER_BUFFER; i++) {
            dataBuffers[i] = ByteBuffer.allocateDirect(BUFFER_SIZE);
            fillBuffer(dataBuffers[i]);
            bufferAddresses[i] = UnSafeUtils.getByteBufferAddress(dataBuffers[i]);
        }

    }

    public void fillBuffer(ByteBuffer buffer) {
        buffer.clear();
        int dataLength = DATA_BLOCK_SIZE - 1 ;

        for (int index = 0; index < (BUFFER_SIZE/(DATA_BLOCK_SIZE )); index ++){  //  BUFFER_SIZE must be multiply of datalength + 1
            int pos = index * 128;
            buffer.put((byte) dataLength);
            for (int value = 0; value < dataLength; value ++) {
                pos++;
                buffer.put((byte) ((value + index) %128));
            }

        }
    }

    // return 0 for more available
    // return 1 for buffer consumed
    // otherwise throw exception
    public int handleOutput(int datacount) {
        int ret = outputBuffer.get();
        //System.out.println("ret is" + ret);

        int value = outputBuffer.get();
        if (value != DATA_BLOCK_SIZE -1) {
            throw new RuntimeException("length is not match!" + value + " index " + datacount) ;
        }

        int firstValue = outputBuffer.get();

        //showMoreValue(outputBuffer, 10);
        if (firstValue != datacount %128 ) {
            throw new RuntimeException(" first value  is not match! " + firstValue + " index " + datacount);
        }
        //showMoreValue(outputBuffer, 10);

        outputBuffer.clear();
        return  ret;

    }

    public static  void showMoreValue(ByteBuffer buffer, int length) {
        for (int i = 0; i < length; i++) {
            int value = buffer.get();
            System.out.print( " " + value + " ");
        }
        System.out.println();
    }
}
