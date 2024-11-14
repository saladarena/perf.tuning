package org.example;

public class NativePerfBenchmark {
    static {
        System.loadLibrary("jni_demo");
    }

    private final NativeActions nativeActions;
    private final NormalActions normalActions;

    public NativePerfBenchmark() {
        this.nativeActions = new NativeActions();
        this.normalActions = new NormalActions();
    }

    public void doNativeBenchmark() {
        int iteration = 1000;
        int totalCount = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int number = nativeActions.doAction();
            totalCount += number;
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("Native Benchmark total count " + totalCount);
        System.out.println(" Native Benchmark elapsed time in ms " + elapsed);
    }

    public void doNormalBenchmark() {
        int iteration = 1000;
        int totalCount = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int number = normalActions.doActions();
            totalCount += number;
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("Normal Benchmark total count " + totalCount);
        System.out.println(" Normal Benchmark elapsed time in ms " + elapsed);

    }

    public static void main(String[] args) {
        NativePerfBenchmark nativePerfBenchmark = new NativePerfBenchmark();
        nativePerfBenchmark.doNativeBenchmark();
        nativePerfBenchmark.doNormalBenchmark();
    }
}