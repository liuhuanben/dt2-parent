package com.jqcx.service;

import java.util.List;
import java.util.Map;

public interface DriverTestService {

    public List<Map<String,Object>> queryList(Map<String,Object> params);

    public Map<String,Object> getById(String id);
}
