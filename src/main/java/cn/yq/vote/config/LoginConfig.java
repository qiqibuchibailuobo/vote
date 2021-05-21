package cn.yq.vote.config;


import cn.yq.vote.config.accesslog.AccessLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * 
 * 
 * @Package: com.*.*.config 
 * @ClassName: LoginConfig 
 * @Description:拦截器配置
 * @author: zk
 * @date: 2019年9月19日 下午2:18:35
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    //设置排除路径，spring boot 2.*，注意排除掉静态资源的路径，不然静态资源无法访问
    private final String[] excludePath = {"/static"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new AdminInterceptor());
        registration.addPathPatterns("/**").excludePathPatterns(                         //添加不拦截路径
                                          "/rest/",//登录页面
                                            "/login/loginIn",//登录
                                            "/user/registerIn",//注册
                                            "/rest/register",//注册页面
//                                            "/user/userPaging",//用户分页
                                         "/**/*.html",            //html静态资源
                                         "/**/*.js",              //js静态资源
                                         "/**/*.css",             //css静态资源
                                         "/**/*.woff",
                                         "/**/*.ttf"
                                         );
        registry.addInterceptor(new AccessLogInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
//        registry.addInterceptor(new AdminInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludePath).excludePathPatterns("/rest/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/rest/").setViewName("login");
        registry.addViewController("/rest/index").setViewName("index");
        registry.addViewController("/rest/register").setViewName("register");
        registry.addViewController("/rest/votingManagement").setViewName("votingManagement");
        registry.addViewController("/rest/admin").setViewName("admin");
        registry.addViewController("/rest/adminUserControl").setViewName("adminUserControl");


    }
}