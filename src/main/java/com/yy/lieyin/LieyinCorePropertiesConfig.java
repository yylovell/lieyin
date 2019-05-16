package com.yy.lieyin;

import com.yy.lieyin.properties.LieyinCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LieyinCoreProperties.class)
public class LieyinCorePropertiesConfig {

}
