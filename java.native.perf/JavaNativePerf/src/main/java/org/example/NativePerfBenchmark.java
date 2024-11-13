package org.example;

public class NativePerfBenchmark {
    static {
        System.loadLibrary("native_test");
    }

    private final NativeActions nativeActions;
    private final NormalActions normalActions;

    public NativePerfBenchmark() {
        this.nativeActions = new NativeActions();
        this.normalActions = new NormalActions();
    }

    public void doNativeBenchmark() {
        nativeActions.doAction();
    }
    public void doNormalActions() {
        normalActions.doActions();
    }

    public static void main(String[] args) {
        NativePerfBenchmark nativePerfBenchmark = new NativePerfBenchmark();
        nativePerfBenchmark.doNativeBenchmark();
    }
}