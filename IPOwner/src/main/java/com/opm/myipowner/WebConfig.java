package com.opm.myipowner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.opm.myipowner")
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter	 {
	
		@Value("${spring.mvc.view.prefix}")
		String PREFIX;
		@Value("${spring.mvc.view.suffix}")
		String SUFFIX;
		
		
	 	@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/");
	    }
	
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	        localeChangeInterceptor.setParamName("lang");
	        registry.addInterceptor(localeChangeInterceptor);
	    }
	    
		@Bean
	    public  ViewResolver viewResolver() {
			
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setViewClass( JstlView.class);
	        viewResolver.setPrefix(PREFIX);
	        viewResolver.setSuffix(SUFFIX);
	        
	        return viewResolver;
	    }
		
}