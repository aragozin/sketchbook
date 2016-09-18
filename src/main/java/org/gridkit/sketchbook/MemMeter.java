package org.gridkit.sketchbook;

import java.lang.management.ManagementFactory;

public class MemMeter {

    private static long OFFSET = measure(new Runnable() {
        @Override
        public void run() {
        }
    });
    
    /**
     * @return amount of memory allocated while executing provided {@link Runnable}
     */
    public static long measure(Runnable x) {
       long now = getCurrentThreadAllocatedBytes();
       x.run();
       long diff = getCurrentThreadAllocatedBytes() - now;
       return diff - OFFSET;
    }
    
    @SuppressWarnings("restriction")
    private static long getCurrentThreadAllocatedBytes() {
        return ((com.sun.management.ThreadMXBean)ManagementFactory.getThreadMXBean()).getThreadAllocatedBytes(Thread.currentThread().getId());
    }
}
