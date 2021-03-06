package com.bwie.hm0422.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;

/**
 * <FileName> ConResource
 * <Author>   lqz
 * <Time>  2020/4/22 21:13
 * <Desc> 资源配置
 **/
@Configuration
public class ConResource extends WebMvcConfigurationSupport {
    @Value("${file.upload.path}")
    private String filePath ;
    @Value("${file.upload.relative}")
    private String relativePath;

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //放开静态资源文件
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //放开上传图片资源
        registry.addResourceHandler(relativePath).addResourceLocations("file:/"+filePath);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> list = new ArrayList<>();
        list.add("/user/**"); //用户列表下的所有请求

        list.add("/static/**/**"); //请求的地址

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(list);
    }
}
