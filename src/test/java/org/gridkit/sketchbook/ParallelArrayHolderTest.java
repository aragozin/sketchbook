package org.gridkit.sketchbook;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

public class ParallelArrayHolderTest {
    
    @Test
    public void simple_very_ops() {
        TestMap map = new TestMap();
        
        map.put("A", "a", 1);
        map.put("C", "c", 3);
        map.put("B", "b", 2);
        
        assertThat(map.keySet()).containsExactly("A", "B", "C");

        assertThat(map.getVal1("A")).isEqualTo("a");
        assertThat(map.getVal1("B")).isEqualTo("b");
        assertThat(map.getVal1("C")).isEqualTo("c");

        assertThat(map.getVal2("A")).isEqualTo(1);
        assertThat(map.getVal2("B")).isEqualTo(2);
        assertThat(map.getVal2("C")).isEqualTo(3);
        
        map.remove("A");

        assertThat(map.getVal1("A")).isNull();;
        
        assertThat(map.keySet()).containsExactly("B", "C");

        map.remove("B");
        map.remove("C");
        map.remove("A");

        assertThat(map.keySet()).containsExactly();
    }

    @Test
    public void simple_ops() {
        TestMap map = new TestMap();
        
        map.put("A", "a", 1);
        map.put("D", "d", 4);
        map.put("E", "e", 5);
        map.put("B", "b", 2);
        map.put("C", "c", 3);
        map.put("F", "f", 6);
        map.put("G", "g", 7);
        
        assertThat(map.keySet()).containsExactly("A", "B", "C", "D", "E", "F", "G");
        
        map.remove("C");
        map.remove("D");

        assertThat(map.keySet()).containsExactly("A", "B", "E", "F", "G");
        
        assertThat(map.getVal1("A")).isEqualTo("a");
        assertThat(map.getVal1("B")).isEqualTo("b");
        assertThat(map.getVal1("E")).isEqualTo("e");
        assertThat(map.getVal1("F")).isEqualTo("f");
        assertThat(map.getVal1("G")).isEqualTo("g");

        assertThat(map.getVal2("A")).isEqualTo(1);
        assertThat(map.getVal2("B")).isEqualTo(2);
        assertThat(map.getVal2("E")).isEqualTo(5);
        assertThat(map.getVal2("F")).isEqualTo(6);
        assertThat(map.getVal2("G")).isEqualTo(7);
        
    }
    
    public static class TestMap extends ParallelArrayHolder {
     
        public TestMap() {
            cols = new Object[]{new String[4], new String[4], new int[4]};
        }

        private String[] keyCol() {
            return (String[]) cols[0];
        }

        private String[] val1Col() {
            return (String[]) cols[1];
        }

        private int[] val2Col() {
            return (int[]) cols[2];
        }
        
        public int size() {
            return super.size();
        }
        
        public String getVal1(String key) {
            int n = Arrays.binarySearch(keyCol(), 0, size(), key);
            if (n >= 0) {
                return val1Col()[n];
            }
            return null;
        }

        public int getVal2(String key) {
            int n = Arrays.binarySearch(keyCol(), 0, size(), key);
            if (n >= 0) {
                return val2Col()[n];
            }
            return -1;
        }

        public void put(String key, String val1, int val2) {
            int n = Arrays.binarySearch(keyCol(), 0, size(), key);
            if (n < 0) {
                n = ~n;
                insert(n);
            }
            keyCol()[n] = key;
            val1Col()[n] = val1;
            val2Col()[n] = val2;
        }
        
        public void remove(String key) {
            int n = Arrays.binarySearch(keyCol(), 0, size(), key);
            if (n >= 0) {
                delete(n);
            }            
        }
        
        public Collection<String> keySet() {
            return Arrays.asList(keyCol()).subList(0, size());
        }
    }
}
