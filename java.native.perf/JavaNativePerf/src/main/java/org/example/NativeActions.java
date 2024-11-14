package org.example;

import java.nio.ByteBuffer;

public class NativeActions extends AbstractActions{
    private long executorRef;

    public NativeActions() {
         super();
    }

    public int doAction(){
        //System.out.println("Start Actions");
        int DataCount = 0;
        executorRef = createNativeExecutor(outputAddress, OUTPUT_BUFFER_SIZE);

        for (int i = 0 ; i < NUMBER_BUFFER; i++) {
            int BufferCount = 0;
            setBuffer(executorRef, bufferAddresses[i], BUFFER_SIZE);
            int ret = 0;
            do {
               processData(executorRef);
               ret = handleOutput(BufferCount);
               BufferCount ++;
            } while (ret == 0);
            DataCount += BufferCount;
        }
        return DataCount;
    }

    public native long createNativeExecutor(long outputAddress, int size);

    public native void setBuffer(long executorRef, long bufferAddress, int size);

    public native void processData(long executorRef);


}
