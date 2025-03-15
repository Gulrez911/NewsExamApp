package com.ctet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ctet.common.PropertyConfig;


//import com.ctet.common.PropertyConfig;

@Configuration
@PropertySource({ "classpath:mail.properties" })
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public PropertyConfig propertyConfig() {
		PropertyConfig propertyConfig = new PropertyConfig();
		propertyConfig.setCompanyName("MC");
		propertyConfig.setHostName(env.getProperty("host"));
		propertyConfig.setSmtpPort(env.getProperty("port"));
		return propertyConfig;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");

		registry.addResourceHandler("/scripts/**").addResourceLocations("/resources/scripts/");
		registry.addResourceHandler("/scripts_login/**").addResourceLocations("/resources/scripts_login/");
		registry.addResourceHandler("/eAssess/**").addResourceLocations("/resources/eAssess/");
		registry.addResourceHandler("/contact/**").addResourceLocations("/resources/contactForm/");
		registry.addResourceHandler("/my_resources/**").addResourceLocations("/resources/my_resources/");
		registry.addResourceHandler("/demo/**").addResourceLocations("/resources/demo/");
		registry.addResourceHandler("/user/**").addResourceLocations("/resources/user/");

	}
}
