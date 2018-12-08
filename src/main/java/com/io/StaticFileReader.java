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
public class StaticFileReader {
    private Map<String, String> map;

    public StaticFileReader() {
        loadCfgFile();
    }

    public String get(String key){
        return map.get(key);
    }

    private void loadCfgFile() {
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

    private File getCfgFilePath(){
        return new File(StaticFileReader.class.getClassLoader().getResource("cfg.properties").getPath());
    }


}
