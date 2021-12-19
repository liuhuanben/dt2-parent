package com.jqcx.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jqcx.util.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: Test02Controller
 * @author: liuH
 * @create: 2021-11-07 16:37
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:
 */

@RestController
@RequestMapping("/test2")
@Slf4j
public class Test02Controller {
    @RequestMapping("/my")
    public R testResult(){
        log.info("aaa日志aaa");
        boolean isLogin = StpUtil.isLogin();
        return R.success(isLogin);
    }
}
