package com.jqcx.service;

import java.util.List;
import java.util.Map;

/**
 * @Title: Test01Service  接口  测试用
 * @author: liuH
 * @create: 2021-09-21 13:29
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:
 */
public interface Test01Service {
    public Map<String,String> test01();

    public List<Map<String,String>> test02(Map<String,String> params);
}
