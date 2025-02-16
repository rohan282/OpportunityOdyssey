package com.springproject.opportunity_odyssey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR="photos";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(UPLOAD_DIR,registry);
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");
    }
}



//Relative Path: A path that is not complete and is relative to the current working directory. For example, "photos/image.jpg".
//Absolute Path: A complete path from the root directory of the file system to the file or directory. For example,
// "C:/Users/YourName/Project/photos/image.jpg" on Windows or "/home/username/project/photos/image.jpg" on Unix-like systems.
//Conversion Process:

//
//Purpose: This conversion ensures that the application always works with full, unambiguous file paths, regardless of the
// current working directory.