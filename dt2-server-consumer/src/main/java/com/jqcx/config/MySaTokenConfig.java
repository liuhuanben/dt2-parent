package com.jqcx.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.jqcx.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: MySaTokenConfig
 * @author: liuH
 * @create: 2021-11-07 15:58
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description: 接口访问鉴权 配置  sa-token拦截器
 * 在SpringBoot2.0及Spring5.0中WebMvcConfigurerAdapter已被废弃 。官方推荐直接实现WebMvcConfigurer或者直接继承WebMvcConfigurationSupport，
 * 方式一实现WebMvcConfigurer接口（推荐使用），方式二继承WebMvcConfigurationSupport类
 *
 * 过滤器和拦截器二者都是AOP编程思想的提现，都能实现诸如权限检查、日志记录等。二者有一定的相似之处，不同的地方在于：
 * 过滤器（Filter）  拦截器（Interceptor）
 * 1Filter是servlet规范，只能用在Web程序中，而拦截器是Spring规范，可以用在Web程序中，也可以用在Application程序中。
 * 2Filter是servlet中定义的，依赖servlet容器。而拦截器在Spring中定义，依赖Spring容器。
 * 3拦截器是一个Spring组件，归Spring管理，配置在Spring的配置文件中，因此它可使用Spring的任何资源。比如Service、数据源等，通过IOC容器注入到拦截器即可，而Filter则不行。
 * 4Filter只在servlet前后起作用，而拦截器则能深入到方法前后，异常抛出前后。使用深度更大一些。
 *过滤器（Filter） 常用场景
 * 比如过拦截掉我们不需要的接口请求
 * 修改请求（request）和响应（response）内容
 * 完成CORS跨域请求等等
 *
 *拦截器（Interceptor） 主要就是 拦截请求  和 放行请求  拦截过程中可获取到spring容器中的所有bean对象 一般用于登陆判断  ,登陆验证,权限查询.等放行业务和 可查看业务
 */
@Configuration
public class MySaTokenConfig implements WebMvcConfigurer {
    /**
     * 上传文件根路径
     */
    @Value("${location.path.files}")
    private String filesPath;

    //免登录 不拦截 路由
    private static final List<String> excludePathPatterns= Arrays.asList(
            "/**/login","/api/**","/**/api/**","/**/test/**",
            "/pages/**","/file/**"  //静态资源和页面存放，非敏感文件存放 如 声明，约定等文件，安装软件，apk，模板等等文件 直接提供在线展示或者下载的路径
    );
    // 需要拦截登录的 路由
    private static final List<String> includePathPatterns= Arrays.asList("/**");

    /**
     * 过滤器   这个需要添加到过滤链中  sa-token 的自定义过滤器设置 , 先于下面拦截器 addInterceptors 方法
     * 过滤器 功能 可以修改 请求信息
     *
     * @return
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        //sa-token的拦截器  顺序号order被默认设置为 -100  多个过滤器时注意顺序 避免出现灵异事件
        return new SaServletFilter()
                // 指定 [拦截路由] 与 [放行路由]
                //.addInclude(includePathPatterns)
                .setIncludeList(includePathPatterns)
                //.addExclude("/favicon.ico")MyFilter dofilter.
                .setExcludeList(excludePathPatterns)
                .setAuth(r->{
                    StpUtil.checkLogin(); //检查是否登陆,没有登陆或者没有传token 则直接抛异常(NotLoginException extends RuntimeException ) 走下面 的.setError()方法
                })
                .setError(e->{
                    //此处接收的异常 是上面setAuth(r->{StpUtil.checkLogin();}) 抛出的NotLoginException异常,此处e可强转成为NotLoginException 对象
                    NotLoginException eventSaToken = (NotLoginException)e;
                    //sa-token 此处在controller 之前验证 此处return 直接返回给页面端, 字符串形式 前端需要按需要解析成json对象 获取数据
                    System.out.println("setError 执行 ");
                    return R.loginFail(eventSaToken.getType(),eventSaToken.getMessage(),eventSaToken.getLoginType());
                })
                // 前置函数：在每次认证函数之前执行 实际执行上， setBeforeAuth 与 setAuth 并无区别
                .setBeforeAuth(r -> {
                    System.out.println("setBeforeAuth 执行 ");
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("sa-server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以0
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff");
                });
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 自定义验证拦截器 new SaRouteInterceptor() 默认为 检查是否登陆StpUtil.checkLogin(); 此处我们重写过滤逻辑 计划用于菜单权限吧
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            //原来的拦截器 默认为 检查是否登陆StpUtil.checkLogin();  此处我们不需要 因为上面有过滤器已经 判判断过是否登陆了
            System.out.println("Url:"+req.getUrl() +"--RequestPath:"+req.getRequestPath());
            //打印的内容-》 Url:http://localhost:6680/JQiCar/pages/index.html--RequestPath:/pages/index.html
		}));
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //表示文件路径，这里的意思是ppages包下的所有文件，所有/picture/开头的请求 都会去后面配置的路径下查找资源 支持: classpath:/xxx/xx/, file:/xxx/xx/, http://xxx/xx/
        //此处设置的 url  不走上面的过滤器 和拦截器 注意文件夹要自己手动确认和创建。。。。
        registry.addResourceHandler("/pages/**").addResourceLocations(ResourceUtils.FILE_URL_PREFIX+"D:/pages/");
        registry.addResourceHandler("/files/**").addResourceLocations(ResourceUtils.FILE_URL_PREFIX+filesPath);
    }
}
