package com.etekcity.cloud.common.configuration;


import java.util.List;

import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Gson配置，修改Gson默认优化null不可输出，配置后可输出null
 *
 * @author vik
 */
@Configuration
public class GsonHttpMessageConverterConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Gson
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        //自定义Gson，配置参数解析规则
        gsonHttpMessageConverter.setGson(new GsonBuilder().serializeNulls().create());
        converters.add(gsonHttpMessageConverter);

    }
}
