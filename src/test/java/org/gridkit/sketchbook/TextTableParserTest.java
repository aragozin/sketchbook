package org.gridkit.sketchbook;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TextTableParserTest {

    @Test
    public void test_simple_table() {
        
        String text = "" +
        "|A|B|C|\n" +
        "|1234|567|890|\n" +
        "|-|--|---|\n";
        
        List<String[]> result = TextTableParser.parseSimpleTable(text, '|');
        
        Assert.assertArrayEquals(new String[]{"A", "B", "C"},  result.get(0));
        Assert.assertArrayEquals(new String[]{"1234", "567", "890"},  result.get(1));
        Assert.assertArrayEquals(new String[]{"-", "--", "---"},  result.get(2));
        Assert.assertEquals(3,  result.size());    
    }
    
    @Test
    public void test_simple_table_no_last_line_end() {
        
        String text = "" +
                "|A|B|C|\n" +
                "|1234|567|890|\n" +
                "|-|--|---|";
        
        List<String[]> result = TextTableParser.parseSimpleTable(text, '|');
        
        Assert.assertArrayEquals(new String[]{"A", "B", "C"},  result.get(0));
        Assert.assertArrayEquals(new String[]{"1234", "567", "890"},  result.get(1));
        Assert.assertArrayEquals(new String[]{"-", "--", "---"},  result.get(2));
        Assert.assertEquals(3,  result.size());    
    }

    @Test
    public void test_simple_table_head_and_tailing_spaces() {
        
        String text = "" +
                "|A|B|C|  \n" +
                "  |1234|567|890|\n" +
                "|-|--|---| ";
        
        List<String[]> result = TextTableParser.parseSimpleTable(text, '|');
        
        Assert.assertArrayEquals(new String[]{"A", "B", "C"},  result.get(0));
        Assert.assertArrayEquals(new String[]{"1234", "567", "890"},  result.get(1));
        Assert.assertArrayEquals(new String[]{"-", "--", "---"},  result.get(2));
        Assert.assertEquals(3,  result.size());    
    }

    @Test
    public void test_simple_table_empty_cells() {
        
        String text = "" +
                "|A|B|C|  \n" +
                "  |1234|567|890|\n" +
                "|||| ";
        
        List<String[]> result = TextTableParser.parseSimpleTable(text, '|');
        
        Assert.assertArrayEquals(new String[]{"A", "B", "C"},  result.get(0));
        Assert.assertArrayEquals(new String[]{"1234", "567", "890"},  result.get(1));
        Assert.assertArrayEquals(new String[]{"", "", ""},  result.get(2));
        Assert.assertEquals(3,  result.size());    
    }

    @Test
    public void test_simple_table_column_missmacth() {
        
        String text = "" +
                "|A|B|C|  \n" +
                "  |1234|567|\n" +
                "|-|--|---| ";
        try {
            
            TextTableParser.parseSimpleTable(text, '|');
            Assert.fail();            
        }
        catch(IllegalArgumentException e) {
            Assert.assertEquals("Row width 3 is expected (line: 2) - [1234, 567]", e.getMessage());
        }
    }

    @Test
    public void test_simple_table_text_before_col_separator() {
        
        String text = "" +
                "|A|B|C|  \n" +
                "a |1234|567||\n" +
                "|-|--|---| ";
        try {
            
            TextTableParser.parseSimpleTable(text, '|');
            Assert.fail();            
        }
        catch(IllegalArgumentException e) {
            Assert.assertEquals("Line should start with '|' (line: 2)", e.getMessage());
        }
    }
    
    @Test
    public void test_simple_table_text_false_col_separator() {
        
        String text = "" +
                "|A|B|C|  \n" +
                "|1234|567||x\n" +
                "|-|--|---| ";
        try {
            
            TextTableParser.parseSimpleTable(text, '|');
            Assert.fail();            
        }
        catch(IllegalArgumentException e) {
            Assert.assertEquals("Line should end with '|' (line: 2)", e.getMessage());
        }
    }
    
}