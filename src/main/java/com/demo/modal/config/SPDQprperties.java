package com.demo.modal.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "shipingdaquan")

@Accessors(chain = true)
public class SPDQprperties {
    private String index;
    private String movie;

    private String series;
    private String comdy;

    private String program;


}