package com.farukkaraman.springbootblogwithquerydsl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                // İzin verilen origin'ler
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:5173", // Vite dev server
                                "http://localhost:3000", // Alternatif port
                                "http://127.0.0.1:5173",
                                "https://blog-frontend-fk.vercel.app", // Vercel deployment (production)
                                "https://blog-frontend.vercel.app", // Vercel deployment (eski)
                                "https://blog-frontend.netlify.app", // Netlify deployment
                                "https://*.vercel.app", // Tüm Vercel subdomains
                                "https://*.netlify.app" // Tüm Netlify subdomains
                ));

                // İzin verilen HTTP metodları
                configuration.setAllowedMethods(List.of(
                                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

                // İzin verilen header'lar
                configuration.setAllowedHeaders(List.of(
                                "Authorization",
                                "Content-Type",
                                "Accept",
                                "Origin",
                                "X-Requested-With"));

                // Exposed header'lar (frontend'in okuyabileceği response header'ları)
                configuration.setExposedHeaders(List.of(
                                "Authorization",
                                "Content-Disposition"));

                // Credentials (cookies, authorization headers) izni
                configuration.setAllowCredentials(true);

                // Preflight cache süresi (saniye)
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
