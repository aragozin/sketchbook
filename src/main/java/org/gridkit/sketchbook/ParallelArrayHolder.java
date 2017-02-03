package org.gridkit.sketchbook;

import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class ParallelArrayHolder {
    
    protected Object[] cols;
    private int size;
    
    protected void insert(int slot) {
        if (slot < 0 || slot > size) {
            throw new IllegalArgumentException(slot + " is outside [0:" + size + "] range");
        }
        if (size >= capacity()) {
            resize(adjustSize(size + 1));
        }
        if (slot != size) {
            moveUp(slot, size, 1);
        }
        ++size;
    }

    protected int size() {
        return size;
    }
    
    protected void delete(int slot) {
        if (slot < 0 || slot >= size) {
            throw new IllegalArgumentException(slot + " is outside [0:" + size + ") range");
        }
        if (slot != size - 1) {
            moveDown(slot + 1, size, 1);
        }
        --size;
        zeroOut(size);
    }

    protected int capacity() {
        return Array.getLength(cols[0]);
    }
    
    protected void resize(int size) {
        for(int i = 0; i != cols.length; ++i) {
            Object col = cols[i];
            if (col instanceof boolean[]) {
                cols[i] = Arrays.copyOf((boolean[])col, size);
            }
            else if (col instanceof byte[]) {
                cols[i] = Arrays.copyOf((byte[])col, size);
            }
            else if (col instanceof short[]) {
                cols[i] = Arrays.copyOf((short[])col, size);
            }
            else if (col instanceof char[]) {
                cols[i] = Arrays.copyOf((char[])col, size);
            }
            else if (col instanceof int[]) {
                cols[i] = Arrays.copyOf((int[])col, size);
            }
            else if (col instanceof long[]) {
                cols[i] = Arrays.copyOf((long[])col, size);
            }
            else if (col instanceof float[]) {
                cols[i] = Arrays.copyOf((float[])col, size);
            }
            else if (col instanceof double[]) {
                cols[i] = Arrays.copyOf((double[])col, size);
            }
            else if (col instanceof Object[]) {
                cols[i] = Arrays.copyOf((Object[])col, size);
            }
            else {
                throw new IllegalArgumentException(col.getClass().getSimpleName() + " is not array type");
            }
        }
    }

    protected void moveUp(int from, int to, int n) {
        for(int c = 0; c != cols.length; ++c) {
            Object col = cols[c];
            if (col instanceof boolean[]) {
                boolean[] tc = (boolean[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof byte[]) {
                byte[] tc = (byte[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof short[]) {
                short[] tc = (short[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof char[]) {
                char[] tc = (char[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof int[]) {
                int[] tc = (int[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof long[]) {
                long[] tc = (long[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof float[]) {
                float[] tc = (float[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof double[]) {
                double[] tc = (double[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else if (col instanceof Object[]) {
                Object[] tc = (Object[]) col;
                for(int i = to - 1; i >= from; --i) {
                    tc[i + n] = tc[i];
                }
            }
            else {
                throw new IllegalArgumentException(col.getClass().getSimpleName() + " is not array type");
            }
        }
    }

    
    protected void moveDown(int from, int to, int n) {
        for(int c = 0; c != cols.length; ++c) {
            Object col = cols[c];
            if (col instanceof boolean[]) {
                boolean[] tc = (boolean[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof byte[]) {
                byte[] tc = (byte[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof short[]) {
                short[] tc = (short[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof char[]) {
                char[] tc = (char[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof int[]) {
                int[] tc = (int[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof long[]) {
                long[] tc = (long[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof float[]) {
                float[] tc = (float[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof double[]) {
                double[] tc = (double[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else if (col instanceof Object[]) {
                Object[] tc = (Object[]) col;
                for(int i = from; i < to; ++i) {
                    tc[i - n] = tc[i];
                }
            }
            else {
                throw new IllegalArgumentException(col.getClass().getSimpleName() + " is not array type");
            }
        }
    }
    
    protected void zeroOut(int row) {
        for(Object col: cols) {
            // only object references are zeroed
            if (col instanceof Object[]) {
                ((Object[])col)[row] = null;
            }
        }
    }
    
    protected void clean(Object array) {
        if (array instanceof Object[]) {
            Arrays.fill((Object[])array, null);
        }    
    }
    
    protected int adjustSize(int size) {
        // adjust to power of two
        int n = Integer.highestOneBit(size);
        return size > n ? n << 1 : n; 
    }
}
