package com.eshop.eshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.beans.BeanProperty;

@Configuration
public class AppConfig {

    @BeanProperty
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
