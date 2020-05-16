package com.example.bookmyshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.example.bookmyshow.repositories"})
@EntityScan(basePackages = {"com.example.bookmyshow"})
public class BookmyshowApplication {

	public static void main(String[] args)
	{
		System.out.println("Starting BookMyShow.. Please wait while we set up the service..");
		SpringApplication.run(BookmyshowApplication.class, args);
	}

	@Bean
	CommonsRequestLoggingFilter requestLoggingFilter() {

		CommonsRequestLoggingFilter filter = new RequestLogger();
		filter.setIncludeQueryString(true);
		filter.setIncludeHeaders(true);
		filter.setIncludePayload(false);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		return filter;
	}



	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE");
			}
		};
	}

}
