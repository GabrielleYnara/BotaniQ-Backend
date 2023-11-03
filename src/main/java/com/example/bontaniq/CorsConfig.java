package com.example.bontaniq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) in the application.<br>
 * This configuration allows for access controls for cross-origin requests.
 */
@Configuration
public class CorsConfig {
    /**
     * Configures global CORS settings for the application.
     *
     * @return A WebMvcConfigurer with the specified CORS mappings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Adds mappings for CORS configuration.
             *
             * @param registry the CORS registry where configurations are applied.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Applies to all endpoints in the application.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specifies allowed HTTP methods.
                        .allowedHeaders("*") // Allows all headers.
                        .allowedOrigins("*") // Permits access from any origin.
                        .exposedHeaders("*"); // Exposes all headers to the client.
            }
        };
    }
}
