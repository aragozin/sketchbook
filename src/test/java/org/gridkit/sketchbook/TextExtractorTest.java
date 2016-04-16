package org.gridkit.sketchbook;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class TextExtractorTest {

    @Rule
    public TestDataInline testData = new TestDataInline();

    /* TESTDATA
Few random
lines of text
     */
    @Test
    public void test() {
        String[] text = {"Few random", "lines of text"};
        Assert.assertArrayEquals(text, testData.textLines());
    }

    /* TESTDATA
Another test
another few
lines of text
     */
    @Test
    public void test2() {
        String[] text = {"Another test", "another few", "lines of text"};
        Assert.assertArrayEquals(text, testData.textLines());
    }

    @Test
    public void test_no_data() {
        Assert.assertNull(testData.textLines());
    }

}
