package com.jqcx.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: MySatokenConfig
 * @author: liuH
 * @create: 2021-11-07 13:27
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:  接口访问鉴权 配置  sa-token拦截器
 */
//@Configuration
public class MySatokenConfig implements WebMvcConfigurer {
    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *  注册sa-token的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义验证拦截器
        registry.addInterceptor(new SaRouteInterceptor((request,response,handler)->{
            // 登录验证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
            SaRouterUtil.match("/**", "/user/doLogin", () -> StpUtil.checkLogin());
            /*// 角色认证 -- 拦截以 admin 开头的路由，必须具备[admin]角色或者[super-admin]角色才可以通过认证
            SaRouterUtil.match("/admin/**", () -> StpUtil.checkRoleOr("admin", "super-admin"));

            // 权限认证 -- 不同模块, 校验不同权限
            SaRouterUtil.match("/user/**", () -> StpUtil.checkPermission("user"));
            SaRouterUtil.match("/admin/**", () -> StpUtil.checkPermission("admin"));
            SaRouterUtil.match("/goods/**", () -> StpUtil.checkPermission("goods"));
            SaRouterUtil.match("/orders/**", () -> StpUtil.checkPermission("orders"));
            SaRouterUtil.match("/notice/**", () -> StpUtil.checkPermission("notice"));
            SaRouterUtil.match("/comment/**", () -> StpUtil.checkPermission("comment"));
            // 匹配RESTful风格路由
            SaRouterUtil.match("/article/get/{id}", () -> StpUtil.checkPermission("article"));*/
        })).addPathPatterns("/**");
    }
}
