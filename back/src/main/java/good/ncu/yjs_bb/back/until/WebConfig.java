package good.ncu.yjs_bb.back.until;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

//全局配置类--配置跨域请求
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

//    @Value("${spring.profiles.active}")
//    private String env;//当前激活的配置文件

    private TokenInterceptor tokenInterceptor;

    //构造方法
    public WebConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      /*addMapping：允许所以映射
      allowedHeaders：允许所以请求头
      allowedMethods:允许所以请求方式，get、post、put、delete
      allowedOrigins：允许所以域名访问
      allowCredentials：允许携带cookie参数*/
        registry.addMapping("/**").allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("Http://localhost:8080", "null")
                .allowCredentials(true).maxAge(3600);
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        /*
//            1.访问路径
//            2.请求与来源
//            3.请求方法
//            4.允许携带
//            5.响应最大时间
//         */
//        registry.addMapping("/**")
//                .allowedOrigins("Http://localhost:8080","null")
//                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }


    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }


    // 配置不token拦截的接口列表
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
//        excludePath.add("/user/register");  //登录
        excludePath.add("/userLogin");
        excludePath.add("/verifyCode");
        excludePath.add("/PDF/**");  //文件静态资源
        excludePath.add("/ZC/**");  //文件静态资源
        excludePath.add("/upload_file");
        excludePath.add("/upload_pdf");
//        excludePath.add("/assets/**");  //静态资源

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }


}
