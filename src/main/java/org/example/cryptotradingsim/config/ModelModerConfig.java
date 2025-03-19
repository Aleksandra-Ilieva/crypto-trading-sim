package org.example.cryptotradingsim.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelModerConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
