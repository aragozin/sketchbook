package org.gridkit.sketchbook;

import org.junit.Test;

public class MemMeterCheck {

    @Test
    @SuppressWarnings("unused")
    public void check() {
        
        System.out.println("boolean[0] - " + MemMeter.measure(new Runnable() {
            
            boolean[] x;
            
            @Override
            public void run() {
                x = new boolean[0];
            }
        }));

        System.out.println("boolean[1] - " + MemMeter.measure(new Runnable() {
            
            boolean[] x;
            
            @Override
            public void run() {
                x = new boolean[1];
            }
        }));

        System.out.println("boolean[64] - " + MemMeter.measure(new Runnable() {
            
            boolean[] x;
            
            @Override
            public void run() {
                x = new boolean[64];
            }
        }));

        System.out.println("size of java.lang.Object is " + MemMeter.measure(new Runnable() {
            
            Object x;
            
            @Override
            public void run() {
                x = new Object();
            }
        }));

        new A();
        System.out.println("A - " + MemMeter.measure(new Runnable() {
            
            A x;
            
            @Override
            public void run() {
                x = new A();
            }
        }));
        
        new B();
        System.out.println("B - " + MemMeter.measure(new Runnable() {
            
            B x;
            
            @Override
            public void run() {
                x = new B();
            }
        }));
        
        System.out.println("nothing - " + MemMeter.measure(new Runnable() {
            
            @Override
            public void run() {
            }
        }));
        
    }
    
    public static class A {
        
        byte v0;
        //int v1;
        long v2;
        //byte v3;
        
    }
    
    public static class B extends A {
        
        byte v4; 
    }
}
