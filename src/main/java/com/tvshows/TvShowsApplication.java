package com.tvshows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TvShowsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TvShowsApplication.class, args);
        String mongoUri = context.getEnvironment().getProperty("spring.data.mongodb.uri");
    }
}
