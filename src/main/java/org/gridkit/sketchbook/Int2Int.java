package org.gridkit.sketchbook;

import java.util.Arrays;

/**
 * Simple array backed class to map low scale integers.
 * @author Alexey Ragozin (alexey.ragozin@gmail.com)
 */
class Int2Int {
	
	public static final int NOT_SET = Integer.MIN_VALUE;
	
	private int[] map;
	
	public void set(int index, int value) {
		if (map == null) {
			map = new int[index + 1];
			Arrays.fill(map, -1);
		}
		else if (map.length <= index) {
			int n = map.length;
			map = Arrays.copyOf(map, index + 1);
			Arrays.fill(map, n, map.length, NOT_SET);
		}
		map[index] = value;
	}
	
	public int get(int index) {
		return map == null || index >= map.length ? NOT_SET : map[index];
	}
	
	public int size() {
		return map == null ? 0 : map.length;
	}
		
	public String toString() {
		return Arrays.toString(map);
	}
	
}
