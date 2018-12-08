package com.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by lenovo on 2018/1/28.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Utils.class, EtcFileReader.class})
public class EtcFileReaderTest {
    @Test
    public void test_get() throws Exception {
        mockBaseDir();

        EtcFileReader reader = new EtcFileReader();

        Assert.assertEquals("value1", reader.get("key1"));
        Assert.assertEquals("value2", reader.get("key2"));
        Assert.assertNull( reader.get("key3"));
    }
    private void mockBaseDir() {
        PowerMockito.mockStatic(Utils.class);
        PowerMockito.when(Utils.getEtcBaseDir()).thenReturn(new File("."));
    }


    @Test
    public void test_get_loadcfg() throws Exception {
        EtcFileReader reader = PowerMockito.spy(new EtcFileReader(false));
        //if getCfgFilePath method is private, you must use like this
        //PowerMockito.when(reader,"getCfgFilePath").thenReturn(new File("./etc/config.properties"));
        //if getCfgFilePath method is protected, you can use like this
        PowerMockito.when(reader.getCfgFilePath()).thenReturn(new File("./etc/config.properties"));

        reader.loadCfgFile();

        Mockito.verify(reader).loadCfgFile();
        Assert.assertEquals("value1", reader.get("key1"));
        Assert.assertEquals("value2", reader.get("key2"));
        Assert.assertNull( reader.get("key3"));
    }
}