package com.ssm.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-23  12:31:55
 */
@Data
public class Msg {
    private String status;
    private String msg;
    private Map<String,Object> map = new HashMap<>();

    public Msg success(){
        this.status="100";
        return this;
    }

    public Msg fail(){
        this.status="200";
        return this;
    }

    public Msg add(String key, Object value){
        this.map.put(key, value);
        return this;
    }
}
