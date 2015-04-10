package com.motivity.tripcase.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car {

	private Map<String, List<String>> data = new HashMap<String, List<String>>();

	public Map<String, List<String>> getData() {
		return data;
	}

	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
}
