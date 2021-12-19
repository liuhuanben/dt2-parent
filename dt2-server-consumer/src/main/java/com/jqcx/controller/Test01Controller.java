package com.jqcx.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jqcx.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqcx.service.Test01Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: Test01Controller
 * @author: liuH
 * @create: 2021-09-21 14:49
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class Test01Controller {
    //@DubboReference(group = "my",registry = {"consumer1"})
    @DubboReference(group = "lwb")
    private Test01Service  test01Service;

    @RequestMapping("/my")
    public R testResult(){
        Object res = test01Service.test01();
        return R.success(res);
    }
    @RequestMapping("/my2")
    public R testResult2(){
        Map<String,String> params = new HashMap<String,String>(0);
        params.put("k1","test");
        Object res = test01Service.test02(params);
        return R.success(res);
    }

    @RequestMapping("/login")
    public R testResult3(){
        log.error("测试登陆 .....error级别");
        boolean isLogin = StpUtil.isLogin();
        return R.success(isLogin);
    }

}
