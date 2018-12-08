package com.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lenovo on 2018/1/28.
 */
public class EtcFileReader {
    private Map<String, String> map;

    public EtcFileReader() {
        this(true);
    }

    public EtcFileReader(boolean autoLoad) {
        if (autoLoad) {
            loadCfgFile();
        }
    }

    public String get(String key){
        return map.get(key);
    }

    public void loadCfgFile() {
        map = new HashMap<String, String>();
        File file = getCfgFilePath();

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(file));
            for (Object key : prop.keySet()) {
                map.put((String)key, prop.getProperty((String)key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected File getCfgFilePath(){
        return new File(Utils.getEtcBaseDir(), "/etc/config.properties");
    }

}
