package org.gridkit.sketchbook;

import java.util.ArrayList;
import java.util.List;

public class TextTableParser {
    
    
    public static List<String[]> parseSimpleTable(CharSequence source, char colSeparator) {

        if (source == null || source.length() == 0) {
            throw new IllegalArgumentException("Source is empty");
        }
    
        
        List<String[]> result = new ArrayList<String[]>();
        List<String> line = new ArrayList<String>();
        boolean firstLine = true;
        int width = -1;

        StringBuilder sb = new StringBuilder();
        boolean started = false;
        int n = 0;
        int ln = 1;
        while(true) {            
            char ch = n >= source.length() ? '\n' : source.charAt(n++);
            if (ch == colSeparator) {
                if (!started) {
                    started = true;
                    continue;
                }
                else {
                    line.add(sb.toString());
                    sb.setLength(0);
                    continue;
                }
            }
            if (ch == '\n') {
                if (sb.toString().trim().length() != 0) {
                    throw new IllegalArgumentException("Line should end with '" + colSeparator + "' (line: " + ln + ")");
                }
                if (firstLine) {
                    if (line.size() == 0) {
                        throw new IllegalArgumentException("Source is empty");
                    }
                    else {
                        width = line.size();
                        firstLine = false;
                    }
                }
                if (line.size() == 0 && n >= source.length()) {
                    // end of table;
                    break;
                }
                if (width != line.size()) {
                    throw new IllegalArgumentException("Row width " + width  + " is expected (line: " + ln + ") - " + line);
                }
                result.add(line.toArray(new String[width]));
                line.clear();
                sb.setLength(0);
                ++ln;
                started = false;
                continue;
            }
            if (!started) {
                if (Character.isWhitespace(ch)) {
                    // ignore
                    continue;
                }
                else {
                    throw new IllegalArgumentException("Line should start with '" + colSeparator + "' (line: " + ln + ")");
                }
            }            
            else {
                sb.append(ch);
            }            
        }
        
        return result;
    }    
}