package org.example;

import java.nio.ByteBuffer;

public class NativeActions {

    public static final int OUTPUT_BUFFER_SIZE = 64;

    ByteBuffer outputBuffer = ByteBuffer.allocateDirect(OUTPUT_BUFFER_SIZE);

    private final long  outputAddress = UnSafeUtils.getByteBufferAddress(outputBuffer);

    // output buffer
    // 1 byte status
    // 1 byte length of result

    private long executorRef;

    public static final int NUMBER_BUFFER = 16;
    public static final int BUFFER_SIZE = 32768;

    private long [] bufferAddresses;
    private ByteBuffer[] dataBuffers;

    public NativeActions() {
        this.generateDataBuffer();
    }

    public void doAction(){
        System.out.println("Start Actions");

        //
        executorRef = createNativeExecutor(outputAddress);

       for (int i = 0 ; i < NUMBER_BUFFER; i ++) {
           setBuffer(executorRef, bufferAddresses[i]);
           int ret = 0;
           do {
               processData(executorRef);
               ret = handleOutput();
           } while (ret != 0);
       }
    }

    public void generateDataBuffer() {

        this.dataBuffers= new ByteBuffer[NUMBER_BUFFER];
        this.bufferAddresses = new long[NUMBER_BUFFER];

        for (int i =0; i < NUMBER_BUFFER; i++) {
            dataBuffers[i] = ByteBuffer.allocateDirect(BUFFER_SIZE);
            bufferAddresses[i] = UnSafeUtils.getByteBufferAddress(dataBuffers[i]);
        }

    }

    public native long createNativeExecutor(long outputAddress);

    public native void setBuffer(long executorRef, long bufferAddress);

    public native void processData(long executorRef);


    // return 0 for completed
    // return 1 for more
    // otherwise throw exception
    public int handleOutput() {


        int ret =  (int) outputBuffer.get();
        System.out.println("ret is " + ret);

        int value = (int) outputBuffer.get();
        System.out.println("value is " + value);

        outputBuffer.clear();
        return ret;
    };

}
