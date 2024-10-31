package com.medilabo.webclient.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig
{
	@Bean
	public HttpMessageConverters customConverters()
	{
		return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
	}

	@Bean
	public FeignErrorDecoder customErrorDecoder()
	{
		return new FeignErrorDecoder();
	}

	@Bean
	public BasicAuthRequestInterceptor mBasicAuthRequestInterceptor()

	{
		return new BasicAuthRequestInterceptor("user", "password");
	}
}
