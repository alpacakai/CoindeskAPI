package com.api.coindesk.entity;

import java.util.Map;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Coindesk {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, Bpi> bpi;

	@Data
	public static class Time {
		private String updated;
		private String updatedISO;
		private String updateduk;
	}

	@Data
	public static class Bpi {
		private String code;
		private String symbol;
		private String rate;
		private String description;
        private Double rate_float;
	}
}
