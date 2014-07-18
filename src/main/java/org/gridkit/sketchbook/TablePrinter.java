package org.gridkit.sketchbook;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for formating simple tables.
 *
 * @author Alexey Ragozin (alexey.ragozin@gmail.com)
 */
public class TablePrinter {

    public static String formatTableFromColumns(Object... collumns) {
        return formatTable(Arrays.asList(collumns), 30, true);
    }

    private static String formatTable(List<Object> content, int maxCell, boolean table) {
        int[] width = new int[arrayLen(content.get(0))];
        for(Object row: content) {
            for(int i = 0; i != width.length; ++i) {
                String cell = textOf(arrayAt(row,i));
                width[i] = Math.min(Math.max(width[i], cell.length()), maxCell);
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean header = table;
        for(Object row: content) {
            for(int i = 0; i != width.length; ++i) {
                String cell = textOf(arrayAt(row,i));
                if (cell.length() > width[i]) {
                    cell = cell.substring(0, width[i] - 3) + "...";
                }
                sb.append(cell);
                for(int s = 0; s != width[i] - cell.length(); ++s) {
                    sb.append(' ');
                }
                if (table) {
                    sb.append('|');
                }
            }
            if (table) {
                sb.setLength(sb.length() - 1);
            }
            sb.append('\n');
            if (header) {
                header = false;
                for(int n: width) {
                    for(int i = 0; i != n; ++i) {
                        sb.append('-');
                    }
                    sb.append('+');
                }
                sb.setLength(sb.length() - 1);
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    private static String textOf(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    private static int arrayLen(Object object) {
        return Array.getLength(object);
    }

    private static Object arrayAt(Object object, int index) {
        try {
            return Array.get(object, index);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

}
