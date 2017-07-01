package org.gridkit.sketchbook;

public class Pow2 {

    public static long powerOfTwo(long val) {
        long hb = Long.highestOneBit(val);
        return hb == val ? hb : 2 * hb;
    }
}
