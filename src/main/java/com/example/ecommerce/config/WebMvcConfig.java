package com.example.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Sesuaikan dengan URL frontend Anda
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/uploads/**")
                .addResourceLocations("classpath:/static/", "file:uploads/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // Konfigurasi view resolver jika diperlukan
        // Contoh: registry.jspViewResolver(...);
    }

    // Jika Anda memerlukan konfigurasi lain, seperti format tanggal atau handler exception, tambahkan di sini
}