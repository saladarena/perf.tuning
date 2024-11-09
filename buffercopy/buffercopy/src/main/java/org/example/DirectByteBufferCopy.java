package org.example;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;
import java.lang.reflect.Field;

import static java.lang.Math.round;

class DirectByteBufferCopyHelper {

    private static Unsafe unsafe;


    static {
        try {
            // Accessing Unsafe instance via reflection
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);

        } catch (Exception e) {
            throw new RuntimeException("Failed to access Unsafe or ByteBuffer fields", e);
        }
    }



    public static void copyMemory(long srdAddr, long destAddr, long lengthToCopy) {
        unsafe.copyMemory(srdAddr, destAddr, lengthToCopy);
    }
}

public class DirectByteBufferCopy {

    private static final int size = 64;
    private static final int  number = 1024 * 256 ;
    private static final ByteBuffer [] srcBuffer = new ByteBuffer[number];
    private static final ByteBuffer [] dstBuffer = new ByteBuffer[number];

    private static final long [] src = new long [number];
    private static final long [] dst = new long [number];
    private static final int []  indices = new int[number];


    private static void prepareMemPieces() {
        for (int i =0 ; i < number; i++) {
            srcBuffer[i] = ByteBuffer.allocateDirect(size);
            dstBuffer[i] = ByteBuffer.allocateDirect(size);
            src[i] = MemoryUtils.getByteBufferAddress(srcBuffer[i] );
            dst[i] = MemoryUtils.getByteBufferAddress(dstBuffer[i]);
            indices[i] = (int) round (Math.random() * number);
            if (indices[i] >= number) {
                System.out.println("Unbelievable i : "+ i + "random number is too large " + indices[i]);
                indices[i] = number -1;
            }

            for (int j = 0; j < size; j++) {
                srcBuffer[i].put(j, (byte) (j %256));
                dstBuffer[i].put(j, (byte)0);
            }

        }

    }

    private static void copyMemory() {
        for (int i =0 ; i < number ; i++) {
            DirectByteBufferCopyHelper.copyMemory(src[indices[i]], dst[indices[i]], size);
        }
    }

    private static void verifyDestBuffer () {
        for (int i =0 ; i < number; i++) {
            for (int j = 0; j < size; j++) {
                byte value =  dstBuffer[indices[i]].get(j);
                if (value == (byte) (j %256)) {
                    // nothing to do
                } else {
                    System.out.println( " buffer " + i + " location " + j + " is not correct " + " src  is "
                            +  (byte) (j %256)  + " but actual is " + value );
                }
            }

        }
    }


    public static void main(String[] args) {

        prepareMemPieces();
        long start = System.nanoTime();
        copyMemory();
        long end = System.nanoTime();
        System.out.println("Copy " + number + " mem segment with size " + size + " takes " + ((end-start)/(1000*1000))  + " ms!" );

        verifyDestBuffer();

    }

}
