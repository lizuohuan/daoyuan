package com.magic.daoyuan.business.redis.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Redis Config Info
 * Created by Eric Xie on 2017/8/15 0015.
 */
public class RedisConfig {


    /**
     * redis.properties Path
     */
    private static final String configPath = "redis.properties";

    /**
     * redis.properties Result Set
     */
    private static final Map<String,String> result = new HashMap<String, String>();

    static {
        init();
    }

    private RedisConfig(){}

    /**
     * Initializes the reading configuration files
     */
    private static void init() {
        try {
            Properties properties = new Properties();
            InputStream in = RedisConfig.class.getClassLoader().getResourceAsStream(configPath);
            properties.load(in);
            Enumeration keys = properties.keys();
            while (keys.hasMoreElements()){
                String key = keys.nextElement().toString();
                result.put(key,properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("初始化配置失败...");
        }
    }

    /**
     * Get the value from the result set
     * @param key key
     * @return
     */
    public static String getValue(String key){
        return result.get(key);
    }

    /**
     * Get the Nodes from the result set
     * @return
     */
    public static List<Map<String,String>> getNodes(){
        String ips = getValue("ips");
        if(null == ips || ips.trim().length() == 0){
            return null;
        }
        List<Map<String,String>> data = new ArrayList<Map<String, String>>();
        String[] nodesSplit = ips.split(",");
        for (int i = 0; i < nodesSplit.length; i++) {
            Map<String,String> ip = new HashMap<String, String>();
            String[] ports = nodesSplit[i].split(":");
            ip.put("ip",ports[0]);
            ip.put("port",ports[1]);
            data.add(ip);
        }
        return data.size() == 0 ? null : data;
    }





}
