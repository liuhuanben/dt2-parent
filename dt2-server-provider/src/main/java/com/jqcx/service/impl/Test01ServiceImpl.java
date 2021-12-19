package com.jqcx.service.impl;


import com.jqcx.service.Test01Service;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.*;

/**
 * @Title: Test01ServiceImpl
 * @author: liuH
 * @create: 2021-09-21 14:39
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:
 */
//group = "lwb"  @DubboService 指定froup值时,相应的 @DubboReference也要指定group的值并相同
    // nacos中会命名该类 的dataId 时会 把group的值写入dataId中,@DubboReference去远程调用就找不到
@DubboService(group = "lwb")
public class Test01ServiceImpl implements Test01Service {
    public Map<String, String> test01() {
        Map<String, String> res = new HashMap<String, String>(0);
        res.put("key1","人生如梦");
        res.put("key2","北雁云依");
        return res;
    }

    public List<Map<String, String>> test02(Map<String, String> params) {
        List<Map<String, String>> returnList = new ArrayList<Map<String, String>>(0);
        if(params == null){
            returnList.add(new HashMap<String, String>(){{put("key","参数为空呀");}});
        }else{
            returnList.add(new HashMap<String, String>(){{put("key","收到参数");}});
            returnList.add(params);
        }
        return returnList;
    }
}
