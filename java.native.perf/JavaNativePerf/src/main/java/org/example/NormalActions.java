package org.example;

import java.nio.ByteBuffer;

public class NormalActions extends AbstractActions {

    private Executor executor;

    public NormalActions() {
        super();
    }

    public int doActions() {
        //System.out.println("Start Actions");
        int DataCount = 0;

        executor = createExecutor(outputBuffer);
        for (int i = 0 ; i < NUMBER_BUFFER; i ++) {
            executor.setBuffer(dataBuffers[i]);
            int BufferCount = 0;
            int ret = 0;
            do {
                executor.processData();
                ret = handleOutput(BufferCount);
                BufferCount ++;
            } while (ret == 0);
            DataCount += BufferCount;
        }
        return DataCount;
    }

    public Executor createExecutor(ByteBuffer outputBuffer) {
        return  new Executor(outputBuffer);
    }


    public static class Executor {
        private ByteBuffer outputBuffer;
        private long outputBufferAddress;

        private ByteBuffer currentBuffer;
        private long currentBufferAddress;
        private int currentBufferPos;
        private long currentBufferSize;

        private ByteBuffer internalBufferPre;
        private final long internalBufferPreAddress;
        public final static int INTERNAL_BUFFER_SIZE = 1024;




        public Executor(ByteBuffer outputBuffer) {
            this.outputBuffer = outputBuffer;
            this.outputBufferAddress = UnSafeUtils.getByteBufferAddress(outputBuffer);


            this.internalBufferPre = ByteBuffer.allocateDirect(INTERNAL_BUFFER_SIZE);
            this.internalBufferPreAddress = UnSafeUtils.getByteBufferAddress(internalBufferPre);


        }

        public void setBuffer(ByteBuffer buffer) {
            // suppose buffer is clear() now
            this.currentBuffer = buffer;
            this.currentBufferAddress = UnSafeUtils.getByteBufferAddress(currentBuffer);
            this.currentBufferPos = 0;
            this.currentBufferSize = buffer.limit();
            //debug
            System.out.println("new buffer is " + this.currentBufferSize);
        }

        public void  processData() {

            System.out.println("Beginnng of processData position " + this.currentBufferPos );
            this.currentBuffer.position(this.currentBufferPos);
            int length = this.currentBuffer.get();
            System.out.println("Beginnng of processData length " +  length );
            UnSafeUtils.copyMemory(this.currentBufferAddress + this.currentBufferPos, internalBufferPreAddress, length + 1);

            this.currentBufferPos += length + 1;

            System.out.println ("next position " + this.currentBufferPos);

            //2. data from internal pre to output buffer
            //retStatus 0 more data , others buffer completed.

            System.out.println("copy to output data address " );
            UnSafeUtils.copyMemory(this.internalBufferPreAddress, this.outputBufferAddress + 1,  length + 1);

            int  retStatus =  this.currentBufferPos  >= this.currentBufferSize ? 1 : 0;
            outputBuffer.position(0);
            outputBuffer.put((byte) retStatus);
            outputBuffer.clear();

            System.out.println("End of processData");
        }

    }
}
