package org.gridkit.sketchbook;

import java.lang.management.ManagementFactory;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Formatter;

import org.apache.xmlgraphics.util.DoubleFormatUtil;
import org.junit.Test;

public class FormatTest {

    @Test
    public void testDecimalFormat() {
        double pi = Math.PI;
        System.out.println("\njava.text.DecimalFormat");
        
        DecimalFormat fmt = new DecimalFormat(".##########");
        fmt.setRoundingMode(RoundingMode.UP);

        // warm up
        fmt.format(pi);
        
        long mme = -(getCurrentThreadAllocatedBytes() - getCurrentThreadAllocatedBytes());
        long memu1 = getCurrentThreadAllocatedBytes();
        
        String val = fmt.format(pi);
       
        long memu2 = getCurrentThreadAllocatedBytes();
        
        System.out.println(val);
        System.out.println("Format + string mem usage: " + (memu2 - memu1 - mme));
        
    }

    @Test
    public void testFormatter() {
        System.out.println("\njava.util.Formatter");
        double pi = Math.PI;
        
        StringBuilder sb = new StringBuilder(64);
        Formatter ftm = new Formatter(sb);

        // warm up
        ftm.format("%.10f", pi);
        sb.setLength(0);
        
        // memory measurment error
        long mme = -(getCurrentThreadAllocatedBytes() - getCurrentThreadAllocatedBytes());
        
        long memu0 = getCurrentThreadAllocatedBytes();
        
        ftm.format("%.10f", pi);
        
        long memu1 = getCurrentThreadAllocatedBytes();
        
        String val = sb.toString();
       
        long memu2 = getCurrentThreadAllocatedBytes();
        
        System.out.println(val);
        System.out.println("Format mem usage: " + (memu1 - memu0 - mme));
        System.out.println("Format + string mem usage: " + (memu2 - memu0 - 2 * mme));
        
    }

    @Test
    public void testStringFormat() {
        System.out.println("\njava.lang.String.format()");
        
        double pi = Math.PI;
        
        // warmup
        String.format("%.10f", pi);

        // memory measurment error
        long mme = -(getCurrentThreadAllocatedBytes() - getCurrentThreadAllocatedBytes());
        
        long memu1 = getCurrentThreadAllocatedBytes();
        
        String val = String.format("%.10f", pi);
        
        long memu2 = getCurrentThreadAllocatedBytes();
        
        System.out.println(val);
        System.out.println("Format + string mem usage: " + (memu2 - memu1 - mme));
        
    }

    @Test
    public void testDoubleFormatUtil() {
        System.out.println("\norg.apache.xmlgraphics.util.DoubleFormatUtil.formatDouble()");
        
        double pi = Math.PI;
        
        StringBuffer sb = new StringBuffer(64);

        // warmup
        DoubleFormatUtil.formatDouble(pi, 10, 10, sb);
        sb.setLength(0);
                
        // memory measurment error
        long mme = -(getCurrentThreadAllocatedBytes() - getCurrentThreadAllocatedBytes());

        long memu0 = getCurrentThreadAllocatedBytes();
        
        DoubleFormatUtil.formatDouble(pi, 10, 10, sb); 

        long memu1 = getCurrentThreadAllocatedBytes();
        
        String val = sb.toString();
        
        long memu2 = getCurrentThreadAllocatedBytes();
        
        System.out.println(val);
        System.out.println("Format mem usage: " + (memu1 - memu0 - mme));
        System.out.println("Format + string mem usage: " + (memu2 - memu0 - 2 * mme));
        
    }

    @SuppressWarnings("restriction")
    private long getCurrentThreadAllocatedBytes() {
        return ((com.sun.management.ThreadMXBean)ManagementFactory.getThreadMXBean()).getThreadAllocatedBytes(Thread.currentThread().getId());
    }
}
