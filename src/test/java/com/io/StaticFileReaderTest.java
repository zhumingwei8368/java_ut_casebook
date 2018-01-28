package com.io;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lenovo on 2018/1/28.
 */
public class StaticFileReaderTest {
    @Test
    public void test_get() throws Exception {
        StaticFileReader reader = new StaticFileReader();

        Assert.assertEquals("value1", reader.get("key1"));
        Assert.assertEquals("value2", reader.get("key2"));
        Assert.assertNull( reader.get("key3"));
    }

}