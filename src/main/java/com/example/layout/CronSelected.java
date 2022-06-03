package com.example.layout;

import ch.qos.logback.classic.util.LogbackMDCAdapter;

import java.util.HashMap;
import java.util.Map;

public class CronSelected {

    private static Map<Integer, String> cronMapper = new HashMap<>();

    public CronSelected(){
        cronMapper.put(30, "0/30 * 0 ? * * *");
    }

    public strictfp String getCronMapper(Integer id){
        return cronMapper.get(id);
    }
}
