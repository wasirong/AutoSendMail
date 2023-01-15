package com.dhl.yxg.util;

import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class GetConfig {
    public String GetConfigByKey(String section, String key) throws IOException {

        Ini.Section sec = null;

        Wini ini = new Wini(new File("C:\\DHLTool\\Config\\AutoSendReceiveUserInfo.ini"));

        sec = ini.get(section);

        return sec.get(key);
    }
}
