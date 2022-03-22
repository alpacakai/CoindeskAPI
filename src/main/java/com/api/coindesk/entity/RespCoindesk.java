package com.api.coindesk.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RespCoindesk {
    private String update;
    private Map<String, Detail> details = new HashMap<>();
    
    @Data
    public static class Detail {
        private String en;
        private String zh;
        private Double rate;
    }
	
}
