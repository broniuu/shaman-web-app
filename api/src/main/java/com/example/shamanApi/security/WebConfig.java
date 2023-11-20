package com.example.shamanApi.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true);;
        registry.addMapping("/restaurants/*/dishes").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true);;
        registry.addMapping("*/user").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true);;
        registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
        registry.addMapping("/login").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
        registry.addMapping("/login").allowedOrigins("http://localhost:4200/login").allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // or your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // Allow credentials

    }

    public class SimpleCORSFilter implements Filter {

        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, authorization, Access-Control-Allow-Origin");

            chain.doFilter(req, res);
        }

        public void init(FilterConfig filterConfig) {}

        public void destroy() {}
        @Bean
        public FilterRegistrationBean<SimpleCORSFilter> corsFilterRegistration() {
            FilterRegistrationBean<SimpleCORSFilter> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new SimpleCORSFilter());
            registrationBean.addUrlPatterns("/login"); // Specify the path pattern
            return registrationBean;
        }
    }

}
