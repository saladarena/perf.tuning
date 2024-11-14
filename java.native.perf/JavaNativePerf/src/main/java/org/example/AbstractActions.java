package org.example;

import java.nio.ByteBuffer;

public class AbstractActions {

    public static final int OUTPUT_BUFFER_SIZE = 1024;
    protected ByteBuffer outputBuffer = ByteBuffer.allocateDirect(OUTPUT_BUFFER_SIZE);
    protected final long  outputAddress = UnSafeUtils.getByteBufferAddress(outputBuffer);

    public static final int NUMBER_BUFFER = 16;
    public static final int BUFFER_SIZE = 32768;


    //data mem layout
    // 1 byte status
    // 1 byte length of result
    protected long [] bufferAddresses;  // address of buffers of RS partition
    protected ByteBuffer[] dataBuffers; // buffers of RS partition

    public AbstractActions() {

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
        int dataLength = 127;
        for (int index = 0; index < (BUFFER_SIZE/(dataLength +1)); index ++){  //  BUFFER_SIZE must be multiply of datalength + 1
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
    public int handleOutput() {
        int ret = outputBuffer.get();
        System.out.println("ret is" + ret);

        int value = outputBuffer.get();
        System.out.println("value size is " + value);

        //showMoreValue(outputBuffer);

        outputBuffer.clear();
        return  ret;

    }

    public static  void showMoreValue(ByteBuffer buffer) {
        for (int i = 0; i < 4; i++) {
            int value = buffer.get();
            System.out.print( " " + value + " ");
        }
        System.out.println();
    }
}
