package com.example.anonymouschat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
	CorsConfiguration corsConfiguration = new CorsConfiguration();
	//设置Http头信息

	//允许域配置
	corsConfiguration.addAllowedOrigin("*");
	//请求头
	corsConfiguration.addAllowedHeader("*");
	//请求方式
	corsConfiguration.addAllowedMethod("*");
	//创建source对象
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//添加映射路径
	source.registerCorsConfiguration("/**",corsConfiguration);
	return new CorsFilter(source);
    }
}
