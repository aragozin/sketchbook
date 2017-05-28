package org.gridkit.sketchbook;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

public class TextTableTest {

    @Test
    public void test_sort() {
        TextTable tt = new TextTable();
        
        tt.addRow("A", "B", "C");
        tt.addRow("1", "10", "22");
        tt.addRow("2", "9", "23");
        tt.addRow("3", "8", "24");
        
        tt.sort(1, true, false, null);
        
        Assert.assertEquals(
                "A|B |C \n" + 
                "-+--+--\n" +
                "1|10|22\n" +
                "3|8 |24\n" +
                "2|9 |23\n", 
                tt.formatTextTable(10));

        tt.sort(1, true, true, null);

        Assert.assertEquals(
                "A|B |C \n" + 
                "-+--+--\n" +
                "2|9 |23\n" + 
                "3|8 |24\n" +
                "1|10|22\n" ,
                tt.formatTextTable(10));


        tt.sort(0, false, false, null);

        Assert.assertEquals(
                "1|10|22\n" + 
                "-+--+--\n" +
                "2|9 |23\n" +
                "3|8 |24\n" +
                "A|B |C \n", 
                tt.formatTextTable(10));
    }
    
    @Test
    public void test_csv_format() throws IOException {
        TextTable tt = new TextTable();
        
        tt.addRow("A", "B", "C");
        tt.addRow("1", "10", "abc");
        tt.addRow("2", "9", "a b\nc");
        tt.addRow("3", "8", "\"abc\"");
        
        Assert.assertEquals(
                "A,B,C\n" + 
                "1,10,abc\n" +
                "2,9,\"a b c\"\n" +
                "3,8,\"\"\"abc\"\"\"\n", 
                formatCsv(tt));
    }

	private String formatCsv(TextTable tt) throws IOException {
		StringWriter writer = new StringWriter();
		tt.formatCsv(writer);
		System.out.println(writer);
		return writer.toString();
	}        
}
