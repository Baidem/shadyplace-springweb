package com.shadyplace.springweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UploadImageConfig implements WebMvcConfigurer {

    final Environment environment; // Accés à application.properties
    private String location = "upload";

    private List<String> allowedFormat = new ArrayList<String>();

    public UploadImageConfig(Environment environment) {
        this.allowedFormat.add("image/jpeg");
        this.allowedFormat.add("image/png");
        this.environment = environment;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getAllowedFormat() {
        return allowedFormat;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        String location = environment.getProperty("upload_dir");
        registry.addResourceHandler("/upload/**").addResourceLocations(location);
    }

}
