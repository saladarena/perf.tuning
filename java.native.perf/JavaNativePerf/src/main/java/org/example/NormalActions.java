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
            int ret = 0;
            do {
                executor.processData();
                DataCount ++;
                ret = handleOutput();
            } while (ret != 0);
        }
        return DataCount;
    }

    public Executor createExecutor(ByteBuffer outputBuffer) {
        return  new Executor(outputBuffer);
    }


    public static class Executor {
        private ByteBuffer outputBuffer;

        public Executor(ByteBuffer outputBuffer) {
            this.outputBuffer = outputBuffer;
        }

        public void setBuffer(ByteBuffer buffer) {

        }

        public int processData() {
            return 0;
        }

    }
}
