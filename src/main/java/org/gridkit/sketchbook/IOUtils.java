package org.gridkit.sketchbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IOUtils {

    public static String toString(InputStream is) throws IOException {
        try {
            StringBuilder buf = new StringBuilder();
            Reader reader = new InputStreamReader(is);
            char[] swap = new char[1024];
            while(true) {
                int n = reader.read(swap);
                if (n < 0) {
                    break;
                }
                else {
                    buf.append(swap, 0, n);
                }
            }
            return buf.toString();
        }
        finally {
            try {
                is.close();
            }
            catch(Exception e) {
                // ignore
            }
        }
    }

    public static Collection<String> toLines(InputStream is) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            List<String> result = new ArrayList<String>();
            while(true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                result.add(line);
            }
            return result;
        }
        finally {
            try {
                is.close();
            }
            catch(Exception e) {
                // ignore
            }
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buf = new byte[1 << 12];
            while(true) {
                int n = in.read(buf);
                if(n >= 0) {
                    out.write(buf, 0, n);
                }
                else {
                    break;
                }
            }
        } finally {
            try {
                in.close();
            }
            catch(Exception e) {
                // ignore
            }
        }
    }   
}
