package com.code.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PropertiesUtil
extends Properties {
    private static PropertiesUtil propertiesUtil = new PropertiesUtil();

    private PropertiesUtil() {
    }

    public static PropertiesUtil getDatabaseProperties() {
        try {
            propertiesUtil.load(PropertiesUtil.class.getResourceAsStream("/config/database.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesUtil;
    }

    public static PropertiesUtil getConfigProperties() {
        try {
            propertiesUtil.load(PropertiesUtil.getInputStreamReader("/config/config.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesUtil;
    }

    public static InputStreamReader getInputStreamReader(String path) {
        try {
            return new InputStreamReader(PropertiesUtil.class.getResourceAsStream(path), "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

